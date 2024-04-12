package kr.or.kpf.lms.framework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kpf.lms.framework.log.CSAbstractControllerLoggingAspect;
import kr.or.kpf.lms.framework.log.CSAopLoggingPolicy;

/**
 * {@link Controller}와 {@link RestController} 어노테이션 빈에서
 * {@link RequestMapping} 어노테이션 함수실행을 Logging하는 Aspect
 *
 * {@link CSAopLoggingPolicy}로 시작/종료로깅, 예외로깅, 종료시응답 Body출력여부를 제어
 * <pre>
 * [사용방법]
 * - pom.xml수정
 * <dependency>
 * 	<groupId>org.springframework.boot</groupId>
 * 	<artifactId>spring-boot-starter-aop</artifactId>
 * </dependency>
 *
 * - spring config
 * @EnableAspectJAutoProxy
 *
 * - 빈 생성
 * @Bean
 * public CSControllerLoggingAspect ableControllerLoggingAspect() {
 * 	return new CSControllerLoggingAspect();
 * }
 * </pre>
 *
 * @see http://www.makeinjava.com/logging-aspect-restful-web-service-using-spring-aop-log-requests-responses/
 *
 * @author hashmap27
 */

@Aspect
public class CSControllerLoggingAspect extends CSAbstractControllerLoggingAspect {

	private String lineHR = "##################################################";
	private String lineSeperator = System.lineSeparator();
	private String linePrefix = lineSeperator + "\t";
	private String linePrefixBoxed = lineSeperator + "\t## ";

	@AfterReturning(pointcut = "(controllerBean() || restControllerBean()) && requestMappingMethod() && allMethod()", returning="retVal")
    public void afterControllerMethod(JoinPoint joinPoint, Object retVal) {
    	if(logger.isDebugEnabled()) {
    		CSAopLoggingPolicy policy = getPolicyAnnotation(joinPoint);
    		if(policy == null || !policy.hideStartEndLog()) {
    			StringBuilder sb = new StringBuilder();
    			//클래스명#함수명
    			sb.append(linePrefixBoxed).append("END ");
    			logClassAndMethodName(sb, joinPoint);
    			//파라미터 목록 출력
    			logArgumentList(sb, linePrefixBoxed, joinPoint.getArgs());
    			//리턴값 출력
    			if(policy != null && policy.hideResponseBody()) {
    				sb.append(linePrefixBoxed).append(" - return: <SKIP>");
    			} else {
    				sb.append(linePrefixBoxed).append(" - return: ");
    				sb.append(convertReturnValueToLogString(retVal));
    			}
    			//라인출력
    			sb.append(linePrefix).append(lineHR);
    			//로그출력
	        	logger.debug(sb.toString());
    		}
    	}
    }

	@AfterThrowing(pointcut = "(controllerBean() || restControllerBean()) && requestMappingMethod() && allMethod()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
    	if(logger.isDebugEnabled()) {
    		CSAopLoggingPolicy policy = getPolicyAnnotation(joinPoint);
    		if(policy == null || !policy.hideExceptionLog()) {
    			StringBuilder sb = new StringBuilder();
    			//클래스명#함수명
    			sb.append(linePrefixBoxed).append("EXCEPTION AT ");
    			logClassAndMethodName(sb, joinPoint);
    			//예외출력
    			sb.append(linePrefixBoxed).append(" - EXCEPTION: ").append(ex.getMessage()).append(lineSeperator);
    			sb.append(convertExceptionToLogString(ex));
    			//라인출력
    			sb.append(linePrefix).append(lineHR);
    			//로그출력
	        	logger.debug(sb.toString());
    		}
    	}
    }

    /** All method execution */
    @Pointcut("execution(* *(..))")
    public void allMethod() { }

    /**
     * AOP Before advice
     */
    @Before("(controllerBean() || restControllerBean()) && requestMappingMethod() && allMethod()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
    	if(logger.isDebugEnabled()) {
    		CSAopLoggingPolicy policy = getPolicyAnnotation(joinPoint);
    		if(policy == null || !policy.hideStartEndLog()) {
    			StringBuilder sb = new StringBuilder();
    			//라인출력
    			sb.append(linePrefix).append(lineHR);
    			//클래스명#함수명
    			sb.append(linePrefixBoxed).append("START ");
    			logClassAndMethodName(sb, joinPoint);
    			//파라미터 목록 출력
    			logArgumentList(sb, linePrefixBoxed, joinPoint.getArgs());
    			//로그출력
	        	logger.debug(sb.toString());
    		}
    	}
    }

	/** {@link Controller} Bean */
	@Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void controllerBean() { /**/ }

    @Override
    protected String convertExceptionToLogCustom(Throwable ex) {
    	//NOTE: 필요시 여기서 타입별로 로깅문자열 취득 커스터마이징
    	return null;
    }

    @Override
    protected String convertParameterToLogCustom(Object arg) {
    	//NOTE: 필요시 여기서 타입별로 로깅문자열 취득 커스터마이징
    	return null;
    }

    @Override
    protected String convertReturnValueToLogCustom(Object retVal) {
    	//NOTE: 필요시 여기서 타입별로 로깅문자열 취득 커스터마이징
    	return null;
    }

    /** {@link RequestMapping} Bean */
    @Pointcut("within(@org.springframework.web.bind.annotation.RequestMapping *)")
    public void requestMappingBean() { /**/ }

    /** {@link RequestMapping} Method */
    @Pointcut("( @annotation(org.springframework.web.bind.annotation.RequestMapping) || "
    		+ "@annotation(org.springframework.web.bind.annotation.GetMapping) || "
    		+ "@annotation(org.springframework.web.bind.annotation.PostMapping) || "
    		+ "@annotation(org.springframework.web.bind.annotation.PutMapping) || "
    		+ "@annotation(org.springframework.web.bind.annotation.DeleteMapping) )")
    public void requestMappingMethod() { /**/ }

    /** {@link RestController} Bean */
	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void restControllerBean() { /**/ }
}
