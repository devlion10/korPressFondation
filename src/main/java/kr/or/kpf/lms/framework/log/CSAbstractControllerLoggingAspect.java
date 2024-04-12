package kr.or.kpf.lms.framework.log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Controller Aspect Logging을 구현하기 위한 추상 구현체
 */
public class CSAbstractControllerLoggingAspect {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 예외 객체의 로깅출력 문자열을 획득 커스터마이징
	 * 
	 * @param ex 예외객체
	 * @return 로깅출력 문자열
	 */
	protected String convertExceptionToLogCustom(Throwable ex) {
		return null;
	}

	// [Exception 출력] ////////////////////////////////////////
	/**
	 * 예외 객체의 로깅출력 문자열을 획득
	 * 
	 * @param ex 예외객체
	 * @return 로깅출력 문자열
	 */
	protected String convertExceptionToLogString(Throwable ex) {
		String customLog = convertExceptionToLogCustom(ex);
		if (!StringUtils.isEmpty(customLog)) {
			return customLog;
		}
		if (ex instanceof CSShortLoggable) {
			CSShortLoggable loggable = (CSShortLoggable) ex;
			return loggable.toStringShort();
		}
		StringBuilder sb = new StringBuilder();
		logExceptionIncludeCause(sb, ex, 10/* includeCauseDepth */);
		return sb.toString();
	}

	// [하위클래스의 Type별 커스터마이징 출력] ////////////////////////////////////////
	/**
	 * 파라미터로부터 로그출력을 위한 문자열 획득 커스터마이징
	 * 
	 * @param arg 출력대상 Argument 객체
	 * @return Argument의 로깅출력 문자열, null반환시 기본 로그출력 적용
	 */
	protected String convertParameterToLogCustom(Object arg) {
		return null;
	}

	/**
	 * 파라미터로부터 로그출력을 위한 문자열 획들
	 * 
	 * @param arg 출력대상 Argument 객체
	 * @return Argument의 로깅출력 문자열
	 */
	private String convertParameterToLogString(Object arg) {
		if (arg == null) {
			return "<null>";
		}
		String customParameterLog = convertParameterToLogCustom(arg);
		if (!StringUtils.isEmpty(customParameterLog)) {
			return customParameterLog;
		}
		if (arg instanceof CSShortLoggable) {
			CSShortLoggable typedArg = (CSShortLoggable) arg;
			return typedArg.toString();
		}
		if (arg instanceof HttpServletRequest) {
			return arg.getClass().getSimpleName() + " - param: " + getRequestParameterMap((HttpServletRequest) arg);
		}
		return arg.toString();
	}

	/**
	 * 메소드 return값의 로깅출력 커스터마이징
	 * 
	 * @param retVal 대상 return값
	 * @return return값의 로깅출력 문자열, null반환시 기본 로그출력 적용
	 */
	protected String convertReturnValueToLogCustom(Object retVal) {
		return null;
	}

	// [Return Value 출력] ////////////////////////////////////////
	/**
	 * 메소드 return값의 로깅출력
	 * 
	 * @param retVal 대상 return값
	 * @return return값의 로깅출력 문자열
	 */
	protected String convertReturnValueToLogString(Object retVal) {
		if (retVal == null) {
			return "<null>";
		}
		String customReturnValueLog = convertReturnValueToLogCustom(retVal);
		if (!StringUtils.isEmpty(customReturnValueLog)) {
			return customReturnValueLog;
		}
		if (retVal instanceof CSShortLoggable) {
			CSShortLoggable typedArg = (CSShortLoggable) retVal;
			return typedArg.toString();
		}
		if (retVal.toString().endsWith("@" + Integer.toHexString(retVal.hashCode()))) {
			return ReflectionToStringBuilder.toString(retVal);
		}
		return retVal.toString();
	}

