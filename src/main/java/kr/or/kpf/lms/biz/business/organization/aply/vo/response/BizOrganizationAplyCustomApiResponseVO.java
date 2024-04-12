package kr.or.kpf.lms.biz.business.organization.aply.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.organization.aply.vo.CreateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.organization.aply.vo.UpdateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancCustomApiResponseVO;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizOrganizationAplyDtl;
import kr.or.kpf.lms.repository.entity.OrganizationAuthorityHistory;
import kr.or.kpf.lms.repository.entity.OrganizationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 사업 공고 신청 응답 객체
 */
@Schema(name="BizOrganizationAplyCustomApiResponseVO", description="사업 공고 신청 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationAplyCustomApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 신청 일련번호", example="1")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC000001")
    private String bizPbancNo;

    @Schema(description="기관 일련 번호", required = true, example="ORG000001")
    private String orgCd;

    @Schema(description="사업공고 신청 기관 대표자명", required = true, example="ORG000001")
    private String bizOrgAplyRprsvNm;

    @Schema(description="사업공고 신청 총 시간", required = false, example="")
    private double bizOrgAplyTime;

    @Schema(description="사업공고 신청 최초 총 시간", required = false, example="")
    private double bizOrgAplyTimeFrst;

    @Schema(description="사업 공고 신청 지역", required = true, example="홍길동")
    private String bizOrgAplyRgn;

    @Schema(description="사업 공고 신청 담당자 아이디", required = true, example="홍길동")
    private String bizOrgAplyPic;

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

    @Schema(description="사업 공고 신청 접수 상태 0: 임시저장, 1: 신청, 2: 승인, 3: 반려, 7: 종료, 9: 가승인", required = true, example="0")
    private Integer bizOrgAplyStts;

    @Schema(description="상태 변경 사유", required = false, example="-")
    private String bizOrgAplySttsCmnt;

    @Schema(description="변경 가능 여부", required = true, example="-")
    private Integer bizOrgAplyChgYn;

    @Schema(description="운영 구분", required = true, example="모집")
    private String bizOrgAplyOperCtgr;

    @Schema(description="수업 형태(운영 방법)", required = false, example="1")
    private String bizOrgAplyOperMeth;

    @Schema(description="수업 계획 - 교육기간 - 시작일", required = false, example="2022-12-01")
    private String bizOrgAplyLsnPlanBgng;

    @Schema(description="수업 계획 - 교육기간 - 종료일", required = false, example="2022-12-20")
    private String bizOrgAplyLsnPlanEnd;

    @Schema(description="수업 대상", required = false, example="1")
    private String bizOrgAplyLsnPlanTrgt;

    @Schema(description="회차당 교육 인원", required = false, example="1")
    private Integer bizOrgAplyLsnPlanNope;

    @Schema(description="설명1(교육의 방향성 및 신청한 이유)", required = false, example="1")
    private String bizOrgAplyLsnPlanDscr1;

    @Schema(description="설명2(미디어교육 운영 현황)", required = false, example="1")
    private String bizOrgAplyLsnPlanDscr2;

    @Schema(description="설명3(향후 활용계획 및 수업을 통한 기대효과)", required = false, example="1")
    private String bizOrgAplyLsnPlanDscr3;

    @Schema(description="건의사항 - 희망강사", required = false, example="1")
    private String bizOrgAplyLsnPlanEtcInstr;

    @Schema(description="기타 의견", required = false, example="1")
    private String bizOrgAplyLsnPlanEtc;

    @Schema(description="신문 신청 여부(없음/0: N, 1: Y)", required = false, example="1")
    private Integer bizOrgAplyPeprYn;

    @Schema(description="사업 공고 신청 첨부파일 (신청서)", required = false, example="1")
    private String bizOrgAplyFile;

    @Convert(converter= DateToStringConverter.class)
    private String createDateTime;

    @Convert(converter=DateToStringConverter.class)
    private String updateDateTime;

    private String modifyUserId;


    private List<BizOrganizationAplyDtl> bizOrganizationAplyDtls = new ArrayList<>();

    private BizPbancCustomApiResponseVO bizPbancMaster;

    private OrganizationInfo organizationInfo;

    private OrganizationAuthorityHistory organization;

    private Long orgAplyCnt;

    private Integer surveyEnd;
}