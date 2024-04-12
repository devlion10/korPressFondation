package kr.or.kpf.lms.biz.education.application.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 교육신청 관련 요청 객체
 */
@Schema(name="EducationApplicationViewRequestVO", description="교육 신청 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EducationApplicationViewRequestVO extends CSViewVOSupport {

    /** 교육 과정 개설 코드 */
    @Schema(description="교육 과정 개설 코드")
    private String educationPlanCode;

    /** 교육 과정 유형 */
    @Schema(description="교육 과정 유형")
    private String educationType;

    /** 지점 */
    @Schema(description="지점")
    private String province;

    /** 교육 과정 명 */
    @Schema(description="교육 과정 명")
    private String lectureName;

    /** 교육 과정 카테고리 */
    @Schema(description="교육 과정 카테고리")
    private String educationCategory;
}
