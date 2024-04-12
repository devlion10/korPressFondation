package kr.or.kpf.lms.biz.business.survey.ans.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizSurveyQitem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

/**
 * 상호평가 평가지 응답 관련 응답 객체
 */
@Schema(name="BizSurveyAnsApiResponseVO", description="상호평가 평가지 응답 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizSurveyAnsApiResponseVO extends CSResponseVOSupport {

    @Schema(description="상호평가 답변 일련번호", example="1")
    private String bizSurveyAnsNo;

    @Schema(description="상호평가 문항 일련번호", example="1")
    private String bizSurveyQitemNo;

    @Schema(description="상호평가 평가지 일련번호", example="1")
    private String bizSurveyNo;

    @Schema(description="상호평가 대상 일련번호(강사 - 사업 신청, 기관 - 사업 신청 수업계획서)", example="1")
    private String bizSurveyTrgtNo;

    @Schema(description="상호평가 문항 답변", example="기관")
    private String bizSurveyAnsCn;

    @Schema(description="상호평가 기타 문항 답변", example="기관평가")
    private String bizSurveyAnsEtc;



}