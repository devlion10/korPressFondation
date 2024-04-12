package kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

@Schema(name="ClassGuideSubjectApiResponseVO", description="수업지도안 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ClassGuideSubjectApiResponseVO extends CSResponseVOSupport {

    /** 일련 번호 */
    @Schema(description="일련 번호", example="")
    private Long sequenceNo;

}
