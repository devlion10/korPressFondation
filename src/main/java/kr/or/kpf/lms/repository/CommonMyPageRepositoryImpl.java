package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.response.BizOrganizationAplyCustomApiResponseVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancCustomApiResponseVO;
import kr.or.kpf.lms.biz.mypage.bizapply.vo.request.MyBizApplyViewRequestVO;
import kr.or.kpf.lms.biz.mypage.businessstate.vo.request.*;
import kr.or.kpf.lms.biz.mypage.businessstate.vo.response.MyBusinessStateSurveyApiResponseVO;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request.ClassSubjectViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.request.MyEducationStateViewRequestVO;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.request.*;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.response.MyInstructorStateAplyCustomApiResponseVO;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.response.MyInstructorStateCompleteCustomApiResponseVO;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.response.MyInstructorStateCustomApiResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.request.OrganizationViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.common.support.CSRepositorySupport;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Tuple;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static kr.or.kpf.lms.repository.entity.QAdminUser.adminUser;
import static kr.or.kpf.lms.repository.entity.QBizAply.bizAply;
import static kr.or.kpf.lms.repository.entity.QBizAplyDtl.bizAplyDtl;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl5.bizPbancTmpl5;
import static kr.or.kpf.lms.repository.entity.QInstructorInfo.instructorInfo;
import static kr.or.kpf.lms.repository.entity.QLmsUser.lmsUser;
import static kr.or.kpf.lms.repository.entity.QOrganizationInfo.organizationInfo;
import static kr.or.kpf.lms.repository.entity.QOrganizationAuthorityHistory.organizationAuthorityHistory;
import static kr.or.kpf.lms.repository.entity.QBizOrganizationRsltRpt.bizOrganizationRsltRpt;
import static kr.or.kpf.lms.repository.entity.QBizPbancMaster.bizPbancMaster;
import static kr.or.kpf.lms.repository.entity.QBizSurveyMaster.bizSurveyMaster;
import static kr.or.kpf.lms.repository.entity.QBizSurvey.bizSurvey;
import static kr.or.kpf.lms.repository.entity.QBizSurveyQitem.bizSurveyQitem;
import static kr.or.kpf.lms.repository.entity.QBizSurveyAns.bizSurveyAns;
import static kr.or.kpf.lms.repository.entity.QClassGuide.classGuide;
import static kr.or.kpf.lms.repository.entity.QClassSubject.classSubject;
import static kr.or.kpf.lms.repository.entity.QClassGuideFile.classGuideFile;
import static kr.or.kpf.lms.repository.entity.QCurriculumApplicationMaster.curriculumApplicationMaster;
import static kr.or.kpf.lms.repository.entity.QBizEditHist.bizEditHist;
import static kr.or.kpf.lms.repository.entity.QBizOrganizationAply.bizOrganizationAply;
import static kr.or.kpf.lms.repository.entity.QBizOrganizationAplyDtl.bizOrganizationAplyDtl;
import static kr.or.kpf.lms.repository.entity.QBizInstructor.bizInstructor;
import static kr.or.kpf.lms.repository.entity.QBizInstructorPbanc.bizInstructorPbanc;
import static kr.or.kpf.lms.repository.entity.QBizInstructorAsgnm.bizInstructorAsgnm;
import static kr.or.kpf.lms.repository.entity.QBizInstructorAply.bizInstructorAply;
import static kr.or.kpf.lms.repository.entity.QBizInstructorQuestion.bizInstructorQuestion;
import static kr.or.kpf.lms.repository.entity.QBizInstructorDist.bizInstructorDist;
import static kr.or.kpf.lms.repository.entity.QBizInstructorIdentify.bizInstructorIdentify;
import static kr.or.kpf.lms.repository.entity.QBizInstructorIdentifyDtl.bizInstructorIdentifyDtl;

import static org.springframework.util.StringUtils.hasText;

/**
 * 나의 페이지 공통 Repository 구현체
 */
