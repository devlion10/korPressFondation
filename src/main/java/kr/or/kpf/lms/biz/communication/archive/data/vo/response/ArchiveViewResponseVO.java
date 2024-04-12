package kr.or.kpf.lms.biz.communication.archive.data.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="ArchiveViewResponseVO", description="자료실 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveViewResponseVO {

    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    @Schema(description="제목")
    private String title;

    @Schema(description="내용")
    private String contents;

    @Schema(description="조회수")
    private BigInteger viewCount;

    @Schema(description="신규 공지사항 여부")
    private Boolean isNew;
}
