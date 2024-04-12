package kr.or.kpf.lms.config.security.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.or.kpf.lms.framework.interceptor.RequestFilter;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

/**
 * 일반 로그인 사용자 정보 (Spring Security UserDetails)
 */
@EqualsAndHashCode(callSuper=false)
public class KoreaPressFoundationUserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 686650356820843365L;

	/** 로그인 회원 정보 DT */
	@JsonIgnore
	private final LmsUser user;
	
	public String getUserId() { return user != null ? user.getUserId() : null; }
	public String getName() { return user != null ? user.getUserName() : null; }
	public String getEmail() { return user != null ? user.getEmail() : null; }
	public String getPhone() { return user != null ? user.getPhone() : null; }
	public String getGender() { return user != null ? user.getGender() : null; }
	public String getBirthday() { return user != null ? user.getBirthDay() : null; }
	public String getBizAuth() { return user != null ? user.getBusinessAuthority() : null; }
	public String getRoleGroup() { return user != null ? user.getRoleGroup() : null; }
	public String getTutor() { return user != null ? user.getTutorYn() : null; }
	public String getOrganizationCode() { return user != null ? user.getOrganizationCode() : null; }
	public String getApproFlag() { return user != null ? user.getApproFlag() : null; }

	public LmsUser getUser() {
		return user;
	}

	/**
	 * 생성자
	 * @param user 로그인 회원.
	 * @param enabled 회원 활성화상태 여부
	 * @param accountNonExpired 계정이 만료되지 않았는지 여부
	 * @param credentialsNonExpired 암호가 만료되지 않았는지 여부
	 * @param accountNonLocked 계정이 잠기지 않았는지 여부
	 * @param authorities 회원 권한목록
	 */
	public KoreaPressFoundationUserDetails(LmsUser user,
										   boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
										   Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPasswordInfo(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.user = user;
	}

	/**
	 * SecurityContextHolder에서 현재 로그인정보 획득
	 * @return 현재 로그인 KoreaPressFoundationUserDetails 정보
	 */
	public static KoreaPressFoundationUserDetails current() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof KoreaPressFoundationUserDetails) {
			return (KoreaPressFoundationUserDetails) authentication.getPrincipal();
		}
		return null;
	}

	@Override
	public String toString() {
		return "KoreaPressFoundationUserDetails [user=" + user.toString() + "]";
	}
}
