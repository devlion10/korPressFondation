package kr.or.kpf.lms.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.or.kpf.lms.biz.business.apply.vo.request.BizAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.response.BizInstructorAplyCustomApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.request.BizInstructorAsgnmViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request.BizInstructorClclnDdlnViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request.BizInstructorDistCrtrAmtItemViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.request.BizInstructorDistCrtrAmtViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.request.BizInstructorDistViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request.BizInstructorIdentifyDtlViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.FormeBizlecinfoApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.FormeBizlecinfoDetailApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.response.FormeBizlecinfoCustomResponseVO;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.request.BizInstructorQuestionAnswerViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.response.BizInstructorCustomApiResponseVO;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.request.BizOrganizationAplyDtlViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.edithist.vo.request.BizEditHistViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.response.BizOrganizationAplyCustomApiResponseVO;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.request.BizOrganizationRsltRptSttsApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.request.BizOrganizationRsltRptViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancCustomApiResponseVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.response.BizPbancRsltCustomApiResponseVO;
import kr.or.kpf.lms.biz.business.survey.ans.vo.request.BizSurveyAnsViewRequestVO;
import kr.or.kpf.lms.biz.business.survey.vo.request.BizSurveyViewRequestVO;
import kr.or.kpf.lms.biz.common.main.vo.request.MainViewRequestVO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.or.kpf.lms.repository.entity.QAdminUser.adminUser;
import static kr.or.kpf.lms.repository.entity.QBizEditHist.bizEditHist;
import static kr.or.kpf.lms.repository.entity.QBizInstructor.bizInstructor;
import static kr.or.kpf.lms.repository.entity.QBizInstructorAply.bizInstructorAply;
import static kr.or.kpf.lms.repository.entity.QBizInstructorAsgnm.bizInstructorAsgnm;
import static kr.or.kpf.lms.repository.entity.QBizInstructorClclnDdln.bizInstructorClclnDdln;
import static kr.or.kpf.lms.repository.entity.QBizInstructorDist.bizInstructorDist;
import static kr.or.kpf.lms.repository.entity.QBizInstructorDistCrtrAmt.bizInstructorDistCrtrAmt;
import static kr.or.kpf.lms.repository.entity.QBizInstructorDistCrtrAmtItem.bizInstructorDistCrtrAmtItem;
import static kr.or.kpf.lms.repository.entity.QBizInstructorIdentify.bizInstructorIdentify;
import static kr.or.kpf.lms.repository.entity.QBizInstructorIdentifyDtl.bizInstructorIdentifyDtl;
import static kr.or.kpf.lms.repository.entity.QBizInstructorPbanc.bizInstructorPbanc;
import static kr.or.kpf.lms.repository.entity.QBizInstructorQuestion.bizInstructorQuestion;
import static kr.or.kpf.lms.repository.entity.QBizInstructorQuestionAnswer.bizInstructorQuestionAnswer;
import static kr.or.kpf.lms.repository.entity.QBizAply.bizAply;
import static kr.or.kpf.lms.repository.entity.QBizOrganizationAply.bizOrganizationAply;
import static kr.or.kpf.lms.repository.entity.QBizOrganizationAplyDtl.bizOrganizationAplyDtl;
import static kr.or.kpf.lms.repository.entity.QBizOrganizationRsltRpt.bizOrganizationRsltRpt;
import static kr.or.kpf.lms.repository.entity.QBizPbancMaster.bizPbancMaster;
import static kr.or.kpf.lms.repository.entity.QBizPbancResult.bizPbancResult;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl0.bizPbancTmpl0;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl0Item.bizPbancTmpl0Item;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl0Trgt.bizPbancTmpl0Trgt;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl1.bizPbancTmpl1;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl1Trgt.bizPbancTmpl1Trgt;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl2.bizPbancTmpl2;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl3.bizPbancTmpl3;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl4.bizPbancTmpl4;
import static kr.or.kpf.lms.repository.entity.QBizPbancTmpl5.bizPbancTmpl5;
import static kr.or.kpf.lms.repository.entity.QBizSurvey.bizSurvey;
import static kr.or.kpf.lms.repository.entity.QBizSurveyAns.bizSurveyAns;
import static kr.or.kpf.lms.repository.entity.QCommonCodeMaster.commonCodeMaster;
import static kr.or.kpf.lms.repository.entity.QFileMaster.fileMaster;
import static kr.or.kpf.lms.repository.entity.QFormeBizlecinfo.formeBizlecinfo;
import static kr.or.kpf.lms.repository.entity.QFormeFomBizapplyTtable.formeFomBizapplyTtable;
import static kr.or.kpf.lms.repository.entity.QLmsUser.lmsUser;
import static kr.or.kpf.lms.repository.entity.QOrganizationAuthorityHistory.organizationAuthorityHistory;
import static kr.or.kpf.lms.repository.entity.QOrganizationInfo.organizationInfo;
import static org.springframework.util.StringUtils.hasText;

/**
 * 사업 공고 공통 Repository 구현체
 */
