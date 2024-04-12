package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.or.kpf.lms.biz.user.webuser.vo.NiceBody;
import kr.or.kpf.lms.biz.user.webuser.vo.NiceHeader;
import lombok.Builder;
import lombok.Data;

/**
 * 본인인증 요청 객체(NICE)
 */
@Data
@Builder
public class NiceRequestVO {
	
	@JsonProperty(value="dataHeader")
	private NiceHeader header;
	
	@JsonProperty(value="dataBody")
	private NiceBody body;
}
