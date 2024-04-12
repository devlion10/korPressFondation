package kr.or.kpf.lms.biz.business.organization.rpt.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.CreateBizOrganizationRsltRpt;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.UpdateBizOrganizationRsltRpt;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 결과보고서 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationRsltRptApiRequestVO {
    @Schema(description="결과보고서 일련번호", required = true, example="BORR00001")
    private String bizOrgRsltRptNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="BOA000001")
    @NotEmpty(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="사업 공고 신청 일련번호는 필수 입니다.")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC000001")
    @NotEmpty(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="사업 공고 일련번호는 필수 입니다.")
    private String bizPbancNo;

    @Schema(description="기관 코드", required = true, example="ORG000001")
    @NotEmpty(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="기관 코드는 필수 입니다.")
    private String orgCd;

    @Schema(description="결과보고서 사업 성과", required = true, example=" ")
    @NotEmpty(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="결과보고서 사업 성과는 필수 입니다.")
    private String bizOrgRsltRptRslt;

    @Schema(description="결과보고서 우수 사례", required = true, example=" ")
    @NotEmpty(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="결과보고서 우수 사례는 필수 입니다.")
    private String bizOrgRsltRptExmp;

    @Schema(description="결과보고서 요청사항(건의사항 및 개선사항)", required = true, example=" ")
    @NotEmpty(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="결과보고서 요청사항은 필수 입니다.")
    private String bizOrgRsltRptDmnd;

    @Schema(description="결과보고서 상태", required = true, example="1")
    @NotNull(groups={CreateBizOrganizationRsltRpt.class, UpdateBizOrganizationRsltRpt.class}, message="결과보고서 상태는 필수 입니다.")
    private Integer bizOrgRsltRptStts;

    @Schema(description="결과보고서 첨부파일", required = false, example=" ")
    private String bizOrgRsltRptFile;

}
