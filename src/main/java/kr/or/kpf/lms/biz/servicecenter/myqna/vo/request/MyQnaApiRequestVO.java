package kr.or.kpf.lms.biz.servicecenter.myqna.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.servicecenter.myqna.controller.MyQnaApiController;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="MyQnaApiRequestVO", description="1:1 문의 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyQnaApiRequestVO {

    @Schema(description="시퀀스 번호", example="")
    @NotNull(groups={MyQnaApiController.UpdateMyQna.class}, message="시퀀스 번호는 필수 입니다.")
    private BigInteger sequenceNo;

    /** 문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의) */
    @Schema(description="문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의)", required=true, example="")
    @NotEmpty(groups={MyQnaApiController.CreateMyQna.class}, message="문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의)는 필수 입니다.")
    private String qnaType;

    /** 문의 제목 */
    @Schema(description="문의 제목", required=true, example="")
    @NotEmpty(groups={MyQnaApiController.CreateMyQna.class}, message="문의 제목는 필수 입니다.")
    private String reqTitle;

    /** 문의 내용 */
    @Schema(description="문의 내용", required=true, example="")
    @NotEmpty(groups={MyQnaApiController.CreateMyQna.class}, message="문의 내용는 필수 입니다.")
    private String reqContents;

    /** 답변 제목 */
    @Schema(description="답변 제목", example="")
    private String resTitle;

    /** 답변 내용 */
    @Schema(description="답변 내용", example="")
    private String resContents;

    /** 문의 상태(1: 미확인, 2: 처리중, 3: 답변 완료) */
    @Schema(description="문의 상태(1: 미확인, 2: 처리중, 3: 답변 완료)", example="")
    private String qnaState;
}
