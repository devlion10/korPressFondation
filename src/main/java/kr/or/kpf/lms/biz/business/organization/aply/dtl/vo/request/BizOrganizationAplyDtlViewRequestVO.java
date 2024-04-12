package kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 사업 공고 신청 수업계획서 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationAplyDtlViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 공고 신청 일련번호 */
    private String bizOrgAplyNo;

    /** 사업 공고 신청 수업계획서 해당 년월 리스트 */
    private String bizOrgAplyLsnDtlYm;

}
