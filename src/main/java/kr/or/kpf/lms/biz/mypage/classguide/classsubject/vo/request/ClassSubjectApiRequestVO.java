package kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.CreateClassSubject;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.UpdateClassSubject;
import kr.or.kpf.lms.biz.mypage.classguide.vo.CreateClassGuide;
import kr.or.kpf.lms.biz.mypage.classguide.vo.UpdateClassGuide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Schema(name="ClassSubjectApiRequestVO", description="수업지도안 교과 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassSubjectApiRequestVO {

    /** 개별 코드 */
    @Schema(description="개별 코드", required = true, example="")
    @NotEmpty(groups={UpdateClassSubject.class}, message="개별 코드")
    private String individualCode;

    /** 상위 개별 코드 */
    @Schema(description="상위 개별 코드", required = true, example="")
    @NotEmpty(groups={CreateClassSubject.class, UpdateClassSubject.class}, message="상위 개별 코드")
    private String upIndividualCode;

    /** 내용 */
    @Schema(description="내용", required = true, example="")
    @NotEmpty(groups={CreateClassSubject.class, UpdateClassSubject.class}, message="내용")
    private String content;

    /** 깊이 */
    @Schema(description="깊이", required = true, example="")
    @NotEmpty(groups={CreateClassSubject.class, UpdateClassSubject.class}, message="깊이")
    private Integer depth;

    /** 순서 */
    @Schema(description="깊이", required = true, example="")
    @NotEmpty(groups={CreateClassSubject.class, UpdateClassSubject.class}, message="깊이")
    private Integer order;

    /** 설명 */
    @Schema(description="설명", required = false, example="")
    private String description;
}
