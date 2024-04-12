package kr.or.kpf.lms.biz.mypage.businessstate.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.organization.aply.vo.CreateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.organization.aply.vo.UpdateBizOrganizationAply;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 사업 공고 신청 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyBusinessStateApiRequestVO {
    @Schema(description="사업 공고 신청 일련번호", required = true, example="BOA000001")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC000001")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 일련번호은 필수 입니다.")
    private String bizPbancNo;

    @Schema(description="기관 일련 번호", required = true, example="ORG000001")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="기관 일련 번호은 필수 입니다.")
    private String orgCd;

    @Schema(description="사업공고 신청 기관 대표자명", required = true, example="ORG000001")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업공고 신청 기관 대표자명은 필수 입니다.")
    private String bizOrgAplyRprsvNm;

    @Schema(description="사업공고 신청 총 시간", required = false, example="")
    private double bizOrgAplyTime;

    @Schema(description="사업공고 신청 최초 총 시간", required = false, example="")
    private double bizOrgAplyTimeFrst;

    @Schema(description="사업 공고 신청 지역", required = true, example="홍길동")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 지역은 필수 입니다.")
    private String bizOrgAplyRgn;

    @Schema(description="사업 공고 신청 담당자 아이디", required = true, example="홍길동")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 담당자 아이디는 필수 입니다.")
    private String bizOrgAplyPic;

    @Schema(description="사업 공고 신청 담당자 이름", required = true, example="홍길동")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 담당자 이름은 필수 입니다.")
    private String bizOrgAplyPicNm;

    @Schema(description="사업 공고 신청 담당자 직위", required = true, example="과장")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 담당자 직위은 필수 입니다.")
    private String bizOrgAplyPicJbgd;

    @Schema(description="사업 공고 신청 담당자 이메일", required = true, example="hong@naver.com")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 담당자 이메일은 필수 입니다.")
    private String bizOrgAplyPicEml;

    @Schema(description="사업 공고 신청 담당자 번호", required = true, example="02-123-1234")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 담당자 번호은 필수 입니다.")
    private String bizOrgAplyPicTelno;

    @Schema(description="사업 공고 신청 담당자 핸드폰번호", required = true, example="010-1234-1234")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 담당자 핸드폰번호은 필수 입니다.")
    private String bizOrgAplyPicMpno;

    @Schema(description="사업 공고 신청 접수 상태 0: 임시저장, 1: 신청, 2: 승인, 3: 반려, 7: 종료, 9: 가승인", required = true, example="0")
    @NotNull(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="사업 공고 신청 접수 상태은 필수 입니다.")
    private Integer bizOrgAplyStts;

    @Schema(description="운영 구분", required = true, example="모집")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="운영 구분는 필수 입니다.")
    private String bizOrgAplyOperCtgr;

    @Schema(description="수업 형태(운영 방법)", required = false, example="1")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 형태(운영 방법)은 필수 입니다.")
    private String bizOrgAplyOperMeth;

    @Schema(description="수업 계획 - 교육기간 - 시작일", required = false, example="2022-12-01")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 계획 - 교육기간 - 시작일은 필수 입니다.")
    private String bizOrgAplyLsnPlanBgng;

    @Schema(description="수업 계획 - 교육기간 - 종료일", required = false, example="2022-12-20")
    @NotEmpty(groups={CreateBizOrganizationAply.class, UpdateBizOrganizationAply.class}, message="수업 계획 - 교육기간 - 종료일은 필수 입니다.")
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

    /** 신문 신청 여부(없음/0: N, 1: Y) */
    @Schema(description="신문 신청 여부(없음/0: N, 1: Y)", required = false, example="1")
    private Integer bizOrgAplyPeprYn;

    @Schema(description="사업 공고 신청 첨부파일 (신청서)", required = false, example="1")
    private String bizOrgAplyFile;

}
