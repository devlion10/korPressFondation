package kr.or.kpf.lms.biz.servicecenter.myqna.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import java.math.BigInteger;

@Schema(name="MyQnaApiResponseVO", description="1:1 문의 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MyQnaApiResponseVO extends CSResponseVOSupport {
    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호", example="")
    private BigInteger sequenceNo;

    /** 문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의) */
    @Schema(description="문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의)", example="")
    private String inqueryType;

    /** 문의 제목 */
    @Schema(description="문의 제목", example="")
    private String reqTitle;

    /** 문의 내용 */
    @Schema(description="문의 내용", example="")
    private String reqContents;

    /** 답변 제목 */
    @Schema(description="답변 제목", example="")
    private String resTitle;

    /** 답변 내용 */
    @Schema(description="답변 내용", example="")
    private String resContents;

    /** 문의 첨부 파일 경로 */
    @Schema(description="문의 첨부 파일 경로")
    private String reqAttachFilePath;

    /** 답변 첨부 파일 일련번호 */
    @Schema(description="답변 첨부 파일 경로")
    private String resAttachFilePath;

    @Schema(description="문의 상태(1: 미확인, 2: 처리중, 3: 답변 완료)", example="")
    private String inqueryState;
}
