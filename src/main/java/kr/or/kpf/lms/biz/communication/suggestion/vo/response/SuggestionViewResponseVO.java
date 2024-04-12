package kr.or.kpf.lms.biz.communication.suggestion.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="SuggestionViewResponseVO", description="교육 주제 제안 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionViewResponseVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 교육 주제 제안 타입(1: 언론인, 2: 시민) */
    @Schema(description="교육 주제 제안 타입(1: 언론인, 2: 시민)")
    private String suggestionType;

    /** 제안 내용 */
    @Schema(description="제안 내용")
    private String contents;

    /** 댓글 */
    @Schema(description="댓글")
    private String comment;

    /** 비밀글 여부 */
    @Schema(description="비밀글 여부")
    private Boolean isSecurity;

    /** 비밀댓글 여부 */
    @Schema(description="비밀댓글 여부")
    private Boolean commentSecurity;
}
