package kr.or.kpf.lms.biz.common.bizppurio.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비즈뿌리오 응답 공통 Body
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BodyOfBizPPurioResponseVO {
    /** 결과 코드 (API 응답 상태 및 결과 코드 참조) */
    @JsonProperty(value="code")
    private String resultCode;

    /** 결과 메세지 */
    @JsonProperty(value="description")
    private String resultMessage;

    /** 메시지 키 (고객 문의 및 리포트 재 요청 기준 키)*/
    @JsonProperty(value="messageKey")
    private String messageKey;

    /** 고객사에서 부여한 키 */
    @JsonProperty(value="refkey")
    private String referenceKey;
}
