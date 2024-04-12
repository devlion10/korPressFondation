package kr.or.kpf.lms.biz.servicecenter.myqna.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Schema(name="MyQnaViewResponseVO", description="1:1 문의 View 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MyQnaViewResponseVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의) */
    @Schema(description="문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의)")
    private String qnaType;

    /** 문의 제목 */
    @Schema(description="문의 제목")
    private String reqTitle;

    /** 문의 내용 */
    @Schema(description="문의 내용")
    private String reqContents;

    /** 답변 제목 */
    @Schema(description="답변 제목")
    private String resTitle;

    /** 답변 내용 */
    @Schema(description="답변 내용")
    private String resContents;

    /** 문의 첨부 파일 경로 */
    @Schema(description="문의 첨부 파일 경로")
    private String reqAttachFilePath;

    /** 문의 첨부 파일 사이즈 */
    @Schema(description="문의 첨부 파일 사이즈")
    private Long reqFileSize;

    /** 답변 첨부 파일 일련번호 */
    @Schema(description="답변 첨부 파일 경로")
    private String resAttachFilePath;

    /** 문의 첨부 파일 경로 */
    @Schema(description="문의 첨부 파일 사이즈")
    private Long resFileSize;

    /** 문의 상태(1: 미확인, 2: 처리중, 3: 답변 완료) */
    @Schema(description="문의 상태(1: 미확인, 2: 처리중, 3: 답변 완료)")
    private String qnaState;
}
