package kr.or.kpf.lms.biz.communication.archive.data.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import java.math.BigInteger;

@Schema(name="ArchiveApiResponseVO", description="자료실 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveApiResponseVO extends CSResponseVOSupport {

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
}
