package kr.or.kpf.lms.biz.user.webuser.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kpf.lms.common.support.util.CustomUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * 본인인증 요청 객체(바디)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NiceBody {
	/** 결과 코드 */
	@JsonProperty(value="result_cd")
	private String resultCode;
	/** 사이트 코드(?) */
	@JsonProperty(value="site_code")
	private String siteCode;
	/** 요청 일시 */
	@JsonProperty(value="req_dtim")
	private String currentDateTime;
	/** 요청 번호 */
	@JsonProperty(value="req_no")
	private String transactionNo;
	/** 암호화 모드 */
	@JsonProperty(value="enc_mode")
	private String encryptMode;
	/** 인증 토큰 */
	@JsonProperty(value="token_val")
	private String token;
	/** 인증 토큰 VersionID */
	@JsonProperty(value="token_version_id")
	private String tokenVersionId;
	/** 인증 토큰 유효시간(?) */
	@JsonProperty(value="period")
	private String tokenValidTime;

	@Data
	public static class Builder {
		private String currentDateTime;
		private String transactionNo;
		private String encryptMode;

		public Builder() {
			this.currentDateTime = CustomUtil.getStringFromJodaTime(new DateTime(), CustomUtil.YYYYMMDDHH24MISS);
			this.transactionNo = new StringBuilder("REQ-").append(UUID.randomUUID()).append("-").append(currentDateTime).toString();
			this.encryptMode = "1";
		}

		public NiceBody build() {
			return new NiceBody(this);
		}
	}

	public NiceBody(Builder builder) {
		this.currentDateTime = builder.currentDateTime;
		this.transactionNo = builder.transactionNo;
		this.encryptMode = builder.encryptMode;
	}
	
}
