package kr.or.kpf.lms.biz.communication.suggestion.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.suggestion.controller.SuggestionApiContoller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="SuggestionApiRequestVO", description="교육 주제 제안 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestionApiRequestVO {

    @Schema(description="시퀀스 번호", required=true, example="")
    @NotNull(groups={SuggestionApiContoller.UpdateSuggestion.class}, message="시퀀스 번호는 필수 입니다.")
    private BigInteger sequenceNo;

    @Schema(description="교육 주제 제안 타입(1: 언론인, 2: 시민)", required = true, example="")
    @NotEmpty(groups={SuggestionApiContoller.CreateSuggestion.class}, message="교육 주제 제안 타입은 필수 입니다.")
    private String suggestionType;

    @Schema(description="제안 내용", required = true, example="")
    @NotEmpty(groups={SuggestionApiContoller.CreateSuggestion.class}, message="제안 내용은 필수 입니다.")
    private String contents;

    @Schema(description="비밀글 여부", required = true, example="")
    @NotNull(groups={SuggestionApiContoller.CreateSuggestion.class}, message="비밀글 여부는 필수 입니다.")
    private Boolean isSecurity;

    @Schema(description="댓글", required = true, example="")
    private String comment;

    @Schema(description="비밀댓글 여부", required = true, example="")
    @NotNull(groups={SuggestionApiContoller.CreateSuggestion.class}, message="비밀댓글 여부는 필수 입니다.")
    private Boolean commentSecurity;
}
