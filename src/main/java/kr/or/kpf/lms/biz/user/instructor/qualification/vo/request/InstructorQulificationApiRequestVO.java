package kr.or.kpf.lms.biz.user.instructor.qualification.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="InstructorQulificationApiRequestVO", description="강사 관리 자격증 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorQulificationApiRequestVO {

    @Schema(description="강사 자격증 일련 번호", example="")
    private Long sequenceNo;

    @Schema(description="강사 일련 번호", example="")
    private Long instrSerialNo;
}
