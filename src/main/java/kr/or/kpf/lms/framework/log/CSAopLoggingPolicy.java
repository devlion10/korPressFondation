package kr.or.kpf.lms.framework.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ControllerLoggingAspect의 로깅정책을 제거하는 Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CSAopLoggingPolicy {

	/** 컨트롤러의 액션 예외를 숨길지 여부 */
	public boolean hideExceptionLog() default false;

	/** 응답Body 출력을 Skip할지 여부 */
	public boolean hideResponseBody() default true;

	/** 컨트롤러의 액션 시작, 종료를 숨길지 여부 */
	public boolean hideStartEndLog() default false;

}
