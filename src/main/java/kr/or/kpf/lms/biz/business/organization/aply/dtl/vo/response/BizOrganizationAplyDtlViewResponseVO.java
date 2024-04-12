package kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 신청 수업계획 응답 객체
 */
@Schema(name="BizOrganizationAplyDtlViewResponseVO", description="수업계획 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationAplyDtlViewResponseVO extends CSResponseVOSupport {

    @Schema(description="강사 모집명", example="강사 모집1")
    private String bizInstructorName;
}