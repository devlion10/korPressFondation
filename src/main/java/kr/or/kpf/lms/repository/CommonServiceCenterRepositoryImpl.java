package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.servicecenter.myqna.vo.request.MyQnaViewRequestVO;
import kr.or.kpf.lms.biz.servicecenter.notice.vo.request.NoticeViewRequestVO;
import kr.or.kpf.lms.biz.servicecenter.topqna.vo.request.TopQnaViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.Notice;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.or.kpf.lms.repository.entity.QAdminUser.adminUser;
import static kr.or.kpf.lms.repository.entity.QMyQna.myQna;
import static kr.or.kpf.lms.repository.entity.QNotice.notice;
import static kr.or.kpf.lms.repository.entity.QTopQna.topQna;
import static kr.or.kpf.lms.repository.entity.QFileMaster.fileMaster;
import static org.springframework.util.StringUtils.hasText;

/**
 * 고객센터 공통 Repository 구현체
 */
@Repository
public class CommonServiceCenterRepositoryImpl extends CSRepositorySupport implements CommonServiceCenterRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CommonServiceCenterRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    @Override
    public <T extends CSViewVOSupport> CSPageImpl <?> findEntityList(T requestObject) {
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
        if(requestObject instanceof MyQnaViewRequestVO) { /** 1:1 문의 */
            return jpaQueryFactory.select(myQna.count())
                    .from(myQna)
                    .where(getQuery(requestObject))
                    .orderBy(myQna.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof TopQnaViewRequestVO) { /** 자주묻는 질문 */
            return jpaQueryFactory.select(topQna.count())
                    .from(topQna)
                    .where(getQuery(requestObject))
                    .orderBy(topQna.createDateTime.desc())
                    .fetchOne();
        } else if(requestObject instanceof NoticeViewRequestVO) { /** 공지사항 */
            return jpaQueryFactory.select(notice.count())
                    .from(notice)
                    .where(getQuery(requestObject))
                    .orderBy(notice.isTop.desc(), notice.createDateTime.desc())
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
        if(requestObject instanceof MyQnaViewRequestVO) { /** 1:1 문의 */
            return jpaQueryFactory.selectFrom(myQna)
                    .where(getQuery(requestObject))
                    .orderBy(myQna.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof TopQnaViewRequestVO) { /** 자주묻는 질문 */
            return jpaQueryFactory.selectFrom(topQna)
                    .where(getQuery(requestObject))
                    .orderBy(topQna.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof NoticeViewRequestVO) { /** 공지사항 */
            List<Notice> entities = jpaQueryFactory.selectFrom(notice)
                    .where(getQuery(requestObject))
                    .orderBy(notice.isTop.desc(), notice.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize()).fetch().stream()
                    .peek(data -> data.setIsNew(new DateTime().minusDays(15).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                            .parseDateTime(data.getCreateDateTime())) < 0)).collect(Collectors.toList());

            if (entities.size() > 0 && !entities.isEmpty()) {
                for (Notice entity : entities) {
                    entity.setFileMasters(jpaQueryFactory.selectFrom(fileMaster)
                            .where(fileMaster.atchFileSn.eq(entity.getNoticeSerialNo()))
                            .fetch());

                    String adminName = jpaQueryFactory.select(adminUser.userName)
                            .from(adminUser)
                            .where(adminUser.userId.eq(entity.getRegistUserId()))
                            .fetchOne();
                    if (adminName != null) {
                        entity.setAdminName(adminName);
                    } else {
                        entity.setAdminName("관리자");
                    }
                }
            }
            return entities;

        } else {
            return new ArrayList<>();
        }
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof MyQnaViewRequestVO) {/** 1:1 문의 */
            return new Predicate[] { betweenTime(requestObject),
                    condition(authenticationInfo().getUserId(), myQna.registUserId::eq),
                    condition(((MyQnaViewRequestVO) requestObject).getSequenceNo(), myQna.sequenceNo::eq),
                    condition(((MyQnaViewRequestVO) requestObject).getMyQnaType(), myQna.qnaType::eq),
                    condition(((MyQnaViewRequestVO) requestObject).getQnaState(), myQna.qnaState::eq),
                    searchContainText(requestObject, ((MyQnaViewRequestVO) requestObject).getContainTextType(), ((MyQnaViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof TopQnaViewRequestVO) {/** 자주묻는 질문 */
            return new Predicate[] { betweenTime(requestObject),
                    searchContainText(requestObject, ((TopQnaViewRequestVO) requestObject).getContainTextType(), ((TopQnaViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof NoticeViewRequestVO) {/** 공지 사항 */
            return new Predicate[] { betweenTime(requestObject),
                    condition(((NoticeViewRequestVO) requestObject).getNoticeSerialNo(), notice.noticeSerialNo::eq),
                    condition(((NoticeViewRequestVO) requestObject).getNoticeType(), notice.noticeType::eq),
                    searchContainText(requestObject, ((NoticeViewRequestVO) requestObject).getContainTextType(), ((NoticeViewRequestVO) requestObject).getContainText())};
        } else {
            return new Predicate[] {};
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containTextType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof MyQnaViewRequestVO) { /** 1:1 문의 포함 단어 조회 */
                switch (Code.CON_TEXT_TYPE.enumOfCode(((MyQnaViewRequestVO) requestObject).getContainTextType())) {
                    case ALL:   /** 제목 + 내용 */
                        booleanBuilder.or(myQna.reqTitle.contains(containsText));
                        booleanBuilder.or(myQna.reqContents.contains(containsText));
                        booleanBuilder.or(myQna.resTitle.contains(containsText));
                        booleanBuilder.or(myQna.resContents.contains(containsText));
                        break;
                    case TITLE: /** 제목 */
                        booleanBuilder.or(myQna.reqTitle.contains(containsText));
                        booleanBuilder.or(myQna.resTitle.contains(containsText));
                        break;
                    case CONTENTS:  /** 내용 */
                        booleanBuilder.or(myQna.reqContents.contains(containsText));
                        booleanBuilder.or(myQna.resContents.contains(containsText));
                        break;
                }
            } else if(requestObject instanceof TopQnaViewRequestVO) { /** 자주묻는 질문 포함 단어 조회 */
                switch (Code.CON_TEXT_TYPE.enumOfCode(((TopQnaViewRequestVO) requestObject).getContainTextType())) {
                    case ALL:   /** 제목 + 내용 */
                        booleanBuilder.or(topQna.question.contains(containsText));
                        booleanBuilder.or(topQna.answer.contains(containsText));
                        break;
                    case TITLE: /** 제목 */
                        booleanBuilder.or(topQna.question.contains(containsText));
                        break;
                    case CONTENTS:  /** 내용 */
                        booleanBuilder.or(topQna.answer.contains(containsText));
                        break;
                }
            } else if(requestObject instanceof NoticeViewRequestVO) {/** 공지사항 포함 단어 조회 */
                switch (Code.CON_TEXT_TYPE.enumOfCode(((NoticeViewRequestVO) requestObject).getContainTextType())) {
                    case ALL:   /** 제목 + 내용 */
                        booleanBuilder.or(notice.title.contains(containsText));
                        booleanBuilder.or(notice.contents.contains(containsText));
                        break;
                    case TITLE: /** 제목 */
                        booleanBuilder.or(notice.title.contains(containsText));
                        break;
                    case CONTENTS:  /** 내용 */
                        booleanBuilder.or(notice.contents.contains(containsText));
                        break;
                }
            }
        }
        return booleanBuilder;
    }

    /** 시간 대 검색 */
    private <T extends CSViewVOSupport> BooleanExpression betweenTime(T requestObject) {
        String startDateTime = Optional.ofNullable(requestObject.getStartDate())
                .map(date -> new StringBuilder(date).append(" 00:00:00").toString())
                .orElse("");
        String endDateTime = Optional.ofNullable(requestObject.getEndDate())
                .map(date -> new StringBuilder(date).append(" 23:59:59").toString())
                .orElse("");

        if((!StringUtils.isEmpty(startDateTime) && StringUtils.isEmpty(endDateTime))
                || (StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime))) {
            throw new KPFException(KPF_RESULT.ERROR9001, "조회 시작 일자 & 조회 종료 일자는 한가지만 존재할 수 없습니다.");
        } else if(!StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime)) {
            if(requestObject instanceof MyQnaViewRequestVO) { /** 1:1 문의 */
                return myQna.createDateTime.between(startDateTime, endDateTime);
            } else if(requestObject instanceof TopQnaViewRequestVO) { /** 자주묻는 질문 */
                return topQna.createDateTime.between(startDateTime, endDateTime);
            } else if(requestObject instanceof NoticeViewRequestVO) {/** 공지 사항 */
                return notice.createDateTime.between(startDateTime, endDateTime);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
