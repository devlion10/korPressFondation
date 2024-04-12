package kr.or.kpf.lms.biz.common.main.vo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import lombok.Data;

@Data
public class AttributeVO {
	/** 현재 SessionId */
	private String sessionId;
	/** 요청 파라미터 (요청 객체가 정의되지 않은 경우) */
	private Map<String,String> attributes = new HashMap<>();
	
	public AttributeVO(HttpServletRequest request) {
		this.sessionId = new WebAuthenticationDetails(request).getSessionId();
		request.getParameterMap().entrySet().stream().forEach(attribute -> {
			attributes.put(attribute.getKey(), attribute.getValue()[0]);
		});
	}
}
