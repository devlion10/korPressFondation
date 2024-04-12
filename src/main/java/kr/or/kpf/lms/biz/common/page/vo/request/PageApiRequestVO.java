package kr.or.kpf.lms.biz.common.page.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Schema(name="MyQnaApiRequestVO", description="1:1 문의 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageApiRequestVO {
    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 문서(개인정보 , 이용 약관 및 기타) 유형 (0: 일반, 1: 이용 약관, 2: 개인정보 처리 방침, 3: 개인정보 동의서) */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 유형 (0: 일반, 1: 이용 약관, 2: 개인정보 처리 방침, 3: 개인정보 동의서)")
    private String documentType;

    /** 문서(개인정보 , 이용 약관 및 기타) 내용 */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 내용")
    private String documentContent;

    /** 문서(개인정보 , 이용 약관 및 기타) 변경 내용 */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 변경 내용")
    private String documentChange;

    /** 문서(개인정보 , 이용 약관 및 기타) 상태 (0: 미사용, 1: 사용) */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 상태 (0: 미사용, 1: 사용)")
    private String documentStatus;
}
