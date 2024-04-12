package kr.or.kpf.lms.biz.user.webuser.vo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kpf.lms.biz.user.webuser.vo.NiceBody;
import kr.or.kpf.lms.biz.user.webuser.vo.NiceHeader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 본인인증 응답 객체(NICE)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NiceResponseVO {
	
	@JsonProperty(value="dataHeader")
	private NiceHeader header;
	
	@JsonProperty(value="dataBody")
	private NiceBody body;
}
