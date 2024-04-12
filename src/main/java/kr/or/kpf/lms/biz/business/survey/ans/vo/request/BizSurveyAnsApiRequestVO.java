package kr.or.kpf.lms.biz.business.survey.ans.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.survey.ans.vo.CreateBizSurveyAns;
import kr.or.kpf.lms.biz.business.survey.ans.vo.UpdateBizSurveyAns;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

/**
 상호평가 - 응답 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizSurveyAnsApiRequestVO {

    @Schema(description="상호평가 응답 일련번호", required = true, example="BSA00000000")
    private String bizSurveyAnsNo;

    @Schema(description="상호평가 문항 일련번호", required = true, example="BSI0000000")
    @NotEmpty(groups={CreateBizSurveyAns.class, UpdateBizSurveyAns.class}, message="상호평가 문항 일련번호는 필수 입니다.")
    private String bizSurveyQitemNo;

    @Schema(description="상호평가 일련번호", required = true, example="BSQ0000000")
    @NotEmpty(groups={CreateBizSurveyAns.class, UpdateBizSurveyAns.class}, message="상호평가 일련번호는 필수 입니다.")
    private String bizSurveyNo;

    @Schema(description="상호평가 대상 일련번호(강사 - 사업 신청, 기관 - 사업 신청 수업계획서)", example="BOAD0000000")
    @NotEmpty(groups={CreateBizSurveyAns.class, UpdateBizSurveyAns.class}, message="상호평가 대상 일련번호는 필수 입니다.")
    private String bizSurveyTrgtNo;

    @Schema(description="상호평가 문항 답변", required = true, example="3")
    @NotEmpty(groups={CreateBizSurveyAns.class, UpdateBizSurveyAns.class}, message="상호평가 문항 답변은 필수 입니다.")
    private String bizSurveyAnsCn;

    @Schema(description="상호평가 기타 문항 답변", required = false, example="기타내용")
    private String bizSurveyAnsEtc;

}
