package kr.or.kpf.lms.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Spring Application Config (YAML)의 app-config 속성
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app-config")
@Validated
public class AppConfig implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	/** Spring Security 관련 설정 */
	@Valid private AppConfigSecurityProperties security;
	/** HTTP 통신 관련 설정 */
	@Valid private HttpWebConnectionProperties webConnection;
	/** 본인 인증 관련 설정 */
	@Valid private AppConfigSelfAuthenticationProperties selfAuthentication;
	/** 비즈뿌리오 관련 설정 */
	@Valid private BizPPurioProperties bizPPurio;
	/** 업로드 파일 관련 설정 */
	@Valid private UploadFileProperties uploadFile;
	/** 서버 도메인 */
	@Valid private String domain;
	/** QR코드 생성 URL */
	@Valid private String qrCodePath;

	public String getDomain(HttpServletRequest request){
		String scheme = String.valueOf(request.getScheme());
		String serverName = String.valueOf(request.getServerName());
		String serverPort = request.getServerPort() == 80 ? "" : ":"+request.getServerPort();
		String fullUrl = scheme+"://"+serverName+serverPort;
		return fullUrl;
	}

	/** Spring Security 관련 설정 */
	@Data
	public static class AppConfigSecurityProperties {
		/** Spring Security Debug 모드 활성화 */
		private boolean debug = false;
		/** Spring Security 비밀번호 암호화 키 */
		@NotNull @NotEmpty
		private String encryptKey;

		public void buildLog(StringBuilder sb, String prefix) {
			sb.append(prefix).append("Debug 모드 활성화: ").append(debug).append(System.lineSeparator()).append(prefix)
			.append("비밀번호 암호화 키: ").append(encryptKey).append(System.lineSeparator());
		}
	}

	/** HTTP 통신 관련 설정 */
	@Data
	public static class HttpWebConnectionProperties {
		/** connectionTimeout */
		private Integer httpConnectTimeout;
		/** readTimeout */
		private Integer httpReadTimeout;
		
		public void buildLog(StringBuilder sb, String prefix) {
			sb.append(prefix).append("웹통신 연결 대기 가능 시간: ").append(httpConnectTimeout).append(System.lineSeparator()).append(prefix)
			.append("웹통신 통신 유지 가능 시간: ").append(httpReadTimeout).append(System.lineSeparator());
		}
	}

	@Data
	public static class AppConfigSelfAuthenticationProperties {
		/** 본인인증 URL(나이스) */
		@NotNull @NotEmpty
		private String url;
		/** 본인인증 ClientId */
		@NotNull @NotEmpty
		private String clientId;
		/** 본인인증 ClientToken */
		@NotNull @NotEmpty
		private String clientToken;
		/** 본인인증 ProductCode */
		@NotNull @NotEmpty
		private String productCode;
		/** 본인인증 SiteCode */
		@NotNull @NotEmpty
		private String siteCode;
		/** 본인인증 SitePassword */
		@NotNull @NotEmpty
		private String sitePassword;
		/** 본인인증 결과 ReturnUrl */
		@NotNull @NotEmpty
		private String returnUrl;
		/** 본인인증 결과 ReturnUrl2 */
		@NotNull @NotEmpty
		private String returnUrl2;
		/** 본인인증 결과 ReturnUrl3 */
		@NotNull @NotEmpty
		private String returnUrl3;
		/** 본인인증 결과 ReturnUrl4 */
		@NotNull @NotEmpty
		private String returnUrl4;
		/** 본인인증 실패 ReturnUrl */
		@NotNull @NotEmpty
		private String failureUrl;

		public void buildLog(StringBuilder sb, String prefix) {
			sb.append(prefix).append("URL(나이스): ").append(url).append(System.lineSeparator()).append(prefix)
			.append("ClientId: ").append(clientId).append(System.lineSeparator()).append(prefix)
			.append("ClientToken: ").append(clientToken).append(System.lineSeparator()).append(prefix)
			.append("ProductCode: ").append(productCode).append(System.lineSeparator()).append(prefix)
			.append("SiteCode: ").append(siteCode).append(System.lineSeparator()).append(prefix)
			.append("SitePassword: ").append(sitePassword).append(System.lineSeparator()).append(prefix)
			.append("ReturnUrl: ").append(returnUrl).append(System.lineSeparator()).append(prefix)
			.append("ReturnUrl2: ").append(returnUrl2).append(System.lineSeparator()).append(prefix)
			.append("ReturnUrl3: ").append(returnUrl3).append(System.lineSeparator()).append(prefix)
			.append("ReturnUrl4: ").append(returnUrl4).append(System.lineSeparator()).append(prefix)
			.append("FailureUrl: ").append(failureUrl).append(System.lineSeparator());
		}
	}

	@Data
	public static class BizPPurioProperties {
		/** 비즈뿌리오 URL */
		@NotNull @NotEmpty
		private String url;
		/** 비즈뿌리오 ID */
		@NotNull @NotEmpty
		private String id;
		/** 비즈뿌리오 PWD */
		@NotNull @NotEmpty
		private String password;

		public void buildLog(StringBuilder sb, String prefix) {
			sb.append(prefix).append("URL: ").append(url).append(System.lineSeparator()).append(prefix)
					.append("Id: ").append(id).append(System.lineSeparator()).append(prefix)
					.append("Password: ").append(password).append(System.lineSeparator());
		}
	}

	/** 업로드 파일 관련 설정 */
	@Data
	public static class UploadFileProperties {
		/** 파일 저장 기본 경로 */
		@NotNull @NotEmpty
		private String uploadContextPath;
		/** 콘텐츠 파일 저장 경로 */
		@NotNull @NotEmpty
		private String contentsContextPath;
		/** 자료실 관련 폴더 */
		@NotNull @NotEmpty
		private String referenceFolder;
		/** 회원 관련 폴더 */
		@NotNull @NotEmpty
		private String userFolder;
		/** 강사 관련 폴더 */
		@NotNull @NotEmpty
		private String instrFolder;
		/** 교육 과정 신청서 폴더 */
		@NotNull @NotEmpty
		private String applicationFolder;
		/** 1:1 문의 첨부파일 폴더 */
		@NotNull @NotEmpty
		private String myQnaFolder;
		/** 공지사항 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String noticeFolder;
		/** 자료실 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String lmsDataFolder;
		/** 배너/팝업 이미지 첨부파일 폴더 */
		@NotNull @NotEmpty
		private String bannerFolder;
		/** 콘텐츠 썸네일 폴더 */
		@NotNull @NotEmpty
		private String thumbnailFolder;
		/** 출석 QR 코드 폴더 */
		@NotNull @NotEmpty
		private String qrCodeFolder;
		/** 시험 폴더 */
		@NotNull @NotEmpty
		private String examFolder;
		/** 교육 과제 폴더 */
		@NotNull @NotEmpty
		private String assignmentFolder;
		/** 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String uploadFolder;
		/** 수업지도안 폴더*/
		@NotNull @NotEmpty
		private String classGuideFolder;
		/** 사업 공고 신청 (신청서) - 언론인/기본형 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizAplyFolder;
		/** 사업 공고 신청 (신청서) 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizOrganizationAplyFolder;
		/** 사업 공고 신청 (신청서) 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizPbancResultFolder;
		/** 강사 모집 공고 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizInstrFolder;
		/** 거리 증빙 지도 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizInstrDistFolder;
		/** 결과보고서 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizOrganizationRsltRptFolder;

		/** 사업자등록증 첨부파일 폴더*/
		@NotNull @NotEmpty
		private String bizRegFolder;

		public void buildLog(StringBuilder sb, String prefix) {
			sb.append(prefix).append("업로드 파일 Context 경로: ").append(uploadContextPath).append(System.lineSeparator()).append(prefix)
					.append("콘텐츠 파일 Context 경로: ").append(contentsContextPath).append(System.lineSeparator()).append(prefix)
					.append("회원 관련 폴더: ").append(userFolder).append(System.lineSeparator()).append(prefix)
					.append("강사 관련 폴더: ").append(instrFolder).append(System.lineSeparator()).append(prefix)
					.append("교육 과정 신청서 폴더: ").append(applicationFolder).append(System.lineSeparator()).append(prefix)
					.append("1:1 문의 첨부파일 폴더: ").append(myQnaFolder).append(System.lineSeparator()).append(prefix)
					.append("공지사항 첨부파일 폴더: ").append(noticeFolder).append(System.lineSeparator()).append(prefix)
					.append("자료실 첨부파일 폴더: ").append(lmsDataFolder).append(System.lineSeparator()).append(prefix)
					.append("배너/팝업 이미지 폴더: ").append(bannerFolder).append(System.lineSeparator()).append(prefix)
					.append("콘텐츠 썸네일 폴더: ").append(thumbnailFolder).append(System.lineSeparator()).append(prefix)
					.append("출석 QR 코드 폴더: ").append(qrCodeFolder).append(System.lineSeparator()).append(prefix)
					.append("시험 폴더: ").append(examFolder).append(System.lineSeparator())
					.append("첨부파일 폴더: ").append(uploadFolder).append(System.lineSeparator())
					.append("수업지도안 폴더: ").append(classGuideFolder).append(System.lineSeparator())
					.append("사업 공고 신청 (신청서) - 언론인/기본형 첨부파일 폴더: ").append(bizAplyFolder).append(System.lineSeparator())
					.append("사업 공고 신청 (신청서) 첨부파일 폴더: ").append(bizOrganizationAplyFolder).append(System.lineSeparator())
					.append("사업 공고 결과 첨부파일 폴더: ").append(bizPbancResultFolder).append(System.lineSeparator())
					.append("강사 모집 공고 결과 첨부파일 폴더: ").append(bizInstrFolder).append(System.lineSeparator())
					.append("거리 증빙 지도 첨부파일 폴더: ").append(bizInstrDistFolder).append(System.lineSeparator())
					.append("결과보고서 첨부파일 폴더: ").append(bizOrganizationRsltRptFolder).append(System.lineSeparator())
			        .append("사업자등록증 첨부파일 폴더: ").append(bizRegFolder).append(System.lineSeparator());

		}
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		this.getSecurity().buildLog(sb, "[Spring Security]");
		this.getWebConnection().buildLog(sb, "[HTTP 통신]");
		this.getSelfAuthentication().buildLog(sb, "[본인인증]");
		this.getUploadFile().buildLog(sb, "[파일 업로드]");
		this.getBizPPurio().buildLog(sb, "[비즈뿌리오]");
		logger.info(sb.toString());
	}
}
