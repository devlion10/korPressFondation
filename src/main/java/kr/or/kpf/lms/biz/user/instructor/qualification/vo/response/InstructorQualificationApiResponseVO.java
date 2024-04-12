package kr.or.kpf.lms.biz.user.instructor.qualification.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="InstructorQualificationApiResponseVO", description="강사 관리 자격증 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorQualificationApiResponseVO extends CSResponseVOSupport {

    @Schema(description="강사 자격증 일련 번호", example="")
    private Long sequenceNo;
}
