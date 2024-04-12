package kr.or.kpf.lms.biz.mypage.educationstate.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.mypage.educationstate.controller.MyEducationStateApiController;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.AnswerEvaluateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(name="MyEducationStateApiRequestVO", description="나의 교육 현황 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyEducationStateApiRequestVO {

    /** 교육 신청 일련번호 */
    @Schema(description="교육 신청 일련번호")
    @NotNull(groups={MyEducationStateApiController.SubmitEducationEvaluate.class, MyEducationStateApiController.SubmitQuestionItem.class, MyEducationStateApiController.SubmitEducationEvaluate.class,
            MyEducationStateApiController.SubmitEducationEvaluate.class}, message="교육 신청 일련번호는 필수 입니다.")
    private String applicationNo;

    /** 강의 평가 질문 문항 일련 번호 */
    @Schema(description="강의 평가 질문 문항 일련 번호")
    @NotNull(groups={MyEducationStateApiController.SubmitEducationEvaluate.class}, message="강의 평가 질문 문항 일련 번호는 필수 입니다.")
    private List<AnswerEvaluateVO> answerEvaluateInfo;

    /** 회원 기타 의견 */
    @Schema(description="회원 기타 의견")
    private String answerComments;

    /** 차시 코드 */
    @Schema(description="차시 코드")
    @NotNull(groups={MyEducationStateApiController.SubmitSectionProgress.class}, message="차시 코드는 필수 입니다.")
    private String chapterCode;

    /** 절 코드 */
    @Schema(description="절 코드")
    @NotNull(groups={MyEducationStateApiController.SubmitSectionProgress.class}, message="절 코드는 필수 입니다.")
    private String sectionCode;

    /** 절 진도율 */
    @Schema(description="절 진도율")
    @NotNull(groups={MyEducationStateApiController.SubmitSectionProgress.class}, message="절 진도율은 필수 입니다.")
    private Double sectionProgressRate;

    /** 시험 일련번호 */
    @Schema(description="시험 일련 번호")
    @NotNull(groups={MyEducationStateApiController.SubmitQuestionItem.class}, message="시험 일련번호는 필수 입니다.")
    private String examSerialNo;

    /** 시험 문제 일련번호 */
    @Schema(description="시험 문제 일련번호")
    @NotNull(groups={MyEducationStateApiController.SubmitQuestionItem.class}, message="시험 문제 일련번호는 필수 입니다.")
    private String questionSerialNo;

    /** 시험 문제 답 */
    @Schema(description="시험 문제 답")
    @NotNull(groups={MyEducationStateApiController.SubmitQuestionItem.class}, message="시험 문제 답은 필수 입니다.")
    private List<String> answerQuestionItemValue;
}
