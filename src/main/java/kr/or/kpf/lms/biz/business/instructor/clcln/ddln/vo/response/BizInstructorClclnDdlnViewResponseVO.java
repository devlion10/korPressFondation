package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 정산 마감일 관련 응답 객체
 */
@Schema(name="BizInstructorClclnDdlnViewResponseVO", description="정산 마감일 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorClclnDdlnViewResponseVO extends CSResponseVOSupport {

    @Schema(description="정산 마감일 일련번호", example="1")
    private String bizInstrClclnDdlnNo;
}