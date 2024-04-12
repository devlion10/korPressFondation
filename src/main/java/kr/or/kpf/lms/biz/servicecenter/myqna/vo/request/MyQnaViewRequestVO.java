package kr.or.kpf.lms.biz.servicecenter.myqna.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import java.math.BigInteger;

@Schema(name="MyQnaViewRequestVO", description="1:1 문의 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyQnaViewRequestVO extends CSViewVOSupport {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 검색어 범위 */
    @Schema(description="검색어 범위")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @Schema(description="검색에 포함할 단어")
    private String containText;

    /** 1:1 문의 타입 */
    @Schema(description="1:1 문의 타입")
    private String myQnaType;

    /** 1:1 문의 답변 타입 */
    @Schema(description="1:1 문의 답변 타입")
    private String qnaState;
}
