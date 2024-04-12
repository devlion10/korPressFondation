package kr.or.kpf.lms.biz.servicecenter.topqna.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="TopQnaViewResponseVO", description="자주묻는 질문 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TopQnaViewResponseVO {

    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    @Schema(description="질문")
    private String question;

    @Schema(description="답변")
    private String answer;
}
