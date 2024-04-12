package kr.or.kpf.lms.common.support;

import javax.validation.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.CommonCodeMasterRepository;
import kr.or.kpf.lms.repository.entity.CommonCodeMaster;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.framework.exception.handler.KPFExceptionVO;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Api Controller Support
 */
@RestControllerAdvice
public abstract class CSApiControllerSupport extends CSComponentSupport {

	@Autowired
	protected CommonCodeMasterRepository commonCodeMasterRepository;

	protected CSApiControllerSupport() { super(); }

	/** 요청파라미터 검증 실패 시 body 셋팅 */
	@ExceptionHandler({
			MethodArgumentNotValidException.class,
			ConstraintViolationException.class,
			MissingServletRequestPartException.class
	})
	public ResponseEntity<KPFExceptionVO> handleBadRequest(@NotNull Exception ex) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9001.code, KPF_RESULT.ERROR9001.message,
				StringUtils.substringAfterLast(ex.getMessage(), "default message").replace("[", "").replace("]", "").trim());
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.BAD_REQUEST);
	}

	/** 요청 타입 오류 시 body 셋팅 */
	@ExceptionHandler({
			HttpMediaTypeNotSupportedException.class
	})
	public ResponseEntity<KPFExceptionVO> handleBadRequest2(@NotNull Exception ex) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		KPFExceptionVO exceptionResponse = new KPFExceptionVO(KPF_RESULT.ERROR9002.code, KPF_RESULT.ERROR9002.message, "지원하지 않는 요청 타입입니다.");
		return new ResponseEntity<>(exceptionResponse, headers, HttpStatus.BAD_REQUEST);
	}

	/** body 객체 변환 및 한글 깨짐 방지 */
	public Object convertToBodyObject(String jsonParams, Class<?> object) {
		try {
			return new ObjectMapper().readValue(new String(jsonParams.getBytes("ISO-8859-1"), StandardCharsets.UTF_8), object);
		} catch (UnsupportedEncodingException | JsonProcessingException e) {
			throw new KPFException(KPF_RESULT.ERROR9003, "요청 JSON 데이터 CHARSET 변경에 실패하였습니다.");
		}
	}

	public Map<String, Object> resultPaging(Page<Object> result, List<String> upIndividualCodeList) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("content", result.getContent());
		/** 페이징 정보 */
		Map<String, Object> page = new HashMap<>();
		/** 첫번째 페이지 여부 */
		page.put("first", result.isFirst());
		/** 마지막 페이지 여부 */
		page.put("last", result.isLast());
		/** 현재 페이지 데이터 갯수 */
		page.put("currentElements", result.getNumberOfElements());
		/** 검색 조건에 해당하는 데이터 총 갯수 */
		page.put("totalElements", result.getTotalElements());
		/** 페이지 당 요청 갯수 */
		page.put("size", result.getSize());
		/** 요청 페이지 */
		page.put("page", result.getNumber());
		/** 전체 페이지 */
		page.put("totalPages", result.getTotalPages());
		/** 이전 블록 존재 여부(한 블록당 10페이지) */
		if(result.getNumber() / 10 < 1) page.put("hasPrevious", false);
		else page.put("hasPrevious", true);
		/** 이후 블록 존재 여부(한 블록당 10페이지) */
		if(Math.floor(result.getNumber() / 10) < Math.floor((result.getTotalPages() - 1) / 10)) page.put("hasNext", true);
		else page.put("hasNext", false);

		int blockStart = ((result.getNumber() / 10) * 10);
		page.put("blockStart", blockStart);
		int blockEnd = blockStart + 9;
		page.put("blockEnd", blockEnd < result.getTotalPages() - 1 ? blockEnd : (result.getTotalPages() - 1 > 0 ? result.getTotalPages() - 1 : 0));

		resultMap.put("pageable", page);

		/** 제공할 공통 코드 리스트가 존재할 경우에만 공통 코드 조회 */
		Optional.ofNullable(upIndividualCodeList).filter(datas -> datas.size() > 0).ifPresent(codes -> {
			resultMap.put("commonCode", codes.stream()
					.map(code -> Optional.ofNullable(Optional.ofNullable(commonCodeMasterRepository.findOne(Example.of(CommonCodeMaster.builder().code(code).build())).orElse(null))
									.map(topCode -> NameOfCode.builder()
											.code(topCode.getCode())
											.codeName(topCode.getCodeName())
											.subCode(commonCodeMasterRepository.findAll(Example.of(CommonCodeMaster.builder()
													.upIndividualCode(topCode.getIndividualCode())
													.codeDepth(1)
													.build())).stream().map(subCode -> NameOfCode.builder()
													.code(subCode.getCode())
													.codeName(subCode.getCodeName())
													.build()).collect(Collectors.toList()))
											.build()).orElse(null))
							.orElse(null))
					.filter(data -> data != null)
					.collect(Collectors.toList()));
		});

		return resultMap;
	}
}
