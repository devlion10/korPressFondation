package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.common.upload.vo.request.FileMasterViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.BizPbancMaster;
import kr.or.kpf.lms.repository.entity.FileMaster;
import kr.or.kpf.lms.repository.entity.QFileMaster;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static kr.or.kpf.lms.repository.entity.QFileMaster.fileMaster;
import static org.springframework.util.StringUtils.hasText;

/**
 * 파일 업로드 공통 Repository 구현체
 */
@Repository
public class CommonFileRepositoryImpl extends CSRepositorySupport implements CommonFileRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public CommonFileRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

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

    @Override
    public <T> Object findEntity(T requestObject) {
        return getEntity(requestObject);
    }

    @Override
    public <T> List<FileMaster> findEntityByAtchFileSn(T requestObject) {
        return jpaQueryFactory.selectFrom(fileMaster)
                .where(fileMaster.atchFileSn.eq(((FileMasterViewRequestVO) requestObject).getAtchFileSn()))
                .fetch();
    }

    /**
     * Entity 총 갯수
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> Long getEntityCount(T requestObject) {
        if(requestObject instanceof FileMasterViewRequestVO) { /** 사업 공고 */
            return jpaQueryFactory.select(fileMaster.count())
                    .from(fileMaster)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else {
            return NumberUtils.LONG_ZERO;
        }
    }

    /**
     * Entity 리스트
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T> Object getEntity(T requestObject) {
        if(requestObject instanceof FileMasterViewRequestVO) { /** 파일 업로드 */
            return jpaQueryFactory.selectFrom(fileMaster)
                    .where(fileMaster.fileSn.eq(((FileMasterViewRequestVO) requestObject).getFileSn()))
                    .fetchOne();
        }
        return null;
    }

    /**
     * Entity 리스트
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> List<?> getEntityList(T requestObject) {
        if(requestObject instanceof FileMasterViewRequestVO) { /** 파일 업로드 */
            return jpaQueryFactory.selectFrom(fileMaster)
                    .where(getQuery(requestObject))
                    .orderBy(fileMaster.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else {
            return null;
        }
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof FileMasterViewRequestVO) { /** 파일 업로드 */
            return new Predicate[] { betweenTime(new FileMaster(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((FileMasterViewRequestVO) requestObject).getFileSn(), fileMaster.fileSn::eq),
                    condition(((FileMasterViewRequestVO) requestObject).getAtchFileSn(), fileMaster.atchFileSn::eq),
                    searchContainText(requestObject, ((FileMasterViewRequestVO) requestObject).getContainTextType(), ((FileMasterViewRequestVO) requestObject).getContainText()) };
        }  else {
            return null;
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containTextType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof FileMasterViewRequestVO) { /** 파일 업로드 */
                if(containTextType.equals("1")) { /** 제목 + 내용 */
                    booleanBuilder.or(fileMaster.originalFileName.contains(containsText));
                    booleanBuilder.or(fileMaster.fileComments.contains(containsText));
                } else if(containTextType.equals("2")) { /** 제목 */
                    booleanBuilder.or(fileMaster.originalFileName.contains(containsText));
                } else if(containTextType.equals("3")) { /** 내용 */
                    booleanBuilder.or(fileMaster.fileComments.contains(containsText));
                }
            }
        }
        return booleanBuilder;
    }

    /** 시간 대 검색 */
    private <T extends CSEntitySupport> BooleanExpression betweenTime(T value, String startDate, String endDate) {
        String startDateTime = Optional.ofNullable(startDate)
                .map(date -> new StringBuilder(date).append(" 00:00:00").toString())
                .orElse("");
        String endDateTime = Optional.ofNullable(endDate)
                .map(date -> new StringBuilder(date).append(" 23:59:59").toString())
                .orElse("");

        if((!StringUtils.isEmpty(startDateTime) && StringUtils.isEmpty(endDateTime))
                || (StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime))) {
            throw new KPFException(KPF_RESULT.ERROR9001, "조회 시작 일자 & 조회 종료 일자는 한가지만 존재할 수 없습니다.");
        } else if(!StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime)) {
            if(value instanceof BizPbancMaster) { /** 파일 업로드 */
                return fileMaster.createDateTime.between(startDateTime, endDateTime);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @Override
    public String generateCode(String prefixCode) {
            return null;
    }

}
