package kr.or.kpf.lms.biz.business.pbanc.master.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 사업 공고 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancCustomViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 구분 */
    private Integer bizPbancCtgr;

    /** 사업 상태 */
    private Integer bizPbancStts;

    /** 사업 년도 */
    private Integer bizPbancYr;

    /** 사업 코드 */
    private String bizPbancNo;

    @JsonIgnore
    private String loginOrgCd;
}
