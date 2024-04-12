package kr.or.kpf.lms.biz.user.webuser.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 본인인증 요청 객체(헤더)
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NiceHeader {
	@JsonProperty(value="CNTY_CD")
	@Builder.Default
	private String headerCode = "ko";
	
	@JsonProperty(value="GW_RSLT_CD")
	private String headerResultCode;
	
	@JsonProperty(value="GW_RSLT_MSG")
	private String headerResultMessage;
}
