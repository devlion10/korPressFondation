package kr.or.kpf.lms.biz.business.instructor.question.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강사 지원 문의 관련 응답 객체
 */
@Schema(name="BizInstructorAplyViewResponseVO", description="강사 지원 문의 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorQuestionViewResponseVO extends CSResponseVOSupport {

    @Schema(description="강사 지원 문의 일련번호", example="1")
    private String bizInstrQstnNo;
}