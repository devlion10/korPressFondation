package kr.or.kpf.lms.biz.business.instructor.question.answer.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.CreateBizInstructorQuestionAnswer;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.UpdateBizInstructorQuestionAnswer;
import kr.or.kpf.lms.biz.business.instructor.question.vo.CreateBizInstructorQuestion;
import kr.or.kpf.lms.biz.business.instructor.question.vo.UpdateBizInstructorQuestion;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 강사 지원 문의 답변 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorQuestionAnswerApiRequestVO {
    @Schema(description="강사 지원 문의 일련번호", required = false, example="1")
    private String bizInstrQstnAnsNo;

    @Schema(description="강사 공고 일련번호(사업명, 수행기관, 교육기간)", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorQuestionAnswer.class, UpdateBizInstructorQuestionAnswer.class}, message="강사 공고 일련번호은 필수 입니다.")
    private String bizInstrQstnNo;

    @Schema(description="강사 지원 문의 내용", required = true, example="홍길동입니다")
    @NotEmpty(groups={CreateBizInstructorQuestionAnswer.class, UpdateBizInstructorQuestionAnswer.class}, message="강사 지원 문의 내용은 필수 입니다.")
    private String bizInstrQstnAnsCn;

    @Schema(description="강사 지원 문의 상태", required = true, example="")
    @NotNull(groups={CreateBizInstructorQuestionAnswer.class, UpdateBizInstructorQuestionAnswer.class}, message="강사 지원 문의 상태는 필수 입니다.")
    private Integer bizInstrQstnStts;

}
