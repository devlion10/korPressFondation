package kr.or.kpf.lms.biz.user.instructor.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="InstructorViewRequestVO", description="강사 관리 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InstructorViewRequestVO extends CSViewVOSupport {
    /** 강사 일련 번호 */
    private Long instrSerialNo;
    /** 회원 ID */
    private String userId;
    /** 강사 ID */
    private String instrId;
    /** 강사 명 */
    private String instrNm;
    /** 강사 유형 */
    private String instrCategory;
    /** 강사 상태 */
    private Integer instrStts;
    /** 회원 상태 */
    private String state;

    /** 팝업 검색 (언론인 연수 강사 제외 키) */
    private String popup;
    /** 강사 명(모두 일치할 때) */
    private String instructorName;
}
