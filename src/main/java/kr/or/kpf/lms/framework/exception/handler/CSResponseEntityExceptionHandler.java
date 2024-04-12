package kr.or.kpf.lms.framework.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.framework.exception.CSBadRequest400Exception;
import kr.or.kpf.lms.framework.exception.CSInternalServer500Exception;
import kr.or.kpf.lms.framework.exception.CSNotFound404Exception;
import kr.or.kpf.lms.framework.exception.KPFException;

/**
 * 예외 처리 핸들러
 */
@RestControllerAdvice
public class CSResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CSBadRequest400Exception.class)
	public final ResponseEntity<KPFExceptionVO> handle400Exception(HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9999.code, KPF_RESULT.ERROR9999.message, "요청값이 유효하지 않습니다.");
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CSNotFound404Exception.class)
	public final ResponseEntity<KPFExceptionVO> handle404Exception(HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9999.code, KPF_RESULT.ERROR9999.message, "요청에 대한 내용을 찾을 수 없습니다.");
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CSInternalServer500Exception.class)
	public final ResponseEntity<KPFExceptionVO> handle500Exception(HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9999.code, KPF_RESULT.ERROR9999.message, "알 수 없는 오류가 발생하였습니다.");
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(KPFException.class)
	public final ResponseEntity<KPFExceptionVO> handleKoreaPressFoundationException(KPFException ex, HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(ex.getResult().code, ex.getResult().message, ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<KPFExceptionVO> handleRuntimeExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9999.code, ex.getMessage().length() > 50 ? KPF_RESULT.ERROR9999.message : ex.getMessage(), ex.getMessage().length() > 50 ? KPF_RESULT.ERROR9999.message : ex.getMessage());
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<KPFExceptionVO> handleAllExceptions(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9999.code, KPF_RESULT.ERROR9999.message, "알 수 없는 오류가 발생하였습니다.");
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
