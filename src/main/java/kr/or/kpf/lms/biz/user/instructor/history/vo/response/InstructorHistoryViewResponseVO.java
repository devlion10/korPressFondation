package kr.or.kpf.lms.biz.user.instructor.history.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강사 관리 주요 이력 관련 응답 객체
 */
@Schema(name="InstructorHistoryViewResponseVO", description="강사 관리 주요 이력 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorHistoryViewResponseVO extends CSResponseVOSupport {
    /** 강사 주요 이력 일련 번호 */
    private Long sequenceNo;
}
