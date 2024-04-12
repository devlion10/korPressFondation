package kr.or.kpf.lms.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.CSNotFound404Exception;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.entity.LmsUser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Spring Security의 User Details Service
 */
public class KoreaPressFoundationUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(KoreaPressFoundationUserDetailsService.class);
	
	@Autowired private LmsUserRepository userRepository;

	/**
	 * 사용자 객체를 로드
	 *
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		logger.debug("username: {}", username);
		/** 회원 정보 조회 */
		LmsUser user = userRepository.findOne(Example.of(LmsUser.builder().userId(username).build()))
											.orElseThrow(() -> new CSNotFound404Exception("존재하지 않는 회원ID 입니다."));

		if("Y".equals(user.getMigrationFlag())) { /** 마이그레이션 대상 회원 */
			throw new KPFException(KPF_RESULT.ERROR0002, "마이그레이션 대상 회원<" + username + ">");
		}

		if(user.getDormancyDate() != null
				|| Code.USER_STATE.DORMANCY.enumCode.equals(user.getState())
				|| Optional.ofNullable(user.getLastLoginDateTime())
					.filter(lastLoginDateTime -> new DateTime().minusYears(2).compareTo(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(lastLoginDateTime)) > 0)
					.isPresent()) {
			throw new KPFException(KPF_RESULT.ERROR0001, "휴면 회원입니다.");
		}
		/**
		 * 탈퇴 일자가 존재할 경우... 탈퇴 회원으로 간주
		 * 탈퇴 회원을 어떻게 처리할 것인지 웹에서 결정할 것...
		 */
		if(user.getWithDrawDate() != null || Code.USER_STATE.WITHDRAWAL.enumCode.equals(user.getState())) {
			throw new KPFException(KPF_RESULT.ERROR0001, "탈퇴 회원입니다.");
		}
		/**
		 * 잠금 정보가 존재할 경우...
		 * 비밀번호 변경 일자가 6개월을 경과했을 경우...
		 * 잠금 회원을 어떻게 처리할 것인지 웹에서 결정할 것...
		 * 비밀번호 초기화 횟수에 따라 LockCount 체크 정책 필요...
		 */
		if(user.getLockDateTime() != null
			|| (user.getIsLock() != null && user.getIsLock())
			|| (user.getLockCount() != null && user.getLockCount() >= 5)
			|| Code.USER_STATE.LOCK.enumCode.equals(user.getState())
		) {
			throw new KPFException(KPF_RESULT.ERROR0001, "잠금 회원(비밀번호 5회 불일치)입니다. 관리자에게 문의 또는 비밀번호 변경 후 재시도 바랍니다.");
		}

		/**
		 * 미사용 계정일 경우
		 */
		if(Code.USER_STATE.NONE.enumCode.equals(user.getState())) {
			throw new KPFException(KPF_RESULT.ERROR0001, "미사용 회원입니다.");
		}

		Map<String,String> userInfoMap = new HashMap<>();
		userInfoMap.put("userId", username);
		userInfoMap.put("password", user.getPassword());
		try {
			String passwordInfo = new ObjectMapper().writeValueAsString(userInfoMap);
			user.setPasswordInfo(passwordInfo);
		} catch (JsonProcessingException e) {
			logger.error("{}- {}", e.getClass().getCanonicalName(), e.getMessage(), e);
		}

		/** 권한 획득 */
		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRoleGroup()));
		/** 회원 반환 */
		return new KoreaPressFoundationUserDetails(user, true/* enabled */, true/* accountNonExpired */, true/* credentialsNonExpired */, true/* accountNonLocked */, authorities);
	}

	/**
	 * 스프링 시큐리티에 롤 권한 셋팅
	 * 
	 * @param user
	 * @param roleList
	 * @return
	 */
	private @NonNull List<GrantedAuthority> getAuthoritiesByMenuRoleMapping(@NonNull KoreaPressFoundationUser user, List<String> roleList) {
		// 사용자 권한 및 접근 메뉴 설정
		List<GrantedAuthority> authorities = new ArrayList<>();
		roleList.stream().forEach(role -> {
			// 권한 설정
			if (!authorities.contains(new SimpleGrantedAuthority(role))) {
				authorities.add(new SimpleGrantedAuthority(role));
			}
		});
		return authorities;
	}
}