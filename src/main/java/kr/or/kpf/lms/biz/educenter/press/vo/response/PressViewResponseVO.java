package kr.or.kpf.lms.biz.educenter.press.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="PressViewResponseVO", description="행사소개(보도자료) View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PressViewResponseVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목")
    private String title;

    /** 내용 */
    @Schema(description="내용")
    private String contents;

    /** 조회수 */
    @Schema(description="조회수")
    private BigInteger viewCount;

    /** 신규 여부 */
    @Schema(description="신규 여부")
    private Boolean isNew;
}