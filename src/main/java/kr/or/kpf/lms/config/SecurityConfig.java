package kr.or.kpf.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.kpf.lms.common.encrypt.EncryptPassword;
import kr.or.kpf.lms.config.security.KoreaPressFoundationDaoAuthenticationProvider;
import kr.or.kpf.lms.config.security.KoreaPressFoundationUserDetailsService;
import lombok.RequiredArgsConstructor;

/**
 * Spring Security Core Config
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final AppConfig appConfig;

	/** UserDetailsService */
	@Bean
	public UserDetailsService userDetailsService() {
		return new KoreaPressFoundationUserDetailsService();
	}

	/** PasswordEncoder */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new EncryptPassword();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		KoreaPressFoundationDaoAuthenticationProvider authProvider = new KoreaPressFoundationDaoAuthenticationProvider(appConfig.getSecurity().getEncryptKey());
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}
