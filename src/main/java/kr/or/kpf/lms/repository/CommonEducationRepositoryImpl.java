package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.common.main.dto.EducationPlanMainDTO;
import kr.or.kpf.lms.biz.common.main.vo.request.MainViewRequestVO;
import kr.or.kpf.lms.biz.education.anonymous.vo.request.AnonymousEducationViewRequestVO;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
import kr.or.kpf.lms.biz.education.schedule.vo.request.ScheduleViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.RoleGroup;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.reverseOrder;
import static kr.or.kpf.lms.repository.entity.QAdminUser.adminUser;
import static kr.or.kpf.lms.repository.entity.QContentsChapter.contentsChapter;
import static kr.or.kpf.lms.repository.entity.QCurriculumApplicationMaster.curriculumApplicationMaster;
import static kr.or.kpf.lms.repository.entity.QCurriculumContents.curriculumContents;
import static kr.or.kpf.lms.repository.entity.QEducationPlan.educationPlan;
import static kr.or.kpf.lms.repository.entity.QOrganizationInfo.organizationInfo;
import static org.springframework.util.StringUtils.hasText;

/**
 * 교육 신청 공통 Repository 구현체
 */
@Repository
public class CommonEducationRepositoryImpl extends CSRepositorySupport implements CommonEducationRepository {

    @Autowired private CurriculumApplicationMasterRepository curriculumApplicationMasterRepository;
    @Autowired private CurriculumContentsRepository curriculumContentsRepository;
    @Autowired private FileMasterRepository fileMasterRepository;

