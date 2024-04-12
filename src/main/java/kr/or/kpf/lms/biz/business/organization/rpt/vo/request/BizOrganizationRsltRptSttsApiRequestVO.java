package kr.or.kpf.lms.biz.business.organization.rpt.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 결과보고서 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationRsltRptSttsApiRequestVO {
    @Schema(description="결과보고서 일련번호 (복수)", required = true, example="['BORR00001']")
    private List<String> bizOrgRsltRptNos;

    @Schema(description="결과보고서 상태", required = true, example="1")
    private Integer bizOrgRsltRptStts;

}
