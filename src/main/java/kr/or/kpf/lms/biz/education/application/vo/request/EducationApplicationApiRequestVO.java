package kr.or.kpf.lms.biz.education.application.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.education.application.controller.EducationApplicaitonApiController;
import kr.or.kpf.lms.common.converter.BooleanConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.NotEmpty;

@Schema(name="EducationApplicationApiRequestVO", description="교육 신청 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EducationApplicationApiRequestVO {

    /** 교육 일정 코드 */
    @Schema(description="과정 일정 코드", required = true, example="PLAN0000001")
    @NotEmpty(groups={EducationApplicaitonApiController.CreateEducationApplication.class}, message="과정 일정 코드는 필수 입니다.")
    private String educationPlanCode;

    /** 선택 교육 유형 */
    @Schema(description="선택 교육 유형")
    private String setEducationType;

    /** 숙박 여부 */
    @Schema(description="숙박 여부")
    private Boolean isAccommodation;

    /** 신청서 파일 경로 */
    @JsonIgnore
    @Schema(description="신청서 파일", hidden = true)
    private MultipartFile applicationFile;
}
