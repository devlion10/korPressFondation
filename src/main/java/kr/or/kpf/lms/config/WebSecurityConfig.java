package kr.or.kpf.lms.config;

import kr.or.kpf.lms.config.auth.KoreaPressFoundationOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final KoreaPressFoundationOAuth2UserService customOAuth2UserService;

	private final AuthenticationSuccessHandler customSuccessHandler;

	@Override
	public void configure(WebSecurity web) {
		//정적리소스를 Security FilterChain에서 무시
		web
				.ignoring()
				.antMatchers("/favicon.ico")
				.antMatchers("/css/**")
				.antMatchers("/static/assets/**")
				.antMatchers("/assets/**")
				.antMatchers("/image/**")
				.antMatchers("/app/**")
				.antMatchers("/error")
				.antMatchers("/ping.html")
				.antMatchers("/ping")
				.antMatchers("/swagger-ui/**")
				.antMatchers("/download/**")
				.antMatchers("/Public/**")
				.antMatchers("/Contents/**")
		;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//HttpSecurity 커스텀 설정
		http
				//Security Context 사용
				.securityContext()
				.and()
				//Servlet API 사용
				.servletApi()
				.and()
				//Cross Site Request Forgery 를 비활성화. CSRF를 켜려면 모든 API호출시 CSRF Token의 전송이 필요
				.csrf()
				.disable()
				//CORS설정 - 정책은 CorsConfigurationSource 빈에서 설정
				.cors()
				.and()
				.headers()
				//X-Frame-Options: 같은 도메인만 허용
				.frameOptions().sameOrigin()
				//Strict-Transport-Security: HTTP Strict Transport Security (HSTS): 전송계층보안은 서브도메인을 포함하여 허용
				.httpStrictTransportSecurity().includeSubDomains(true).and()
				//Cache-Control: no-cache, no-store, max-age=0, must-revalidate
				//Pragma: no-cache
				//Expires: 0
				.cacheControl().and()
				//X-XSS-Protection: 1; mode=block 적용을 해제
				.xssProtection().block(false).and()
				.and()
				//요청 접근제어
				.authorizeRequests()
				//모두접근허용
				.antMatchers(
						"/",
						"/api/bizppurio/send-sms",
						"/login",
						"/login/**",
						"/logout",
						"/anonymous/**",
						"/api/anonymous/**",
						"/api/main/**",
						"/api/documents/**",
						"/terms/**",
						"/service-center/notice/**",
						"/api/service-center/notice/**",
						"/service-center/top-qna/**",
						"/api/service-center/top-qna/**",
						"/service-center/remote/**",
						"/api/service-center/remote/**",
						"/api/user/**",
						"/user/**",
						"/api/education/update-view-count/**",
						"/education/e-learning/**",
						"/education/schedule/**",
						"/api/education/schedule/**",
						"/education/application/**",
						"/api/education/application/**",
						"/business/pbanc/**",
						"/business/qualification/**",
						"/api/business/pbanc/rslt/download/**",
						"/communication/**",
						"/api/communication/**",
						"/edu-center/**",
						"/api/edu-center/**",
						"/h2-console/**",
						"/swagger-ui/**",
						"/favicon.ico",
						"/css/**",
						"/static/assets/**",
						"/assets/**",
						"/image/**",
						"/app/**",
						"/error",
						"/node_modules/**",
						"/ping.html",
						"/ping",
						"/js/**",
						"/test/**",
						"/download/**",
						"/Public/**",
						"/Contents/**",
						"/api/common/upload/download/**",
						"/search/**"
				).permitAll()
				//이외 나머지 모든요청은 로그인필요
				.anyRequest().authenticated()
				.and()
				//Form기반 로그인 설정
				.formLogin()
				//커스텀 로그인 FROM URL
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.loginProcessingUrl("/loginProcess")
				.defaultSuccessUrl("/")
				.successHandler(customSuccessHandler)
//				.successHandler(savedRequestAwareAuthenticationSuccessHandler())
//				.failureHandler(authenticationFailureHandler()) //실패 정책 변경 확장
				.permitAll()
				.and()
				//로그아웃 핸들러
				.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				.logoutSuccessUrl("/")
				.permitAll()
				.and()
				//익멍 유저
				.anonymous()
				.principal("anonymousUser")
				.authorities("ANONYMOUS")
				.and()
				.oauth2Login()
				.loginPage("/login")
				.and()
				.oauth2Login()
				.userInfoEndpoint()
				.userService(customOAuth2UserService);
	}

	/** Spring Security에 CORS정책 지정 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}