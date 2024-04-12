package kr.or.kpf.lms.biz.business.survey.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizSurveyQitem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 상호평가 평가지 관련 응답 객체
 */
@Schema(name="BizSurveyViewResponseVO", description="상호평가 평가지 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizSurveyViewResponseVO extends CSResponseVOSupport {

    @Schema(description="상호평가 평가지 일련번호", example="1")
    private String bizSurveyNo;

    @Schema(description="상호평가 평가지 구분", example="기관")
    private String bizSurveyCtgr;

    @Schema(description="상호평가 평가지명", example="기관평가")
    private String bizSurveyNm;

    @Schema(description="상호평가 평가지 내용", example="기관평가 입니다.")
    private String bizSurveyCn;

    @Schema(description="상호평가 평가지 상태", example="1")
    private String bizSurveyStts;

    @Schema(description="상호평가 평가지 문항 리스트", required = false, example="1")
    private List<BizSurveyQitem> bizSurveyQitems;
    
}