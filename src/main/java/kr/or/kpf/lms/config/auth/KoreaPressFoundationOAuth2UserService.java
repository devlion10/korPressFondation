package kr.or.kpf.lms.config.auth;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.auth.vo.KoreaPressFoundationOAuth2User;
import kr.or.kpf.lms.config.auth.vo.OAuthAttributes;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 로그인 이후 가져온 사용자의 정보(email,name,picture등) 들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
 */
@RequiredArgsConstructor
@Service
public class KoreaPressFoundationOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final LmsUserRepository userRepository;

	@Override
	public KoreaPressFoundationOAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, ?> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		LmsUser user = userRepository.findOne(Example.of(LmsUser.builder().userName(attributes.getName()).email(attributes.getEmail()).build()))
				.map(userInfo -> {
					userInfo.setEasyLogin(Code.EASY_LGN_CD.enumOfCode(registrationId).name());
					return userRepository.saveAndFlush(userInfo);
				})
				.orElseThrow(() -> new KPFException(KPF_RESULT.ERROR0001, "일치하는 회원 정보가 존재하지 않습니다."));

		return new KoreaPressFoundationOAuth2User(user, Collections.singleton(new SimpleGrantedAuthority(user.getRoleGroup())),
				attributes.getAttributes(), attributes.getNameAttributeKey());
	}
}