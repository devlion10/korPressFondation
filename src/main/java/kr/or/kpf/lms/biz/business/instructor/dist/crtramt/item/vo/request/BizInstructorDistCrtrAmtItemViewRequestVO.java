package kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 이동거리 기준단가 항목 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorDistCrtrAmtItemViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 이동거리 기준단가 항목 일련번호 */
    private String bizInstrDistCrtrAmtItemNo;

    /** 이동거리 기준단가 일련번호 */
    private String bizInstrDistCrtrAmtNo;

}