@Repository
public class CommonMyPageRepositoryImpl extends CSRepositorySupport implements CommonMyPageRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public CommonMyPageRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

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
        if(requestObject instanceof MyBizApplyViewRequestVO) { /** 사업 신청 내역 - 자유형 */
            return jpaQueryFactory.select(bizAply.count())
                    .from(bizAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyBusinessStateApplyViewRequestVO) { /** 사업 참여 현황 - 신청 리스트 */
            return jpaQueryFactory.select(bizOrganizationAply.count())
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyBusinessStateViewRequestVO) { /** 사업 참여 현황 - 진행 중 리스트 */
            return jpaQueryFactory.select(bizOrganizationAply.count())
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyBusinessStateSurveyViewRequestVO) { /** 사업 참여 현황 - 진행 중 리스트 - 상호평가 */
            return jpaQueryFactory.select(bizOrganizationAply.count())
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyBusinessStateCompleteViewRequestVO) { /** 사업 참여 현황 - 완료 리스트 */
            return jpaQueryFactory.select(bizOrganizationAply.count())
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 사업 참여 현황 - 강의확인서 */
            return jpaQueryFactory.select(bizInstructorIdentify.count())
                    .from(bizInstructorIdentify)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorIdentify.bizOrgAplyNo))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyInstructorStateApplyViewRequestVO) { /** 강사 참여 현황 - 강사 모집 - 지원 중 */
            return jpaQueryFactory.select(bizInstructorAply.count())
                    .from(bizInstructorAply)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorAply.bizOrgAplyNo))
                    .leftJoin(bizInstructorPbanc).on(bizInstructorPbanc.bizInstrNo.eq(bizInstructorAply.bizInstrNo), bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyInstructorStateApplyResultViewRequestVO) { /** 강사 참여 현황 - 강사 모집 - 지원 완료 */
            return jpaQueryFactory.select(bizInstructorAply.count())
                    .from(bizInstructorAply)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorAply.bizOrgAplyNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .leftJoin(bizInstructor).on(bizInstructor.bizInstrNo.eq(bizInstructorAply.bizInstrNo))
                    .leftJoin(bizInstructorPbanc).on(bizInstructorPbanc.bizInstrNo.eq(bizInstructorAply.bizInstrNo),
                            bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyInstructorStateViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 진행 중 */
            List<Long> countDto = jpaQueryFactory.select(bizInstructorAply.count())
                    .from(bizInstructorAply)
                    .innerJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorAply.bizOrgAplyNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .innerJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .innerJoin(bizInstructorAsgnm).on(bizInstructorAsgnm.bizInstrAplyNo.eq(bizInstructorAply.bizInstrAplyNo))
                    .innerJoin(bizOrganizationAplyDtl).on(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                    .leftJoin(bizInstructorIdentify).on(bizInstructorIdentify.bizOrgAplyNo.eq(bizOrganizationAply.bizOrgAplyNo),
                            bizInstructorIdentify.bizInstrIdntyYm.startsWith(bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,4)),
                            bizInstructorIdentify.bizInstrIdntyYm.endsWith(bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(5,7)),
                            bizInstructorIdentify.registUserId.eq(bizInstructorAply.bizInstrAplyInstrId))
                    .where(getQuery(requestObject))
                    .groupBy(bizInstructorAply.bizInstrAplyNo, bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0, 7))
                    .fetch();
            Long result = Long.valueOf(0);
            if (countDto.size() > 0) {
                for(Long count : countDto){
                    result=result + 1;
                }
            }
            return result;
        } else if(requestObject instanceof MyInstructorStateCompleteViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 완료 */
            List<Long> countDto = jpaQueryFactory.select(bizInstructorAsgnm.count())
                    .from(bizInstructorAsgnm)
                    .innerJoin(bizInstructorIdentifyDtl).on(bizInstructorIdentifyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                    .innerJoin(bizInstructorIdentify).on(bizInstructorIdentify.bizInstrIdntyNo.eq(bizInstructorIdentifyDtl.bizInstrIdntyNo))
                    .innerJoin(bizOrganizationAplyDtl).on(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                    .innerJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizOrganizationAplyDtl.bizOrgAplyNo))
                    .innerJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetch();

            Long result = Long.valueOf(0);
            if (countDto.size() > 0 && countDto.get(0) != 0) {
                for(Long count : countDto){
                    result=result + 1;
                }
            }
            return result;
        } else if(requestObject instanceof MyInstructorStateCalculateViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 정산 */
            return jpaQueryFactory.select(bizInstructorIdentify.count())
                    .from(bizInstructorIdentify)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 참여 현황 - 강사 지원 문의 */
            return jpaQueryFactory.select(bizInstructorQuestion.count())
                    .from(bizInstructorQuestion)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorQuestion.bizOrgAplyNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof MyEducationStateViewRequestVO) { /** 교육 수료 현황 */
            return jpaQueryFactory.select(curriculumApplicationMaster.count())
                    .from(curriculumApplicationMaster)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof ClassGuideViewRequestVO) { /** 수업지도안 */
            return jpaQueryFactory.select(classGuide.count())
                    .from(classGuide)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof ClassSubjectViewRequestVO) { /** 수업지도안 - 교과 */
            return jpaQueryFactory.select(classSubject.count())
                    .from(classSubject)
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
        if(requestObject instanceof MyBizApplyViewRequestVO) { /** 사업 신청 내역 - 자유형 */
            List<BizAply> dtos = jpaQueryFactory.selectFrom(bizAply)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(bizAply.bizAplyUserID))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(lmsUser.organizationCode))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch().stream().peek(data -> {
                        List<BizAplyDtl> dtls = jpaQueryFactory.selectFrom(bizAplyDtl)
                                .where(bizAplyDtl.sequenceNo.eq(data.getSequenceNo()))
                                .leftJoin(bizPbancTmpl5).on(bizPbancTmpl5.bizPbancTmpl5No.eq(bizAplyDtl.bizPbancTmpl5No))
                                .orderBy(bizPbancTmpl5.bizPbancTmpl5Ordr.asc()).fetch().stream().peek(subData -> {
                                    subData.setBizPbancTmpl5(jpaQueryFactory.selectFrom(bizPbancTmpl5).where(bizPbancTmpl5.bizPbancTmpl5No.eq(subData.getBizPbancTmpl5No())).fetchOne());
                                }).collect(Collectors.toList());
                        if (dtls != null && dtls.size() > 0)
                            data.setBizAplyDtls(dtls);
                    }).collect(Collectors.toList());
            return  dtos;
        } else if(requestObject instanceof MyBusinessStateApplyViewRequestVO) { /** 사업 참여 현황 - 신청 리스트 */
            List<BizOrganizationAplyCustomApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(BizOrganizationAplyCustomApiResponseVO.class,
                            bizOrganizationAply.bizOrgAplyNo,
                            bizOrganizationAply.bizPbancNo,
                            bizOrganizationAply.orgCd,
                            bizOrganizationAply.bizOrgAplyRprsvNm,
                            bizOrganizationAply.bizOrgAplyTime,
                            bizOrganizationAply.bizOrgAplyTimeFrst,
                            bizOrganizationAply.bizOrgAplyRgn,
                            bizOrganizationAply.bizOrgAplyPic,
                            bizOrganizationAply.bizOrgAplyPicNm,
                            bizOrganizationAply.bizOrgAplyPicJbgd,
                            bizOrganizationAply.bizOrgAplyPicEml,
                            bizOrganizationAply.bizOrgAplyPicTelno,
                            bizOrganizationAply.bizOrgAplyPicMpno,
                            bizOrganizationAply.bizOrgAplyStts,
                            bizOrganizationAply.bizOrgAplySttsCmnt,
                            bizOrganizationAply.bizOrgAplyChgYn,
                            bizOrganizationAply.bizOrgAplyOperCtgr,
                            bizOrganizationAply.bizOrgAplyOperMeth,
                            bizOrganizationAply.bizOrgAplyLsnPlanBgng,
                            bizOrganizationAply.bizOrgAplyLsnPlanEnd,
                            bizOrganizationAply.bizOrgAplyLsnPlanTrgt,
                            bizOrganizationAply.bizOrgAplyLsnPlanNope,
                            bizOrganizationAply.bizOrgAplyLsnPlanDscr1,
                            bizOrganizationAply.bizOrgAplyLsnPlanDscr2,
                            bizOrganizationAply.bizOrgAplyLsnPlanDscr3,
                            bizOrganizationAply.bizOrgAplyLsnPlanEtcInstr,
                            bizOrganizationAply.bizOrgAplyPeprYn,
                            bizOrganizationAply.bizOrgAplyLsnPlanEtc,
                            bizOrganizationAply.bizOrgAplyFile,
                            bizOrganizationAply.createDateTime,
                            bizOrganizationAply.updateDateTime,
                            bizOrganizationAply.modifyUserId
                    ))
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetch();

            if (!dtos.isEmpty() && dtos.size() > 0) {
                for (BizOrganizationAplyCustomApiResponseVO dto : dtos) {
                    dto.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(dto.getOrgCd()))
                            .fetchOne());

                    List<OrganizationAuthorityHistory> organizationAuthorityHistories = jpaQueryFactory.selectFrom(organizationAuthorityHistory)
                            .where(organizationAuthorityHistory.userId.eq(dto.getBizOrgAplyPic()), organizationAuthorityHistory.bizAuthorityApprovalState.eq("2"))
                            .orderBy(organizationAuthorityHistory.createDateTime.asc())
                            .fetch();

                    if (organizationAuthorityHistories != null && organizationAuthorityHistories.size() > 0)
                        dto.setOrganization(organizationAuthorityHistories.get(0));

                    BizPbancCustomApiResponseVO pbancDto = jpaQueryFactory.select(Projections.fields(BizPbancCustomApiResponseVO.class,
                                    bizPbancMaster.bizPbancNo,
                                    bizPbancMaster.bizPbancType,
                                    bizPbancMaster.bizPbancCtgr,
                                    bizPbancMaster.bizPbancNm,
                                    bizPbancMaster.bizPbancMaxTm,
                                    bizPbancMaster.bizPbancYr,
                                    bizPbancMaster.bizPbancRnd,
                                    bizPbancMaster.bizPbancMaxInst,
                                    bizPbancMaster.bizPbancSlctnMeth,
                                    bizPbancMaster.bizPbancInstrSlctnMeth,
                                    bizPbancMaster.bizPbancStts,
                                    bizPbancMaster.bizPbancRcptBgng,
                                    bizPbancMaster.bizPbancRcptEnd,
                                    bizPbancMaster.bizPbancSprtBgng,
                                    bizPbancMaster.bizPbancSprtEnd,
                                    bizPbancMaster.bizPbancRsltYmd,
                                    bizPbancMaster.bizPbancCn,
                                    bizPbancMaster.bizPbancPeprYn,
                                    bizPbancMaster.bizPbancPicTel,
                                    bizPbancMaster.createDateTime,
                                    bizPbancMaster.registUserId,
                                    bizPbancMaster.updateDateTime,
                                    bizPbancMaster.modifyUserId,
                                    adminUser.userName
                            ))
                            .from(bizPbancMaster)
                            .leftJoin(adminUser).on(adminUser.userId.eq(bizPbancMaster.registUserId))
                            .where(bizPbancMaster.bizPbancNo.eq(dto.getBizPbancNo()))
                            .fetchOne();

                    dto.setBizPbancMaster(pbancDto);
                }
            }
            return dtos;

        } else if(requestObject instanceof MyBusinessStateViewRequestVO) { /** 사업 참여 현황 - 진행 중 리스트 */
            List<BizOrganizationAplyCustomApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(BizOrganizationAplyCustomApiResponseVO.class,
                            bizOrganizationAply.bizOrgAplyNo,
                            bizOrganizationAply.bizPbancNo,
                            bizOrganizationAply.orgCd,
                            bizOrganizationAply.bizOrgAplyRprsvNm,
                            bizOrganizationAply.bizOrgAplyTime,
                            bizOrganizationAply.bizOrgAplyTimeFrst,
                            bizOrganizationAply.bizOrgAplyRgn,
                            bizOrganizationAply.bizOrgAplyPic,
                            bizOrganizationAply.bizOrgAplyPicNm,
                            bizOrganizationAply.bizOrgAplyPicJbgd,
                            bizOrganizationAply.bizOrgAplyPicEml,
                            bizOrganizationAply.bizOrgAplyPicTelno,
                            bizOrganizationAply.bizOrgAplyPicMpno,
                            bizOrganizationAply.bizOrgAplyStts,
                            bizOrganizationAply.bizOrgAplySttsCmnt,
                            bizOrganizationAply.bizOrgAplyChgYn,
                            bizOrganizationAply.bizOrgAplyOperCtgr,
                            bizOrganizationAply.bizOrgAplyOperMeth,
                            bizOrganizationAply.bizOrgAplyLsnPlanBgng,
                            bizOrganizationAply.bizOrgAplyLsnPlanEnd,
                            bizOrganizationAply.bizOrgAplyLsnPlanTrgt,
                            bizOrganizationAply.bizOrgAplyLsnPlanNope,
                            bizOrganizationAply.bizOrgAplyLsnPlanDscr1,
                            bizOrganizationAply.bizOrgAplyLsnPlanDscr2,
                            bizOrganizationAply.bizOrgAplyLsnPlanDscr3,
                            bizOrganizationAply.bizOrgAplyLsnPlanEtcInstr,
                            bizOrganizationAply.bizOrgAplyPeprYn,
                            bizOrganizationAply.bizOrgAplyLsnPlanEtc,
                            bizOrganizationAply.bizOrgAplyFile,
                            bizOrganizationAply.createDateTime,
                            bizOrganizationAply.updateDateTime,
                            bizOrganizationAply.modifyUserId
                    ))
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetch();

            if (!dtos.isEmpty() && dtos.size() > 0) {
                for (BizOrganizationAplyCustomApiResponseVO dto : dtos) {
                    dto.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(dto.getOrgCd()))
                            .fetchOne());

                    List<OrganizationAuthorityHistory> organizationAuthorityHistories = jpaQueryFactory.selectFrom(organizationAuthorityHistory)
                            .where(organizationAuthorityHistory.userId.eq(dto.getBizOrgAplyPic()), organizationAuthorityHistory.bizAuthorityApprovalState.eq("2"))
                            .orderBy(organizationAuthorityHistory.createDateTime.asc())
                            .fetch();

                    if (organizationAuthorityHistories != null && organizationAuthorityHistories.size() > 0)
                        dto.setOrganization(organizationAuthorityHistories.get(0));

                    List<BizOrganizationAplyDtl> bizOrganizationAplyDtls = jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                            .where(bizOrganizationAplyDtl.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .fetch();

                    if (bizOrganizationAplyDtls != null && bizOrganizationAplyDtls.size() > 0) {
                        int i = 0;
                        for (BizOrganizationAplyDtl dtlEntity : bizOrganizationAplyDtls) {
                            List<BizSurveyAns> bizSurveyAnsEntity = jpaQueryFactory.selectFrom(bizSurveyAns)
                                    .where(bizSurveyAns.bizSurveyTrgtNo.eq(dtlEntity.getBizOrgAplyDtlNo()))
                                    .fetch();

                            if (bizSurveyAnsEntity != null && bizSurveyAnsEntity.size() > 0) {
                                dtlEntity.setBizSurveyAns(bizSurveyAnsEntity);
                                i = i + 1;
                            }

                            List<BizInstructorAsgnm> asgnmEntities = jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                                    .where(bizInstructorAsgnm.bizOrgAplyDtlNo.eq(dtlEntity.getBizOrgAplyDtlNo()))
                                    .fetch();

                            if (asgnmEntities != null && asgnmEntities.size() > 0) {
                                dtlEntity.setBizInstructorAsgnms(asgnmEntities);

                                for (BizInstructorAsgnm asgnmEntity : asgnmEntities) {
                                    asgnmEntity.setBizInstructorAply(jpaQueryFactory.selectFrom(bizInstructorAply)
                                            .where(bizInstructorAply.bizInstrAplyNo.eq(asgnmEntity.getBizInstrAplyNo()))
                                            .fetchOne());
                                }
                            }
                        }

                        if (i == bizOrganizationAplyDtls.size())
                            dto.setSurveyEnd(1);
                        dto.setBizOrganizationAplyDtls(bizOrganizationAplyDtls);
                    }

                    BizPbancCustomApiResponseVO pbancDto = jpaQueryFactory.select(Projections.fields(BizPbancCustomApiResponseVO.class,
                                    bizPbancMaster.bizPbancNo,
                                    bizPbancMaster.bizPbancType,
                                    bizPbancMaster.bizPbancCtgr,
                                    bizPbancMaster.bizPbancNm,
                                    bizPbancMaster.bizPbancMaxTm,
                                    bizPbancMaster.bizPbancYr,
                                    bizPbancMaster.bizPbancRnd,
                                    bizPbancMaster.bizPbancMaxInst,
                                    bizPbancMaster.bizPbancSlctnMeth,
                                    bizPbancMaster.bizPbancInstrSlctnMeth,
                                    bizPbancMaster.bizPbancStts,
                                    bizPbancMaster.bizPbancRcptBgng,
                                    bizPbancMaster.bizPbancRcptEnd,
                                    bizPbancMaster.bizPbancSprtBgng,
                                    bizPbancMaster.bizPbancSprtEnd,
                                    bizPbancMaster.bizPbancRsltYmd,
                                    bizPbancMaster.bizPbancCn,
                                    bizPbancMaster.bizPbancPeprYn,
                                    bizPbancMaster.bizPbancPicTel,
                                    bizPbancMaster.createDateTime,
                                    bizPbancMaster.registUserId,
                                    bizPbancMaster.updateDateTime,
                                    bizPbancMaster.modifyUserId,
                                    adminUser.userName
                            ))
                            .from(bizPbancMaster)
                            .leftJoin(adminUser).on(adminUser.userId.eq(bizPbancMaster.registUserId))
                            .where(bizPbancMaster.bizPbancNo.eq(dto.getBizPbancNo()))
                            .fetchOne();

                    dto.setBizPbancMaster(pbancDto);
                }
            }
            return dtos;

        } else if(requestObject instanceof MyBusinessStateSurveyViewRequestVO) { /** 사업 참여 현황 - 진행 중 리스트 - 상호평가 */
            List<MyBusinessStateSurveyApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(MyBusinessStateSurveyApiResponseVO.class,
                            bizPbancMaster.bizPbancNm,
                            bizPbancMaster.bizPbancNo,
                            bizOrganizationAply.bizOrgAplyNo,
                            bizOrganizationAply.bizOrgAplyLsnPlanBgng,
                            bizOrganizationAply.bizOrgAplyLsnPlanEnd
                    ))
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizOrganizationAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (dtos != null && dtos.size() > 0){
                for (MyBusinessStateSurveyApiResponseVO dto : dtos){
                    List<BizInstructorAsgnm> subBizInstructorAsgnms = jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                            .innerJoin(bizOrganizationAplyDtl).on(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                            .innerJoin(bizInstructorAply).on(bizInstructorAply.bizInstrAplyNo.eq(bizInstructorAsgnm.bizInstrAplyNo))
                            .where(bizInstructorAsgnm.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .orderBy(bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.desc(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlRnd.desc())
                            .limit(100000L)
                            .fetch();

                    Map<String, List<BizInstructorAsgnm>> groupBizInstructorAsgnms = subBizInstructorAsgnms.stream()
                            .collect(Collectors.groupingBy(BizInstructorAsgnm::getBizInstrAplyNo));

                    List<BizInstructorAsgnm> bizInstructorAsgnms = new ArrayList<>();
                    groupBizInstructorAsgnms.forEach((key, value) -> {
                        bizInstructorAsgnms.add(value.get(0));
                    });

                    if (bizInstructorAsgnms != null && bizInstructorAsgnms.size() > 0) {
                        for (BizInstructorAsgnm instructorAsgnm : bizInstructorAsgnms) {
                            BizOrganizationAplyDtl organizationAplyDtl = jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                                    .where(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(instructorAsgnm.getBizOrgAplyDtlNo()))
                                    .fetchOne();

                            if (organizationAplyDtl != null) {
                                instructorAsgnm.setBizOrganizationAplyDtl(organizationAplyDtl);
                            }

                            List<BizSurvey> bizSurveyEntities = jpaQueryFactory.selectFrom(bizSurvey)
                                    .where(bizSurvey.bizSurveyCtgr.eq(2), bizSurvey.bizSurveyStts.eq(1))
                                    .orderBy(bizSurvey.createDateTime.desc())
                                    .fetch();

                            if (bizSurveyEntities.size() > 0 && bizSurveyEntities != null) {
                                BizSurvey survey = bizSurveyEntities.get(0);
                                survey.setBizSurveyQitems(jpaQueryFactory.selectFrom(bizSurveyQitem)
                                        .where(bizSurveyMaster.bizSurveyNo.eq(bizSurveyEntities.get(0).getBizSurveyNo()))
                                        .innerJoin(bizSurveyMaster).on(bizSurveyMaster.bizSurveyQitemNo.eq(bizSurveyQitem.bizSurveyQitemNo))
                                        .fetch());
                                instructorAsgnm.setBizSurvey(survey);
                            }

                            List<BizSurveyAns> bizSurveyAnsList = jpaQueryFactory.selectFrom(bizSurveyAns)
                                    .where(bizSurveyAns.bizSurveyTrgtNo.eq(instructorAsgnm.getBizInstrAplyNo()))
                                    .fetch();

                            if (bizSurveyAnsList != null && bizSurveyAnsList.size()>0) {
                                instructorAsgnm.setBizSurveyAnsList(bizSurveyAnsList);
                            }
                        }
                        dto.setBizInstructorAsgnms(bizInstructorAsgnms);
                    }
                }
            }
            return dtos;

        } else if(requestObject instanceof MyBusinessStateCompleteViewRequestVO) { /** 사업 참여 현황 - 완료 리스트 */
            List<BizOrganizationAply> entities = jpaQueryFactory.selectFrom(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizOrganizationAply.bizOrgAplyLsnPlanBgng.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (entities != null && entities.size() > 0){
                for (BizOrganizationAply entity : entities){
                    BizOrganizationRsltRpt rsltRptEntity = jpaQueryFactory.selectFrom(bizOrganizationRsltRpt)
                            .where(bizOrganizationRsltRpt.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()))
                            .fetchOne();

                    if (rsltRptEntity != null && !rsltRptEntity.equals(null)) {
                        entity.setBizOrganizationRsltRpt(rsltRptEntity);
                    }

                    OrganizationInfo orgInfo = jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(entity.getOrgCd()))
                            .fetchOne();

                    if (orgInfo != null && !orgInfo.equals(null)) {
                        entity.setOrganizationInfo(orgInfo);
                    }

                    if (entity.getBizOrganizationAplyDtls().size() > 0 && !entity.getBizOrganizationAplyDtls().isEmpty()) {
                        for (BizOrganizationAplyDtl dtlEntity : entity.getBizOrganizationAplyDtls()) {

                            List<BizInstructorAsgnm> asgnmEntities = jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                                    .where(bizInstructorAsgnm.bizOrgAplyDtlNo.eq(dtlEntity.getBizOrgAplyDtlNo()))
                                    .fetch();

                            if (asgnmEntities != null && asgnmEntities.size() > 0) {
                                dtlEntity.setBizInstructorAsgnms(asgnmEntities);
                            }

                            List<BizInstructorIdentifyDtl> bizInstructorIdentifyDtls = jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                                    .where(bizInstructorIdentifyDtl.bizOrgAplyDtlNo.eq(dtlEntity.getBizOrgAplyDtlNo()))
                                    .fetch();

                            if (bizInstructorIdentifyDtls != null && bizInstructorIdentifyDtls.size() > 0) {
                                dtlEntity.setBizInstructorIdentifyDtls(bizInstructorIdentifyDtls);
                            }
                        }
                    }
                }
            }
            return entities;

        } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 사업 참여 현황 - 강의 확인서 */
            List<BizInstructorIdentify> entities = jpaQueryFactory.selectFrom(bizInstructorIdentify)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorIdentify.bizOrgAplyNo))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorIdentify.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (entities.size() > 0 && !entities.isEmpty()) {
                for (BizInstructorIdentify entity : entities) {
                    BizOrganizationAply bizOrganizationAplyEntity = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()))
                            .fetchOne();

                    if (bizOrganizationAplyEntity != null && !bizOrganizationAplyEntity.equals(null)) {
                        bizOrganizationAplyEntity.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                .where(organizationInfo.organizationCode.eq(bizOrganizationAplyEntity.getOrgCd()))
                                .fetchOne());
                        entity.setBizOrganizationAply(bizOrganizationAplyEntity);

                        entity.setBizPbancMaster(jpaQueryFactory.selectFrom(bizPbancMaster)
                                .where(bizPbancMaster.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                                .fetchOne());
                    }
                    LmsUser user = jpaQueryFactory.selectFrom(lmsUser)
                            .where(lmsUser.userId.eq(entity.getRegistUserId()))
                            .fetchOne();
                    entity.setUserName(user.getUserName());
                }
            }
            return entities;

        } else if(requestObject instanceof MyInstructorStateApplyViewRequestVO) { /** 강사 참여 현황 - 강사 모집 - 지원 중 */
            List<BizInstructorAply> entities = jpaQueryFactory.selectFrom(bizInstructorAply)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorAply.bizOrgAplyNo))
                    .leftJoin(bizInstructorPbanc).on(bizInstructorPbanc.bizInstrNo.eq(bizInstructorAply.bizInstrNo), bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorPbanc.createDateTime.desc(), bizInstructorAply.bizInstrAplyCndtOrdr.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (entities != null && entities.size() > 0){
                for (BizInstructorAply entity : entities){
                    entity.setBizInstructor(jpaQueryFactory.selectFrom(bizInstructor)
                            .where(bizInstructor.bizInstrNo.eq(entity.getBizInstrNo()))
                            .fetchOne());

                    BizOrganizationAply bizOrganizationAplyEntity = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()))
                            .fetchOne();

                    if (bizOrganizationAplyEntity != null && !bizOrganizationAplyEntity.equals(null)) {
                        bizOrganizationAplyEntity.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                .where(organizationInfo.organizationCode.eq(bizOrganizationAplyEntity.getOrgCd()))
                                .fetchOne());
                        entity.setBizOrganizationAply(bizOrganizationAplyEntity);

                        entity.setBizPbancMaster(jpaQueryFactory.selectFrom(bizPbancMaster)
                                .where(bizPbancMaster.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                                .fetchOne());

                        entity.setBizInstructorPbanc(jpaQueryFactory.selectFrom(bizInstructorPbanc)
                                .where(bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()),
                                        bizInstructorPbanc.bizInstrNo.eq(entity.getBizInstrNo()))
                                .fetchOne());
                    }
                }
            }

            return entities;

        } else if(requestObject instanceof MyInstructorStateApplyResultViewRequestVO) { /** 강사 참여 현황 - 강사 모집 - 지원 완료 */
            List<MyInstructorStateAplyCustomApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(MyInstructorStateAplyCustomApiResponseVO.class,
                            bizInstructorAply.bizInstrAplyNo,
                            bizInstructorAply.bizInstrNo,
                            bizInstructorAply.bizOrgAplyNo,
                            bizInstructorAply.bizInstrAplyInstrNm,
                            bizInstructorAply.bizInstrAplyInstrId,
                            bizInstructorAply.bizInstrAplyCndtOrdr,
                            bizInstructorAply.bizInstrAplyCndtDist,
                            bizInstructorAply.bizInstrAplySlctnScr,
                            bizInstructorAply.bizInstrAplySlctnCndt,
                            bizInstructorAply.bizInstrAplyCmnt,
                            bizInstructorAply.bizInstrAplyStts,
                            bizInstructorAply.registUserId
                    ))
                    .from(bizInstructorAply)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorAply.bizOrgAplyNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .leftJoin(bizInstructor).on(bizInstructor.bizInstrNo.eq(bizInstructorAply.bizInstrNo))
                    .leftJoin(bizInstructorPbanc).on(bizInstructorPbanc.bizInstrNo.eq(bizInstructorAply.bizInstrNo),
                            bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorAply.bizInstrAplyCndtOrdr.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (dtos != null && dtos.size() > 0){
                for (MyInstructorStateAplyCustomApiResponseVO dto : dtos){
                    dto.setBizInstructor(jpaQueryFactory.selectFrom(bizInstructor)
                            .where(bizInstructor.bizInstrNo.eq(dto.getBizInstrNo()))
                            .fetchOne());

                    BizOrganizationAply bizOrganizationAplyEntity = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .fetchOne();

                    dto.setBizInstructorDist(jpaQueryFactory.selectFrom(bizInstructorDist)
                            .where(bizInstructorDist.bizInstrAplyNo.eq(dto.getBizInstrAplyNo()),
                                    bizInstructorDist.registUserId.eq(dto.getBizInstrAplyInstrId()))
                            .fetchOne());

                    bizOrganizationAplyEntity.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(bizOrganizationAplyEntity.getOrgCd()))
                            .fetchOne());

                    dto.setBizOrganizationAply(bizOrganizationAplyEntity);
                    dto.setBizPbancMaster(jpaQueryFactory.selectFrom(bizPbancMaster)
                            .where(bizPbancMaster.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                            .fetchOne());

                    dto.setBizInstructorPbanc(jpaQueryFactory.selectFrom(bizInstructorPbanc)
                            .where(bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()),
                                    bizInstructorPbanc.bizInstrNo.eq(dto.getBizInstrNo()))
                            .fetchOne());
                }
            }
            return dtos;

        } else if(requestObject instanceof MyInstructorStateViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 진행 중 */
            List<MyInstructorStateCustomApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(MyInstructorStateCustomApiResponseVO.class,
                            bizInstructorAply.bizInstrAplyNo,
                            bizInstructorAply.bizInstrNo,
                            bizInstructorAply.bizOrgAplyNo,
                            bizInstructorAply.bizInstrAplyInstrNm,
                            bizInstructorAply.bizInstrAplyInstrId,
                            bizInstructorAply.bizInstrAplyCndtOrdr,
                            bizInstructorAply.bizInstrAplyCndtDist,
                            bizInstructorAply.bizInstrAplySlctnScr,
                            bizInstructorAply.bizInstrAplySlctnCndt,
                            bizInstructorAply.bizInstrAplyCmnt,
                            bizInstructorAply.bizInstrAplyStts,
                            bizInstructorIdentify.registUserId,
                            bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,7).as("month")
                    ))
                    .from(bizInstructorAply)
                    .innerJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorAply.bizOrgAplyNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .innerJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .innerJoin(bizInstructorAsgnm).on(bizInstructorAsgnm.bizInstrAplyNo.eq(bizInstructorAply.bizInstrAplyNo))
                    .innerJoin(bizOrganizationAplyDtl).on(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                    .leftJoin(bizInstructorIdentify).on(bizInstructorIdentify.bizOrgAplyNo.eq(bizOrganizationAply.bizOrgAplyNo),
                            bizInstructorIdentify.bizInstrIdntyYm.startsWith(bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,4)),
                            bizInstructorIdentify.bizInstrIdntyYm.endsWith(bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(5,7)),
                            bizInstructorIdentify.registUserId.eq(bizInstructorAply.bizInstrAplyInstrId))
                    .where(getQuery(requestObject))
                    .groupBy(bizInstructorAply.bizInstrAplyNo, bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,7))
                    .orderBy(bizPbancMaster.bizPbancYr.desc(), organizationInfo.organizationName.asc(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlRnd.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .distinct()
                    .fetch();

            if (dtos != null && dtos.size() > 0){
                for (MyInstructorStateCustomApiResponseVO dto : dtos){
                    dto.setBizInstructor(jpaQueryFactory.selectFrom(bizInstructor)
                            .where(bizInstructor.bizInstrNo.eq(dto.getBizInstrNo()))
                            .fetchOne());

                    BizOrganizationAply bizOrganizationAplyEntity = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .fetchOne();

                    BizInstructorPbanc bizInstrPbanc =jpaQueryFactory.selectFrom(bizInstructorPbanc)
                            .where(bizInstructorPbanc.bizInstrNo.eq(dto.getBizInstrNo()),
                                    bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                            .fetchOne();
                    if(!bizInstrPbanc.equals(null)){
                        dto.setSequenceNo(bizInstrPbanc.getSequenceNo());
                    }
                    bizOrganizationAplyEntity.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(bizOrganizationAplyEntity.getOrgCd()))
                            .fetchOne());

                    dto.setBizOrganizationAply(bizOrganizationAplyEntity);
                    BizPbancMaster pbancMaster = jpaQueryFactory.selectFrom(bizPbancMaster)
                            .where(bizPbancMaster.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                            .fetchOne();
                    dto.setBizPbancMaster(pbancMaster);

                    // 강사 신청서에 해당하는 강사 배정 리스트 호출
                    List<BizInstructorAsgnm> bizInstructorAsgnms = jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                            .where(bizInstructorAsgnm.bizInstrAplyNo.eq(dto.getBizInstrAplyNo()), bizInstructorAsgnm.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()),
                                    bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0, 7).eq(dto.getMonth()))
                            .innerJoin(bizOrganizationAplyDtl).on(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                            .orderBy(bizOrganizationAplyDtl.bizOrgAplyLsnDtlRnd.asc())
                            .groupBy(bizInstructorAsgnm.bizOrgAplyDtlNo)
                            .fetch();

                    BizInstructorDist instructorDist = jpaQueryFactory.selectFrom(bizInstructorDist)
                            .where(bizInstructorDist.registUserId.eq(dto.getBizInstrAplyInstrId()), bizInstructorDist.bizInstrAplyNo.eq(dto.getBizInstrAplyNo()),
                                    bizInstructorDist.bizDistStts.eq(1))
                            .fetchOne();

                    if (instructorDist != null) {
                        dto.setBizInstructorDist(instructorDist);
                    }

                    // 강사 배정 리스트 있을 시
                    if (bizInstructorAsgnms != null && bizInstructorAsgnms.size() > 0){
                        // 최근 정산 마감일 호출
                        BizInstructorClclnDdln bizInstructorClclnDdlnEntity = ((MyInstructorStateViewRequestVO) requestObject).getStandClclnDdlnNearDate();
                        List<BizOrganizationAplyDtl> bizOrganizationAplyDtlList = new ArrayList<>();

                        for (BizInstructorAsgnm asgnm : bizInstructorAsgnms) {
                            BizOrganizationAplyDtl dtl = jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                                    .where(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(asgnm.getBizOrgAplyDtlNo()),
                                            bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0, 7).eq(dto.getMonth()))
                                    .fetchOne();
                            if(dtl != null){
                                bizOrganizationAplyDtlList.add(dtl);
                            }
                        }
                        dto.setBizOrganizationAplyDtls(bizOrganizationAplyDtlList);
                        List<BizInstructorIdentify> bizInstructorIdentifyEntity = jpaQueryFactory.selectFrom(bizInstructorIdentify)
                                .where(bizInstructorIdentify.bizOrgAplyNo.eq(bizOrganizationAplyEntity.getBizOrgAplyNo()),
                                        bizInstructorIdentify.bizInstrIdntyYm.eq(dto.getMonth().replace("-","")),
                                        bizInstructorIdentify.registUserId.eq(dto.getBizInstrAplyInstrId()))
                                .orderBy(bizInstructorIdentify.createDateTime.desc())
                                .fetch();
                        if(bizInstructorIdentifyEntity!=null && bizInstructorIdentifyEntity.size() > 0){
                            dto.setBizInstrIdnty(bizInstructorIdentifyEntity.get(0));
                            dto.setBizInstrIdntyNo(bizInstructorIdentifyEntity.get(0).getBizInstrIdntyNo());
                        }
                    }
                }
            }

            return dtos;

        } else if(requestObject instanceof MyInstructorStateCompleteViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 완료 */
            List<MyInstructorStateCompleteCustomApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(MyInstructorStateCompleteCustomApiResponseVO.class,
                            bizInstructorAsgnm.bizInstrAsgnmNo,
                            bizInstructorAsgnm.bizInstrNo,
                            bizInstructorAsgnm.bizOrgAplyNo,
                            bizInstructorAsgnm.bizOrgAplyDtlNo,
                            bizInstructorAsgnm.bizInstrAplyNo
                    ))
                    .from(bizInstructorAsgnm)
                    .innerJoin(bizInstructorIdentifyDtl).on(bizInstructorIdentifyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                    .innerJoin(bizInstructorIdentify).on(bizInstructorIdentify.bizInstrIdntyNo.eq(bizInstructorIdentifyDtl.bizInstrIdntyNo))
                    .innerJoin(bizOrganizationAplyDtl).on(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(bizInstructorAsgnm.bizOrgAplyDtlNo))
                    .innerJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizOrganizationAplyDtl.bizOrgAplyNo))
                    .innerJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.desc(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlRnd.desc())
                    .fetch();

            if (dtos != null && dtos.size() > 0){
                for (MyInstructorStateCompleteCustomApiResponseVO dto : dtos){
                    BizOrganizationAply organizationAply = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .fetchOne();

                    if (organizationAply != null) {
                        organizationAply.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                .where(organizationInfo.organizationCode.eq(organizationAply.getOrgCd()))
                                .fetchOne());
                        organizationAply.setBizPbancMaster(jpaQueryFactory.selectFrom(bizPbancMaster)
                                .where(bizPbancMaster.bizPbancNo.eq(organizationAply.getBizPbancNo()))
                                .fetchOne());
                        dto.setBizOrganizationAply(organizationAply);
                    }

                    BizInstructorAply instructorAply = jpaQueryFactory.selectFrom(bizInstructorAply)
                            .where(bizInstructorAply.bizInstrAplyNo.eq(dto.getBizInstrAplyNo()))
                            .fetchOne();

                    if (instructorAply != null) {
                        dto.setBizInstructorAply(instructorAply);

                        dto.setBizInstructorPbanc(jpaQueryFactory.selectFrom(bizInstructorPbanc)
                                .where(bizInstructorPbanc.bizInstrNo.eq(instructorAply.getBizInstrNo()),
                                        bizInstructorPbanc.bizPbancNo.eq(organizationAply.getBizPbancNo()))
                                .fetchOne());

                        List<BizInstructorIdentify> bizInstructorIdentifies = jpaQueryFactory.selectFrom(bizInstructorIdentify)
                                .where(bizInstructorIdentify.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()),
                                        bizInstructorIdentify.registUserId.eq(instructorAply.getBizInstrAplyInstrId()))
                                .fetch();

                        if (bizInstructorIdentifies != null && bizInstructorIdentifies.size() > 0) {
                            for (BizInstructorIdentify instructorIdentify : bizInstructorIdentifies) {
                                List<BizInstructorIdentifyDtl> bizInstructorIdentifyDtls = jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                                        .where(bizInstructorIdentifyDtl.bizInstrIdntyNo.eq(instructorIdentify.getBizInstrIdntyNo()))
                                        .fetch();

                                if (bizInstructorIdentifyDtls != null && bizInstructorIdentifyDtls.size() > 0) {
                                    for (BizInstructorIdentifyDtl instructorIdentifyDtl : bizInstructorIdentifyDtls) {
                                        instructorIdentifyDtl.setBizOrganizationAplyDtl(jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                                                .where(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(instructorIdentifyDtl.getBizOrgAplyDtlNo()))
                                                .fetchOne());
                                    }
                                    instructorIdentify.setBizInstructorIdentifyDtls(bizInstructorIdentifyDtls);
                                }
                            }
                            dto.setBizInstructorIdentifies(bizInstructorIdentifies);
                        }
                    }

                    List<BizSurvey> bizSurveyEntities = jpaQueryFactory.selectFrom(bizSurvey)
                            .where(bizSurvey.bizSurveyCtgr.eq(1), bizSurvey.bizSurveyStts.eq(1))
                            .orderBy(bizSurvey.createDateTime.desc())
                            .fetch();

                    if (bizSurveyEntities.size() > 0 && bizSurveyEntities != null) {
                        bizSurveyEntities.get(0).setBizSurveyQitems(jpaQueryFactory.selectFrom(bizSurveyQitem)
                                .where(bizSurveyMaster.bizSurveyNo.eq(bizSurveyEntities.get(0).getBizSurveyNo()))
                                .innerJoin(bizSurveyMaster).on(bizSurveyMaster.bizSurveyQitemNo.eq(bizSurveyQitem.bizSurveyQitemNo))
                                .fetch());
                        dto.setBizSurvey(bizSurveyEntities.get(0));
                    }

                    List<BizSurveyAns> bizSurveyAnsList = jpaQueryFactory.selectFrom(bizSurveyAns)
                            .where(bizSurveyAns.bizSurveyTrgtNo.eq(dto.getBizOrgAplyNo()), bizSurveyAns.registUserId.eq(((MyInstructorStateCompleteViewRequestVO) requestObject).getUserId()))
                            .fetch();

                    if (bizSurveyAnsList != null && bizSurveyAnsList.size()>0) {
                        dto.setBizSurveyAns(bizSurveyAnsList);
                    }

                    LmsUser user = jpaQueryFactory.selectFrom(lmsUser)
                            .where(lmsUser.userId.eq(((MyInstructorStateCompleteViewRequestVO) requestObject).getUserId()))
                            .fetchOne();

                    if (user != null) {
                        dto.setLmsUser(user);
                    }

                    List<InstructorInfo> instructor = jpaQueryFactory.selectFrom(instructorInfo)
                            .where(instructorInfo.userId.eq(((MyInstructorStateCompleteViewRequestVO) requestObject).getUserId()))
                            .orderBy(instructorInfo.createDateTime.desc())
                            .fetch();

                    if (instructor != null && instructor.size() > 0) {
                        dto.setInstructorInfo(instructor.get(0));
                    }
                }
            }
            return dtos;

        } else if(requestObject instanceof MyInstructorStateCalculateViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 정산 */
            List<BizInstructorIdentify> entities = jpaQueryFactory.selectFrom(bizInstructorIdentify)
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorIdentify.bizInstrIdntyYm.asc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (entities != null && entities.size() > 0){
                for (BizInstructorIdentify entity : entities){
                    if(entity.getBizInstructorIdentifyDtls() !=null && entity.getBizInstructorIdentifyDtls().size()>0 ){
                        for(BizInstructorIdentifyDtl dtl:entity.getBizInstructorIdentifyDtls()){
                            dtl.setBizOrganizationAplyDtl(jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                                    .where(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(dtl.getBizOrgAplyDtlNo()))
                                    .fetchOne());
                        }
                    }
                    BizOrganizationAply bizOrganizationAplyEntity = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()))
                            .fetchOne();

                    if(bizOrganizationAplyEntity!=null && bizOrganizationAplyEntity.getBizOrganizationAplyDtls().size()>0){
                        entity.getBizOrganizationAplyDtls().add(bizOrganizationAplyEntity.getBizOrganizationAplyDtls().get(0));
                        entity.getBizOrganizationAplyDtls().add(bizOrganizationAplyEntity.getBizOrganizationAplyDtls().get(bizOrganizationAplyEntity.getBizOrganizationAplyDtls().size()-1));
                    }
                    OrganizationInfo organizationInfoEntity = jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(bizOrganizationAplyEntity.getOrgCd()))
                            .fetchOne();
                    bizOrganizationAplyEntity.setOrganizationInfo(organizationInfoEntity);
                    entity.setBizOrganizationAply(bizOrganizationAplyEntity);

                    entity.setBizPbancMaster(jpaQueryFactory.selectFrom(bizPbancMaster)
                            .where(bizPbancMaster.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                            .fetchOne());
                    entity.setCalculateYM(entity.getUpdateDateTime());

                    BizInstructorAply instructorAply = jpaQueryFactory.selectFrom(bizInstructorAply)
                            .where(bizInstructorAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()), bizInstructorAply.bizInstrAplyInstrId.eq(entity.getRegistUserId()))
                            .fetchOne();
                    entity.setBizInstructorPbanc(jpaQueryFactory.selectFrom(bizInstructorPbanc)
                            .where(bizInstructorPbanc.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()), bizInstructorPbanc.bizInstrNo.eq(instructorAply.getBizInstrNo()))
                            .fetchOne());
                }
            }
            return entities;

        } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 참여 현황 - 강사 지원 문의 */
            List<BizInstructorQuestion> entities = jpaQueryFactory.selectFrom(bizInstructorQuestion)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorQuestion.bizOrgAplyNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorQuestion.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (entities != null && entities.size() > 0) {
                for (BizInstructorQuestion entity : entities) {
                    BizOrganizationAply bizOrganizationAplyEntity = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()))
                            .fetchOne();

                    if (!bizOrganizationAplyEntity.equals(null) || bizOrganizationAplyEntity != null) {
                        OrganizationInfo organizationInfoEntity = jpaQueryFactory.selectFrom(organizationInfo)
                                .where(organizationInfo.organizationCode.eq(bizOrganizationAplyEntity.getOrgCd()))
                                .fetchOne();

                        bizOrganizationAplyEntity.setOrganizationInfo(organizationInfoEntity);
                        entity.setBizOrganizationAply(bizOrganizationAplyEntity);
                        entity.setBizPbancMaster(jpaQueryFactory.selectFrom(bizPbancMaster)
                                .where(bizPbancMaster.bizPbancNo.eq(bizOrganizationAplyEntity.getBizPbancNo()))
                                .fetchOne());
                    }
                }
            }

            return entities;

        } else if(requestObject instanceof MyEducationStateViewRequestVO) { /** 교육 수료 현황 */
            return jpaQueryFactory.selectFrom(curriculumApplicationMaster)
                .where(getQuery(requestObject))
                .orderBy(curriculumApplicationMaster.createDateTime.desc())
                .offset(requestObject.getPageable().getOffset())
                .limit(requestObject.getPageable().getPageSize())
                .fetch().stream().peek(data -> {
                    if(data.getEducationPlan().getCurriculumMaster().getEducationType().equals(Code.EDU_TYPE.LECTURE.enumCode)) {
                        if (data.getSetEducationType().equals(Code.EDU_TYPE.VIDEO.enumCode)) {
                            data.getEducationPlan().getCurriculumMaster().setCurriculumEvaluateList(data.getEducationPlan().getCurriculumMaster().getCurriculumEvaluateList()
                                    .stream().filter(value -> value.getSortNo() == 0).collect(Collectors.toList()));
                        } else {
                            data.getEducationPlan().getCurriculumMaster().setCurriculumEvaluateList(data.getEducationPlan().getCurriculumMaster().getCurriculumEvaluateList()
                                    .stream().filter(value -> value.getSortNo() == 1).collect(Collectors.toList()));
                        }
                    }

                    if(data.getEducationPlan().getIsReview() && data.getEducationPlan().getAvailableReviewTerm() != null && !StringUtils.isEmpty(data.getCompleteDateTime())) { /** 복습을 지원하고, 수료를 한 경우 */
                        try {
                            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                            Date completeDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.getCompleteDateTime());
                            if(data.getEducationPlan().getAvailableReviewTerm() > 0) {
                                data.setReviewDateTime(new LocalDateTime(completeDateTime).plusMonths(data.getEducationPlan().getAvailableReviewTerm()).toString(formatter));
                            } else {
                                data.setReviewDateTime("2999-12-31 23:59:59");
                            }
                        } catch (ParseException e) {
                            throw new RuntimeException("교육 수료 현황 조회 중 날짜 파싱에 실패하였습니다.");
                        }
                    }
                }).collect(Collectors.toList());
        } else if(requestObject instanceof ClassGuideViewRequestVO) { /** 수업지도안 */
            List<ClassGuide> entities = jpaQueryFactory.selectFrom(classGuide)
                    .where(getQuery(requestObject))
                    .orderBy(classGuide.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (!entities.isEmpty() && entities.size() > 0) {
                for (ClassGuide entity : entities) {
                    List<ClassGuideFile> guideFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.TEACH.enumCode))
                            .fetch();
                    if (guideFileList.size() > 0 && !guideFileList.isEmpty())
                        entity.setGuideFileList(guideFileList);

                    List<ClassGuideFile> activityFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.ACTIVITY.enumCode))
                            .fetch();
                    if (activityFileList.size() > 0 && !activityFileList.isEmpty())
                        entity.setActivityFileList(activityFileList);

                    List<ClassGuideFile> answerFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.ANSWER.enumCode))
                            .fetch();
                    if (answerFileList.size() > 0 && !answerFileList.isEmpty())
                        entity.setAnswerFileList(answerFileList);

                    List<ClassGuideFile> nieFileList = jpaQueryFactory.selectFrom(classGuideFile)
                            .where(classGuideFile.classGuideCode.eq(entity.getClassGuideCode()), classGuideFile.fileType.eq(Code.GUI_FILE_TYPE.NIE.enumCode))
                            .fetch();
                    if (nieFileList.size() > 0 && !nieFileList.isEmpty())
                        entity.setNieFileList(nieFileList);

                    entity.setUserName(jpaQueryFactory.selectFrom(lmsUser)
                            .where(lmsUser.userId.eq(entity.getRegistUserId()))
                            .fetchOne().getUserName());

                    if(entity.getReferenceSubject() != null) {
                        List<ClassSubject> subjects = new ArrayList<>();
                        String[] list = entity.getReferenceSubject().split(",");

                        for (String str : list) {
                            subjects.add(jpaQueryFactory.selectFrom(classSubject)
                                    .where(classSubject.individualCode.eq(str))
                                    .fetchOne());
                        }

                        if (subjects.size() > 0 && !subjects.isEmpty()) {
                            int n = 0;
                            for (ClassSubject subject : subjects) {
                                List<String> subjectStrList = new ArrayList<>();
                                String code = subject.getIndividualCode();

                                for (int i=0; i < subject.getDepth(); i++) {
                                    ClassSubject subjectSub = jpaQueryFactory.selectFrom(classSubject)
                                            .where(classSubject.individualCode.eq(code))
                                            .orderBy(classSubject.individualCode.desc())
                                            .fetchOne();

                                    if (subjectSub != null && !subjectSub.equals(null)) {
                                        code = subjectSub.getUpIndividualCode();
                                        subjectStrList.add(subjectSub.getContent());
                                    }
                                }

                                if (subjectStrList.size() > 0 && !subjectStrList.isEmpty()) {
                                    for (int i = subjectStrList.size() - 1; i >= 0; i--) {
                                        if (i == subjectStrList.size()-1) {
                                            code = new StringBuilder("").append(subjectStrList.get(i)).toString();
                                        } else {
                                            code = new StringBuilder(code).append(">").append(subjectStrList.get(i)).toString();
                                        }
                                    }
                                }
                                subjects.get(n).setCodeInfo(code);
                                n++;
                            }
                            entity.setClassSubjectList(subjects);
                        }
                    }
                }
            }
            return entities;

        } else if(requestObject instanceof ClassSubjectViewRequestVO) { /** 수업지도안 - 교과 */
            return jpaQueryFactory.selectFrom(classSubject)
                    .where(getQuery(requestObject))
                    .orderBy(classSubject.depth.asc(), classSubject.order.asc())
                    .fetch();
        } else {
            return null;
        }
    }

    /**
     * where 절
     */
    private <T extends CSViewVOSupport> Predicate[] getQuery(T requestObject) {
        if(requestObject instanceof MyBizApplyViewRequestVO) { /** 사업 신청 내역 */
            return new Predicate[] { searchContainText(requestObject, ((MyBizApplyViewRequestVO) requestObject).getContainTextType(), ((MyBizApplyViewRequestVO) requestObject).getContainText()),
                    condition(((MyBizApplyViewRequestVO) requestObject).getSequenceNo(), bizAply.sequenceNo::eq),
                    condition(((MyBizApplyViewRequestVO) requestObject).getUserId(), bizAply.bizAplyUserID::eq),
                    condition(((MyBizApplyViewRequestVO) requestObject).getBizAplyStts(), bizAply.bizAplyStts::eq),
                    condition(((MyBizApplyViewRequestVO) requestObject).getYear(), bizPbancMaster.bizPbancYr::eq) };
        } else if(requestObject instanceof MyBusinessStateApplyViewRequestVO) { /** 사업 참여 현황 - 신청 리스트(가승인(승인이 될 결과공고 전 상태), 임시저장, 제출, 반려) */
            // 변경 이력 불러오기(변경 이력이 진행된 사업신청서 기준)
            List<String> bizEditHistStrs = jpaQueryFactory.select(bizEditHist.bizEditHistTrgtNo)
                    .from(bizEditHist)
                    .where(bizEditHist.bizEditHistTrgtNo.contains("BOA"))
                    .fetch().stream().distinct().collect(Collectors.toList());
            String[] editBizOrgAplyCodes = bizEditHistStrs.toArray(new String[bizEditHistStrs.size()]);

            if (((MyBusinessStateApplyViewRequestVO) requestObject).getBizOrgAplyStts().equals(3)) { // 승인 전 반려
                return new Predicate[] { condition(((MyBusinessStateApplyViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                        condition(editBizOrgAplyCodes, bizOrganizationAply.bizOrgAplyNo::notIn),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getOrganizationCode(), bizOrganizationAply.orgCd::eq),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getUserId(), bizOrganizationAply.bizOrgAplyPic::eq),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getBizOrgAplyStts(), bizOrganizationAply.bizOrgAplyStts::eq),
                        searchContainText(requestObject, ((MyBusinessStateApplyViewRequestVO) requestObject).getContainTextType(), ((MyBusinessStateApplyViewRequestVO) requestObject).getContainText())};
            } else {    // 임시저장, 신청, 가승인 상태
                return new Predicate[] { condition(((MyBusinessStateApplyViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getOrganizationCode(), bizOrganizationAply.orgCd::eq),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getUserId(), bizOrganizationAply.bizOrgAplyPic::eq),
                        condition(((MyBusinessStateApplyViewRequestVO) requestObject).getBizOrgAplyStts(), bizOrganizationAply.bizOrgAplyStts::eq),
                        searchContainText(requestObject, ((MyBusinessStateApplyViewRequestVO) requestObject).getContainTextType(), ((MyBusinessStateApplyViewRequestVO) requestObject).getContainText())};
            }
        } else if(requestObject instanceof MyBusinessStateViewRequestVO) { /** 사업 참여 현황 - 진행 중 리스트(승인, 반려) */
            // 변경 이력 불러오기(변경 이력이 진행된 사업신청서 기준)
            List<String> bizEditHistStrs = jpaQueryFactory.select(bizEditHist.bizEditHistTrgtNo)
                    .from(bizEditHist)
                    .where(bizEditHist.bizEditHistTrgtNo.contains("BOA"))
                    .fetch().stream().distinct().collect(Collectors.toList());

            if (((MyBusinessStateViewRequestVO) requestObject).getBizOrgAplyStts().equals(3)) {    // 승인 후 반려
                return new Predicate[] { condition(((MyBusinessStateViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                        condition(bizEditHistStrs, bizOrganizationAply.bizOrgAplyNo::in),
                        condition(((MyBusinessStateViewRequestVO) requestObject).getOrganizationCode(), bizOrganizationAply.orgCd::eq),
                        condition(((MyBusinessStateViewRequestVO) requestObject).getBizOrgAplyStts(), bizOrganizationAply.bizOrgAplyStts::eq),
                        condition(((MyBusinessStateViewRequestVO) requestObject).getUserId(), bizOrganizationAply.bizOrgAplyPic::eq),
                        checkPeriod(new BizOrganizationAply(), "loe"),
                        checkPeriod(new BizOrganizationAply(), "goe"),
                        searchContainText(requestObject, ((MyBusinessStateViewRequestVO) requestObject).getContainTextType(), ((MyBusinessStateViewRequestVO) requestObject).getContainText())};
            } else {    // 승인
                return new Predicate[] { condition(((MyBusinessStateViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                        condition(((MyBusinessStateViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                        condition(((MyBusinessStateViewRequestVO) requestObject).getOrganizationCode(), bizOrganizationAply.orgCd::eq),
                        condition(2, bizOrganizationAply.bizOrgAplyStts::eq),
                        condition(((MyBusinessStateViewRequestVO) requestObject).getUserId(), bizOrganizationAply.bizOrgAplyPic::eq),
                        searchContainText(requestObject, ((MyBusinessStateViewRequestVO) requestObject).getContainTextType(), ((MyBusinessStateViewRequestVO) requestObject).getContainText())};
            }
        } else if(requestObject instanceof MyBusinessStateSurveyViewRequestVO) { /** 사업 참여 현황 - 진행 중 리스트 - 상호평가 */
            return new Predicate[] { condition(((MyBusinessStateSurveyViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq)};
        } else if(requestObject instanceof MyBusinessStateCompleteViewRequestVO) { /** 사업 참여 현황 - 완료 리스트 */
            return new Predicate[] { condition(((MyBusinessStateCompleteViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                    condition(((MyBusinessStateCompleteViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                    condition(((MyBusinessStateCompleteViewRequestVO) requestObject).getOrganizationCode(), bizOrganizationAply.orgCd::eq),
                    condition(7, bizOrganizationAply.bizOrgAplyStts::eq),
                    condition(((MyBusinessStateCompleteViewRequestVO) requestObject).getUserId(), bizOrganizationAply.bizOrgAplyPic::eq),
                    searchContainText(requestObject, ((MyBusinessStateCompleteViewRequestVO) requestObject).getContainTextType(), ((MyBusinessStateCompleteViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 사업 참여 현황 - 강의확인서 */
            return new Predicate[] { condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                    condition(((BizInstructorIdentifyViewRequestVO) requestObject).getOrgCd(), bizOrganizationAply.orgCd::eq),
                    condition(((BizInstructorIdentifyViewRequestVO) requestObject).getYear(), bizInstructorIdentify.bizInstrIdntyYm.substring(0,4)::eq),
                    condition(((BizInstructorIdentifyViewRequestVO) requestObject).getMonth(), bizInstructorIdentify.bizInstrIdntyYm.substring(4,6)::eq),
                    condition(((BizInstructorIdentifyViewRequestVO) requestObject).getContainText(), bizPbancMaster.bizPbancNm::contains),
                    condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizOrgAplyPic(), bizOrganizationAply.bizOrgAplyPic::eq),
                    condition(((BizInstructorIdentifyViewRequestVO) requestObject).getRegistUserId(), bizInstructorIdentify.registUserId::eq),
                    searchContainText(requestObject, ((BizInstructorIdentifyViewRequestVO) requestObject).getContainTextType(), ((BizInstructorIdentifyViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof MyInstructorStateApplyViewRequestVO) { /** 강사 참여 현황 - 강사 모집 - 지원 중 */
            return new Predicate[] { condition(((MyInstructorStateApplyViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                    condition(((MyInstructorStateApplyViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                    condition(((MyInstructorStateApplyViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::goe),
                    condition(1, bizInstructorAply.bizInstrAplyStts::loe)};
                    //checkPeriod(new BizInstructorPbanc(), "loe"),
                    //checkPeriod(new BizInstructorPbanc(), "goe")};
        } else if(requestObject instanceof MyInstructorStateApplyResultViewRequestVO) {
            Integer[] bizOrgAplyStts = {2, 7, 9};
            /** 강사 참여 현황 - 강사 모집 - 지원 완료 */
            if (((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyStts() != null) {
                if (((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyStts() == 1) {
                    return new Predicate[] { condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::goe),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::goe),
                            condition(bizOrgAplyStts, bizOrganizationAply.bizOrgAplyStts::in),
                            searchContainText(requestObject, ((MyInstructorStateApplyResultViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateApplyResultViewRequestVO) requestObject).getContainText()) };
                } else {
                    return new Predicate[] { condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::eq),
                            condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::goe),
                            condition(bizOrgAplyStts, bizOrganizationAply.bizOrgAplyStts::in),
                            searchContainText(requestObject, ((MyInstructorStateApplyResultViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateApplyResultViewRequestVO) requestObject).getContainText()) };
                }
            } else {
                return new Predicate[] { condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                        condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                        condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                        condition(((MyInstructorStateApplyResultViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::goe),
                        condition(bizOrgAplyStts, bizOrganizationAply.bizOrgAplyStts::in),
                        searchContainText(requestObject, ((MyInstructorStateApplyResultViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateApplyResultViewRequestVO) requestObject).getContainText()) };
            }
        } else if(requestObject instanceof MyInstructorStateViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 진행 중 */
            DateTime dateYear = new DateTime();
            Integer year = dateYear.getYear();
            Integer[] states = {2, 9};
            /** 강의확인서 비교하기 위한 데이터 조회의 경우 states 삭제*/
            if(((MyInstructorStateViewRequestVO) requestObject).getNotBizInstrAplyNo()!=null){
                states =null;
            }
            if(((MyInstructorStateViewRequestVO) requestObject).getIsWrite()==null){
                return new Predicate[] { condition(year, bizPbancMaster.bizPbancYr::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getNotBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::ne),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrIdntyStts(), bizInstructorIdentify.bizInstrIdntyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getSearchYm(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,7)::eq),
                        condition(states, bizOrganizationAply.bizOrgAplyStts::in),
                        checkPeriod(new BizOrganizationAply(), "dtl"),
                        searchContainText(requestObject, ((MyInstructorStateViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateViewRequestVO) requestObject).getContainText())};
            }else if(((MyInstructorStateViewRequestVO) requestObject).getIsWrite() || ((MyInstructorStateViewRequestVO) requestObject).getIsWrite()==true){
                return new Predicate[] { condition(year, bizPbancMaster.bizPbancYr::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getNotBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::ne),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrIdntyStts(), bizInstructorIdentify.bizInstrIdntyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getSearchYm(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,7)::eq),
                        bizInstructorIdentify.registUserId.isNotNull(),
                        condition(states, bizOrganizationAply.bizOrgAplyStts::in),
                        checkPeriod(new BizOrganizationAply(), "dtl"),
                        searchContainText(requestObject, ((MyInstructorStateViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateViewRequestVO) requestObject).getContainText())};
            }else if(!((MyInstructorStateViewRequestVO) requestObject).getIsWrite() || ((MyInstructorStateViewRequestVO) requestObject).getIsWrite()==false){
                return new Predicate[] { condition(year, bizPbancMaster.bizPbancYr::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getNotBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::ne),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrIdntyStts(), bizInstructorIdentify.bizInstrIdntyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getSearchYm(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,7)::eq),
                        bizInstructorIdentify.registUserId.isNull(),
                        condition(states, bizOrganizationAply.bizOrgAplyStts::in),
                        checkPeriod(new BizOrganizationAply(), "dtl"),
                        searchContainText(requestObject, ((MyInstructorStateViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateViewRequestVO) requestObject).getContainText())};
            }else{
                return new Predicate[] { condition(year, bizPbancMaster.bizPbancYr::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getNotBizInstrAplyNo(), bizInstructorAply.bizInstrAplyNo::ne),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getBizInstrIdntyStts(), bizInstructorIdentify.bizInstrIdntyStts::eq),
                        condition(((MyInstructorStateViewRequestVO) requestObject).getSearchYm(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0,7)::eq),
                        condition(states, bizOrganizationAply.bizOrgAplyStts::in),
                        checkPeriod(new BizOrganizationAply(), "dtl"),
                        searchContainText(requestObject, ((MyInstructorStateViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateViewRequestVO) requestObject).getContainText())};
            }
        } else if(requestObject instanceof MyInstructorStateCompleteViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 완료 */
            return new Predicate[] {
                    condition(((MyInstructorStateCompleteViewRequestVO) requestObject).getBizInstructorAsgnmNos(), bizInstructorAsgnm.bizInstrAsgnmNo::in),
                    condition(((MyInstructorStateCompleteViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                    condition(((MyInstructorStateCompleteViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                    condition(4, bizInstructorIdentify.bizInstrIdntyStts::eq),
                    searchContainText(requestObject, ((MyInstructorStateCompleteViewRequestVO) requestObject).getContainTextType(), ((MyInstructorStateCompleteViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof MyInstructorStateCalculateViewRequestVO) { /** 강사 참여 현황 - 강의 현황 - 정산 */
            return new Predicate[] { condition(((MyInstructorStateCalculateViewRequestVO) requestObject).getRegistUserId(), bizInstructorIdentify.registUserId::eq),
                    condition(4, bizInstructorIdentify.bizInstrIdntyStts::eq)};
        } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 참여 현황 - 강사 지원 문의 */
            return new Predicate[] { condition(((BizInstructorQuestionViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                    condition(((BizInstructorQuestionViewRequestVO) requestObject).getRegistUserId(), bizInstructorQuestion.registUserId::eq),
                    condition(((BizInstructorQuestionViewRequestVO) requestObject).getBizInstrQstnNo(), bizInstructorQuestion.bizInstrQstnNo::eq),
                    searchContainText(requestObject, ((BizInstructorQuestionViewRequestVO) requestObject).getContainTextType(), ((BizInstructorQuestionViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof MyEducationStateViewRequestVO) { /** 교육 수료 현황 */
            if (((MyEducationStateViewRequestVO) requestObject).getEducationState() != null) {
                if (((MyEducationStateViewRequestVO) requestObject).getEducationState().equals(Code.EDU_STATE.PROCEEDING.enumCode)) {
                    return new Predicate[] { condition(true, curriculumApplicationMaster.educationPlan.isUsable::eq),
                            checkPeriod(new CurriculumApplicationMaster(), "goe"),
                            condition(((MyEducationStateViewRequestVO) requestObject).getEducationState(), curriculumApplicationMaster.educationState::eq),
                            condition(authenticationInfo().getUserId(), curriculumApplicationMaster.userId::eq)};
                } else if (((MyEducationStateViewRequestVO) requestObject).getEducationState().equals(Code.EDU_STATE.END.enumCode)) {
                    return new Predicate[] { // condition(true, curriculumApplicationMaster.educationPlan.isUsable::eq), // 수료 메뉴에서는 사용 여부 체크 하지 않음(모든 교육 수료 현황 노출 필요)
                            //checkPeriod(new CurriculumApplicationMaster(), "lt"), // 화상/집합 수료 기준 우선 기간 확인
                            condition(((MyEducationStateViewRequestVO) requestObject).getApplicationNo(), curriculumApplicationMaster.applicationNo::eq),
                            condition(((MyEducationStateViewRequestVO) requestObject).getYear(), curriculumApplicationMaster.educationPlan.yearOfEducationPlan::eq),
                            condition(authenticationInfo().getUserId(), curriculumApplicationMaster.userId::eq)};
                } else {
                    return new Predicate[] { condition(true, curriculumApplicationMaster.educationPlan.isUsable::eq),
                            condition(((MyEducationStateViewRequestVO) requestObject).getEducationState(), curriculumApplicationMaster.educationState::eq),
                            condition(authenticationInfo().getUserId(), curriculumApplicationMaster.userId::eq),
                            condition(((MyEducationStateViewRequestVO) requestObject).getEducationType(), curriculumApplicationMaster.educationPlan.curriculumMaster.educationType::eq),
                            condition(((MyEducationStateViewRequestVO) requestObject).getApplicationNo(), curriculumApplicationMaster.applicationNo::eq),
                            condition(((MyEducationStateViewRequestVO) requestObject).getReferenceSequenceNo(), curriculumApplicationMaster.educationPlan.curriculumMaster.curriculumReferenceRoomList.any().sequenceNo::eq)};
                }
            } else {
                return new Predicate[] { condition(true, curriculumApplicationMaster.educationPlan.isUsable::eq),
                        condition(((MyEducationStateViewRequestVO) requestObject).getEducationState(), curriculumApplicationMaster.educationState::eq),
                        condition(authenticationInfo().getUserId(), curriculumApplicationMaster.userId::eq),
                        condition(((MyEducationStateViewRequestVO) requestObject).getEducationType(), curriculumApplicationMaster.educationPlan.curriculumMaster.educationType::eq),
                        condition(((MyEducationStateViewRequestVO) requestObject).getApplicationNo(), curriculumApplicationMaster.applicationNo::eq),
                        condition(((MyEducationStateViewRequestVO) requestObject).getReferenceSequenceNo(), curriculumApplicationMaster.educationPlan.curriculumMaster.curriculumReferenceRoomList.any().sequenceNo::eq)};
            }
        } else if(requestObject instanceof ClassGuideViewRequestVO) { /** 수업지도안 */
            return new Predicate[] { condition(((ClassGuideViewRequestVO) requestObject).getRegistUserId(), classGuide.registUserId::eq),
                    condition(((ClassGuideViewRequestVO) requestObject).getClassGuideCode(), classGuide.classGuideCode::eq),
                    condition(((ClassGuideViewRequestVO) requestObject).getClassGuideType(), classGuide.classGuideType::eq),
                    searchContainText(requestObject, ((ClassGuideViewRequestVO) requestObject).getContainTextType(), ((ClassGuideViewRequestVO) requestObject).getContainText())};
        } else if(requestObject instanceof ClassSubjectViewRequestVO) { /** 수업지도안 - 과목 */
            return new Predicate[] { condition(((ClassSubjectViewRequestVO) requestObject).getRegistUserId(), classSubject.registUserId::eq)};
        } else {
            return null;
        }
    }

    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containTextType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof MyBizApplyViewRequestVO) { /** 사업 신청 내역 */
                booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
            } else if(requestObject instanceof MyBusinessStateApplyViewRequestVO) { /** 사업 참여 현황 - 신청 리스트 */
                booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
            } else if(requestObject instanceof MyBusinessStateViewRequestVO) { /** 사업 참여 현황 - 진행 리스트 */
                booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
            } else if(requestObject instanceof MyBusinessStateCompleteViewRequestVO) { /** 사업 참여 현황 - 완료 리스트 */
                booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
            } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 사업 참여 현황 - 강의확인서 */
                booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
            } else if(requestObject instanceof MyInstructorStateApplyResultViewRequestVO) { /** 강의 참여 현황 - 지원완료 */
                if(containTextType.equals("1")) { /** 사업명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                } else if(containTextType.equals("2")) { /** 기관명 */
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                }
            } else if(requestObject instanceof MyInstructorStateViewRequestVO) { /** 강의 참여 현황 - 진행 중 */
                if(containTextType.equals("1")) { /** 사업명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                } else if(containTextType.equals("2")) { /** 기관명 */
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                }
            } else if(requestObject instanceof MyInstructorStateCompleteViewRequestVO) { /** 강의 참여 현황 - 완료 */
                booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
            } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강의 참여 현황 - 지원 문의 */
                if(containTextType.equals("1")) { /** 전체 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                } else if(containTextType.equals("2")) { /** 기관명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                } else if(containTextType.equals("3")) { /** 기관명 */
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                }
            } else if(requestObject instanceof ClassGuideViewRequestVO) { /** 수업지도안 */
                if(containTextType.equals("1")) { /** 제목 + 내용 */
                    booleanBuilder.or(classGuide.title.contains(containsText));
                    booleanBuilder.or(classGuide.learningArea.contains(containsText));
                    booleanBuilder.or(classGuide.learningGoal.contains(containsText));
                    booleanBuilder.or(classGuide.learningMaterial.contains(containsText));
                } else if(containTextType.equals("2")) { /** 제목 */
                    booleanBuilder.or(classGuide.title.contains(containsText));
                } else if(containTextType.equals("3")) { /** 내용 */
                    booleanBuilder.or(classGuide.learningArea.contains(containsText));
                    booleanBuilder.or(classGuide.learningGoal.contains(containsText));
                    booleanBuilder.or(classGuide.learningMaterial.contains(containsText));
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
            if(requestObject instanceof ClassGuideViewRequestVO) /** 수업지도안 */
                return classGuide.createDateTime.between(startDateTime, endDateTime);
            else return null;
        } else {
            return null;
        }
    }

    /**
     * 수업지도안 코드 생성
     */
    @Override
    public String generateClassGuideCode(String prefixCode) {
        return jpaQueryFactory.selectFrom(classGuide)
                .orderBy(classGuide.classGuideCode.desc())
                .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                        .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getClassGuideCode().replace(prefixCode, "")) + 1), 7, "0"))
                        .toString())
                .orElse(new StringBuilder(prefixCode).append("0000000").toString());
    }

    /**
     * 수업지도안 교과 코드 생성
     */
    @Override
    public String generateClassSubjectCode(String prefixCode) {
        return jpaQueryFactory.selectFrom(classSubject)
                .where(classSubject.individualCode.like(prefixCode+"%"))
                .orderBy(classSubject.individualCode.desc())
                .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                        .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getIndividualCode().replace(prefixCode, "")) + 1), 5, "0"))
                        .toString())
                .orElse(new StringBuilder(prefixCode).append("00000").toString());
    }

    /** 날짜 비교 */
    private <T extends CSEntitySupport> BooleanExpression checkPeriod(T value, String type) {
        LocalDate ld = LocalDate.now();
        Date d = java.sql.Date.valueOf(ld);
        DateExpression<Date> expressionNow = Expressions.dateTemplate(Date.class, "{0}", d);

        if (value instanceof BizPbancMaster) {
            if (type.equals("goe")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizPbancMaster.bizPbancRcptEnd, "%Y-%m-%d");
                return expression.goe(expressionNow);

            } else if (type.equals("loe")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizPbancMaster.bizPbancRcptBgng, "%Y-%m-%d");
                return expression.loe(expressionNow);

            } else return null;
        } else if (value instanceof BizOrganizationAply) {
            if (type.equals("gt")) {    // expression > expressionNow
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanBgng, "%Y-%m-%d");
                return expression.gt(expressionNow);

            } else if (type.equals("goe")) {    // expression >= expressionNow
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanEnd, "%Y-%m-%d");
                return expression.goe(expressionNow);

            } else if (type.equals("loe")) {    // expression <= expressionNow
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanBgng, "%Y-%m-%d");
                return expression.loe(expressionNow);

            } else if (type.equals("lt")) { // expression < expressionNow
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanEnd, "%Y-%m-%d");
                return expression.lt(expressionNow);

            } else if (type.equals("dtl")) { // expression <= expressionNow
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd, "%Y-%m");
                return expression.loe(expressionNow);

            } else {
                return null;
            }
        } else if (value instanceof BizInstructorIdentify) {
            if (type.equals("eqY")) {
                DateExpression<Date> expressionY = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanBgng, "%Y-%m-%d");
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanBgng, "%Y-%m-%d");
                return expression.eq(expressionY);

            } else if (type.equals("eqM")) {
                DateExpression<Date> expressionM = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanBgng, "%Y-%m-%d");
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizOrganizationAply.bizOrgAplyLsnPlanEnd, "%Y-%m-%d");
                return expression.eq(expressionM);

            } else {
                return null;
            }
        } else if (value instanceof BizInstructorPbanc) {
            if (type.equals("goe")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizInstructorPbanc.bizInstrRcptEnd, "%Y-%m-%d");
                return expression.goe(expressionNow);

            } else if (type.equals("loe")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizInstructorPbanc.bizInstrRcptBgng, "%Y-%m-%d");
                return expression.loe(expressionNow);

            } else if (type.equals("lt")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", bizInstructorPbanc.bizInstrRcptEnd, "%Y-%m-%d");
                return expression.gt(expressionNow);

            } else return null;
        } else if (value instanceof CurriculumApplicationMaster) {
            if (type.equals("loe")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", curriculumApplicationMaster.operationBeginDateTime, "%Y-%m-%d");
                return expression.loe(expressionNow);

            } else if (type.equals("goe")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", curriculumApplicationMaster.operationEndDateTime, "%Y-%m-%d");
                return expression.goe(expressionNow);

            } else if (type.equals("lt")) {
                DateExpression<Date> expression = Expressions.dateTemplate(Date.class, "STR_TO_DATE({0},{1})", curriculumApplicationMaster.operationEndDateTime, "%Y-%m-%d");
                return expression.lt(expressionNow);

            } else return null;
        } else return null;
    }
}
