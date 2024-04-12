package kr.or.kpf.lms.config.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * 소셜 로그인 사용자 정보 (Spring Security DefaultOAuth2User)
 */
@EqualsAndHashCode(callSuper=false)
public class KoreaPressFoundationOAuth2User extends DefaultOAuth2User {

	private static final long serialVersionUID = 686650356820843365L;

	/** 로그인 회원 정보 DT */
	@JsonIgnore
	private final LmsUser user;
	
	public String getUserId() { return user != null ? user.getUserId() : null; }
	public String getName() { return user != null ? user.getUserName() : null; }
	public String getEmail() { return user != null ? user.getEmail() : null; }

	public LmsUser getUser() {
		return user;
	}

	/**
	 * 생성자
	 * @param user
	 * @param authorities
	 * @param attributes
	 * @param nameAttributeKey
	 */
	public KoreaPressFoundationOAuth2User(LmsUser user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
		super(authorities, attributes, nameAttributeKey);
		this.user = user;
	}

	/**
	 * SecurityContextHolder에서 현재 로그인정보 획득
	 * @return 현재 로그인 KoreaPressFoundationOAuth2User 정보
	 */
	public static KoreaPressFoundationOAuth2User current() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof KoreaPressFoundationOAuth2User) {
			return (KoreaPressFoundationOAuth2User) authentication.getPrincipal();
		}
		return null;
	}

	@Override
	public String toString() {
		return "KoreaPressFoundationOAuth2User [user=" + user.toString() + "]";
	}
}
