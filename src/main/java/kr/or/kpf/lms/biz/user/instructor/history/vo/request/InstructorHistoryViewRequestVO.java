package kr.or.kpf.lms.biz.user.instructor.history.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="InstructorHistoryViewRequestVO", description="강사 관리 주요 이력 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstructorHistoryViewRequestVO extends CSViewVOSupport {
    /** 강사 주요 이력 일련 번호 */
    private Long sequenceNo;

    /** 강사 일련 번호 */
    private Long instrSerialNo;
}
