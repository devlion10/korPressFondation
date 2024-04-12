package kr.or.kpf.lms.biz.common.bizppurio.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 비즈뿌리오 요청 공통 Body
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BodyOfBizPPurioRequestVO {
    /** 비즈뿌리오 계정 */
    @JsonProperty(value="account")
    @Builder.Default
    private String account = "kpfmeca";

    /** 메시지 데이터 (SMS, LMS, MMS, AT, FT, RCS) */
    @JsonProperty(value="type")
    @Builder.Default
    private String type = "sms";

    /** 발신 번호 */
    @JsonProperty(value="from")
    private String sender;

    /** 수신 번호 */
    @JsonProperty(value="to")
    private String receiver;

    /** 국가 코드 (국제 메시지 발송 참조) */
    @JsonProperty(value="country")
    private String country;

    /** 메시지 데이터 (CONTENT 참조) */
    @JsonProperty(value="content")
    private Map<String, Map<String,String>> content = new HashMap<>();

    /** 고객사에서 부여한 키 */
    @JsonProperty(value="refkey")
    private String referenceKey="kpf21906006!";

    /** 정산용 부서 코드 */
    @JsonProperty(value="userinfo")
    private String department;

    /** 대체 전송 메시지 유형 (RESEND 참조)*/
    @JsonProperty(value="resend")
    private String resend;

    /** 대체 전송 메시지 데이터 (CONTENT 참조)*/
    @JsonProperty(value="recontent")
    private String reContent;

    /** 특부가사업자특부가사업자 식별코드식별코드 */
    @JsonProperty(value="resellercode")
    private String reSellerCode;

    /** 발송 시간 unixtime , 최대 30 일 이내
     * 비즈뿌리오 서버 시간 기준 , 한국 표준시 GMT+9)
     * 과거 시간 입력시 즉시 발송
     * 즉시 발송을 원하는 경우 미입력
     */
    @JsonProperty(value="sendtime")
    private String sendTime;
}
