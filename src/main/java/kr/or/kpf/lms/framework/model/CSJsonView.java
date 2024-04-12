package kr.or.kpf.lms.framework.model;

/**
 * JsonView - 모든 JsonView의 공통 상위클래스 (공통부품등에 정책을 적용하기 위한 설계)
 *
 * 사이트 전체 공용 기본 JsonView
 *
 * 추가적인 Json Serialization View를 만들려면 내려주려는 DT에 직접 View interface를 선언. KpcJsonView.BaseView 상속
 *
 * 추가적인 요청타입. ValidationGroup, Json Deserialization View은 각각의 하위 DT, Entity클래스에 정의
 *
 */
public interface CSJsonView {

	//////////////////////////////////////////////////////////////////////////////
	// Json Serialization View
	// REST API응답시 Json으로 내려줄 속성의 범위를 지정

	/** 최상위 JsonView (REST 공통 전문 필드들에 사용) - 다른 JsonView들을 이 View를 extend하여 구현 필요  */
	public interface CSBaseView {}

	/** 최상위 기본 공통 요청 */
	public interface CSBaseAction {}
}
