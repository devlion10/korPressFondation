package kr.or.kpf.lms.biz.business.survey.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.survey.vo.CreateBizSurvey;
import kr.or.kpf.lms.biz.business.survey.vo.UpdateBizSurvey;
import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 상호평가 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizSurveyApiRequestVO {
    @Schema(description="상호평가 평가지 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizSurvey.class, UpdateBizSurvey.class}, message="상호평가 평가지 일련번호는 필수 입니다.")
    private String bizSurveyNo;

    @Schema(description="사업 공고 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizSurvey.class, UpdateBizSurvey.class}, message="사업 공고 일련번호는 필수 입니다.")
    private String bizPbancNo;

    @Schema(description="상호평가 평가지 구분", required = true, example="기관")
    @NotEmpty(groups={CreateBizSurvey.class, UpdateBizSurvey.class}, message="상호평가 평가지 구분은 필수 입니다.")
    private String bizSurveyCtgr;

    @Schema(description="상호평가 평가지명", required = true, example="기관평가")
    @NotEmpty(groups={CreateBizSurvey.class, UpdateBizSurvey.class}, message="상호평가 평가지명은 필수 입니다.")
    private String bizSurveyNm;

    @Schema(description="상호평가 평가지 내용", required = true, example="기관평가 입니다.")
    @NotEmpty(groups={CreateBizSurvey.class, UpdateBizSurvey.class}, message="상호평가 평가지 내용은 필수 입니다.")
    private String bizSurveyCn;

    @Schema(description="상호평가 평가지 상태", required = true, example="1")
    @NotEmpty(groups={CreateBizSurvey.class, UpdateBizSurvey.class}, message="상호평가 평가지 상태는 필수 입니다.")
    private String bizSurveyStts;

}