@Repository
public class CommonBusinessRepositoryImpl extends CSRepositorySupport implements CommonBusinessRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public CommonBusinessRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

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
    public <T> List<?> updateSttsEntityList(T requestObject) {
        if(requestObject instanceof BizOrganizationRsltRptSttsApiRequestVO) { /** 결과보고서 상태 업데이트 */
            long execute = jpaQueryFactory.update(bizOrganizationRsltRpt)
                    .set(bizOrganizationRsltRpt.bizOrgRsltRptStts, ((BizOrganizationRsltRptSttsApiRequestVO) requestObject).getBizOrgRsltRptStts())
                    .where(bizOrganizationRsltRpt.bizOrgRsltRptNo.in(((BizOrganizationRsltRptSttsApiRequestVO) requestObject).getBizOrgRsltRptNos()))
                    .execute();
            if (execute > 0){
                return jpaQueryFactory.selectFrom(bizOrganizationRsltRpt)
                        .where(bizOrganizationRsltRpt.bizOrgRsltRptNo.in(((BizOrganizationRsltRptSttsApiRequestVO) requestObject).getBizOrgRsltRptNos()))
                        .fetch();
            }
        }else if(requestObject instanceof BizEditHistViewRequestVO) { /** 변경 기록 상태 업데이트 */
            long execute = jpaQueryFactory.update(bizEditHist)
                    .set(bizEditHist.bizEditHistStts, ((BizEditHistViewRequestVO) requestObject).getBizEditHistStts())
                    .where(bizEditHist.bizEditHistTrgtNo.eq(((BizEditHistViewRequestVO) requestObject).getBizEditHistTrgtNo()), bizEditHist.bizEditHistStts.eq(0))
                    .execute();
            if (execute > 0){
                return jpaQueryFactory.selectFrom(bizEditHist)
                        .where(bizEditHist.bizEditHistTrgtNo.eq(((BizEditHistViewRequestVO) requestObject).getBizEditHistTrgtNo()))
                        .fetch();
            }
        }
        return null;
    }

    @Override
    public <T> Long countEntity(T requestObject) {
        if(requestObject instanceof BizInstructorAplyApiRequestVO) {
            return jpaQueryFactory.select(bizInstructorAply.count())
                    .from(bizInstructorAply)
                    .where(bizInstructorAply.bizInstrAplyInstrId.eq(((BizInstructorAplyApiRequestVO) requestObject).getLoginUserId()), bizInstructorAply.bizInstrNo.eq(((BizInstructorAplyApiRequestVO) requestObject).getBizInstrNo()), bizInstructorAply.bizInstrAplyStts.eq(1))
                    .fetchOne();
        } else if (requestObject instanceof BizOrganizationAplyApiRequestVO) {
            Integer[] sttss = {2, 7, 9}; // 0: 임시저장, 1: 신청, 2: 승인, 3: 반려, 7: 종료, 9: 가승인
            return jpaQueryFactory.select(bizOrganizationAply.count())
                    .from(bizOrganizationAply)
                    .where(bizOrganizationAply.orgCd.eq(((BizOrganizationAplyApiRequestVO) requestObject).getOrgCd()),
                            bizOrganizationAply.bizPbancMaster.bizPbancYr.eq(((BizOrganizationAplyApiRequestVO) requestObject).getYears()),
                            bizOrganizationAply.bizPbancMaster.bizPbancType.eq(2),
                            bizOrganizationAply.bizOrgAplyStts.in(sttss))
                    .fetchOne();
        }
        return NumberUtils.LONG_ZERO;
    }

    /**
     * Entity 총 갯수
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    private <T extends CSViewVOSupport> Long getEntityCount(T requestObject) {
        if(requestObject instanceof BizPbancViewRequestVO) { /** 사업 공고 */
            return jpaQueryFactory.select(bizPbancMaster.count())
                    .from(bizPbancMaster)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizPbancRsltViewRequestVO) { /** 사업 공고 결과 */
            return jpaQueryFactory.select(bizPbancResult.count())
                    .from(bizPbancResult)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorViewRequestVO) { /** 강사 모집 */
            return jpaQueryFactory.select(bizInstructorPbanc.count())
                    .from(bizInstructorPbanc)
                    .leftJoin(bizInstructor).on(bizInstructor.bizInstrNo.eq(bizInstructorPbanc.bizInstrNo))
                    .innerJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizInstructorPbanc.bizPbancNo))
                    .innerJoin(bizOrganizationAply).on(bizOrganizationAply.bizPbancNo.eq(bizInstructorPbanc.bizPbancNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorAplyViewRequestVO) { /** 강사 신청 */
            return jpaQueryFactory.select(bizInstructorAply.count())
                    .from(bizInstructorAply)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorAsgnmViewRequestVO) { /** 강사 배정 */
            return jpaQueryFactory.select(bizInstructorAsgnm.count())
                    .from(bizInstructorAsgnm)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 강의확인서 */
            if (((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyStts() != null) {
                return jpaQueryFactory.select(bizInstructorIdentify.count())
                        .from(bizInstructorIdentify)
                        .where(getQuery(requestObject))
                        .fetchOne();
            } else {
                return jpaQueryFactory.select(bizInstructorIdentify.count())
                        .from(bizInstructorIdentify)
                        .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorIdentify.bizOrgAplyNo))
                        .where(getQuery(requestObject))
                        .fetchOne();
            }
        }else if(requestObject instanceof BizInstructorIdentifyDtlViewRequestVO) { /** 강의확인서 강의시간표 */
            return jpaQueryFactory.select(bizInstructorIdentifyDtl.count())
                    .from(bizInstructorIdentifyDtl)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof  FormeBizlecinfoApiRequestVO) { /** 포미 강의확인서 API */
            return  jpaQueryFactory.select(formeBizlecinfo.count())
                    .from(formeBizlecinfo)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof  FormeBizlecinfoDetailApiRequestVO) { /** 포미 강의확인서 API */
            return  jpaQueryFactory.select(formeBizlecinfo.count())
                    .from(formeBizlecinfo)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizSurveyViewRequestVO) { /** 상호평가 평가지 */
            return jpaQueryFactory.select(bizSurvey.count())
                    .from(bizSurvey)
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizOrganizationAplyViewRequestVO) { /** 사업 공모 신청 */
            return jpaQueryFactory.select(bizOrganizationAply.count())
                    .from(bizOrganizationAply)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizAplyViewRequestVO) { /** 사업 공고 신청 - 언론인/기본형 */
            return jpaQueryFactory.select(bizAply.count())
                    .from(bizAply)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(bizAply.bizAplyUserID))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(lmsUser.organizationCode))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        } else if(requestObject instanceof BizOrganizationRsltRptViewRequestVO) { /** 결과보고서 */
            return jpaQueryFactory.select(bizOrganizationRsltRpt.count())
                    .from(bizOrganizationRsltRpt)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizOrganizationRsltRpt.bizOrgAplyNo))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizSurveyAnsViewRequestVO) { /** 상호평가 답변 */
            return jpaQueryFactory.select(bizSurveyAns.count())
                    .from(bizSurveyAns)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 지원 문의 */
            return jpaQueryFactory.select(bizInstructorQuestion.count())
                    .from(bizInstructorQuestion)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorQuestionAnswerViewRequestVO) { /** 강사 지원 문의 답변 */
            return jpaQueryFactory.select(bizInstructorQuestionAnswer.count())
                    .from(bizInstructorQuestionAnswer)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorClclnDdlnViewRequestVO) { /** 정산 마감일 */
            return jpaQueryFactory.select(bizInstructorClclnDdln.count())
                    .from(bizInstructorClclnDdln)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorDistViewRequestVO) { /** 거리 증빙 */
            return jpaQueryFactory.select(bizInstructorDist.count())
                    .from(bizInstructorDist)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorDistCrtrAmtViewRequestVO) { /** 이동거리 기준단가 */
            return jpaQueryFactory.select(bizInstructorDistCrtrAmt.count())
                    .from(bizInstructorDistCrtrAmt)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizInstructorDistCrtrAmtItemViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.select(bizInstructorDistCrtrAmtItem.count())
                    .from(bizInstructorDistCrtrAmtItem)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizEditHistViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.select(bizEditHist.count())
                    .from(bizEditHist)
                    .where(getQuery(requestObject))
                    .fetchOne();
        }else if(requestObject instanceof BizOrganizationAplyDtlViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.select(bizOrganizationAplyDtl.count())
                    .from(bizOrganizationAplyDtl)
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
        if(requestObject instanceof BizPbancViewRequestVO) { /** 사업 공고 */
            BizPbancCustomApiResponseVO dto = jpaQueryFactory.select(Projections.fields(BizPbancCustomApiResponseVO.class,
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
                            bizPbancMaster.bizPbancCn,
                            bizPbancMaster.createDateTime,
                            bizPbancMaster.registUserId,
                            bizPbancMaster.updateDateTime,
                            bizPbancMaster.modifyUserId,
                            bizPbancResult.bizPbancRsltNo,
                            adminUser.userName
                    ))
                    .from(bizPbancMaster)
                    .leftJoin(adminUser).on(adminUser.userId.eq(bizPbancMaster.registUserId))
                    .leftJoin(bizPbancResult).on(bizPbancResult.bizPbancNo.eq(bizPbancMaster.bizPbancNo))
                    .where(bizPbancMaster.bizPbancNo.eq(((BizPbancViewRequestVO) requestObject).getBizPbancNo()))
                    .fetchOne();
            if (dto != null) {
                dto.setFileMasters(
                        jpaQueryFactory.selectFrom(fileMaster)
                                .where(fileMaster.atchFileSn.eq(dto.getBizPbancNo()))
                                .fetch()
                );

                if (dto.getBizPbancCtgr() != 1) {
                    BizOrganizationAply bizOrganizationApplied = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizPbancNo.eq(dto.getBizPbancNo()), bizOrganizationAply.orgCd.eq(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()))
                            .fetchOne();

                    if (bizOrganizationApplied != null) {
                        dto.setLoginUserApplied("applied");
                    }
                } else {
                    BizAply bizApplied = jpaQueryFactory.selectFrom(bizAply)
                            .where(bizAply.bizPbancNo.eq(dto.getBizPbancNo()), bizAply.bizAplyUserID.eq(((BizPbancViewRequestVO) requestObject).getUserId()))
                            .fetchOne();

                    if (bizApplied != null) {
                        dto.setLoginUserApplied("applied");
                    }
                }
            }
            return dto;
        } else if(requestObject instanceof BizPbancRsltViewRequestVO) { /** 사업 공고 결과 */
            return jpaQueryFactory.select(Projections.fields(BizPbancRsltCustomApiResponseVO.class,
                            bizPbancResult.bizPbancRsltNo,
                            bizPbancResult.bizPbancNo,
                            bizPbancResult.bizPbancRsltCn,
                            bizPbancResult.bizPbancNtcYn,
                            bizPbancResult.bizPbancRsltFile,
                            bizPbancResult.registUserId,
                            bizPbancMaster.bizPbancCtgr,
                            bizPbancMaster.bizPbancNm,
                            adminUser.userName
                    ))
                    .from(bizPbancResult)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizPbancResult.bizPbancNo))
                    .leftJoin(adminUser).on(adminUser.userId.eq(bizPbancResult.registUserId))
                    .where(bizPbancResult.bizPbancRsltNo.eq(((BizPbancRsltViewRequestVO) requestObject).getBizPbancRsltNo()))
                    .fetchOne();

        } else if(requestObject instanceof BizInstructorViewRequestVO) { /** 강사 모집 */
            List<BizInstructor> entities = jpaQueryFactory.selectFrom(bizInstructor)
                    .where(bizInstructor.bizInstrStts.eq(1))
                    .orderBy(bizInstructor.createDateTime.desc())
                    .fetch();

            BizInstructor entity = new BizInstructor();
            if (entities.size() > 0 && !entities.isEmpty()) {
                entity = entities.get(0);

                List<BizInstructorPbanc> pbancs = jpaQueryFactory.selectFrom(bizInstructorPbanc)
                        .where(bizInstructorPbanc.bizInstrNo.eq(entity.getBizInstrNo()))
                        .fetch();
                if (pbancs.size() > 0 && !pbancs.isEmpty()) {
                    String period = new StringBuilder(pbancs.get(0).getBizInstrRcptBgng()).append(" ~ ").append(pbancs.get(0).getBizInstrRcptEnd()).toString();
                    entity.setBizInstrPeriod(period);
                } else {
                    entity.setBizInstrPeriod("모집 중인 강사 공고가 없습니다.");
                }
            }
            return entity;
        } else if(requestObject instanceof BizInstructorAplyViewRequestVO) { /** 강사 신청 */
            return jpaQueryFactory.selectFrom(bizInstructorAply)
                    .where(bizInstructorAply.bizInstrAplyNo.eq(((BizInstructorAplyViewRequestVO) requestObject).getBizInstrAplyNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorAsgnmViewRequestVO) { /** 강사 배정 */
            return jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                    .where(bizInstructorAsgnm.bizInstrAsgnmNo.eq(((BizInstructorAsgnmViewRequestVO) requestObject).getBizInstrAsgnmNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 강의확인서 */
            return jpaQueryFactory.selectFrom(bizInstructorIdentify)
                    .where(bizInstructorIdentify.bizInstrIdntyNo.eq(((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorIdentifyDtlViewRequestVO) { /** 강의확인서 강의시간표 */
            return jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                    .where(bizInstructorIdentifyDtl.bizInstrIdntyDtlNo.eq(((BizInstructorIdentifyDtlViewRequestVO) requestObject).getBizInstrIdntyDtlNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizSurveyViewRequestVO) { /** 상호평가 퍙가지 */
            return jpaQueryFactory.selectFrom(bizSurvey)
                    .where(bizSurvey.bizSurveyNo.eq(((BizSurveyViewRequestVO) requestObject).getBizSurveyNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizOrganizationAplyViewRequestVO) { /** 사업 공모 신청 */
            return jpaQueryFactory.selectFrom(bizOrganizationAply)
                    .where(bizOrganizationAply.bizOrgAplyNo.eq(((BizOrganizationAplyViewRequestVO) requestObject).getBizOrgAplyNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizOrganizationRsltRptViewRequestVO) { /** 결과보고서 */
            return jpaQueryFactory.selectFrom(bizOrganizationRsltRpt)
                    .where(bizOrganizationRsltRpt.bizOrgRsltRptNo.eq(((BizOrganizationRsltRptViewRequestVO) requestObject).getBizOrgRsltRptNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizSurveyAnsViewRequestVO) { /** 상호평가 답변 */
            return jpaQueryFactory.selectFrom(bizSurveyAns)
                    .where(bizSurveyAns.bizSurveyNo.eq(((BizSurveyAnsViewRequestVO) requestObject).getBizSurveyNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 지원 문의 */
            return jpaQueryFactory.selectFrom(bizInstructorQuestion)
                    .where(bizInstructorQuestion.bizInstrQstnNo.eq(((BizInstructorQuestionViewRequestVO) requestObject).getBizInstrQstnNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorQuestionAnswerViewRequestVO) { /** 강사 지원 문의 답변 */
            return jpaQueryFactory.selectFrom(bizInstructorQuestionAnswer)
                    .where(bizInstructorQuestionAnswer.bizInstrQstnAnsNo.eq(((BizInstructorQuestionAnswerViewRequestVO) requestObject).getBizInstrQstnAnsNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorClclnDdlnViewRequestVO) { /** 정산 마감일 */
            return jpaQueryFactory.selectFrom(bizInstructorClclnDdln)
                    .where(bizInstructorClclnDdln.bizInstrClclnDdlnNo.eq(((BizInstructorClclnDdlnViewRequestVO) requestObject).getBizInstrClclnDdlnNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorDistViewRequestVO) { /** 거리 증빙 */
            return jpaQueryFactory.selectFrom(bizInstructorDist)
                    .where(bizInstructorDist.bizInstrDistNo.eq(((BizInstructorDistViewRequestVO) requestObject).getBizInstrDistNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorDistCrtrAmtViewRequestVO) { /** 이동거리 기준단가 */
            return jpaQueryFactory.selectFrom(bizInstructorDistCrtrAmt)
                    .where(bizInstructorDistCrtrAmt.bizInstrDistCrtrAmtNo.eq(((BizInstructorDistCrtrAmtViewRequestVO) requestObject).getBizInstrDistCrtrAmtNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizInstructorDistCrtrAmtItemViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.selectFrom(bizInstructorDistCrtrAmtItem)
                    .where(bizInstructorDistCrtrAmtItem.bizInstrDistCrtrAmtItemNo.eq(((BizInstructorDistCrtrAmtItemViewRequestVO) requestObject).getBizInstrDistCrtrAmtItemNo()))
                    .fetchOne();
        } else if(requestObject instanceof BizEditHistViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.selectFrom(bizEditHist)
                    .where(bizEditHist.bizEditHistNo.eq(((BizEditHistViewRequestVO) requestObject).getBizEditHistNo()))
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
        if(requestObject instanceof BizPbancViewRequestVO) { /** 사업 공고 */

        List<BizPbancCustomApiResponseVO> dtos = new ArrayList<>();
            dtos = jpaQueryFactory.select(Projections.fields(BizPbancCustomApiResponseVO.class,
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
                            bizPbancMaster.isTop,
                            bizPbancMaster.ckBox,
                            bizPbancMaster.createDateTime,
                            bizPbancMaster.registUserId,
                            bizPbancMaster.updateDateTime,
                            bizPbancMaster.modifyUserId,
                            bizPbancResult.bizPbancRsltNo,
                            adminUser.userName
                    ))
                    .from(bizPbancMaster)
                    .leftJoin(adminUser).on(adminUser.userId.eq(bizPbancMaster.registUserId))
                    .leftJoin(bizPbancResult).on(bizPbancResult.bizPbancNo.eq(bizPbancMaster.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizPbancMaster.isTop.desc(), bizPbancMaster.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch()
                    .stream()
                    .peek(data -> data.setIsNew(new DateTime().minusDays(15).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                            .parseDateTime(data.getCreateDateTime())) < 0)).collect(Collectors.toList());

            if (dtos != null && dtos.size() > 0){
                for (BizPbancCustomApiResponseVO dto : dtos){
                    dto.setFileMasters(
                            jpaQueryFactory.selectFrom(fileMaster)
                                    .where(fileMaster.atchFileSn.eq(dto.getBizPbancNo()))
                                    .fetch()
                    );

                    if (((BizPbancViewRequestVO) requestObject).getAuth() != null && !((BizPbancViewRequestVO) requestObject).getAuth().equals("N")) {
                        if(dto.getBizPbancType()==5){
                            BizAply applied = jpaQueryFactory.selectFrom(bizAply)
                                    .where(bizAply.bizPbancNo.eq(dto.getBizPbancNo()),
                                            bizAply.bizAplyUserID.eq(((BizPbancViewRequestVO) requestObject).getUserId()))
                                    .fetchFirst();
                            if (applied != null) {
                                dto.setLoginUserApplied("applied");
                            }
                        }
                        if (dto.getBizPbancCtgr() == 0 && dto.getBizPbancType()!=5) {
                            BizOrganizationAply bizOrganizationApplied = jpaQueryFactory.selectFrom(bizOrganizationAply)
                                    .where(bizOrganizationAply.bizPbancNo.eq(dto.getBizPbancNo()), bizOrganizationAply.orgCd.eq(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()))
                                    .fetchOne();
                            if (bizOrganizationApplied != null && !bizOrganizationApplied.equals(null)) {
                                dto.setLoginUserApplied("applied");
                            }

                            List<OrganizationAuthorityHistory> orgAuthorityHistory = jpaQueryFactory.selectFrom(organizationAuthorityHistory)
                                    .where(organizationAuthorityHistory.organizationCode.eq(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()), organizationAuthorityHistory.bizAuthorityApprovalState.eq("2"))
                                    .orderBy(organizationAuthorityHistory.createDateTime.asc())
                                    .fetch();

                            if (orgAuthorityHistory != null && orgAuthorityHistory.size() > 0) {
                                dto.setOrganiztionRepName(orgAuthorityHistory.get(0).getRepresentative());
                                dto.setOrganizationUserRank(orgAuthorityHistory.get(0).getRank());
                                dto.setDepartment(orgAuthorityHistory.get(0).getDepartment());
                            }

                            dto.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                    .where(organizationInfo.organizationCode.eq(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()))
                                    .fetchOne());

                            if (dto.getBizPbancSlctnMeth() == 1) {
                                dto.setOrgAplyCnt(jpaQueryFactory.select(bizOrganizationAply.count())
                                        .from(bizOrganizationAply)
                                        .where(bizOrganizationAply.bizOrgAplyStts.eq(2), bizOrganizationAply.bizPbancNo.eq(dto.getBizPbancNo()))
                                        .fetchOne());
                            }

                        } else if(dto.getBizPbancCtgr() == 1) {
                            BizAply bizApplied = jpaQueryFactory.selectFrom(bizAply)
                                    .where(bizAply.bizPbancNo.eq(dto.getBizPbancNo()), bizAply.bizAplyUserID.eq(((BizPbancViewRequestVO) requestObject).getUserId()))
                                    .fetchOne();

                            if (bizApplied != null) {
                                dto.setLoginUserApplied("applied");
                            }

                            if(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()!=null){
                                dto.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                        .where(organizationInfo.organizationCode.eq(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()))
                                        .fetchOne());

                            }
                        }
                    }else{
                        if(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()!=null){
                            dto.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                    .where(organizationInfo.organizationCode.eq(((BizPbancViewRequestVO) requestObject).getLoginOrgCd()))
                                    .fetchOne());

                        }
                    }


                    // Temp 정보 추가
                    if (dto.getBizPbancType() == 0){
                        BizPbancTmpl0 bizPbancTmpl0Entity = jpaQueryFactory.selectFrom(bizPbancTmpl0)
                                .where(bizPbancTmpl0.bizPbancNo.eq(dto.getBizPbancNo()))
                                .fetchOne();

                        if (bizPbancTmpl0Entity != null){
                            List<BizPbancTmpl0Trgt> tmpl0TrgtEntities = jpaQueryFactory.selectFrom(bizPbancTmpl0Trgt)
                                    .where(bizPbancTmpl0Trgt.bizPbancTmpl0No.eq(bizPbancTmpl0Entity.getBizPbancTmpl0No()))
                                    .fetch();

                            if (tmpl0TrgtEntities != null && tmpl0TrgtEntities.size() > 0){
                                List<Integer> tmpl0Trgt = new ArrayList<>();
                                for (BizPbancTmpl0Trgt i : tmpl0TrgtEntities) {
                                    tmpl0Trgt.add(i.getBizPbancTmpl0TrgtCd());
                                }
                                bizPbancTmpl0Entity.setBizPbancTmpl0Trgts(tmpl0Trgt);
                            }

                            bizPbancTmpl0Entity.setBizPbancTmpl0Items(
                                    jpaQueryFactory.selectFrom(bizPbancTmpl0Item)
                                            .where(bizPbancTmpl0Item.bizPbancTmpl0No.eq(bizPbancTmpl0Entity.getBizPbancTmpl0No()))
                                            .fetch()
                            );
                        }

                        dto.setBizPbancTmpl0(bizPbancTmpl0Entity);
                    }else if (dto.getBizPbancType() == 1){

                        BizPbancTmpl1 bizPbancTmpl1Entity = jpaQueryFactory.selectFrom(bizPbancTmpl1)
                                .where(bizPbancTmpl1.bizPbancNo.eq(dto.getBizPbancNo()))
                                .fetchOne();

                        if (bizPbancTmpl1Entity != null) {

                            List<BizPbancTmpl1Trgt> tmpl1TrgtEntities = jpaQueryFactory.selectFrom(bizPbancTmpl1Trgt)
                                    .where(bizPbancTmpl1Trgt.bizPbancTmpl1No.eq(bizPbancTmpl1Entity.getBizPbancTmpl1No()))
                                    .fetch();

                            if (tmpl1TrgtEntities != null && tmpl1TrgtEntities.size() >0 ){
                                List<Integer> tmpl1Trgt = new ArrayList<>();
                                for (BizPbancTmpl1Trgt i : tmpl1TrgtEntities) {
                                    tmpl1Trgt.add(i.getBizPbancTmpl1TrgtCd());
                                }
                                bizPbancTmpl1Entity.setBizPbancTmpl1Trgts(tmpl1Trgt);
                            }

                        }

                        dto.setBizPbancTmpl1(bizPbancTmpl1Entity);
                    }else if (dto.getBizPbancType() == 2){
                        dto.setBizPbancTmpl2(
                                jpaQueryFactory.selectFrom(bizPbancTmpl2)
                                        .where(bizPbancTmpl2.bizPbancNo.eq(dto.getBizPbancNo()))
                                        .fetchOne()
                        );
                    }else if (dto.getBizPbancType() == 3){
                        dto.setBizPbancTmpl3(
                                jpaQueryFactory.selectFrom(bizPbancTmpl3)
                                        .where(bizPbancTmpl3.bizPbancNo.eq(dto.getBizPbancNo()))
                                        .fetchOne()
                        );
                    }else if (dto.getBizPbancType() == 4){
                        dto.setBizPbancTmpl4(
                                jpaQueryFactory.selectFrom(bizPbancTmpl4)
                                        .where(bizPbancTmpl4.bizPbancNo.eq(dto.getBizPbancNo()))
                                        .fetchOne()
                        );
                    } else if( dto.getBizPbancType()==5){
                        dto.setBizPbancTmpl5(jpaQueryFactory.selectFrom(bizPbancTmpl5)
                                .where(bizPbancTmpl5.bizPbancNo.eq(dto.getBizPbancNo()))
                                .orderBy(bizPbancTmpl5.bizPbancTmpl5Ordr.asc())
                                .fetch());
                    }

                }
            }

            return dtos;
        } else if(requestObject instanceof BizPbancRsltViewRequestVO) { /** 사업 공고 결과 */
            List<BizPbancRsltCustomApiResponseVO> dtos = new ArrayList<>();
            dtos = jpaQueryFactory.select(Projections.fields(BizPbancRsltCustomApiResponseVO.class,
                            bizPbancResult.bizPbancRsltNo,
                            bizPbancResult.bizPbancNo,
                            bizPbancResult.bizPbancRsltCn,
                            bizPbancResult.bizPbancNtcYn,
                            bizPbancResult.bizPbancRsltFile,
                            bizPbancResult.bizPbancRsltFileOrigin,
                            bizPbancResult.registUserId,
                            bizPbancMaster.bizPbancType,
                            bizPbancMaster.bizPbancCtgr,
                            bizPbancMaster.bizPbancNm,
                            adminUser.userName
                    ))
                    .from(bizPbancResult)
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizPbancResult.bizPbancNo))
                    .leftJoin(adminUser).on(adminUser.userId.eq(bizPbancResult.registUserId))
                    .where(getQuery(requestObject))
                    .orderBy(bizPbancResult.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (dtos != null && dtos.size() > 0) {
                for (BizPbancRsltCustomApiResponseVO dto : dtos) {
                    if (dto.getBizPbancType() == 5) {
                        List<BizAply> bizAprvs = jpaQueryFactory.selectFrom(bizAply)
                                .where(bizAply.bizPbancNo.eq(dto.getBizPbancNo()), bizAply.bizAplyStts.in(5,7))
                                .orderBy(bizAply.lmsUser.userName.asc())
                                .fetch().stream().peek(aply -> {
                                    aply.setLmsUser(null);
                                    String orgCd = jpaQueryFactory.selectFrom(lmsUser)
                                            .where(lmsUser.userId.eq(aply.getBizAplyUserID()))
                                            .fetchOne().getOrganizationCode();
                                    aply.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                            .where(organizationInfo.organizationCode.eq(orgCd))
                                            .fetchOne());
                                }).collect(Collectors.toList());
                        dto.setBizAprvs(bizAprvs);
                    } else {
                        List<BizOrganizationAply> bizOrganizationAprvs = jpaQueryFactory.selectFrom(bizOrganizationAply)
                                .where(bizOrganizationAply.bizPbancNo.eq(dto.getBizPbancNo()), bizOrganizationAply.bizOrgAplyStts.eq(2))
                                .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                                .orderBy(organizationInfo.organizationName.asc())
                                .fetch();

                        if (bizOrganizationAprvs != null && bizOrganizationAprvs.size() > 0) {
                            for (BizOrganizationAply aprv : bizOrganizationAprvs) {
                                aprv.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                                        .where(organizationInfo.organizationCode.eq(aprv.getOrgCd()))
                                        .fetchOne());
                            }
                        }
                        dto.setBizOrganizationAprvs(bizOrganizationAprvs);
                    }

                    // 공통 코드에서 카테고리명 호출
                    CommonCodeMaster commonCode = jpaQueryFactory.selectFrom(commonCodeMaster)
                            .where(commonCodeMaster.code.eq("BIZ_PBANC_CTGR"))
                            .fetchOne();

                    if (commonCode != null) {
                        List<CommonCodeMaster> bizCtgrCodes = jpaQueryFactory.selectFrom(commonCodeMaster)
                                .where(commonCodeMaster.upIndividualCode.eq(commonCode.getIndividualCode()))
                                .fetch();

                        if (bizCtgrCodes != null && bizCtgrCodes.size() > 0) {
                            for (CommonCodeMaster i : bizCtgrCodes) {
                                if (i.getCode().equals(dto.getBizPbancCtgr().toString())) {
                                    dto.setBizPbancCtgrNm(i.getCodeName());
                                }
                            }
                        }
                    }
                }
            }
            return dtos;

        } else if(requestObject instanceof BizInstructorViewRequestVO) { /** 강사 모집 */
            List<BizInstructorCustomApiResponseVO> dtos = jpaQueryFactory.select(Projections.fields(BizInstructorCustomApiResponseVO.class,
                            bizInstructorPbanc.sequenceNo,
                            bizInstructorPbanc.bizInstrNo,
                            bizInstructorPbanc.bizPbancNo,
                            bizInstructorPbanc.bizInstrRcptBgng,
                            bizInstructorPbanc.bizInstrRcptEnd,
                            bizInstructorPbanc.bizInstrPbancStts,

                            bizPbancMaster.bizPbancNm,
                            bizInstructor.bizInstrNm,
                            bizInstructor.bizInstrCn,
                            bizInstructor.bizInstrMaxInst,
                            bizInstructor.bizInstrFile,
                            bizInstructor.bizInstrFileDscr,
                            bizInstructor.bizInstrFileSize,
                            bizInstructor.bizInstrFileOrigin,
                            bizInstructor.bizInstrStts,
                            bizInstructor.createDateTime,

                            bizOrganizationAply.bizOrgAplyNo,
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
                            bizPbancMaster.bizPbancInstrSlctnMeth
                    ))
                    .from(bizInstructorPbanc)
                    .leftJoin(bizInstructor).on(bizInstructor.bizInstrNo.eq(bizInstructorPbanc.bizInstrNo))
                    .innerJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizInstructorPbanc.bizPbancNo))
                    .innerJoin(bizOrganizationAply).on(bizOrganizationAply.bizPbancNo.eq(bizInstructorPbanc.bizPbancNo))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(bizOrganizationAply.orgCd))
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructor.createDateTime.desc(), bizPbancMaster.createDateTime.desc(), bizOrganizationAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch()
                    .stream()
                    .peek(data -> data.setIsNew(new DateTime().minusDays(15).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
                            .parseDateTime(data.getCreateDateTime())) < 0)).collect(Collectors.toList());

            if (dtos != null && dtos.size() > 0) {
                for (BizInstructorCustomApiResponseVO dto : dtos) {
                    dto.setBizOrganizationAply(jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .fetchOne());

                    OrganizationInfo info = jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(dto.getOrgCd()))
                            .fetchOne();

                    if (info != null && !info.equals(null)) {
                        dto.setOrganizationInfo(info);
                    }

                    List<BizOrganizationAplyDtl> bizOrganizationAplyDtls = jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                            .where(bizOrganizationAplyDtl.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .orderBy(bizOrganizationAplyDtl.bizOrgAplyLsnDtlRnd.asc())
                            .fetch();

                    if (bizOrganizationAplyDtls != null && !bizOrganizationAplyDtls.isEmpty()) {
                        for (BizOrganizationAplyDtl organizationAplyDtl : bizOrganizationAplyDtls) {
                            List<BizInstructorIdentifyDtl> bizInstructorIdentifyDtls = jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                                    .where(bizInstructorIdentifyDtl.bizOrgAplyDtlNo.eq(organizationAplyDtl.getBizOrgAplyDtlNo()))
                                    .fetch();

                            if (bizInstructorIdentifyDtls != null && bizInstructorIdentifyDtls.size() > 0) {
                                organizationAplyDtl.setBizInstructorIdentifyDtls(bizInstructorIdentifyDtls);
                            }

                            List<BizInstructorAsgnm> asgnmEntities = jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                                    .where(bizInstructorAsgnm.bizOrgAplyDtlNo.eq(organizationAplyDtl.getBizOrgAplyDtlNo()))
                                    .fetch();

                            if (asgnmEntities != null && asgnmEntities.size() > 0) {
                                organizationAplyDtl.setBizInstructorAsgnms(asgnmEntities);
                            }
                        }
                        dto.setBizOrganizationAplyDtls(bizOrganizationAplyDtls);
                    }

                    List<BizInstructorAply> instructorAply = jpaQueryFactory.selectFrom(bizInstructorAply)
                            .where(bizInstructorAply.bizInstrNo.eq(dto.getBizInstrNo()), bizInstructorAply.bizInstrAplyInstrId.eq(((BizInstructorViewRequestVO) requestObject).getUserId()), bizInstructorAply.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .fetch();

                    if (!instructorAply.isEmpty()) {
                        dto.setLoginUserApplied("applied");
                    }
                }
            }
            return dtos;

        } else if(requestObject instanceof BizInstructorAplyViewRequestVO) { /** 강사 신청 */
            List<BizInstructorAplyCustomApiResponseVO> dtos = new ArrayList<>();
            dtos = jpaQueryFactory.select(Projections.fields(BizInstructorAplyCustomApiResponseVO.class,
                            bizInstructorAply.bizInstrAplyNo,
                            bizInstructorAply.bizInstrNo,
                            bizInstructorAply.bizInstrAplyInstrNm,
                            bizInstructorAply.bizInstrAplyInstrId,
                            bizInstructorAply.bizInstrAplyCndtOrdr,
                            bizInstructorAply.bizInstrAplyCndtDist,
                            bizInstructorAply.bizInstrAplySlctnScr,
                            bizInstructorAply.bizInstrAplySlctnCndt,
                            bizInstructorAply.bizInstrAplyCmnt,
                            bizInstructorAply.bizInstrAplyStts,
                            bizInstructorAply.registUserId,
                            bizInstructorAply.createDateTime,
                            bizInstructorAply.modifyUserId,
                            bizInstructorAply.updateDateTime,
                            organizationInfo.organizationCode,
                            organizationInfo.organizationName
                            ))
                    .from(bizInstructorAply)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(bizInstructorAply.registUserId))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(lmsUser.organizationCode))
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            return dtos;
        } else if(requestObject instanceof BizInstructorAsgnmViewRequestVO) { /** 강사 배정 */
            return jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorAsgnm.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 강의확인서 */
            List<BizInstructorIdentify> entities = new ArrayList<>();

            if (((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyStts() != null) {
                entities = jpaQueryFactory.selectFrom(bizInstructorIdentify)
                        .where(getQuery(requestObject))
                        .orderBy(bizInstructorIdentify.createDateTime.desc())
                        .offset(requestObject.getPageable().getOffset())
                        .limit(requestObject.getPageable().getPageSize())
                        .fetch();
            } else {
                entities = jpaQueryFactory.selectFrom(bizInstructorIdentify)
                        .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizInstructorIdentify.bizOrgAplyNo))
                        .where(getQuery(requestObject))
                        .orderBy(bizInstructorIdentify.createDateTime.desc())
                        .offset(requestObject.getPageable().getOffset())
                        .limit(requestObject.getPageable().getPageSize())
                        .fetch();
            }

            if (entities.size() > 0 && !entities.isEmpty()) {
                for (BizInstructorIdentify entity : entities) {
                    entity.setBizInstructorAply(jpaQueryFactory.selectFrom(bizInstructorAply)
                                    .where(bizInstructorAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()),
                                            bizInstructorAply.bizInstrAplyInstrId.eq(entity.getRegistUserId())).fetchOne());

                    entity.setBizInstructorDist(jpaQueryFactory.selectFrom(bizInstructorDist)
                            .where(bizInstructorDist.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()), bizInstructorDist.registUserId.eq(entity.getRegistUserId()))
                            .fetchOne());
                    List<String> identifyDtlNo=new ArrayList<>();
                    for(BizInstructorIdentifyDtl dto : entity.getBizInstructorIdentifyDtls()){
                        dto.setBizOrganizationAplyDtl(jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                                .where(bizOrganizationAplyDtl.bizOrgAplyDtlNo.eq(dto.getBizOrgAplyDtlNo()))
                                .fetchOne());
                        identifyDtlNo.add(dto.getBizOrgAplyDtlNo());
                    }
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
                        entity.setBizOrganizationAplyDtls(new ArrayList<>());
                        for(BizOrganizationAplyDtl dtl : bizOrganizationAplyEntity.getBizOrganizationAplyDtls()){
                            if(dtl.getBizOrgAplyLsnDtlYmd().substring(5,7).equals(entity.getBizInstrIdntyYm().substring(4,6)) &&
                               ! identifyDtlNo.contains( dtl.getBizOrgAplyDtlNo())){
                                BizInstructorAsgnm bizAsgnms = jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                                        .where(bizInstructorAsgnm.bizOrgAplyDtlNo.eq(dtl.getBizOrgAplyDtlNo()),
                                                bizInstructorAsgnm.bizInstrAplyNo.eq(entity.getBizInstructorDist().getBizInstrAplyNo()))
                                        .fetchFirst();
                                if(bizAsgnms != null){
                                    entity.getBizOrganizationAplyDtls().add(dtl);
                                }
                            }
                        }
                    }
                }
            }

            return entities;
        } else if(requestObject instanceof BizInstructorIdentifyDtlViewRequestVO) { /** 강의확인서 강의시간표 */
            return jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorIdentifyDtl.bizInstrIdntyNo.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        }  else if(requestObject instanceof  FormeBizlecinfoApiRequestVO) { /** 포미 강의확인서 API */
            List<FormeBizlecinfoCustomResponseVO> dtos = jpaQueryFactory.select(Projections.fields(FormeBizlecinfoCustomResponseVO.class,
                    formeBizlecinfo.bninTitle,
                    formeBizlecinfo.bainInstNm,
                    formeBizlecinfo.blciUserId,
                    formeBizlecinfo.blciId,
                    formeBizlecinfo.bninId,
                    formeBizlecinfo.bainId,
                    formeBizlecinfo.bainEduEdate,
                    formeBizlecinfo.bainEduSdate,
                    formeBizlecinfo.blciAreaNm,
                    formeBizlecinfo.blciYymm,
                    formeBizlecinfo.battSumTime.sum().as("eduTime"),
                    formeBizlecinfo.bainYear))
                    .from(formeBizlecinfo)
                    .where(getQuery(requestObject))
                    .groupBy(formeBizlecinfo.bninId, formeBizlecinfo.bainId)
                    .orderBy(formeBizlecinfo.bainEduEdate.desc())
                    .fetch();
            return dtos;

        }else if(requestObject instanceof  FormeBizlecinfoDetailApiRequestVO) { /** 포미 강의확인서 API */
            List<FormeBizlecinfo> dtos = jpaQueryFactory.selectFrom(formeBizlecinfo)
                    .where(getQuery(requestObject))
                    .orderBy(formeBizlecinfo.blciId.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
            for(FormeBizlecinfo dto : dtos){
                dto.setFormeFomBizapplyTtables(
                        jpaQueryFactory.selectFrom(formeFomBizapplyTtable)
                                .where(formeFomBizapplyTtable.bainId.eq(dto.getBainId()))
                                .orderBy(formeFomBizapplyTtable.battInning.asc())
                                .fetch());
            }
            return  dtos;

        } else if(requestObject instanceof BizSurveyViewRequestVO) { /** 상호평가 평가지 */
            return jpaQueryFactory.selectFrom(bizSurvey)
                    .where(getQuery(requestObject))
                    .orderBy(bizSurvey.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizOrganizationAplyViewRequestVO) { /** 사업 공모 신청(신청서 상세 및 수정 페이지) */
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
                    .orderBy(bizOrganizationAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
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

                    dto.setBizOrganizationAplyDtls(jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                            .where(bizOrganizationAplyDtl.bizOrgAplyNo.eq(dto.getBizOrgAplyNo()))
                            .orderBy(bizOrganizationAplyDtl.bizOrgAplyLsnDtlRnd.asc())
                            .fetch());

                    for (BizOrganizationAplyDtl dtlEntity : dto.getBizOrganizationAplyDtls()) {
                        List<BizInstructorIdentifyDtl> bizInstructorIdentifyDtls = jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                                .where(bizInstructorIdentifyDtl.bizOrgAplyDtlNo.eq(dtlEntity.getBizOrgAplyDtlNo()))
                                .fetch();

                        if (bizInstructorIdentifyDtls != null && bizInstructorIdentifyDtls.size() > 0) {
                            dtlEntity.setBizInstructorIdentifyDtls(bizInstructorIdentifyDtls);
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

                    if (pbancDto != null && !pbancDto.equals(null)){
                        if (pbancDto.getBizPbancSlctnMeth() == 1) {
                            dto.setOrgAplyCnt(jpaQueryFactory.select(bizOrganizationAply.count())
                                    .from(bizOrganizationAply)
                                    .where(bizOrganizationAply.bizOrgAplyStts.eq(2), bizOrganizationAply.bizPbancNo.eq(dto.getBizPbancNo()))
                                    .fetchOne());
                        }

                        pbancDto.setFileMasters(
                                jpaQueryFactory.selectFrom(fileMaster)
                                        .where(fileMaster.atchFileSn.eq(pbancDto.getBizPbancNo()))
                                        .fetch()
                        );

                        // Temp 정보 추가
                        if (pbancDto.getBizPbancType() == 0) {

                            BizPbancTmpl0 bizPbancTmpl0Entity = jpaQueryFactory.selectFrom(bizPbancTmpl0)
                                    .where(bizPbancTmpl0.bizPbancNo.eq(pbancDto.getBizPbancNo()))
                                    .fetchOne();

                            if (bizPbancTmpl0Entity != null) {

                                List<BizPbancTmpl0Trgt> tmpl0TrgtEntitys = jpaQueryFactory.selectFrom(bizPbancTmpl0Trgt)
                                        .where(bizPbancTmpl0Trgt.bizPbancTmpl0No.eq(bizPbancTmpl0Entity.getBizPbancTmpl0No()))
                                        .fetch();

                                if (tmpl0TrgtEntitys != null && tmpl0TrgtEntitys.size() > 0) {
                                    List<Integer> tmpl0Trgt = new ArrayList<>();
                                    for (BizPbancTmpl0Trgt i : tmpl0TrgtEntitys) {
                                        tmpl0Trgt.add(i.getBizPbancTmpl0TrgtCd());
                                    }
                                    bizPbancTmpl0Entity.setBizPbancTmpl0Trgts(tmpl0Trgt);
                                }

                                List<BizPbancTmpl0Item> tmpl0ItemEntitys = jpaQueryFactory.selectFrom(bizPbancTmpl0Item)
                                        .where(bizPbancTmpl0Item.bizPbancTmpl0No.eq(bizPbancTmpl0Entity.getBizPbancTmpl0No()))
                                        .fetch();

                                if (tmpl0ItemEntitys != null && tmpl0ItemEntitys.size() > 0) {
                                    bizPbancTmpl0Entity.setBizPbancTmpl0Items(tmpl0ItemEntitys);
                                }
                                pbancDto.setBizPbancTmpl0(bizPbancTmpl0Entity);
                            }
                            pbancDto.setBizPbancTmpl0(bizPbancTmpl0Entity);

                        } else if (pbancDto.getBizPbancType() == 1) {
                            BizPbancTmpl1 bizPbancTmpl1Entity = jpaQueryFactory.selectFrom(bizPbancTmpl1)
                                    .where(bizPbancTmpl1.bizPbancNo.eq(pbancDto.getBizPbancNo()))
                                    .fetchOne();

                            if (bizPbancTmpl1Entity != null) {
                                List<BizPbancTmpl1Trgt> tmpl1TrgtEntitys = jpaQueryFactory.selectFrom(bizPbancTmpl1Trgt)
                                        .where(bizPbancTmpl1Trgt.bizPbancTmpl1No.eq(bizPbancTmpl1Entity.getBizPbancTmpl1No()))
                                        .fetch();

                                if (tmpl1TrgtEntitys != null && tmpl1TrgtEntitys.size() > 0) {
                                    List<Integer> tmpl1Trgt = new ArrayList<>();
                                    for (BizPbancTmpl1Trgt i : tmpl1TrgtEntitys) {
                                        tmpl1Trgt.add(i.getBizPbancTmpl1TrgtCd());
                                    }
                                    bizPbancTmpl1Entity.setBizPbancTmpl1Trgts(tmpl1Trgt);
                                }

                            }

                            pbancDto.setBizPbancTmpl1(bizPbancTmpl1Entity);
                        } else if (pbancDto.getBizPbancType() == 2) {
                            pbancDto.setBizPbancTmpl2(
                                    jpaQueryFactory.selectFrom(bizPbancTmpl2)
                                            .where(bizPbancTmpl2.bizPbancNo.eq(pbancDto.getBizPbancNo()))
                                            .fetchOne()
                            );
                        } else if (pbancDto.getBizPbancType() == 3) {
                            pbancDto.setBizPbancTmpl3(
                                    jpaQueryFactory.selectFrom(bizPbancTmpl3)
                                            .where(bizPbancTmpl3.bizPbancNo.eq(pbancDto.getBizPbancNo()))
                                            .fetchOne()
                            );
                        } else if (pbancDto.getBizPbancType() == 4) {
                            pbancDto.setBizPbancTmpl4(
                                    jpaQueryFactory.selectFrom(bizPbancTmpl4)
                                            .where(bizPbancTmpl4.bizPbancNo.eq(pbancDto.getBizPbancNo()))
                                            .fetchOne()
                            );
                        }else if( pbancDto.getBizPbancType()==5){
                            pbancDto.setBizPbancTmpl5(jpaQueryFactory.selectFrom(bizPbancTmpl5)
                                    .where(bizPbancTmpl5.bizPbancNo.eq(dto.getBizPbancNo()))
                                    .orderBy(bizPbancTmpl5.bizPbancTmpl5Ordr.asc())
                                    .fetch());
                        }
                    }
                    dto.setBizPbancMaster(pbancDto);
                }
            }
            return dtos;

        } else if(requestObject instanceof BizAplyViewRequestVO) { /** 사업 공고 신청 - 언론인/기본형 */
            return jpaQueryFactory.selectFrom(bizAply)
                    .leftJoin(lmsUser).on(lmsUser.userId.eq(bizAply.bizAplyUserID))
                    .leftJoin(organizationInfo).on(organizationInfo.organizationCode.eq(lmsUser.organizationCode))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizAply.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

        } else if(requestObject instanceof BizOrganizationRsltRptViewRequestVO) { /** 결과보고서 */
            List<BizOrganizationRsltRpt> entities = jpaQueryFactory.selectFrom(bizOrganizationRsltRpt)
                    .leftJoin(bizOrganizationAply).on(bizOrganizationAply.bizOrgAplyNo.eq(bizOrganizationRsltRpt.bizOrgAplyNo))
                    .leftJoin(bizPbancMaster).on(bizPbancMaster.bizPbancNo.eq(bizOrganizationAply.bizPbancNo))
                    .where(getQuery(requestObject))
                    .orderBy(bizOrganizationRsltRpt.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();

            if (entities.size() > 0 && !entities.isEmpty()) {
                for (BizOrganizationRsltRpt entity : entities) {
                    BizOrganizationAply organizationAply = jpaQueryFactory.selectFrom(bizOrganizationAply)
                            .where(bizOrganizationAply.bizOrgAplyNo.eq(entity.getBizOrgAplyNo()))
                            .fetchOne();
                    organizationAply.setOrganizationInfo(jpaQueryFactory.selectFrom(organizationInfo)
                            .where(organizationInfo.organizationCode.eq(entity.getOrgCd()))
                            .fetchOne());
                    if (organizationAply.getBizOrganizationAplyDtls().size() > 0 && !organizationAply.getBizOrganizationAplyDtls().isEmpty()) {
                        for (BizOrganizationAplyDtl dtlEntity : organizationAply.getBizOrganizationAplyDtls()) {

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
                    entity.setBizOrganizationAply(organizationAply);
                }
            }
            return entities;

        } else if(requestObject instanceof BizSurveyAnsViewRequestVO) { /** 상호평가 답변 */
            return jpaQueryFactory.selectFrom(bizSurveyAns)
                    .where(getQuery(requestObject))
                    .orderBy(bizSurveyAns.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 지원 문의 */
            return jpaQueryFactory.selectFrom(bizInstructorQuestion)
                    .where(getQuery(requestObject))
                    .orderBy(bizInstructorQuestion.createDateTime.desc())
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorQuestionAnswerViewRequestVO) { /** 강사 지원 문의 답변 */
            return jpaQueryFactory.selectFrom(bizInstructorQuestionAnswer)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorClclnDdlnViewRequestVO) { /** 정산 마감일 */
            return jpaQueryFactory.selectFrom(bizInstructorClclnDdln)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorDistViewRequestVO) { /** 거리 증빙 */
            return jpaQueryFactory.selectFrom(bizInstructorDist)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorDistCrtrAmtViewRequestVO) { /** 이동거리 기준단가 */
            return jpaQueryFactory.selectFrom(bizInstructorDistCrtrAmt)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizInstructorDistCrtrAmtItemViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.selectFrom(bizInstructorDistCrtrAmtItem)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizEditHistViewRequestVO) { /** 이동거리 기준단가 항목 */
            return jpaQueryFactory.selectFrom(bizEditHist)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof BizOrganizationAplyDtlViewRequestVO) { /** 사업 신청 수업계획서 리스트 */
            return jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                    .where(getQuery(requestObject))
                    .offset(requestObject.getPageable().getOffset())
                    .limit(requestObject.getPageable().getPageSize())
                    .fetch();
        } else if(requestObject instanceof MainViewRequestVO) { /** 메인 통합 */
            return jpaQueryFactory.selectFrom(bizPbancMaster)
                    .where(getQuery(requestObject))
                    .orderBy(bizPbancMaster.isTop.desc(), bizPbancMaster.createDateTime.desc())
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
        if(requestObject instanceof BizPbancViewRequestVO) { /** 사업 공고 */
            return new Predicate[] { betweenTime(new BizPbancMaster(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((BizPbancViewRequestVO) requestObject).getBizPbancCtgr(), bizPbancMaster.bizPbancCtgr::eq),
                    condition(((BizPbancViewRequestVO) requestObject).getBizPbancStts(), bizPbancMaster.bizPbancStts::eq),
                    condition(((BizPbancViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                    condition(((BizPbancViewRequestVO) requestObject).getBizPbancNo(), bizPbancMaster.bizPbancNo::eq),
                    condition(9, bizPbancMaster.bizPbancStts::ne),
                    searchContainText(requestObject, ((BizPbancViewRequestVO) requestObject).getContainTextType(), ((BizPbancViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizPbancRsltViewRequestVO) { /** 사업 공고 결과 */
            return new Predicate[] { betweenTime(new BizPbancResult(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((BizPbancRsltViewRequestVO) requestObject).getBizPbancRsltNo(), bizPbancResult.bizPbancRsltNo::eq),
                    condition(((BizPbancRsltViewRequestVO) requestObject).getBizPbancNo(), bizPbancResult.bizPbancNo::eq),
                    searchContainText(requestObject, ((BizPbancRsltViewRequestVO) requestObject).getContainTextType(), ((BizPbancRsltViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorViewRequestVO) { /** 강사 모집 */
            if (((BizInstructorViewRequestVO) requestObject).getList() != null && ((BizInstructorViewRequestVO) requestObject).getList().length() > 0 && !((BizInstructorViewRequestVO) requestObject).getList().isEmpty()) {
                if (((BizInstructorViewRequestVO) requestObject).getBizOrgAplyRgn() != null && ((BizInstructorViewRequestVO) requestObject).getBizOrgAplyRgn().equals("지역")) {
                    return new Predicate[] { condition(bizInstructorPbanc.bizInstrNo, bizInstructor.bizInstrNo::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getSequenceNo(), bizInstructorPbanc.sequenceNo::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizInstrNo(), bizInstructor.bizInstrNo::eq),
                            condition(1, bizInstructor.bizInstrStts::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::eq),
                            condition(2, bizOrganizationAply.bizOrgAplyStts::eq),
                            searchContainText(requestObject, ((BizInstructorViewRequestVO) requestObject).getContainTextType(), ((BizInstructorViewRequestVO) requestObject).getContainText()) };
                } else {
                    return new Predicate[] { condition(bizInstructorPbanc.bizInstrNo, bizInstructor.bizInstrNo::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getSequenceNo(), bizInstructorPbanc.sequenceNo::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizInstrNo(), bizInstructor.bizInstrNo::eq),
                            condition(1, bizInstructor.bizInstrStts::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::eq),
                            condition(((BizInstructorViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                            condition(2, bizOrganizationAply.bizOrgAplyStts::eq),
                            searchContainText(requestObject, ((BizInstructorViewRequestVO) requestObject).getContainTextType(), ((BizInstructorViewRequestVO) requestObject).getContainText()) };
                }
            } else {
                return new Predicate[] { condition(bizInstructorPbanc.bizInstrNo, bizInstructor.bizInstrNo::eq),
                        condition(((BizInstructorViewRequestVO) requestObject).getSequenceNo(), bizInstructorPbanc.sequenceNo::eq),
                        condition(((BizInstructorViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                        condition(((BizInstructorViewRequestVO) requestObject).getBizInstrNo(), bizInstructor.bizInstrNo::eq),
                        condition(((BizInstructorViewRequestVO) requestObject).getBizInstrPbancStts(), bizInstructorPbanc.bizInstrPbancStts::eq),
                        condition(((BizInstructorViewRequestVO) requestObject).getBizOrgAplyRgn(), bizOrganizationAply.bizOrgAplyRgn::eq),
                        condition(2, bizOrganizationAply.bizOrgAplyStts::eq),
                        searchContainText(requestObject, ((BizInstructorViewRequestVO) requestObject).getContainTextType(), ((BizInstructorViewRequestVO) requestObject).getContainText()) };
            }
        } else if(requestObject instanceof BizInstructorAplyViewRequestVO) { /** 강사 신청 */
            return new Predicate[] { betweenTime(new BizInstructorAply(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((BizInstructorAplyViewRequestVO) requestObject).getBizInstrAplyInstrId(), bizInstructorAply.bizInstrAplyInstrId::eq),
                    condition(((BizInstructorAplyViewRequestVO) requestObject).getBizInstrAplyStts(), bizInstructorAply.bizInstrAplyStts::eq),
                    searchContainText(requestObject, ((BizInstructorAplyViewRequestVO) requestObject).getContainTextType(), ((BizInstructorAplyViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorAsgnmViewRequestVO) { /** 강사 배정 */
            return new Predicate[] { betweenTime(new BizInstructorAsgnm(), requestObject.getStartDate(), requestObject.getEndDate()),
                    searchContainText(requestObject, ((BizInstructorAsgnmViewRequestVO) requestObject).getContainTextType(), ((BizInstructorAsgnmViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorIdentifyViewRequestVO) { /** 강의확인서 */
            if (((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyStts() != null) {
                return new Predicate[]{betweenTime(new BizInstructorIdentify(), requestObject.getStartDate(), requestObject.getEndDate()),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyNo(), bizInstructorIdentify.bizInstrIdntyNo::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getYear(), bizInstructorIdentify.bizInstrIdntyYm.substring(0,4)::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getMonth(), bizInstructorIdentify.bizInstrIdntyYm.substring(4,6)::eq)
                };
            } else {
                return new Predicate[]{betweenTime(new BizInstructorIdentify(), requestObject.getStartDate(), requestObject.getEndDate()),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyNo(), bizInstructorIdentify.bizInstrIdntyNo::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getUserId(), bizInstructorIdentify.registUserId::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getOrgCd(), bizOrganizationAply.orgCd::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizOrgAplyPic(), bizOrganizationAply.bizOrgAplyPic::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getBizInstrIdntyStts(), bizInstructorIdentify.bizInstrIdntyStts::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getYear(), bizInstructorIdentify.bizInstrIdntyYm.substring(0,4)::eq),
                        condition(((BizInstructorIdentifyViewRequestVO) requestObject).getMonth(), bizInstructorIdentify.bizInstrIdntyYm.substring(4,6)::eq)};
            }
        } else if(requestObject instanceof BizInstructorIdentifyDtlViewRequestVO) { /** 강의확인서 강의시간표 */
            return new Predicate[] {
                    condition(((BizInstructorIdentifyDtlViewRequestVO) requestObject).getBizInstrIdntyNo(), bizInstructorIdentifyDtl.bizInstrIdntyNo::eq),
                    condition(((BizInstructorIdentifyDtlViewRequestVO) requestObject).getVdoLctYn(), bizInstructorIdentifyDtl.vdoLctYn::eq),
                    searchContainText(requestObject, ((BizInstructorIdentifyDtlViewRequestVO) requestObject).getContainTextType(), ((BizInstructorIdentifyDtlViewRequestVO) requestObject).getContainText()) };
        }else if(requestObject instanceof FormeBizlecinfoApiRequestVO) { /** 포미 강의확인서 API */
            return new Predicate[]{
                    condition(((FormeBizlecinfoApiRequestVO) requestObject).getBlciId(), formeBizlecinfo.blciId::eq),
                    condition(((FormeBizlecinfoApiRequestVO) requestObject).getYear(), formeBizlecinfo.blciYymm::startsWith),
                    condition(((FormeBizlecinfoApiRequestVO) requestObject).getRgn(), formeBizlecinfo.blciAreaNm::eq),
                    condition(((FormeBizlecinfoApiRequestVO) requestObject).getBlciUserId(), formeBizlecinfo.blciUserId::eq),
                    searchContainText(requestObject, ((FormeBizlecinfoApiRequestVO) requestObject).getContainTextType(), ((FormeBizlecinfoApiRequestVO) requestObject).getContainText()) };
        }else if(requestObject instanceof FormeBizlecinfoDetailApiRequestVO) { /** 포미 강의확인서 API */
            return new Predicate[]{
                    condition(((FormeBizlecinfoDetailApiRequestVO) requestObject).getBlciId(), formeBizlecinfo.blciId::eq),
                    condition(((FormeBizlecinfoDetailApiRequestVO) requestObject).getBlciUserId(), formeBizlecinfo.blciUserId::eq),
            };
        } else if(requestObject instanceof BizOrganizationAplyViewRequestVO) { /** 사업 공모 신청 */
            return new Predicate[] { condition(((BizOrganizationAplyViewRequestVO) requestObject).getBizOrgAplyNo(), bizOrganizationAply.bizOrgAplyNo::eq),
                    condition(((BizOrganizationAplyViewRequestVO) requestObject).getUserId(), bizOrganizationAply.bizOrgAplyPic::eq)};
        } else if(requestObject instanceof BizAplyViewRequestVO) { /** 사업 공고 신청 - 언론인/기본형 */
            if (((BizAplyViewRequestVO) requestObject).getBizAplyType() != null) { // 있을 때
                if (((BizAplyViewRequestVO) requestObject).getBizAplyType().equals("journalist")) { // 언론인
                    return new Predicate[] { condition(((BizAplyViewRequestVO) requestObject).getName(), lmsUser.userName::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizPbancType(), bizPbancMaster.bizPbancType::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizPbancRnd(), bizPbancMaster.bizPbancRnd::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizAplyType(), bizAply.bizAplyType::eq),
                            searchContainText(requestObject, ((BizAplyViewRequestVO) requestObject).getContainTextType(), ((BizAplyViewRequestVO) requestObject).getContainText())};
                } else { // 기본형
                    return new Predicate[] { condition(((BizAplyViewRequestVO) requestObject).getName(), organizationInfo.organizationName::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizPbancType(), bizPbancMaster.bizPbancType::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizPbancRnd(), bizPbancMaster.bizPbancRnd::eq),
                            condition(((BizAplyViewRequestVO) requestObject).getBizAplyType(), bizAply.bizAplyType::eq),
                            searchContainText(requestObject, ((BizAplyViewRequestVO) requestObject).getContainTextType(), ((BizAplyViewRequestVO) requestObject).getContainText())};
                }
            } else { // 없을 때
                return new Predicate[] { condition(((BizAplyViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                        condition(((BizAplyViewRequestVO) requestObject).getBizPbancType(), bizPbancMaster.bizPbancType::eq),
                        condition(((BizAplyViewRequestVO) requestObject).getBizPbancRnd(), bizPbancMaster.bizPbancRnd::eq),
                        condition(((BizAplyViewRequestVO) requestObject).getBizAplyType(), bizAply.bizAplyType::eq),
                        searchContainText(requestObject, ((BizAplyViewRequestVO) requestObject).getContainTextType(), ((BizAplyViewRequestVO) requestObject).getContainText())};
            }
        } else if(requestObject instanceof BizOrganizationRsltRptViewRequestVO) { /** 결과보고서 */
            return new Predicate[] { betweenTime(new BizOrganizationRsltRpt(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((BizOrganizationRsltRptViewRequestVO) requestObject).getBizPbancYr(), bizPbancMaster.bizPbancYr::eq),
                    condition(((BizOrganizationRsltRptViewRequestVO) requestObject).getBizOrgRsltRptNo(), bizOrganizationRsltRpt.bizOrgRsltRptNo::eq),
                    condition(((BizOrganizationRsltRptViewRequestVO) requestObject).getUserId(), bizOrganizationRsltRpt.registUserId::eq),
                    searchContainText(requestObject, ((BizOrganizationRsltRptViewRequestVO) requestObject).getContainTextType(), ((BizOrganizationRsltRptViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizSurveyAnsViewRequestVO) { /** 상호평가 답변 */
            return new Predicate[] { betweenTime(new BizSurveyAns(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((BizSurveyAnsViewRequestVO) requestObject).getBizSurveyNo(), bizSurveyAns.bizSurveyNo::eq),
                    searchContainText(requestObject, ((BizSurveyAnsViewRequestVO) requestObject).getContainTextType(), ((BizSurveyAnsViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorQuestionViewRequestVO) { /** 강사 문의 질문 */
            return new Predicate[] { betweenTime(new BizInstructorQuestion(), requestObject.getStartDate(), requestObject.getEndDate()),
                    condition(((BizInstructorQuestionViewRequestVO) requestObject).getRegistUserId(), bizInstructorQuestion.registUserId::eq),
                    searchContainText(requestObject, ((BizInstructorQuestionViewRequestVO) requestObject).getContainTextType(), ((BizInstructorQuestionViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorQuestionAnswerViewRequestVO) { /** 강사 문의 질문 답변 */
            return new Predicate[] {
                    condition(((BizInstructorQuestionAnswerViewRequestVO) requestObject).getBizInstrQstnNo(), bizInstructorQuestionAnswer.bizInstrQstnNo::eq),
                    searchContainText(requestObject, ((BizInstructorQuestionAnswerViewRequestVO) requestObject).getContainTextType(), ((BizInstructorQuestionAnswerViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorClclnDdlnViewRequestVO) { /** 정산 마감일 */
            return new Predicate[] {
                    condition(((BizInstructorClclnDdlnViewRequestVO) requestObject).getBizInstrClclnDdlnNo(), bizInstructorClclnDdln.bizInstrClclnDdlnNo::eq),
                    searchContainText(requestObject, ((BizInstructorClclnDdlnViewRequestVO) requestObject).getContainTextType(), ((BizInstructorClclnDdlnViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorDistViewRequestVO) { /** 거리 증빙 */
            return new Predicate[] {
                    condition(((BizInstructorDistViewRequestVO) requestObject).getBizInstrDistNo(), bizInstructorDist.bizInstrDistNo::eq),
                    condition(((BizInstructorDistViewRequestVO) requestObject).getBizDistStts(), bizInstructorDist.bizDistStts::eq),
                    condition(((BizInstructorDistViewRequestVO) requestObject).getRegistUserId(), bizInstructorDist.registUserId::eq),
                    searchContainText(requestObject, ((BizInstructorDistViewRequestVO) requestObject).getContainTextType(), ((BizInstructorDistViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorDistCrtrAmtViewRequestVO) { /** 이동거리 기준단가 */
            return new Predicate[] {
                    condition(((BizInstructorDistCrtrAmtViewRequestVO) requestObject).getBizInstrDistCrtrAmtNo(), bizInstructorDistCrtrAmt.bizInstrDistCrtrAmtNo::eq),
                    condition(((BizInstructorDistCrtrAmtViewRequestVO) requestObject).getBizInstrDistCrtrAmtYr(), bizInstructorDistCrtrAmt.bizInstrDistCrtrAmtYr::eq),
                    searchContainText(requestObject, ((BizInstructorDistCrtrAmtViewRequestVO) requestObject).getContainTextType(), ((BizInstructorDistCrtrAmtViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizInstructorDistCrtrAmtItemViewRequestVO) { /** 이동거리 기준단가 항목 */
            return new Predicate[] {
                    condition(((BizInstructorDistCrtrAmtItemViewRequestVO) requestObject).getBizInstrDistCrtrAmtItemNo(), bizInstructorDistCrtrAmtItem.bizInstrDistCrtrAmtItemNo::eq),
                    condition(((BizInstructorDistCrtrAmtItemViewRequestVO) requestObject).getBizInstrDistCrtrAmtNo(), bizInstructorDistCrtrAmtItem.bizInstrDistCrtrAmtNo::eq),
                    searchContainText(requestObject, ((BizInstructorDistCrtrAmtItemViewRequestVO) requestObject).getContainTextType(), ((BizInstructorDistCrtrAmtItemViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizEditHistViewRequestVO) { /** 이동거리 기준단가 항목 */
            return new Predicate[] {
                    searchContainText(requestObject, ((BizEditHistViewRequestVO) requestObject).getContainTextType(), ((BizEditHistViewRequestVO) requestObject).getContainText()) };
        } else if(requestObject instanceof BizOrganizationAplyDtlViewRequestVO) { /** 사업 등록 수업계획서 */
            return new Predicate[] {
                    condition(((BizOrganizationAplyDtlViewRequestVO) requestObject).getBizOrgAplyLsnDtlYm(), bizOrganizationAplyDtl.bizOrgAplyLsnDtlYmd.substring(0, 7)::eq),
            };
        } else if(requestObject instanceof MainViewRequestVO) { /** 메인 통합 */
            return new Predicate[]{ betweenTime(requestObject),
                    condition(9, bizPbancMaster.bizPbancStts::ne),
                    condition(((MainViewRequestVO) requestObject).getBizPbancCtgr(), bizPbancMaster.bizPbancCtgr::eq),
                    condition(((MainViewRequestVO) requestObject).getBizPbancCtgrSub(), bizPbancMaster.bizPbancCtgrSub::eq),
                    condition(((MainViewRequestVO) requestObject).getBizPbancType(), bizPbancMaster.bizPbancType::in)};
        } else {
            return null;
        }
    }

    /** 조회 타입 별 포함 단어 조회 */
    private <T extends CSViewVOSupport> BooleanBuilder searchContainText(T requestObject, String containTextType, String containsText) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(containsText)) { /** 검색어가 존재할 경우 */
            if(requestObject instanceof BizPbancViewRequestVO) { /** 사업 공고 */
                if(containTextType.equals("1")) { /** 제목 + 내용 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                    booleanBuilder.or(bizPbancMaster.bizPbancCn.contains(containsText));
                } else if(containTextType.equals("2")) { /** 제목 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                } else if(containTextType.equals("3")) { /** 내용 */
                    booleanBuilder.or(bizPbancMaster.bizPbancCn.contains(containsText));
                }
            } else if(requestObject instanceof BizInstructorViewRequestVO) { /** 강사 모집 */
                if(containTextType.equals("1")) { /** 사업명 + 기관명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                } else if(containTextType.equals("2")) { /** 사업명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                } else if(containTextType.equals("3")) { /** 기관명 */
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                }
            } else if(requestObject instanceof BizInstructorAplyViewRequestVO) { /** 강사 신청 */
                if(containTextType.equals("1")) { /** 강사 모집 공고 신청 강사명 */
                    booleanBuilder.or(bizInstructorAply.bizInstrAplyInstrNm.contains(containsText));
                }
            } else if(requestObject instanceof BizInstructorAsgnmViewRequestVO) { /** 강사 배정 */
                if(containTextType.equals("1")) { /** 강사 모집 공고 신청 강사명 */
                    booleanBuilder.or(bizInstructor.bizInstrNm.contains(containsText));
                }
            } else if(requestObject instanceof BizInstructorIdentifyDtlViewRequestVO) { /** 강의확인서 강의시간표 */
                if(containTextType.equals("1")) { /** 강사 모집 공고 신청 강사명 */
                    booleanBuilder.or(bizInstructorIdentifyDtl.bizInstrIdntyDtlCn.contains(containsText));
                }
            } else if(requestObject instanceof BizSurveyViewRequestVO) { /** 상호평가 평가지 */
                if(containTextType.equals("1")) { /** 제목 + 내용 */
                    booleanBuilder.or(bizSurvey.bizSurveyNm.contains(containsText));
                    booleanBuilder.or(bizSurvey.bizSurveyCn.contains(containsText));
                } else if(containTextType.equals("2")) { /** 제목 */
                    booleanBuilder.or(bizSurvey.bizSurveyNm.contains(containsText));
                } else if(containTextType.equals("3")) { /** 내용 */
                    booleanBuilder.or(bizSurvey.bizSurveyCn.contains(containsText));
                }
            } else if(requestObject instanceof BizAplyViewRequestVO) { /** 사업 공모 신청 */
                if(containTextType.equals("1")) { /** 사업명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                }
            } else if(requestObject instanceof BizOrganizationRsltRptViewRequestVO) { /** 결과보고서 */
                if(containTextType.equals("1")) { /** 사업명 */
                    booleanBuilder.or(bizPbancMaster.bizPbancNm.contains(containsText));
                } else if(containTextType.equals("2")) { /** 수행기관 */
                    booleanBuilder.or(organizationInfo.organizationName.contains(containsText));
                }
            }else if(requestObject instanceof  FormeBizlecinfoApiRequestVO){  /** 포미 강의내역 */
                if(containTextType.equals("1")) { /** 사업명 */
                    booleanBuilder.or(formeBizlecinfo.bninTitle.contains(containsText));
                } else if(containTextType.equals("2")) { /** 수행기관 */
                    booleanBuilder.or(formeBizlecinfo.bainInstNm.contains(containsText));
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
            if(value instanceof BizPbancMaster) { /** 사업 공고 */
                return bizPbancMaster.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof BizInstructor) { /** 강사 모집 */
                return bizInstructor.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof BizSurvey) { /** 상호평가 평가지 */
                return bizSurvey.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof BizOrganizationAply) { /** 사업 공모 신청 */
                return bizOrganizationAply.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof BizInstructorAply) { /** 강사 신청 */
                return bizInstructorAply.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof BizInstructorAsgnm) { /** 강사 모집 공고 배정 */
                return bizInstructorAsgnm.createDateTime.between(startDateTime, endDateTime);
            } else if(value instanceof BizInstructorIdentify) { /** 강의확인서 */
                return bizInstructorIdentify.createDateTime.between(startDateTime, endDateTime);
            } else {
                return null;
            }
        } else {
            return null;
        }
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
            if (requestObject instanceof MainViewRequestVO) { /** 메인 통합 */
                return (bizPbancMaster.bizPbancRcptBgng.goe(startDateTime).and(bizPbancMaster.bizPbancRcptEnd.loe(endDateTime)))
                        .or(bizPbancMaster.bizPbancRcptBgng.loe(startDateTime).and(bizPbancMaster.bizPbancRcptEnd.between(startDateTime, endDateTime)))
                        .or(bizPbancMaster.bizPbancRcptBgng.between(startDateTime, endDateTime).and(bizPbancMaster.bizPbancRcptEnd.goe(endDateTime)))
                        .or(bizPbancMaster.bizPbancRcptBgng.loe(startDateTime).and(bizPbancMaster.bizPbancRcptEnd.goe(endDateTime)));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String generateCode(String prefixCode) {
        if (prefixCode.equals("PAC")) {
            return jpaQueryFactory.selectFrom(bizPbancMaster)
                    .where(bizPbancMaster.bizPbancNo.like(prefixCode+"%"))
                    .orderBy(bizPbancMaster.bizPbancNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIM")) {
            return jpaQueryFactory.selectFrom(bizInstructor)
                    .where(bizInstructor.bizInstrNo.like(prefixCode+"%"))
                    .orderBy(bizInstructor.bizInstrNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BOA")) {
            return jpaQueryFactory.selectFrom(bizOrganizationAply)
                    .where(bizOrganizationAply.bizOrgAplyNo.like(prefixCode+"%"))
                    .orderBy(bizOrganizationAply.bizOrgAplyNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizOrgAplyNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BOAD")) {
            return jpaQueryFactory.selectFrom(bizOrganizationAplyDtl)
                    .where(bizOrganizationAplyDtl.bizOrgAplyDtlNo.like(prefixCode+"%"))
                    .orderBy(bizOrganizationAplyDtl.bizOrgAplyDtlNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizOrgAplyDtlNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BORR")) {
            return jpaQueryFactory.selectFrom(bizOrganizationRsltRpt)
                    .where(bizOrganizationRsltRpt.bizOrgRsltRptNo.like(prefixCode+"%"))
                    .orderBy(bizOrganizationRsltRpt.bizOrgRsltRptNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizOrgRsltRptNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BSA")) {
            return jpaQueryFactory.selectFrom(bizSurveyAns)
                    .where(bizSurveyAns.bizSurveyAnsNo.like(prefixCode+"%"))
                    .orderBy(bizSurveyAns.bizSurveyAnsNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizSurveyAnsNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIA")) {
            return jpaQueryFactory.selectFrom(bizInstructorAply)
                    .where(bizInstructorAply.bizInstrAplyNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorAply.bizInstrAplyNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrAplyNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIG")) {
            return jpaQueryFactory.selectFrom(bizInstructorAsgnm)
                    .where(bizInstructorAsgnm.bizInstrAsgnmNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorAsgnm.bizInstrAsgnmNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrAsgnmNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BII")) {
            return jpaQueryFactory.selectFrom(bizInstructorIdentify)
                    .where(bizInstructorIdentify.bizInstrIdntyNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorIdentify.bizInstrIdntyNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrIdntyNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIID")) {
            return jpaQueryFactory.selectFrom(bizInstructorIdentifyDtl)
                    .where(bizInstructorIdentifyDtl.bizInstrIdntyDtlNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorIdentifyDtl.bizInstrIdntyDtlNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrIdntyDtlNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIQ")) {
            return jpaQueryFactory.selectFrom(bizInstructorQuestion)
                    .where(bizInstructorQuestion.bizInstrQstnNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorQuestion.bizInstrQstnNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrQstnNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIQA")) {
            return jpaQueryFactory.selectFrom(bizInstructorQuestionAnswer)
                    .where(bizInstructorQuestionAnswer.bizInstrQstnAnsNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorQuestionAnswer.bizInstrQstnAnsNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrQstnAnsNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BICD")) {
            return jpaQueryFactory.selectFrom(bizInstructorClclnDdln)
                    .where(bizInstructorClclnDdln.bizInstrClclnDdlnNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorClclnDdln.bizInstrClclnDdlnNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrClclnDdlnNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("P0T")) {
            return jpaQueryFactory.selectFrom(bizPbancTmpl0)
                    .where(bizPbancTmpl0.bizPbancTmpl0No.like(prefixCode+"%"))
                    .orderBy(bizPbancTmpl0.bizPbancTmpl0No.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancTmpl0No().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("P0TI")) {
                return jpaQueryFactory.selectFrom(bizPbancTmpl0Item)
                        .orderBy(bizPbancTmpl0Item.bizPbancTmpl0ItemNo.desc())
                        .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                                .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancTmpl0ItemNo().replace(prefixCode, "")) + 1), 7, "0"))
                                .toString())
                        .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("P1T")) {
            return jpaQueryFactory.selectFrom(bizPbancTmpl1)
                    .where(bizPbancTmpl1.bizPbancTmpl1No.like(prefixCode+"%"))
                    .orderBy(bizPbancTmpl1.bizPbancTmpl1No.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancTmpl1No().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("P2T")) {
            return jpaQueryFactory.selectFrom(bizPbancTmpl2)
                    .where(bizPbancTmpl2.bizPbancTmpl2No.like(prefixCode+"%"))
                    .orderBy(bizPbancTmpl2.bizPbancTmpl2No.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancTmpl2No().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("P3T")) {
            return jpaQueryFactory.selectFrom(bizPbancTmpl3)
                    .where(bizPbancTmpl3.bizPbancTmpl3No.like(prefixCode+"%"))
                    .orderBy(bizPbancTmpl3.bizPbancTmpl3No.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancTmpl3No().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("P4T")) {
            return jpaQueryFactory.selectFrom(bizPbancTmpl4)
                    .where(bizPbancTmpl4.bizPbancTmpl4No.like(prefixCode+"%"))
                    .orderBy(bizPbancTmpl4.bizPbancTmpl4No.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancTmpl4No().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BID")) {
            return jpaQueryFactory.selectFrom(bizInstructorDist)
                    .where(bizInstructorDist.bizInstrDistNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorDist.bizInstrDistNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrDistNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIDA")) {
            return jpaQueryFactory.selectFrom(bizInstructorDistCrtrAmt)
                    .where(bizInstructorDistCrtrAmt.bizInstrDistCrtrAmtNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorDistCrtrAmt.bizInstrDistCrtrAmtNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrDistCrtrAmtNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BIAI")) {
            return jpaQueryFactory.selectFrom(bizInstructorDistCrtrAmtItem)
                    .where(bizInstructorDistCrtrAmtItem.bizInstrDistCrtrAmtItemNo.like(prefixCode+"%"))
                    .orderBy(bizInstructorDistCrtrAmtItem.bizInstrDistCrtrAmtItemNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizInstrDistCrtrAmtItemNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("BEH")) {
            return jpaQueryFactory.selectFrom(bizEditHist)
                    .where(bizEditHist.bizEditHistNo.like(prefixCode+"%"))
                    .orderBy(bizEditHist.bizEditHistNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizEditHistNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else if (prefixCode.equals("PACR")) {
            return jpaQueryFactory.selectFrom(bizPbancResult)
                    .where(bizPbancResult.bizPbancRsltNo.like(prefixCode+"%"))
                    .orderBy(bizPbancResult.bizPbancRsltNo.desc())
                    .fetch().stream().findFirst().map(data -> new StringBuilder(prefixCode)
                            .append(StringUtils.leftPad(String.valueOf(Integer.parseInt(data.getBizPbancRsltNo().replace(prefixCode, "")) + 1), 7, "0"))
                            .toString())
                    .orElse(new StringBuilder(prefixCode).append("0000000").toString());
        }else{
            return null;
        }
    }

    @Override
    public Integer generatePbancAutoIncrease(Integer bizPbancType, Integer bizPbancYr) {
        return jpaQueryFactory.selectFrom(bizPbancMaster)
                .where(bizPbancMaster.bizPbancType.eq(bizPbancType), bizPbancMaster.bizPbancYr.eq(bizPbancYr))
                .orderBy(bizPbancMaster.bizPbancRnd.desc())
                .fetch().stream().findFirst().get().getBizPbancRnd() + 1;
    }
}
