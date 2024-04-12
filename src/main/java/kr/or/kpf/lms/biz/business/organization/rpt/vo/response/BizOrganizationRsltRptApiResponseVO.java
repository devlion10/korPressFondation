package kr.or.kpf.lms.biz.business.organization.rpt.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결과보고서 응답 객체
 */
@Schema(name="BizOrganizationRsltRptApiResponseVO", description="결과보고서 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationRsltRptApiResponseVO extends CSResponseVOSupport {

    @Schema(description="결과보고서 일련번호", example="1")
    private String bizOrgRsltRptNo;
}