package kr.or.kpf.lms.biz.educenter.press.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.educenter.press.controller.PressApiController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="PressApiRequestVO", description="행사소개(보도자료) API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PressApiRequestVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호", required=true, example="")
    @NotNull(groups={PressApiController.UpdatePress.class}, message="시퀀스 번호는 필수 입니다.")
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목", required=true, example="")
    @NotEmpty(groups={PressApiController.UpdatePress.class}, message="제목은 필수 입니다.")
    private String title;

    /** 내용 */
    @Schema(description="내용", required=true, example="")
    @NotEmpty(groups={PressApiController.UpdatePress.class}, message="내용은 필수 입니다.")
    private String contents;
}
