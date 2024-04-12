package kr.or.kpf.lms.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 400 HttpStatus.BAD_REQUEST로 응답하는 API 예외
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CSBadRequest400Exception extends CSRuntimeException {
	private static final long serialVersionUID = -1213274279820161913L;

	public CSBadRequest400Exception(String exception) {
		super(exception);
	}
}
