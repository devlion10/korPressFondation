package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.user.adminuser.vo.request.AdminUserViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorViewRequestVO;
import kr.or.kpf.lms.biz.user.webuser.vo.request.OrganizationMediaViewRequestVO;
import kr.or.kpf.lms.biz.user.webuser.vo.request.OrganizationViewRequestVO;
import kr.or.kpf.lms.biz.user.webuser.vo.request.UserBizViewRequestVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.UserBizApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.OrganizationAuthorityHistory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kr.or.kpf.lms.repository.entity.QAdminUser.adminUser;
import static kr.or.kpf.lms.repository.entity.QInstructorInfo.instructorInfo;
import static kr.or.kpf.lms.repository.entity.QLmsUser.lmsUser;
import static kr.or.kpf.lms.repository.entity.QOrganizationAuthorityHistory.organizationAuthorityHistory;
import static kr.or.kpf.lms.repository.entity.QOrganizationInfo.organizationInfo;
import static kr.or.kpf.lms.repository.entity.QOrganizationInfoMedia.organizationInfoMedia;
import static org.springframework.util.StringUtils.hasText;

/**
 * 유저 공통 Repository 구현체
 */
@Repository
public class CommonUserRepositoryImpl extends CSRepositorySupport implements CommonUserRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public CommonUserRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

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
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    @Override
    public <T> Object findEntity(T requestObject) {
        return getEntity(requestObject);
    }

    /**
     * Entity 총 갯수
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> Long getEntityCount(T requestObject) {
        if(requestObject instanceof OrganizationViewRequestVO) { /** 기관 정보 */
            return jpaQueryFactory.select(organizationInfo.count())
                    .from(organizationInfo)
                    .where(getQuery(requestObject))
                    .orderBy(organizationInfo.organizationName.asc())
                    .fetchOne();
        } else if(requestObject instanceof AdminUserViewRequestVO) { /** 관리자 정보 */
            return jpaQueryFactory.select(adminUser.count())
                    .from(adminUser)
                    .where(getQuery(requestObject))
                    .orderBy(adminUser.createDateTime.asc())
                    .fetchOne();
        } else if(requestObject instanceof InstructorViewRequestVO) { /** 강사 정보 */
            return jpaQueryFactory.select(instructorInfo.count())
                    .from(instructorInfo)
                    .where(getQuery(requestObject))
                    .orderBy(instructorInfo.createDateTime.asc())
                    .fetchOne();
        } else if(requestObject instanceof OrganizationMediaViewRequestVO) { /** 매체 정보 */
            return jpaQueryFactory.select(organizationInfoMedia.count())
                    .from(organizationInfoMedia)
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(organizationInfoMedia.organizationCode))
                    .where(getQuery(requestObject))
                    .orderBy(organizationInfoMedia.mediaName.asc())
                    .fetchOne();
        } else {
            return NumberUtils.LONG_ZERO;
        }
    }

    /**
     * Entity
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T> Object getEntity(T requestObject) {
        if(requestObject instanceof UserBizViewRequestVO) { /** 강사 모집 */
            UserBizApiResponseVO dto = jpaQueryFactory.select(Projections.fields(UserBizApiResponseVO.class,
                            lmsUser.userId,
                            lmsUser.password,
                            lmsUser.userName,
                            lmsUser.birthDay,
                            lmsUser.roleGroup,
                            lmsUser.businessAuthority,
                            lmsUser.phone,
                            lmsUser.isSmsAgree,
                            lmsUser.email,
                            lmsUser.isEmailAgree,
                            lmsUser.gender,
                            lmsUser.certValue,
                            lmsUser.di,
                            lmsUser.ci,
                            lmsUser.passwordChangeDate,
                            lmsUser.withDrawDate,
                            lmsUser.lockCount,
                            lmsUser.lockDateTime,
                            lmsUser.isLock,
                            lmsUser.state,
                            lmsUser.organizationCode,
                            lmsUser.department,
                            lmsUser.rank,
                            lmsUser.position,
                            lmsUser.attachFilePath,
                            lmsUser.parentName,
                            lmsUser.parentBirthDay,
                            lmsUser.parentGender
                    ))
                    .from(lmsUser)
                    .where(lmsUser.userId.eq(((UserBizViewRequestVO) requestObject).getUserId()))
                    .fetchOne();

            List<OrganizationAuthorityHistory> entities = jpaQueryFactory.selectFrom(organizationAuthorityHistory)
                    .where(organizationAuthorityHistory.userId.eq(((UserBizViewRequestVO) requestObject).getUserId()))
                    .orderBy(organizationAuthorityHistory.createDateTime.desc())
                    .fetch();

            if (entities.size() > 0 && !entities.isEmpty()) {
                if (!entities.get(0).getBizAuthorityApprovalState().equals("4")) {
                    dto.setOrganizationInfo(entities.get(0));
                    return dto;
                } else return null;
            } else return null;
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
        if(requestObject instanceof OrganizationViewRequestVO) { /** 기관 정보 */
            return jpaQueryFactory.selectFrom(organizationInfo)
                    .where(getQuery(requestObject))
                    .orderBy(organizationInfo.organizationName.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof OrganizationMediaViewRequestVO) { /** 매체 정보 */
            return jpaQueryFactory.selectFrom(organizationInfoMedia)
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(organizationInfoMedia.organizationCode))
                    .where(getQuery(requestObject))
                    .orderBy(organizationInfoMedia.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof AdminUserViewRequestVO) { /** 관리자 정보 */
            return jpaQueryFactory.select(adminUser)
                    .from(adminUser)
                    .where(getQuery(requestObject))
                    .orderBy(adminUser.createDateTime.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof InstructorViewRequestVO) { /** 강사 정보 */
            return jpaQueryFactory.select(instructorInfo)
                    .from(instructorInfo)
                    .where(getQuery(requestObject))
                    .orderBy(instructorInfo.createDateTime.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Entity 전체 리스트
     *
     * @param requestObject
     * @param <T>
     * @return
     */
    @Override
    public <T extends CSViewVOSupport> List<?> allEntityList(T requestObject) {
        if(requestObject instanceof OrganizationViewRequestVO) { /** 기관 정보 */
            return jpaQueryFactory.selectFrom(organizationInfo)
                    .where(getQuery(requestObject))
                    .orderBy(organizationInfo.organizationName.asc())
                    .fetch();
        } else if(requestObject instanceof AdminUserViewRequestVO) { /** 관리자 정보 */
            return jpaQueryFactory.select(adminUser)
                    .from(adminUser)
                    .where(getQuery(requestObject))
                    .orderBy(adminUser.createDateTime.asc())
                    .fetch();
        } else if(requestObject instanceof InstructorViewRequestVO) { /** 강사 정보 */
            return jpaQueryFactory.select(instructorInfo)
                    .from(instructorInfo)
                    .where(getQuery(requestObject))
                    .orderBy(instructorInfo.createDateTime.asc())
                    .fetch();
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof OrganizationViewRequestVO) { /** 기관 정보 */
            /* 기관 사용 여부 옵션 예외 처리
            if (((OrganizationViewRequestVO) requestObject).getOrganizationType() != null && ((OrganizationViewRequestVO) requestObject).getOrganizationType().equals(Code.ORG_TYPE.MEDIA.enumCode)) {
                return new Predicate[] { betweenTime(requestObject),
                        condition(((OrganizationViewRequestVO) requestObject).getOrganizationCode(), organizationInfo.organizationCode::eq),
                        condition(((OrganizationViewRequestVO) requestObject).getOrganizationName(), organizationInfo.organizationName::contains),
                        condition(((OrganizationViewRequestVO) requestObject).getOrganizationType(), organizationInfo.organizationType::eq)};
            } else {
                return new Predicate[] { betweenTime(requestObject),
                        condition(((OrganizationViewRequestVO) requestObject).getOrganizationCode(), organizationInfo.organizationCode::eq),
                        condition(((OrganizationViewRequestVO) requestObject).getOrganizationName(), organizationInfo.organizationName::contains),
                        condition(((OrganizationViewRequestVO) requestObject).getOrganizationType(), organizationInfo.organizationType::eq),
                        condition("Y", organizationInfo.isUsable::eq)};
            }*/
            return new Predicate[] { betweenTime(requestObject),
                    condition(((OrganizationViewRequestVO) requestObject).getOrganizationCode(), organizationInfo.organizationCode::eq),
                    condition(((OrganizationViewRequestVO) requestObject).getOrganizationName(), organizationInfo.organizationName::contains),
                    condition(((OrganizationViewRequestVO) requestObject).getOrganizationType(), organizationInfo.organizationType::eq),
                    condition("Y", organizationInfo.isUsable::eq)};
        } else if(requestObject instanceof OrganizationMediaViewRequestVO) { /** 매체 정보 */
            return new Predicate[]{ condition(true, organizationInfoMedia.isUsable::eq),
                    condition("Y", organizationInfo.isUsable::eq)};
        } else if(requestObject instanceof AdminUserViewRequestVO) { /** 관리자 정보 */
            return new Predicate[]{condition(((AdminUserViewRequestVO) requestObject).getUserId(), adminUser.userId::eq),
                    condition(((AdminUserViewRequestVO) requestObject).getUserName(), adminUser.userName::contains)};
        } else if(requestObject instanceof InstructorViewRequestVO) { /** 강사 정보 */
            return new Predicate[]{ condition(((InstructorViewRequestVO) requestObject).getInstrSerialNo(), instructorInfo.instrSerialNo::eq),
                    condition(((InstructorViewRequestVO) requestObject).getUserId(), lmsUser.userId::contains),
                    condition(((InstructorViewRequestVO) requestObject).getInstrId(), instructorInfo.userId::eq),
                    condition(((InstructorViewRequestVO) requestObject).getInstrCategory(), instructorInfo.instrCategory::contains),
                    condition(((InstructorViewRequestVO) requestObject).getInstrNm(), instructorInfo.instrNm::contains),
                    condition(((InstructorViewRequestVO) requestObject).getInstructorName(), instructorInfo.instrNm::eq), // 정확한 검색에 추가된 키값 instructorName
                    condition(1, instructorInfo.instrStts::eq)};
        } else {
            return new Predicate[] {};
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containTextType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof OrganizationViewRequestVO) { /** 기관 정보 */
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
            if(requestObject instanceof OrganizationViewRequestVO) { /** 기관 정보 */
                return organizationInfo.createDateTime.between(startDateTime, endDateTime);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @Override
    public String generateCode(String prefixCode) {
        return jpaQueryFactory.selectFrom(organizationInfo)
                .where(organizationInfo.organizationCode.like(prefixCode+"%"))
                .orderBy(organizationInfo.organizationCode.desc())
                .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                        .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getOrganizationCode().replace(prefixCode, "")) + 1), 7, "0"))
                        .toString())
                .orElse(new StringBuilder(prefixCode).append("0000000").toString());
    }
}