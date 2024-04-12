package kr.or.kpf.lms.biz.educenter.apply.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import java.math.BigInteger;

@Schema(name="ApplyApiResponseVO", description="교육장 사용 신청 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ApplyApiResponseVO extends CSResponseVOSupport {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목")
    private String title;

    /** 내용 */
    @Schema(description="내용")
    private String contents;
}
