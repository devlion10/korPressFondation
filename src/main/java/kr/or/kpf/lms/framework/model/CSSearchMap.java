package kr.or.kpf.lms.framework.model;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class CSSearchMap extends HashMap<String, Object>  {
	
	private static final long serialVersionUID = -7116224054358578695L;
	private static final Logger logger = LoggerFactory.getLogger(CSSearchMap.class);

	private static final String SEARCH_PARAM_PREFIX = "search";
	
	/**
	 * 생성자 - 주어진 맵에서 search로 시작하는 파라미터만 담는다. decodeURIComponent적용
	 */
	public CSSearchMap(Map<? extends String, ? extends Object> map) {
		for (Entry<? extends String, ? extends Object> entry : map.entrySet()) {
			addByMapEntry(entry);
		}
	}
	
	private void addByMapEntry(Entry<? extends String, ? extends Object> entry) {
//		if( entry.getKey().startsWith(SEARCH_PARAM_PREFIX) ) {
			if(entry.getValue() instanceof String[]) {
				//String array value
				String[] values = (String[]) entry.getValue();
				if(values.length == 1) {
					String value = values[0];
					logger.debug("key: {}, trace: {}", entry.getKey(), value);
					putStringValue(entry.getKey(), value);
				} else if(values.length > 1) {
					List<String> convertedValues = Stream.of(values).map(CSSearchMap::decodeURIComponent).collect(Collectors.toList());
					this.put(entry.getKey(), convertedValues);
				}
			} else if (entry.getValue() instanceof String) {
				//String value
				String value = (String) entry.getValue();
				putStringValue(entry.getKey(), value);
			} else {
				//Other
				this.put(entry.getKey(), entry.getValue());
			}
//		}
	}
	
	/**
	 * 문자열 값을 추가 ("true"/"false" => true/false로 추가)
	 * @param key 키
	 * @param value 값
	 */
	private void putStringValue(String key, String value) {
		if("true".equals(value)) {
			this.put(key, true); //true
		} else if("false".equals(value)) {
			this.put(key, false); //false
		} else {
			this.put(key, decodeURIComponent(value)); //String value
		}
	}
	
	/**
	 * Client에서 encoding 하여 올린 문자열 decoding
	 */
	private static String decodeURIComponent(String value) {
		try {
			return URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.trace("{} - {}", e.getClass().getName(), e.getMessage(), e);
			return value;
		}
	}
	
	/**
	 * 생성자 - Request의 ParameterMap에서 search로 시작하는 파라미터로 생성
	 */
	public CSSearchMap(ServletRequest request) {
		this(request.getParameterMap());
	}

	/**
	 * Factory - Request의 ParameterMap에서 search로 시작하는 파라미터로 생성
	 */
	public static CSSearchMap of(ServletRequest request) {
		return new CSSearchMap(request);
	}
}
