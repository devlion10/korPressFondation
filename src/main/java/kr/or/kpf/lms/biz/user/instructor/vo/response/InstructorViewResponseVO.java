package kr.or.kpf.lms.biz.user.instructor.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 강사 관리 관련 응답 객체
 */
@Schema(name="InstructorViewResponseVO", description="강사 관리 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorViewResponseVO extends CSResponseVOSupport {
    /** 강사 일련 번호 */
    private Long instrSerialNo;
    /** 회원 ID */
    private String userId;
    /** 강사 명 */
    private String instrNm;
    /** 강사 유형 */
    private String instrCategory;
    /** 강사 상태 */
    private String instrStts;
    /** 회원 상태 */
    private String state;
}
