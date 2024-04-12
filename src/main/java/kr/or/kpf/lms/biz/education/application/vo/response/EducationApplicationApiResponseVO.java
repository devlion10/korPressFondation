package kr.or.kpf.lms.biz.education.application.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

@Schema(name="EducationApplicationApiResponseVO", description="교육 신청 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EducationApplicationApiResponseVO extends CSResponseVOSupport {

    @Schema(description="로그인 아이디", example="")
    private String userId;

    @Schema(description="과정 코드", example="CRCL00001")
    private String curriculumCode;

    @Schema(description="관리자 승인 여부(N: 미승인, Y: 승인)", example="false")
    private Boolean isAdminApproval;

    @Schema(description="교육 과정 신청 지원서 파일 경로", example="")
    private String applicationFilePath;
}
