package kr.or.kpf.lms.biz.common.page.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import javax.persistence.Convert;
import java.math.BigInteger;

/**
 * 페이지 View 관련 요청 객체
 */
@Schema(name="PageViewRequestVO", description="페이지 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PageViewRequestVO extends CSViewVOSupport {

    /** 문서 일련번호 */
    @Schema(description="문서 일련번호")
    private BigInteger sequenceNo;

    /** 문서(개인정보 , 이용 약관 및 기타) 유형 (0: 일반, 1: 이용 약관, 2: 개인정보 처리 방침, 3: 개인정보 동의서) */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 유형 (0: 일반, 1: 이용 약관, 2: 개인정보 처리 방침, 3: 개인정보 동의서)")
    private String documentType;

    /** 문서(개인정보 , 이용 약관 및 기타) 상태 */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 상태")
    private String documentStatus;
}
