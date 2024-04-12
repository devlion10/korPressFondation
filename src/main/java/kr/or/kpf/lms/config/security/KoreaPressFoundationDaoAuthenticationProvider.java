package kr.or.kpf.lms.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;

import kr.or.kpf.lms.common.encrypt.SecurityUtil;

public class KoreaPressFoundationDaoAuthenticationProvider extends DaoAuthenticationProvider {
	
	private SecurityUtil encryptMode;

	public KoreaPressFoundationDaoAuthenticationProvider(String encryptKey) {
		this.encryptMode = new SecurityUtil(encryptKey);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
}
