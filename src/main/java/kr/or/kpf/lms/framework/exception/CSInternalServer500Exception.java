package kr.or.kpf.lms.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 500 HttpStatus.INTERNAL_SERVER_ERROR로 응답하는 API 예외
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CSInternalServer500Exception extends CSRuntimeException {
	private static final long serialVersionUID = -1213274279820161913L;

	public CSInternalServer500Exception(String exception) {
		super(exception);
	}
}
