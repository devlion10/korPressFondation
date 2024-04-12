package kr.or.kpf.lms.common.result;

import kr.or.kpf.lms.framework.exception.KPFException;

import java.util.Arrays;

/**
 * NICE 본인인증 응답 코드
 *
 */
public enum NICE_RESULT {
	/** 처리할 내용이 없음(빈값으로 응답해야할 경우에만 사용) */
	EMPTY("", ""),
	/** 성공 */
	SUCCESS("0000", "성공"),
    NORMAL("1200", "오류 없음 (정상)(No Error)"),
    NULL_PARAM("1300", "request body가 비었습니다.(Request Body is Null)"),
    BAD_REQUEST("1400", "잘못된 요청(bad Request)"),
    REQUIRE_AUTHORIZED("1401", "인증 필요(authorized required)"),
    UN_AUTHORIZED("1402", "권한없음(unAuthorized)"),
    SERVICE_DISABLED("1403", "서비스 사용 중지됨(service Disabled)"),
    SERVICE_NOTFOUND("1404", "서비스를 찾을 수 없음(Service Not Found)"),
    INTERNERL_ERROR("1500", "서버 내부 오류(Internal Server Error)"),
    ACCESS_DENIED("1501", "보호된 서비스에서 엑세스가 거부되었습니다.(Access Denied by Protected Service)"),
    BAD_RESPONSE("1502", "보호된 서비스에서 응답이 잘못되었습니다.(Bad Response from Protected Service)"),
    TEMP_UNAVAILABLE("1503", "일시적으로 사용할 수 없는 서비스(Service Temporarily Unavailable)"),
    NOT_ALLOWED_CLIENT_ID("1700", "엑세스가 허용되지 않습니다.(Client ID Access Not Allowed - Client ID)"),
    NOT_ALLOWED_SERVER_URI("1701", "엑세스가 허용되지 않습니다.(Service URI Access Not Allowed - Service URI)"),
    NOT_ALLOWED_CLIENT_ID_IP("1702", "엑세스가 허용되지 않습니다.(Client ID + Client IP Access Not Allowed - Client ID + Client IP)"),
    NOT_ALLOWED_CLIENT_ID_SERVER_URI("1703", "엑세스가 허용되지 않습니다.(Client ID + Service URI Access Not Allowed - Client ID + Service URI)"),
    NOT_ALLOWED_BLACKLIST("1705", "엑세스가 허용되지 않습니다.(Client ID + Black List Client IP Access is not allowed - Client ID + Black List Client IP)"),
    NOT_ALLOWED_PRODUCT("1706", "액세스가 허용되지 않습니다(Client ID + Product Code Access is not allowed - Client ID + Product Code)"),
    RESTRICTED_WEEK("1711", "거래제한된 요일입니다.(Transaction Restriction week)"),
    RESTRICTED_TIME("1712", "거래제한된 시간입니다.(Transaction Restriction time)"),
    RESTRICTED_WEEK_TIME("1713", "거래제한된 요일/시간입니다.(Transaction Restriction week/time)"),
    RESTRICTED_DATE("1714", "거래제한된 일자입니다.(Transaction Restriction date)"),
    RESTRICTED_DATE_TIME("1715", "거래제한된 일자/시간입니다.(Transaction Restriction date/time)"),
    RESTRICTED_HOLIDAY("1716", "공휴일 거래가 제한된 서비스입니다.(Transaction Restriction holiday)"),
    ERROR_SQLINJECT("1717", "SQL인젝션, XSS방어(Sql Injection Error)"),
    INVALID_TOKEN("1800", "잘못된 토큰(Invalid Token)"),
    INVALID_CLIENT_TOKEN("1801", "잘못된 클라이언트 정보(INVALID_TOKEN)"),
    EXCEED_CONNECTION("1900", "초과된 연결 횟수(Exceeded Connections)"),
    EXCEED_GETTOKEN("1901", "초과 된 토큰 조회 실패(Exceeded getToken failed count)"),
    EXCEED_CHECKTOKEN("1902", "초과된 토큰 체크 실패(Exceeded checkToken failed count)"),
    EXCEED_USER_CONNECTION("1903", "초과된 접속자 수 (Exceeded User Connections)"),
    EXCEED_CONTENT_LENGTH("1904", "전송 크기 초과(Exceeded Content Length)"),
    EXCEED_OVER_CONNECTION("1905", "접속량이 너무 많음 Over Connections"),
    EXCEED_PRODUCT_USECOUNT_LIMIT("1906", "상품이용 한도 초과(Exceeded Product Use Limit)"),
    EXCEED_API_USETIME_LIMIT("1907", "API 이용 주기 초과(Exceeded API Use Time Limit)"),
    EXCEED_PRODUCT_USETIME_LIMIT("1908", "상품 이용 주기 초과(Exceeded Product Use Time Limit)");
	
	public final String code;
	public final String message;
	
	NICE_RESULT(String code, String message) { 
		this.code = code; 
		this.message = message;
	}

	/** 코드로 메세지 매핑 */
	public static String messageOfCode(String maybeCode) {
		return Arrays.asList(NICE_RESULT.values()).stream()
				.filter(result -> maybeCode.equals(result.code))
				.map(result -> result.message)
				.findFirst()
				.orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9999, "정의되지 않은 NICE 응답코드 입니다."));
	}
}
