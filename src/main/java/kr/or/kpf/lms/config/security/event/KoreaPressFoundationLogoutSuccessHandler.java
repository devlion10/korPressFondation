package kr.or.kpf.lms.config.security.event;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;

/**
 * 로그아웃 성공 이벤트 리스너
 */
public class KoreaPressFoundationLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		KoreaPressFoundationUserDetails user = (KoreaPressFoundationUserDetails) authentication.getPrincipal();
		WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
		
		super.onLogoutSuccess(request, response, authentication);
	}
}
