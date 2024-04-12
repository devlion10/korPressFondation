package kr.or.kpf.lms.config.security.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleGroup {
	ANONYMOUS("ANONYMOUS", "비회원"),
	JOURNALIST("JOURNALIST", "언론인"),
	TEACHER("TEACHER", "교원"),
	STUDENT("STUDENT", "학생"),
	PARENTS("PARENTS", "학부모"),
	GENERAL("GENERAL", "일반인"),
	SUPER("SUPER", "관리자");

	private final String key;
	private final String title;
}