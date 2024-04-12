package kr.or.kpf.lms.common.client;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.config.AppConfig;
import kr.or.kpf.lms.framework.exception.KPFException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CSWebClient {

	private static final Logger logger = LoggerFactory.getLogger(CSWebClient.class);
	
	private final AppConfig appConfig;

	/**
	 * 웹 통신 규격 설정
	 *
	 * @return
	 */
	public RestTemplate settingWebProtocol() {
		return settingWebProtocol(false);
	}

	/**
	 * 웹 통신 규격 설정
	 * 
	 * @return
	 */
	public RestTemplate settingWebProtocol(boolean sslIgnore) {
		//1. SSL 인증서 만료 무시(개발계에서만 사용할 것...)
		SimpleClientHttpRequestFactory requestFactory = Optional.ofNullable(sslIgnore)
															.filter(isIgnore -> isIgnore)
															.map(isIgnore -> new SimpleClientHttpRequestFactory() {
																@Override
																protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
																	if (connection instanceof HttpsURLConnection) {
																		logger.info("SSL 인증서 무시");
																		SSLContext sc;
																		try {
																			sc = SSLContext.getInstance("SSL"); // SSLContext를 생성
																			sc.init(null, new TrustManager[] { new X509TrustManager() { // 공개키 암호화 설정 무력화
																				@Override
																				public X509Certificate[] getAcceptedIssuers() { return null; }
																				@Override
																				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
																				@Override
																				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
																			} }, new SecureRandom());

																			((HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
																			((HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true); // 호스트 검증 무시
																		} catch (NoSuchAlgorithmException e1) {
																			logger.error("{}- {}", e1.getClass().getCanonicalName(), e1.getMessage(), e1);
																			throw new KPFException(KPF_RESULT.ERROR9999, "예상치 못한 에러가 발생하였습니다.");
																		} catch (KeyManagementException e2) {
																			logger.error("{}- {}", e2.getClass().getCanonicalName(), e2.getMessage(), e2);
																			throw new KPFException(KPF_RESULT.ERROR9999, "예상치 못한 에러가 발생하였습니다.");
																		}
															}
															super.prepareConnection(connection, httpMethod);
														}
													})
													.orElse(null);

		//1.1. 운영계 환경일 경우 기본으로 셋팅
		if(requestFactory == null) {
			requestFactory = new SimpleClientHttpRequestFactory();
		}

		//1. 서버 통신 Timeout 설정
		requestFactory.setConnectTimeout(appConfig.getWebConnection().getHttpConnectTimeout());
		requestFactory.setReadTimeout(appConfig.getWebConnection().getHttpReadTimeout());

		//2. 통신 간 Charset 지정(utf-8)
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		restTemplate.setRequestFactory(requestFactory);

		return restTemplate;
	}
}
