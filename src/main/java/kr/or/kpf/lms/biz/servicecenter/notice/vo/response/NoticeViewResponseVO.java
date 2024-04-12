package kr.or.kpf.lms.biz.servicecenter.notice.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="NoticeViewResponseVO", description="공지사항 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class NoticeViewResponseVO {

    @Schema(description="공지사항 일련번호")
    private String noticeSerialNo;

    @Schema(description="공지사항 타입")
    private String noticeType;

    @Schema(description="제목")
    private String title;

    @Schema(description="내용")
    private String contents;

    @Schema(description="첨부 파일 일련번호")
    private String attachFilePath;

    @Schema(description="조회수")
    private BigInteger viewCount;

    @Schema(description="상위 노출 여부")
    private Boolean isTop;

    @Schema(description="신규 공지사항 여부")
    private Boolean isNew;
}
