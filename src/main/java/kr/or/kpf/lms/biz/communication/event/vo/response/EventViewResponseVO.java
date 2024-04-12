package kr.or.kpf.lms.biz.communication.event.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="EventViewResponseVO", description="이벤트 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventViewResponseVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목")
    private String title;

    /** 타입(1: 이벤트, 2: 설문) */
    @Schema(description="타입(1: 이벤트, 2: 설문)")
    private String type;
}
