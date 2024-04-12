package kr.or.kpf.lms.config.auth.vo;

import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.Getter;

import java.io.Serializable;

/**
 * 세션에 사용자 정보를 저장하기 위한 Dto 클래스
 */
@Getter
public class SessionUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String email;
	private String birthDay;
	private String phone;
	private String gender;
	public SessionUser(LmsUser user) {
		this.name = user.getUserName();
		this.email = user.getEmail();
		this.birthDay = user.getBirthDay();
		this.phone = user.getPhone();
		this.gender = user.getGender();
	}
}
