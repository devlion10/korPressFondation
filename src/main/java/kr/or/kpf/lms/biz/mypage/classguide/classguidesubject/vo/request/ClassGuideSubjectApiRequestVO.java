package kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.CreateArchiveClassGuide;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.UpdateArchiveClassGuide;
import kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.controller.ClassGuideSubjectApiController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(name="ClassGuideApiRequestVO", description="수업지도안 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassGuideSubjectApiRequestVO {

    /** 일련 번호 */
    @Schema(description="일련 번호", required=true, example="")
    @NotEmpty(groups={ClassGuideSubjectApiController.CreateClassGuideSubject.class}, message="일련 번호는 필수 입니다.")
    private Long sequenceNo;

    /** 수업 지도안 코드 */
    @Schema(description="수업 지도안 코드", required=true, example="")
    @NotEmpty(groups={ClassGuideSubjectApiController.CreateClassGuideSubject.class}, message="수업 지도안 코드는 필수 입니다.")
    private String classGuideCode;

    /** 과목 코드 */
    @Schema(description="과목 코드", required=true, example="")
    @NotEmpty(groups={ClassGuideSubjectApiController.CreateClassGuideSubject.class}, message="과목 코드는 필수 입니다.")
    private String individualCode;

}
