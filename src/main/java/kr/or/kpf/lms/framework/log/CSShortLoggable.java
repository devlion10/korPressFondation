package kr.or.kpf.lms.framework.log;

/**
 * toStringShort를 지원하는 인터페이스
 * 공통부품에서 해당 인터페이스를 구현한 경우 toString이 아닌 toStringShort로 로깅처리 목적
 */
@FunctionalInterface
public interface CSShortLoggable {

	String toStringShort();
}
