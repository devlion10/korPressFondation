package kr.or.kpf.lms.biz.business.organization.rpt.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 결과보고서 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationRsltRptViewRequestVO extends CSViewVOSupport {
    // 사업명 / 수행기관 명 검색 필요
    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 결과보고서 일련번호 */
    private String bizOrgRsltRptNo;

    /** 사업 공고 년도 */
    private Integer bizPbancYr;

    /** 결과보고서 작성자 id */
    private String userId;

}
