package kr.or.kpf.lms.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 404 HttpStatus.NOT_FOUND로 응답하는 API 예외
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CSNotFound404Exception extends CSRuntimeException {
	private static final long serialVersionUID = -1213274279820161913L;

	public CSNotFound404Exception(String exception) {
		super(exception);
	}
}
