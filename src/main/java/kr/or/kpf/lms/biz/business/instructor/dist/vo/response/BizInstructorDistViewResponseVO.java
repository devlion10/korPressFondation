package kr.or.kpf.lms.biz.business.instructor.dist.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 거리 증빙 관련 응답 객체
 */
@Schema(name="BizInstructorDistViewResponseVO", description="거리 증빙 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorDistViewResponseVO extends CSResponseVOSupport {

    @Schema(description="거리 증빙 일련번호", example="1")
    private String bizInstrDistNo;
}