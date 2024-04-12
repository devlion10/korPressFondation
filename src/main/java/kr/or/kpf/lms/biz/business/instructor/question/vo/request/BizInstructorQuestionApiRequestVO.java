package kr.or.kpf.lms.biz.business.instructor.question.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.CreateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.UpdateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.CreateBizInstructorQuestionAnswer;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.UpdateBizInstructorQuestionAnswer;
import kr.or.kpf.lms.biz.business.instructor.question.vo.CreateBizInstructorQuestion;
import kr.or.kpf.lms.biz.business.instructor.question.vo.UpdateBizInstructorQuestion;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 강사 지원 문의 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorQuestionApiRequestVO {
    @Schema(description="강사 지원 문의 일련번호", required = false, example="1")
    private String bizInstrQstnNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorQuestion.class, UpdateBizInstructorQuestion.class}, message="사업 공고 신청 일련번호는 필수 입니다.")
    private String bizOrgAplyNo;

    @Schema(description="강사 지원 문의 내용", required = true, example="홍길동은 누구 입니까?")
    @NotEmpty(groups={CreateBizInstructorQuestion.class, UpdateBizInstructorQuestion.class}, message="강사 지원 문의 내용은 필수 입니다.")
    private String bizInstrQstnCn;

    @Schema(description="강사 지원 문의 상태", required = true, example="")
    @NotNull(groups={CreateBizInstructorQuestionAnswer.class, UpdateBizInstructorQuestionAnswer.class}, message="강사 지원 문의 상태는 필수 입니다.")
    private Integer bizInstrQstnStts;
}
