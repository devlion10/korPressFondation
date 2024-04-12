package kr.or.kpf.lms.biz.business.organization.aply.edithist.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 사업 공고 신청 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizEditHistViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 공고 신청 일련번호 */
    private String bizEditHistNo;

    /** 사업 공고 신청 일련번호 */
    private String bizEditHistTrgtNo;

    /** 사업 공고 신청 일련번호 */
    private int bizEditHistStts;


}
