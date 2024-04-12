package kr.or.kpf.lms.framework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CSViewException extends CSRuntimeException {
    private static final long serialVersionUID = -1213274279820161913L;

    public CSViewException(String exception) {
        super(exception);
    }
}
