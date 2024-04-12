package kr.or.kpf.lms.framework.exception.handler;

import lombok.Data;

/**
 * 서버 오류 응답 처리
 */
@Data
public class KPFExceptionVO {

	private String resultCode;
	private String resultMessage;
	private String verboseMessage;
	
	public KPFExceptionVO(String resultErrorCode, String resultErrorMsg, String verboseMessage) {
		this.resultCode = resultErrorCode;
		this.resultMessage = resultErrorMsg;
		this.verboseMessage = verboseMessage;
	}
}
