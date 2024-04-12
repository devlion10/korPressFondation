package kr.or.kpf.lms.biz.communication.suggestion.vo.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 교육 주제 제안 관련 요청 객체
 */
@Schema(name="SuggestionViewRequestVO", description="교육 주제 제안 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionViewRequestVO extends CSViewVOSupport {
    /** 교육 주제 제안 타입 */
    @ApiModelProperty(name="suggestionType", value="교육 주제 제안 타입", required=false, dataType="string", position=0, example = "")
    private String suggestionType;
}
