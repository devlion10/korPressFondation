package kr.or.kpf.lms.biz.business.survey.ans.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 상호평가 평가지 응답 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizSurveyAnsViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 상호평가 일련번호 */
    private String bizSurveyNo;

    /** 상호평가 답변 일련번호 */
    private String bizSurveyAnsNo;

    /** 상호평가 답변 사용자 일련번호 */
    private Long userSn;
}
