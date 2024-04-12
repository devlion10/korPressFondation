package kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

@Schema(name="ClassGuideApiResponseVO", description="수업지도안 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectApiResponseVO extends CSResponseVOSupport {

    /** 개별 코드 */
    @Schema(description="개별 코드", example="")
    private String individualCode;
}
