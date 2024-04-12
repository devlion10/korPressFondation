package kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.CreateBizOrganizationAplyDtl;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.UpdateBizOrganizationAplyDtl;
import lombok.*;

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
public class BizOrganizationAplyDtlApiRequestVO {

    @Schema(description="사업 공고 신청 수업 계획서 일련번호", required = true, example="BOA0000001")
    private String bizOrgAplyDtlNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="BOA0000003")
    @NotEmpty(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="사업 공고 신청 일련번호은 필수 입니다.")
    private String bizOrgAplyNo;

    @Schema(description="수업 계획서 교육 회차", required = true, example="2")
    @NotNull(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 교육 회차은 필수 입니다.")
    private Integer bizOrgAplyLsnDtlRnd;

    @Schema(description="수업 계획서 수업 일자", required = true, example="2022-12-01")
    @NotEmpty(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 일자은 필수 입니다.")
    private String bizOrgAplyLsnDtlYmd;

    @Schema(description="수업 계획서 수업 주제", required = true, example="미디어란")
    @NotEmpty(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 주제은 필수 입니다.")
    private String bizOrgAplyLsnDtlTpic;

    @Schema(description="수업 계획서 수업 시수", required = true, example="3")
    @NotNull(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 시수은 필수 입니다.")
    private Integer bizOrgAplyLsnDtlHr;

    @Schema(description="수업 계획서 수업 시작 시각", required = true, example="10:00")
    @NotEmpty(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 시작 시각은 필수 입니다.")
    private String bizOrgAplyLsnDtlBgngTm;

    @Schema(description="수업 계획서 수업 종료 시각", required = true, example="12:00")
    @NotEmpty(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 종료 시각은 필수 입니다.")
    private String bizOrgAplyLsnDtlEndTm;

    @Schema(description="수업 계획서 수업 인원", required = true, example="5")
    @NotNull(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 인원은 필수 입니다.")
    private Integer bizOrgAplyLsnDtlNope;

    @Schema(description="수업 계획서 수업 장소", required = true, example="5")
    @NotEmpty(groups={CreateBizOrganizationAplyDtl.class, UpdateBizOrganizationAplyDtl.class}, message="수업 계획서 수업 장소는 필수 입니다.")
    private String bizOrgAplyLsnDtlPlc;

    @Schema(description="수업 계획서 수업 계획서 비고", required = false, example="수업계획서 비고")
    private String bizOrgAplyLsnDtlEtc;

}
