package kr.or.kpf.lms.biz.communication.archive.guide.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

@Schema(name="ArchiveClassGuideViewRequestVO", description="자료실 - 수업지도안 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveClassGuideViewRequestVO extends CSViewVOSupport {
    /** 검색어 범위 */
    @Schema(description="검색어 범위")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @Schema(description="검색에 포함할 단어")
    private String containText;

    /** 수업 지도안 코드 */
    @Schema(description="수업 지도안 코드")
    private String classGuideCode;

    /** 수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) ) */
    @Schema(description="수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) )")
    private String classGuideType;

    /** 수업지도안 신청 상태(0: 임시저장, 1: 신청(접수)) */
    @Schema(description="수업지도안 신청 상태(0: 임시저장, 1: 신청(접수))")
    private String classGuideApplyStatus;

    /** 수업지도안 승인 상태(0: 미승인, 1: 승인) */
    @Schema(description="수업지도안 승인 상태(0: 미승인, 1: 승인)")
    private String classGuideApprovalStatus;

    private String targets;
    private String targetGrades;
    private String years;
    private String subjects;
}
