package kr.or.kpf.lms.biz.mypage.instructorstate.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.organization.aply.vo.CreateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.organization.aply.vo.UpdateBizOrganizationAply;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.repository.entity.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 마이페이지 - 완료된 사업공고 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyInstructorStateCustomApiResponseVO extends CSViewVOSupport {

/*    @Schema(description="사업 공고 신청 일련번호", required = true, example="BOA000001")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC000001")
    private String bizPbancNo;

    @Schema(description="기관 일련 번호", required = true, example="ORG000001")
    private String orgCd;

    @Schema(description="사업 공고 신청 담당자 이름", required = true, example="홍길동")
    private String bizOrgAplyPicNm;

    @Schema(description="사업 공고 신청 담당자 직위", required = true, example="과장")
    private String bizOrgAplyPicJbgd;

    @Schema(description="사업 공고 신청 담당자 이메일", required = true, example="hong@naver.com")
    private String bizOrgAplyPicEml;

    @Schema(description="사업 공고 신청 담당자 번호", required = true, example="02-123-1234")
    private String bizOrgAplyPicTelno;

    @Schema(description="사업 공고 신청 담당자 핸드폰번호", required = true, example="010-1234-1234")
    private String bizOrgAplyPicMpno;

    @Schema(description="사업 공고 신청 접수 상태 - 0: 임시저장, 1: 신청, 2: 승인, 3: 반려", required = true, example="0")
    private Integer bizOrgAplyStts;

    @Schema(description="상태 변경 사유", required = true, example="-")
    private String bizOrgAplySttsCmnt;

    @Schema(description="운영 구분", required = true, example="모집")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="운영 구분는 필수 입니다.")
    private String bizOrgAplyOperCtgr;

    @Schema(description="수업 형태(운영 방법)", required = false, example="1")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 형태(운영 방법)은 필수 입니다.")
    private String bizOrgAplyOperMeth;

    @Schema(description="수업 계획 - 교육기간 - 시작일", required = false, example="2022-12-01")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 계획 - 교육기간 - 시작일은 필수 입니다.")
    @Convert(converter=DateYMDToStringConverter.class)
    private String bizOrgAplyLsnPlanBgng;

    @Schema(description="수업 계획 - 교육기간 - 종료일", required = false, example="2022-12-20")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 계획 - 교육기간 - 종료일은 필수 입니다.")
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizOrgAplyLsnPlanEnd;

    @Schema(description="수업 대상", required = false, example="1")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 대상은 필수 입니다.")
    private String bizOrgAplyLsnPlanTrgt;

    @Schema(description="회차당 교육 인원", required = false, example="1")
    @NotNull(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="회차당 교육 인원은 필수 입니다.")
    private Integer bizOrgAplyLsnPlanNope;

    @Schema(description="설명1(교육의 방향성 및 신청한 이유)", required = false, example="1")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="설명1(교육의 방향성 및 신청한 이유)은 필수 입니다.")
    private String bizOrgAplyLsnPlanDscr1;

    @Schema(description="설명2(미디어교육 운영 현황)", required = false, example="1")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="설명2(미디어교육 운영 현황)은 필수 입니다.")
    private String bizOrgAplyLsnPlanDscr2;

    @Schema(description="설명3(향후 활용계획 및 수업을 통한 기대효과)", required = false, example="1")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="설명3(향후 활용계획 및 수업을 통한 기대효과)은 필수 입니다.")
    private String bizOrgAplyLsnPlanDscr3;

    @Schema(description="건의사항 - 희망강사", required = false, example="1")
    private String bizOrgAplyLsnPlanEtcInstr;

    @Schema(description="기타 의견", required = false, example="1")
    private String bizOrgAplyLsnPlanEtc;

    @Schema(description="사업 공고 신청 첨부파일 (신청서)", required = false, example="1")
    private String bizOrgAplyFile;

    @Transient
    @Schema(description="사업 공고 수업 계획서 리스트", required = false, example="1")
    private List<BizOrganizationAplyDtl> bizOrganizationAplyDtlList;

    @Transient
    @Schema(description="교육월", required = false, example="1")
    private String bizOrganizationDtlM;*/

    @Schema(description="강사 모집 신청 일련번호", required = false, example="1")
    private String bizInstrAplyNo;

    @Schema(description="강사 모집 일련번호", required = true, example="ISR0000001")
    private String bizInstrNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="ISR0000001")
    private String bizOrgAplyNo;

    @Schema(description="강사 모집 공고 신청 강사명", required = false, example="홍길동")
    private String bizInstrAplyInstrNm;

    @Schema(description="강사 모집 공고 신청 아이디", required = true, example="abc")
    private String bizInstrAplyInstrId;

    @Schema(description="강사 모집 공고 신청 강사 지원 조건 - 순위", required = false, example="1")
    private Integer bizInstrAplyCndtOrdr;

    @Schema(description="강사 모집 공고 신청 강사 지원 조건 - 거리", required = false, example="42.195")
    private float bizInstrAplyCndtDist;

    @Schema(description="강사 모집 공고 신청 강사 배정 점수(선정 점수)", required = false, example="10")
    private Integer bizInstrAplySlctnScr;

    @Schema(description="강사 모집 공고 신청 강사 배정 조건(선정 조건)", required = false, example="5")
    private Integer bizInstrAplySlctnCndt;

    @Schema(description="강사 모집 공고 신청 강사 코멘트", required = false, example="memo")
    private String bizInstrAplyCmnt;

    @Schema(description="강사모집 사업공고 시퀀스 번호", required = false, example="memo")
    private Long sequenceNo;

    @Schema(description="강사 모집 공고 신청 강사 정보 상태 0: 임시저장, 1: 접수, 2: 미승인, 3: 승인", required = true, example="1")
    private Integer bizInstrAplyStts;

    @Schema(description="등록자 id", required = true, example="1")
    private String registUserId;

    @Schema(description="작성월", required = true, example="1")
    private String month;

    @Schema(description="강사 신청 대상 신청 공고", example="1")
    private BizOrganizationAply bizOrganizationAply;

    @Transient
    private BizInstructor bizInstructor;

    @Transient
    private BizPbancMaster bizPbancMaster;

    @Transient
    private List<BizOrganizationAplyDtl> bizOrganizationAplyDtls;

    @Transient
    private BizInstructorDist bizInstructorDist;

    @Transient
    private String bizInstrIdntyNo;

    @Transient
    private BizInstructorIdentify bizInstrIdnty;

    private String bizInstructorIdentifyDtls;
}
