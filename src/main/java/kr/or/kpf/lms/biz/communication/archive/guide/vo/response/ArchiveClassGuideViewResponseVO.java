package kr.or.kpf.lms.biz.communication.archive.guide.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name="ArchiveViewResponseVO", description="자료실 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveClassGuideViewResponseVO {

    /** 수업 지도안 코드 */
    @Schema(description="수업 지도안 코드")
    private String classGuideCode;
}
