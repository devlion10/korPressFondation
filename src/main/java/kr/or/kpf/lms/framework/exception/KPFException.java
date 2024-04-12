package kr.or.kpf.lms.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import lombok.Getter;

/**
 * 커스텀 예외처리
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class KPFException extends CSRuntimeException {
	private static final long serialVersionUID = -3220788191096236245L;
	
	private final KPF_RESULT result;

	public KPFException(KPF_RESULT result, String exception) {
		super(exception);
		this.result = result;
	}
}