	/**
	 * 주어진 join point의 method signature에서 로깅정책 {@link CSAopLoggingPolicy}를 획득
	 * 
	 * @param joinPoint {@link CSAopLoggingPolicy}를 획득해볼 AOP joinPoint
	 * @return 로깅정책 Annotation, 없으면 null
	 */
	protected CSAopLoggingPolicy getPolicyAnnotation(JoinPoint joinPoint) {
		try {
			if (joinPoint.getSignature() instanceof MethodSignature) {
				MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
				return methodSignature.getMethod()
						.getAnnotation(CSAopLoggingPolicy.class);
			}
		} catch (Exception ex) {
			logger.warn("{} - {}", ex.getClass().getName(), ex.getMessage(), ex);
		}
		return null;
	}

	// [Type별 커스터마이징 출력] ////////////////////////////////////////
	/**
	 * 요청객체의 파라미터를 로깅이 가능하도록 Map<String, String> 형태로 반환 request.getParameterMap()은
	 * Map<String, String[]>인데 Map<String, String>으로 로깅이 가능하게 변환하여 반환
	 * 
	 * @param request 요청 객체
	 * @return Map<String, String> 형태의 요청 파라미터맵
	 */
	private Map<String, String> getRequestParameterMap(ServletRequest request) {
		Map<String, String> result = new HashMap<>();
		Map<String, String[]> parameterMap = request.getParameterMap();
		String key;
		String[] value;
		for (Entry<String, String[]> item : parameterMap.entrySet()) {
			key = item.getKey();
			value = item.getValue();
			result.put(key, Arrays.asList(value).toString());
		}
		return result;
	}

	/**
	 * 주어진 StackTrace[]와 depth로 로그 출력을 위한 스택트레이스 획득
	 */
	private void getStackTraceLogByDepth(StringBuilder sb, StackTraceElement[] stackTrace, int depth) {
		int i = 0;
		for (StackTraceElement s : stackTrace) {
			i++;
			if (i > depth) {
				break;
			}
			sb.append("\n\tat ").append(s.getClassName()).append(".").append(s.getMethodName()).append("(")
					.append(s.getFileName()).append(":").append(s.getLineNumber()).append(")");
		}
	}

	// [Arguments 출력] ////////////////////////////////////////
	/**
	 * 메소드 Argument 목록의 로깅출력
	 * 
	 * @param sb         출력대상 StringBuilder
	 * @param linePrefix 인자별 출력로깅 라인 Prefix
	 * @param args       인자목록
	 */
	protected void logArgumentList(StringBuilder sb, String linePrefix, Object[] args) {
		if (args == null || args.length == 0) {
			sb.append(linePrefix).append(" - no args");
			return;
		}
		for (int i = 0; i < args.length; i++) {
			String logStringOfParameter = convertParameterToLogString(args[i]);
			sb.append(linePrefix).append(String.format(" - args[%d]: ", i)).append(logStringOfParameter);
		}
	}

	/**
	 * JoinPoint의 "클래스명#메소드명" 로깅출력
	 * 
	 * @param sb        출력대상 StringBuilder
	 * @param joinPoint AOP joinPoint
	 */
	protected void logClassAndMethodName(StringBuilder sb, JoinPoint joinPoint) {
		sb.append(joinPoint.getTarget().getClass().getSimpleName()).append("#")
				.append(joinPoint.getSignature().getName());
	}

	/**
	 * 예외 객체의 로깅문자열 출력 (내부 InnerException포함)
	 * 
	 * @param sb                출력대상 StringBuilder
	 * @param ex                예외
	 * @param includeCauseDepth cuase를 출력할 최대제한
	 */
	private void logExceptionIncludeCause(StringBuilder sb, Throwable ex, int includeCauseDepth) {
		if (ex == null) {
			return;
		}
		sb.append(ex.getClass().getCanonicalName()).append(": ").append(ex.getLocalizedMessage());
		getStackTraceLogByDepth(sb, ex.getStackTrace(), 10/* depth */);
		Throwable cause = ex.getCause();
		if (cause != null && includeCauseDepth >= 0) {
			sb.append("Caused by: ");
			logExceptionIncludeCause(sb, cause, includeCauseDepth - 1);
		}
	}

}