    private final JPAQueryFactory jpaQueryFactory;
    public CommonEducationRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

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
        if(requestObject instanceof ScheduleViewRequestVO
                || requestObject instanceof EducationApplicationViewRequestVO) { /** 교육 일정 or 교육 과정 일정 */
            return jpaQueryFactory.select(educationPlan.count())
                    .from(educationPlan)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof AnonymousEducationViewRequestVO) {
            return jpaQueryFactory.select(contentsChapter.count())
                    .from(contentsChapter)
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
    private <T extends CSViewVOSupport> List<?> getEntityList(T requestObject) {
        if(requestObject instanceof ScheduleViewRequestVO) { /** 교육 일정 - 관련된 조회 API 에 대해서는 페이징 처리를 하지 않는다. */
            return jpaQueryFactory.selectFrom(educationPlan)
                    .where(getQuery(requestObject))
                    .orderBy(educationPlan.operationBeginDateTime.desc())
                    .fetch();
        } else if(requestObject instanceof EducationApplicationViewRequestVO) { /** 교육 신청 */
            return jpaQueryFactory.selectFrom(educationPlan)
                    .where(getQuery(requestObject))
                    .orderBy(educationPlan.isTop.desc(), educationPlan.operationBeginDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch().stream()
                    .peek(plan -> {
                        String userId = RoleGroup.ANONYMOUS.name();
                        try {
                            userId = authenticationInfo().getUserId();
                        } catch (Exception e) {
                            logger.warn("비회원 교육 과정 접근");
                        }
                        plan.setAvailableApplicationType(curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                                        .educationPlanCode(plan.getEducationPlanCode())
                                        .curriculumCode(plan.getCurriculumCode())
                                        .userId(userId)
                                        .build()))
                                        .filter(entity -> !entity.getAdminApprovalState().equals(Code.ADM_APL_STATE.CANCEL.enumCode))
                                .map(data -> "4" /** 신청 완료 */)
                                .orElseGet(() -> {
                                    try {
                                        if(StringUtils.isEmpty(plan.getApplyBeginDateTime())) {
                                            if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getOperationEndDateTime()).after(new Date())) {
                                                return "3"; /** 신청 가능 */
                                            } else {
                                                return "1"; /** 신청 불가 */
                                            }
                                        } else if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyBeginDateTime()).before(new Date())
                                                && new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyEndDateTime()).after(new Date()))
                                            return "3"; /** 신청 가능 */
                                        else if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyEndDateTime()).before(new Date()))
                                            return "2"; /** 신청 마감 */
                                        else return "1"; /** 신청 불가 */
                                    } catch (Exception e) {
                                        throw new KPFException(KPF_RESULT.ERROR3022, "교육 과정 조회 중 날짜 파싱 오류.");
                                    }
                                }));
                        /** 해당 교육 현재 신청 인원 셋팅 */
                        plan.setCurrentNumberOfPeople(curriculumApplicationMasterRepository.findByEducationPlanCodeAndAdminApprovalStateIn(plan.getEducationPlanCode(), List.of(Code.ADM_APL_STATE.WAIT.enumCode, Code.ADM_APL_STATE.APPROVAL.enumCode)).size());

                        /** 신청 인원이 허용 인원보다 크거나 같을 경우 */
                        if(plan.getNumberOfPeople() != null && plan.getNumberOfPeople() > 0 && plan.getNumberOfPeople() <= plan.getCurrentNumberOfPeople()) {
                            /** 교육 일자 기준으로 신청 가능 이였을 경우 신청 마감으로 재변경 */
                            if(plan.getAvailableApplicationType().equals("3")) {
                                plan.setAvailableApplicationType("2");
                            }
                        }

                        if (((EducationApplicationViewRequestVO) requestObject).getEducationPlanCode() != null) {
                            plan.setFileMasters(fileMasterRepository.findAll(Example.of(FileMaster.builder()
                                    .atchFileSn(plan.getEducationPlanCode())
                                    .build())));
                        }
                        /** 교육 과정 시작 일자, 교육 일정 생성 일자 내림차순 정렬 */
                    }).sorted(Comparator.comparing(EducationPlan::getIsTop).thenComparing(EducationPlan::getOperationBeginDateTime).reversed()).collect(Collectors.toList());

        } else if(requestObject instanceof MainViewRequestVO) { /** 메인 통합 */
            if (((MainViewRequestVO) requestObject).getEducationType() != null) {
                if (((MainViewRequestVO) requestObject).getEducationType().equals("0")) {
                    return jpaQueryFactory.select(Projections.fields(EducationPlanMainDTO.class,
                                    educationPlan.educationPlanCode,
                                    educationPlan.curriculumCode,
                                    educationPlan.thumbnailFilePath,
                                    educationPlan.curriculumMaster.educationType,
                                    educationPlan.curriculumMaster.educationTarget,
                                    educationPlan.curriculumMaster.curriculumName,
                                    educationPlan.curriculumMaster.educationContent,
                                    educationPlan.operationBeginDateTime,
                                    educationPlan.operationEndDateTime,
                                    educationPlan.applyBeginDateTime,
                                    educationPlan.applyEndDateTime,
                                    educationPlan.createDateTime,
                                    educationPlan.educationPlace,
                                    educationPlan.numberOfPeople))
                            .from(educationPlan)
                            .where(getQuery(requestObject))
                            .orderBy(educationPlan.operationBeginDateTime.desc())
                            .offset(requestObject.getPageable().getOffset())
                            .limit(30)
                            .fetch().stream()
                            .peek(plan -> {
                                String userId = RoleGroup.ANONYMOUS.name();
                                try {
                                    userId = authenticationInfo().getUserId();
                                } catch (Exception e) {
                                    logger.warn("비회원 교육 과정 접근");
                                }
                                // 1순위: 신청 가능-신청 마감-신청 완료-신청 불가, 2순위: 교육 신청 시작 기간 빠른거부터, 3순위: 교육 시작 기간 느린거부터
                                // 최종: 1순위: 미신청 과정, 2순위: 모집인원 미마감, 3순위: 교육 신청기간 시작일 빠른순, 4순위: 교육 등록일 선등록 순, 5순위: 교육 신청기간 종료일 빠른순
                                plan.setAvailableApplicationType(curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                                                .educationPlanCode(plan.getEducationPlanCode())
                                                .userId(userId)
                                                .build()))
                                        .map(data -> "1" /** 신청 완료 */)
                                        .orElseGet(() -> {
                                            Integer count = jpaQueryFactory.select(curriculumApplicationMaster.count())
                                                    .from(curriculumApplicationMaster)
                                                    .where(curriculumApplicationMaster.educationPlanCode.eq(plan.getEducationPlanCode()),
                                                            curriculumApplicationMaster.adminApprovalState.eq(Code.ADM_APL_STATE.APPROVAL.enumCode))
                                                    .fetchOne().intValue();
                                            if (plan.getNumberOfPeople() <= count) {
                                                return "2"; // 신청 마감(정원 마감)
                                            } else {
                                                try {
                                                    if (StringUtils.isEmpty(plan.getApplyBeginDateTime())) {
                                                        if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getOperationEndDateTime()).after(new Date())) {
                                                            return "4"; // 신청 가능
                                                        } else {
                                                            return "3"; // 신청 불가
                                                        }
                                                    } else if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyBeginDateTime()).before(new Date())
                                                            && new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyEndDateTime()).after(new Date()))
                                                        return "4"; // 신청 가능
                                                    else return "3"; // 신청 불가
                                                } catch (Exception e) {
                                                    throw new KPFException(KPF_RESULT.ERROR3022, "교육 과정 조회 중 날짜 파싱 오류.");
                                                }
                                            }
                                        }));
                            })
                            .sorted(Comparator.comparing(EducationPlanMainDTO::getAvailableApplicationType).reversed()
                                    .thenComparing(EducationPlanMainDTO::getApplyEndDateTime)
                                    .thenComparing(EducationPlanMainDTO::getCreateDateTime)
                                    .thenComparing(EducationPlanMainDTO::getApplyBeginDateTime)).collect(Collectors.toList());
                } else {
                    return jpaQueryFactory.select(Projections.fields(EducationPlanMainDTO.class,
                                    educationPlan.educationPlanCode,
                                    educationPlan.curriculumCode,
                                    educationPlan.thumbnailFilePath,
                                    educationPlan.curriculumMaster.educationType,
                                    educationPlan.curriculumMaster.educationTarget,
                                    educationPlan.curriculumMaster.curriculumName,
                                    educationPlan.curriculumMaster.educationContent,
                                    educationPlan.operationBeginDateTime,
                                    educationPlan.operationEndDateTime,
                                    educationPlan.applyBeginDateTime,
                                    educationPlan.applyEndDateTime,
                                    educationPlan.createDateTime,
                                    educationPlan.educationPlace,
                                    educationPlan.numberOfPeople))
                            .from(educationPlan)
                            .where(getQuery(requestObject))
                            .orderBy(educationPlan.operationBeginDateTime.desc())
                            .offset(requestObject.getPageable().getOffset())
                            .limit(30)
                            .fetch().stream()
                            .peek(plan -> {
                                String userId = RoleGroup.ANONYMOUS.name();
                                try {
                                    userId = authenticationInfo().getUserId();
                                } catch (Exception e) {
                                    logger.warn("비회원 교육 과정 접근");
                                }
                                // 1순위: 신청 가능-신청 마감-신청 완료-신청 불가, 2순위: 교육 시작 기간 빠른거부터, 3순위: 교육 종료 기간 느린거부터
                                plan.setAvailableApplicationType(curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                                                .curriculumCode(plan.getCurriculumCode())
                                                .userId(userId)
                                                .build()))
                                        .map(data -> "2" /** 신청 완료 */)
                                        .orElseGet(() -> {
                                            try {
                                                if (StringUtils.isEmpty(plan.getApplyBeginDateTime())) {
                                                    if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getOperationEndDateTime()).after(new Date())) {
                                                        return "4"; /** 신청 가능 */
                                                    } else {
                                                        return "1"; /** 신청 불가 */
                                                    }
                                                } else if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyBeginDateTime()).before(new Date())
                                                        && new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyEndDateTime()).after(new Date()))
                                                    return "4"; /** 신청 가능 */
                                                else if (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getApplyEndDateTime()).before(new Date()))
                                                    return "3"; /** 신청 마감 */
                                                else return "1"; /** 신청 불가 */
                                            } catch (Exception e) {
                                                throw new KPFException(KPF_RESULT.ERROR3022, "교육 과정 조회 중 날짜 파싱 오류.");
                                            }
                                        }));
                            })
                            .sorted(Comparator.comparing(EducationPlanMainDTO::getAvailableApplicationType)
                                    .thenComparing(EducationPlanMainDTO::getCreateDateTime).reversed()).collect(Collectors.toList());
                }
            } else return null;
        } else if(requestObject instanceof AnonymousEducationViewRequestVO) {
            return jpaQueryFactory.selectFrom(contentsChapter)
                    .where(getQuery(requestObject))
                    .orderBy(contentsChapter.sortNo.asc())
                    .fetch();
        } else {
            return null;
        }
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof ScheduleViewRequestVO) { /** 교육 일정 */
            return new Predicate[] { betweenTime(requestObject),
                    condition(true, educationPlan.isUsable::eq)};
        } else if(requestObject instanceof EducationApplicationViewRequestVO) { /** 교육 과정 일정 */
            return new Predicate[]{ betweenTime(requestObject),
                    condition(true, educationPlan.isUsable::eq),
                    condition(!StringUtils.isEmpty(((EducationApplicationViewRequestVO) requestObject).getEducationType())
                                    ? (((EducationApplicationViewRequestVO) requestObject).getEducationType().equals("0") // 이러닝 외
                                    ? new String[]{"1", "2", "4"} // 1: 화상교육, 2: 집합교육, 3: 이러닝교육, 4: 병행(화상+집합)교육
                                    : new String[]{((EducationApplicationViewRequestVO) requestObject).getEducationType()})
                                    : null,
                            educationPlan.curriculumMaster.educationType::in),
                    condition(((EducationApplicationViewRequestVO) requestObject).getEducationPlanCode(), educationPlan.educationPlanCode::eq),
                    condition(((EducationApplicationViewRequestVO) requestObject).getProvince(), educationPlan.province::eq),
                    condition(((EducationApplicationViewRequestVO) requestObject).getLectureName(), educationPlan.curriculumMaster.curriculumName::contains),
                    condition(((EducationApplicationViewRequestVO) requestObject).getEducationCategory(), educationPlan.curriculumMaster.categoryCode::eq) };
        } else if(requestObject instanceof MainViewRequestVO) { /** 메인 통합 */
            return new Predicate[] { betweenTime(requestObject),
                    condition(true, educationPlan.isUsable::eq),
                    condition(!StringUtils.isEmpty(((MainViewRequestVO) requestObject).getEducationType())
                                    ? (((MainViewRequestVO) requestObject).getEducationType().equals("0") // 이러닝 외
                                    ? new String[]{"1", "2", "4"} // 1: 화상교육, 2: 집합교육, 3: 이러닝교육, 4: 병행(화상+집합)교육
                                    : new String[]{((MainViewRequestVO) requestObject).getEducationType()})
                                    : null,
                            educationPlan.curriculumMaster.educationType::in),
                    condition(((MainViewRequestVO) requestObject).getEducationTarget(), educationPlan.curriculumMaster.educationTarget::in)};
        } else if(requestObject instanceof AnonymousEducationViewRequestVO) {
            return new Predicate[] { betweenTime(requestObject),
                    condition(((AnonymousEducationViewRequestVO) requestObject).getContentsCode(), contentsChapter.contentsCode::eq)};
        } else {
            return null;
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private BooleanBuilder searchContainText(Object searchMiddleType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if((!StringUtils.isEmpty(startDateTime) && StringUtils.isEmpty(endDateTime))
                || (StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime))) {
            throw new KPFException(KPF_RESULT.ERROR9001, "조회 시작 일자 & 조회 종료 일자는 한가지만 존재할 수 없습니다.");
        } else if(!StringUtils.isEmpty(startDateTime) && !StringUtils.isEmpty(endDateTime)) {
            if (requestObject instanceof ScheduleViewRequestVO /** 교육 일정 */
                    || requestObject instanceof EducationApplicationViewRequestVO /** 교육 과정 일정 */) {
                return (educationPlan.operationBeginDateTime.goe(startDateTime).and(educationPlan.operationEndDateTime.loe(endDateTime)))
                        .or(educationPlan.operationBeginDateTime.loe(startDateTime).and(educationPlan.operationEndDateTime.between(startDateTime, endDateTime)))
                        .or(educationPlan.operationBeginDateTime.between(startDateTime, endDateTime).and(educationPlan.operationEndDateTime.goe(endDateTime)))
                        .or(educationPlan.operationBeginDateTime.loe(startDateTime).and(educationPlan.operationEndDateTime.goe(endDateTime)));
            } else if (requestObject instanceof MainViewRequestVO) { /** 메인 통합 */
                return (educationPlan.applyBeginDateTime.goe(startDateTime).and(educationPlan.applyEndDateTime.loe(endDateTime)))
                        .or(educationPlan.applyBeginDateTime.loe(startDateTime).and(educationPlan.applyEndDateTime.between(startDateTime, endDateTime)))
                        .or(educationPlan.applyBeginDateTime.between(startDateTime, endDateTime).and(educationPlan.applyEndDateTime.goe(endDateTime)))
                        .or(educationPlan.applyBeginDateTime.loe(startDateTime).and(educationPlan.applyEndDateTime.goe(endDateTime)));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 교육신청 일련번호 생성
     */
    @Override
    public String generateApplicationNo(String prefixCode) {
        try {
            return jpaQueryFactory.selectFrom(curriculumApplicationMaster)
                    .where(curriculumApplicationMaster.applicationNo.like(prefixCode+"%"))
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .orderBy(curriculumApplicationMaster.applicationNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getApplicationNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        } catch (CannotAcquireLockException e1) {
            logger.error("{}- {}", e1.getClass().getCanonicalName(), e1.getMessage(), e1);
            throw new RuntimeException("타 사용자의 신청을 처리 중입니다. 다시 시도해주세요.");
        } catch (Exception e2) {
            logger.error("{}- {}", e2.getClass().getCanonicalName(), e2.getMessage(), e2);
            throw new RuntimeException("타 사용자의 신청을 처리 중입니다. 다시 시도해주세요.");
        }
    }
}