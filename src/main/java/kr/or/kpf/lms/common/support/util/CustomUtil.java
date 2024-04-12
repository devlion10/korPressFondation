package kr.or.kpf.lms.common.support.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 유용한 Util 모음(라이브러리가 따로 없어 새로 커스텀으로 만드는 것들은 이곳에 모을 것) * 
 */
public class CustomUtil {

	public static final String DEFAULT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
	public static final String DIGIT2FORMAT = "%1$02d";
	public static final String HH24MISS = "HHmmss";
	private static final List<String> NOT_USER_METHODS = Arrays.asList("getStackTrace", "getCurrentMethodName", "getStackTraceMethodName");
	public static final Random random = new Random();
	public static final String YYMMDD = "yyMMdd";
	public static final String YYYYMMDD = "yyyyMMdd";
	
	public static final String YYYYMMDDHH24MISS = "yyyyMMddHHmmss";
	public static final String YYMMDDHH24MISS = "yyMMddHHmmss";
	/**
	 * 현재 월의 첫번때 날을 DD로 획득
	 * @return 현재 월의 첫번때 날 (예: 01)
	 */
	public static String firstDate() {
		int firstDate = DateTime.now().dayOfMonth().getMinimumValue();
		return String.format(DIGIT2FORMAT, firstDate);
	}
	/**
	 * 현재 일자의 시작 시간
	 * @return
	 */
	public static String firstHour() {
		int firstHour = DateTime.now().hourOfDay().getMinimumValue();
		return String.format(DIGIT2FORMAT, firstHour);
	}
	
	/**
	 * 현재 시간의 첫번째 분
	 * @return
	 */
	public static String firstMinute() {
		int firstMinute = DateTime.now().minuteOfHour().getMinimumValue();
		return String.format(DIGIT2FORMAT, firstMinute);
	}
	
	/**
	 * 현재 분의 첫번째 초
	 * @return
	 */
	public static String firstSecond() {
		int firstSecond = DateTime.now().secondOfMinute().getMinimumValue();
		return String.format(DIGIT2FORMAT, firstSecond);
	}
	
	/**
	 * 현재메소드명 획득
	 */
	public static String getCurrentMethodName() {
		return getStackTraceMethodName(0);
	}	
	
	/**
	 * 상위메소드명 획득
	 */
	public static String getCurrentMethodName(int depth) {
		return getStackTraceMethodName(depth);
	}
	
	/**
	 * 문자타입 시간 -> Date
	 * @param string
	 * @return
	 */
	public static Date getDate(String strDate) {
		Date date;
		try {
			date = new SimpleDateFormat(DEFAULT_DATE_TIME).parse(strDate);
		} catch (Exception e) {
			throw new RuntimeException("일자 획득에 실패하였습니다.");
		}
		return date;		
	}
	
	/**
	 * 문자타입 시간 -> Date
	 * @param string
	 * @return
	 */
	public static Date getDate(String strDate, String patter) {
		Date date;
		try {
			date = new SimpleDateFormat(patter).parse(strDate);
		} catch (Exception e) {
			throw new RuntimeException("일자 획득에 실패하였습니다.");
		}
		return date;		
	}	
	
	/**
	 * 문자타입 시간 -> jodatime 으로 포맷팅
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static DateTime getJodaTime(String strDate) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_TIME);
		return formatter.parseDateTime(strDate);
	}
	
	/**
	 * 문자타입 시간 -> jodatime 으로 포맷팅
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static DateTime getJodaTime(String strDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		return formatter.parseDateTime(strDate);
	}
	
	/**
	 * 서버의 IP를 획득 (InetAddress.getLocalHost())
	 */
	public static String getServerIP() {
		try {
			//서버IP
			InetAddress inetAddress = InetAddress.getLocalHost();
			return inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException("서버 IP 획득에 실패하였습니다.");
		}
	}	
	
	/**
	 * 현재메소드명 또는 호출자의 메소드명을 획득
	 * @param upperDepth 0이면 현재메소드명, 1이상이면 상위 호출 depth 메소드명
	 * @return 메소드명
	 */
	public static String getStackTraceMethodName(final int upperDepth) {
		final StackTraceElement[] steList = Thread.currentThread().getStackTrace();
		int length = steList.length;
		int startIndex = 0;
		for (int i=0; i<length; i++) {
			String methodName = steList[i].getMethodName();
			if(NOT_USER_METHODS.contains(methodName)) {
				startIndex++;
			} else {
				break;
			}
		}
		
		return steList[startIndex + upperDepth].getMethodName();
	}
	
	/**
	 * jodatime -> 문자타입 시간
	 * @param jodaTime
	 * @return
	 */
	public static String getStringFromJodaTime(DateTime jodaTime) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_TIME);
		return formatter.print(jodaTime);
	}
	
	/**
	 * jodatime -> 문자타입 시간
	 * @param jodaTime
	 * @return
	 */
	public static String getStringFromJodaTime(DateTime jodaTime, String pattern) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		return formatter.print(jodaTime);
	}
	
	/**
	 * 문자계열과 컬렉션계열에서 null, 'null', 빈컬렉션인지 여부
	 * @param value 문자열 또는 컬렉션
	 * @return 문자열 또는 컬렉션이 비었거나 'null'인지 여부
	 */
	public static boolean isNullCheck(Object value) {
		if(value == null) {
			return true;
		}
		if(value instanceof Number) {
			return false; 
		}
		if(value instanceof CharSequence) {
			return StringUtils.isBlank((CharSequence) value) || value.equals("null"); 
		} 
		if(value instanceof Collection<?>) {
			return CollectionUtils.size(value) == 0;
		}
		if(value instanceof List) {
			return ((List<?>) value).isEmpty();
		}
		if(value instanceof Enum) {
			return false;
		}
		throw new RuntimeException("isNullCheck가 현재 지원하지 않는 ObjectType입니다. " + value.getClass().getCanonicalName());
	}
	
	/**
	 * 현재 달의 마지막 일자
	 * @return
	 */
	public static String lastDate() {
		int lastDate = DateTime.now().dayOfMonth().getMaximumValue();
		return String.format(DIGIT2FORMAT, lastDate);
	}
	
	/**
	 * 현재 일자의 마지막 시간
	 * @return
	 */
	public static String lastHour() {
		int lastHour = DateTime.now().hourOfDay().getMaximumValue();
		return String.format(DIGIT2FORMAT, lastHour);
	}
	
	/**
	 * 현재 시간의 마지막 분
	 * @return
	 */
	public static String lastMinute() {
		int lastMinute = DateTime.now().minuteOfHour().getMaximumValue();
		return String.format(DIGIT2FORMAT, lastMinute);
	}
	
	/**
	 * 현재 분의 마지막 초
	 * @return
	 */
	public static String lastSecond() {
		int lastSecond = DateTime.now().secondOfMinute().getMaximumValue();
		return String.format(DIGIT2FORMAT, lastSecond);
	}
	
	public static final long getTimeStamp() {
		Date currentDate = new Date();
		return currentDate.getTime() / 1000;
	}
	
	private CustomUtil() {
	    throw new IllegalAccessError("Utility class");
	}
}
