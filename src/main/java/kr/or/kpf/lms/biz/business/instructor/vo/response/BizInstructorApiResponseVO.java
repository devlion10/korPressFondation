package kr.or.kpf.lms.biz.business.instructor.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 강사 모집 관련 응답 객체
 */
@Schema(name="BizInstructorApiResponseVO", description="강사 모집 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorApiResponseVO extends CSResponseVOSupport {

    @Schema(description="강사 모집 일련번호", example="1")
    private String bizInstrNo;
}