package kr.or.kpf.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import kr.or.kpf.lms.framework.aspect.CSControllerLoggingAspect;

/**
 * AOP 설정
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

	@Bean
	public CSControllerLoggingAspect CSControllerLoggingAspect() {
		return new CSControllerLoggingAspect();
	}
}
