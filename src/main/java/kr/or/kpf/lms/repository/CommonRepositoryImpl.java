package kr.or.kpf.lms.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.common.code.vo.request.CommonCodeViewRequestVO;
import kr.or.kpf.lms.biz.common.page.vo.request.PageViewRequestVO;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static kr.or.kpf.lms.repository.entity.QCommonCodeMaster.commonCodeMaster;
import static kr.or.kpf.lms.repository.entity.QDocuments.documents;

/**
 * 공통 Repository 구현체
 */
@Repository
public class CommonRepositoryImpl extends CSRepositorySupport implements CommonRepository  {

    private final JPAQueryFactory jpaQueryFactory;
    public CommonRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

    @Override
    public List<CommonCodeMaster> findTopCommonCode(CommonCodeViewRequestVO requestObject) {
        return jpaQueryFactory.selectFrom(commonCodeMaster)
                .where(condition(requestObject.getCodeName(), commonCodeMaster.codeName::contains),
                        condition(requestObject.getUpIndividualCode(), commonCodeMaster.upIndividualCode::eq),
                        commonCodeMaster.codeDepth.eq(0))
                .orderBy(commonCodeMaster.code.asc(), commonCodeMaster.codeSort.asc())
                .offset(requestObject.getPageable().getOffset()) // 페이지 번호
                .limit(requestObject.getPageable().getPageSize()) // 페이지 사이즈
                .fetch();
    }
    @Override
    public <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject) {
        /** Entity 총 갯수 */
        Long count = getEntityCount(requestObject);
        /** Entity 리스트 */
        List<?> content = getEntityList(requestObject);

        ResponseSummary summary = ResponseSummary.builder()
                .count(content.size())
                .offset(requestObject.getPageNum())
                .limit(requestObject.getPageSize())
                .build();
        Pageable pageableToApply = summary.ensureValidPageable(requestObject.getPageable());

        return CSPageImpl.of(content, pageableToApply, count);
    }

    private <T extends CSViewVOSupport> Long getEntityCount(T requestObject) {
        if(requestObject instanceof PageViewRequestVO) { /** 페이지(문서) */
            return jpaQueryFactory.select(documents.count())
                    .from(documents)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else return NumberUtils.LONG_ZERO;
    }

    public <T extends CSViewVOSupport> List<?> getEntityList(T requestObject) {
        if(requestObject instanceof PageViewRequestVO) { /** 페이지(문서) */
            return jpaQueryFactory.selectFrom(documents)
                    .where(getQuery(requestObject))
                    .orderBy(documents.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else return new ArrayList<>();
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof PageViewRequestVO) { /** 페이지(문서) */
            return new Predicate[] { condition(((PageViewRequestVO) requestObject).getDocumentType(), documents.documentType::eq),
                    condition(((PageViewRequestVO) requestObject).getSequenceNo(), documents.sequenceNo::eq),
                    condition(Code.STATE.USE.enumCode, documents.documentStatus::eq) };
        } else {
            return null;
        }
    }
}
