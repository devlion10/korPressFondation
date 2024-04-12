package kr.or.kpf.lms.common.support;

import com.google.gson.Gson;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.config.AppConfig;
import kr.or.kpf.lms.config.auth.vo.KoreaPressFoundationOAuth2User;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.CommonCodeMasterRepository;
import kr.or.kpf.lms.repository.CommonRepository;
import kr.or.kpf.lms.repository.entity.LmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.kpf.lms.framework.exception.CSRuntimeException;

/**
 * 모든 Component의 최상위 클래스
 */
public abstract class CSComponentSupport {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired protected ObjectMapper objectMapper;
	@Autowired protected AppConfig appConfig;

	/**
	 * 주어진 객체를 JSON문자열로 Serialize (Jackson2 ObjectMapper 사용)
	 * @param object 변환할 객체
	 * @return JSON 문자열
	 *
	 * @see http://wiki.fasterxml.com/JacksonJsonViews
	 * @see http://www.jroller.com/RickHigh/entry/working_with_jackson_json_views
	 */
	public String toJson(Object object) {
		return toJson(object, null);
	}

	/**
	 * 주어진 객체를 JSON문자열로 Serialize (Jackson2 ObjectMapper 사용)
	 * @param object 변환할 객체
	 * @param serializationView Serialization에 사용할 View Class (<code>@JsonView</code> 참고)
	 * @return JSON 문자열
	 *
	 * @see http://wiki.fasterxml.com/JacksonJsonViews
	 * @see http://www.jroller.com/RickHigh/entry/working_with_jackson_json_views
	 */
	public String toJson(Object object, Class<?> serializationView) {
		String json = null;
		try {
			if(serializationView != null) {
				json = objectMapper.writerWithView(serializationView).writeValueAsString(object);
			} else {
				json = (object == null) ? null : objectMapper.writeValueAsString(object);
			}
		} catch (JsonProcessingException e) {
			throw new CSRuntimeException("Failed to write as json", e);
		}
		return StringUtils.replace(json, "/", "\\/"); //StringEscapeUtils.escapeJson
	}

	public static <T extends CSViewVOSupport> Object params(Class<?> object, CSSearchMap searchMap, Pageable pageable) {
		return CSViewVOSupport.requestParameterSetting(object, searchMap, pageable);
	}

	public LmsUser authenticationInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if(authentication.getPrincipal() instanceof KoreaPressFoundationUserDetails) {
			return ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUser();
		}else if (authentication.getPrincipal() instanceof KoreaPressFoundationOAuth2User) {
			return ((KoreaPressFoundationOAuth2User) authentication.getPrincipal()).getUser();
		} else {
			throw new KPFException(KPF_RESULT.ERROR0001, "인증 회원 정보 획득 실패");
		}
	}
}
