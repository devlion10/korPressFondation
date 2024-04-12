package kr.or.kpf.lms.config.security.event;


import kr.or.kpf.lms.common.support.CSComponentSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Example;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 로그인 성공 이벤트 리스너
 */
@Component
@RequiredArgsConstructor
public class KoreaPressFoundationLoginSuccessEventListener extends CSComponentSupport implements ApplicationListener<AuthenticationSuccessEvent> {

	private final LmsUserRepository lmsUserRepository;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		try {
			handleAuthenticationSuccessEvent(event);
		} catch (Exception ex) {
			logger.error("AuthenticationSuccessEvent Handler ERROR: {}-{}", ex.getClass().getCanonicalName(), ex.getMessage(), ex);
		}
	}

	private void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
		try {
			KoreaPressFoundationUserDetails user = (KoreaPressFoundationUserDetails) event.getAuthentication().getPrincipal();
			WebAuthenticationDetails details = (WebAuthenticationDetails) event.getAuthentication().getDetails();
			LmsUser lmsUser = lmsUserRepository.findOne(Example.of(LmsUser.builder()
					.userId(user.getUserId())
					.build())).orElseThrow(() -> new RuntimeException("유효하지 않은 회원입니다."));
			
			/** 최종 접속 일자 */
			lmsUser.setLastLoginDateTime(new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
			/** 휴면 계정 전환 일자 초기화 */
			lmsUser.setDormancyDate(null);
			/** 탈퇴 일자 초기화 */
			lmsUser.setWithDrawDate(null);
			/** 잠금 전제 조건 초기화 */
			lmsUser.setLockCount(0);
			lmsUser.setLockDateTime(null);
			lmsUser.setIsLock(false);
			/** 회원 상태 정상 */
			lmsUser.setState(Code.USER_STATE.GENERAL.enumCode);

			lmsUserRepository.saveAndFlush(lmsUser);
			//로그인성공
			logger.debug("로그인성공 User: {}", user);
		} catch (Exception e) {
			DefaultOAuth2User socialUser = (DefaultOAuth2User) event.getAuthentication().getPrincipal();
			logger.debug("로그인성공 User: {}", socialUser.getAttribute("name").toString());
		}
	}
}
