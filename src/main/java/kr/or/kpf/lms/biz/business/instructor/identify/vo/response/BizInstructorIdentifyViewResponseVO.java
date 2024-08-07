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
@Schema(name="BizInstructorIdentifyViewResponseVO", description="강의확인서 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorIdentifyViewResponseVO extends CSResponseVOSupport {

    @Schema(description="강의확인서 일련번호", example="1")
    private String bizInstrIdentifyNo;
}