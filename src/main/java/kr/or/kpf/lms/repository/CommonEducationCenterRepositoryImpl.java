package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.response.BizPbancRsltCustomApiResponseVO;
import kr.or.kpf.lms.biz.educenter.apply.vo.request.ApplyViewRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.request.PressViewRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.response.PressCustomApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.or.kpf.lms.repository.entity.QBizPbancMaster.bizPbancMaster;
import static kr.or.kpf.lms.repository.entity.QBizPbancResult.bizPbancResult;
import static kr.or.kpf.lms.repository.entity.QLmsUser.lmsUser;
import static kr.or.kpf.lms.repository.entity.QPressRelease.pressRelease;
import static kr.or.kpf.lms.repository.entity.QEduPlaceAply.eduPlaceAply;
import static org.springframework.util.StringUtils.hasText;

/**
 * 연수원 소개 공통 Repository 구현체
 */
@Repository
public class CommonEducationCenterRepositoryImpl extends CSRepositorySupport implements CommonEducationCenterRepository {

    @Autowired private PressReleaseRepository pressReleaseRepository;

    private final JPAQueryFactory jpaQueryFactory;
    public CommonEducationCenterRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
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

    /**
     * Entity 총 갯수
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> Long getEntityCount(T requestObject) {
        if(requestObject instanceof PressViewRequestVO) { /** 행사소개(보도자료) */
            return jpaQueryFactory.select(pressRelease.count())
                    .from(pressRelease)
                    .where(getQuery(requestObject))
                    .orderBy(pressRelease.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof ApplyViewRequestVO) { /** 교육장 사용 신청 */
            return jpaQueryFactory.select(eduPlaceAply.count())
                    .from(eduPlaceAply)
                    .where(getQuery(requestObject))
                    .orderBy(eduPlaceAply.createDateTime.desc())
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
    private <T extends CSViewVOSupport> List<?> getEntityList(T requestObject) {
        if(requestObject instanceof PressViewRequestVO) { /** 행사소개(보도자료) */

            List<PressCustomApiResponseVO> dtos = new ArrayList<>();
                dtos = jpaQueryFactory.select(Projections.fields(PressCustomApiResponseVO.class,
                                pressRelease.sequenceNo,
                                pressRelease.title,
                                pressRelease.contents,
                                pressRelease.atchFilePath,
                                pressRelease.atchFileSize,
                                pressRelease.atchFileOrigin,
                                pressRelease.viewCount,
                                pressRelease.createDateTime,
                                pressRelease.registUserId,
                                lmsUser.userName
                        ))
                        .from(pressRelease)
                        .leftJoin(lmsUser).on(lmsUser.userId.eq(pressRelease.registUserId))
                        .where(getQuery(requestObject))
                        .orderBy(pressRelease.createDateTime.desc(), pressRelease.sequenceNo.desc())
                        .offset(requestObject.getPageable().getOffset())
                        .limit(requestObject.getPageable().getPageSize())
                        .fetch()
                        .stream()
                        .peek(data -> data.setIsNew(new DateTime().minusDays(15).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                                .parseDateTime(data.getCreateDateTime())) < 0)).collect(Collectors.toList());

            return dtos;

        } else if(requestObject instanceof ApplyViewRequestVO) { /** 교육장 사용 신청  */
            return jpaQueryFactory.selectFrom(eduPlaceAply)
                    .where(getQuery(requestObject))
                    .orderBy(eduPlaceAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize()).fetch().stream()
                    .peek(data -> data.setIsNew(new DateTime().minusDays(15).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                            .parseDateTime(data.getCreateDateTime())) < 0)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof PressViewRequestVO) { /** 행사소개(보도자료) */
            return new Predicate[] { betweenTime(new PressRelease(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((PressViewRequestVO) requestObject).getSequenceNo(), pressRelease.sequenceNo::eq),
                    searchContainText(requestObject, ((PressViewRequestVO) requestObject).getContainTextType(), ((PressViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof ApplyViewRequestVO) { /** 행사소개(보도자료) */
            return new Predicate[] { betweenTime(new EduPlaceAply(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((ApplyViewRequestVO) requestObject).getSequenceNo(), eduPlaceAply.sequenceNo::eq),
                    searchContainText(requestObject, ((ApplyViewRequestVO) requestObject).getContainTextType(), ((ApplyViewRequestVO) requestObject).getContainText())};
        } else {
            return null;
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containTextType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof PressViewRequestVO) { /** 행사소개(보도자료) */
                if(containTextType.equals("1")) { /** 제목 + 내용 */
                    booleanBuilder.or(pressRelease.title.contains(containsText));
                    booleanBuilder.or(pressRelease.contents.contains(containsText));
                } else if(containTextType.equals("2")) { /** 제목 */
                    booleanBuilder.or(pressRelease.title.contains(containsText));
                } else if(containTextType.equals("3")) { /** 내용 */
                    booleanBuilder.or(pressRelease.contents.contains(containsText));
                }
            } else if(requestObject instanceof ApplyViewRequestVO) { /** 행사소개(보도자료) */
                if(containTextType.equals("1")) { /** 제목 + 내용 */
                    booleanBuilder.or(eduPlaceAply.title.contains(containsText));
                    booleanBuilder.or(eduPlaceAply.contents.contains(containsText));
                } else if(containTextType.equals("2")) { /** 제목 */
                    booleanBuilder.or(eduPlaceAply.title.contains(containsText));
                } else if(containTextType.equals("3")) { /** 내용 */
                    booleanBuilder.or(eduPlaceAply.contents.contains(containsText));
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if((!StringUtils.isEmpty(startDateTime) && StringUtils.isEmpty(endDateTime))
                || (StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime))) {
            throw new KPFException(KPF_RESULT.ERROR9001, "조회 시작 일자 & 조회 종료 일자는 한가지만 존재할 수 없습니다.");
        } else if(!StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime)) {
            if(value instanceof PressRelease) { /** 행사소개(보도자료) */
                return pressRelease.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof EduPlaceAply) { /** 행사소개(보도자료) */
                return eduPlaceAply.createDateTime.between(startDateTime, endDateTime);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String generateCode(String prefixCode) {
        return null;
    }
}