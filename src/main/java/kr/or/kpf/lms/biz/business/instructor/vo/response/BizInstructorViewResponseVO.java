package kr.or.kpf.lms.biz.business.instructor.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강사 모집 관련 응답 객체
 */
@Schema(name="BizInstructorViewResponseVO", description="강사 모집 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorViewResponseVO extends CSResponseVOSupport {

    @Schema(description="강사 모집명", example="강사 모집1")
    private String bizInstructorName;
}