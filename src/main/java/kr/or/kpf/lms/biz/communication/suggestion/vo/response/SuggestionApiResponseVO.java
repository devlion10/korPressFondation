package kr.or.kpf.lms.biz.communication.suggestion.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.suggestion.controller.SuggestionApiContoller;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="SuggestionApiResponseVO", description="교육 주제 제안 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionApiResponseVO extends CSResponseVOSupport {

    @Schema(description="시퀀스 번호", example="")
    private BigInteger sequenceNo;

    @Schema(description="교육 주제 제안 타입(1: 언론인, 2: 시민)", example="")
    private String suggestionType;

    @Schema(description="제안 내용", example="")
    private String contents;

    @Schema(description="비밀글 여부", example="")
    private Boolean isSecurity;

    @Schema(description="댓글", example="")
    private String comment;

    @Schema(description="비밀댓글 여부", required = true, example="")
    @NotNull(groups={SuggestionApiContoller.CreateSuggestion.class}, message="비밀댓글 여부는 필수 입니다.")
    private Boolean commentSecurity;
}
