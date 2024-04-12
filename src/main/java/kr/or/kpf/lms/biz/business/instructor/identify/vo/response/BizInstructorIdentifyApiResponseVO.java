package kr.or.kpf.lms.biz.business.instructor.identify.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강의확인서 관련 응답 객체
 */
@Schema(name="BizInstructorIdentifyApiResponseVO", description="강의확인서 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorIdentifyApiResponseVO extends CSResponseVOSupport {

    @Schema(description="강의확인서 일련번호", example="1")
    private String bizInstrIdntyNo;
}