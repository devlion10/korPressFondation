package kr.or.kpf.lms.config.auth.vo;

import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
 */
@Getter
public class OAuthAttributes {
		private Map<String, Object> attributes;
		private String nameAttributeKey;
		private String name;
		private String userId;
		private String email;
		private String picture;

		@Builder
		public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String userId, String email, String picture) {
			this.attributes = attributes;
			this.nameAttributeKey = nameAttributeKey;
			this.name = name;
			this.userId = userId;
			this.email = email;
			this.picture = picture;
		}

		public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
			if("kakao".equals(registrationId)){
				return ofKakao("id", attributes);
			}

			if ("naver".equals(registrationId)) {
				return ofNaver("id", attributes);
			}

			return ofGoogle(userNameAttributeName, attributes);
		}

		private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
			return OAuthAttributes.builder()
					.name((String) attributes.get("name"))
					.userId(new StringBuilder().append("GOOGLE").append((String) attributes.get("sub")).toString())
					.email((String) attributes.get("email"))
					.picture((String) attributes.get("picture"))
					.attributes(attributes)
					.nameAttributeKey(userNameAttributeName).build();
		}

		private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
			@SuppressWarnings("unchecked")
			Map<String, Object> response = (Map<String, Object>) attributes.get("response");

			return OAuthAttributes.builder()
					.name((String) response.get("name"))
					.userId(new StringBuilder().append("NAVER").append((String) response.get("id")).toString())
					.email((String) response.get("email"))
					.picture((String) response.get("profile_image"))
					.attributes(response)
					.nameAttributeKey(userNameAttributeName).build();
		}

		private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
			// kakao는 kakao_account에 유저정보가 있다. (email)
			@SuppressWarnings("unchecked")
			Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
			// kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
			@SuppressWarnings("unchecked")
			Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

			return OAuthAttributes.builder()
					.name((String) kakaoProfile.get("nickname"))
					.userId(new StringBuilder().append("KAKAO").append(attributes.get("id")).toString())
					.email((String) kakaoAccount.get("email"))
					.picture((String) kakaoProfile.get("profile_image_url"))
					.attributes(attributes)
					.nameAttributeKey(userNameAttributeName)
					.build();
		}

		public LmsUser toEntity() {
			return LmsUser.builder()
					.userName(name)
					.userId(userId)
					.email(email)
					.build();
		}
}