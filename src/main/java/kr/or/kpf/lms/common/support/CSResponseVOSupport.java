package kr.or.kpf.lms.common.support;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 공통 응답 객체
 */
@Data
@NoArgsConstructor
public class CSResponseVOSupport {
	/** 결과 코드 */
	@Schema(description="결과 코드", required = true, example="SUCCESS")
	private String resultCode = "SUCCESS";

	/** 결과 메세지 */
	@Schema(description="결과 메세지", required = true, example="성공")
	private String resultMessage = "0000";

	public CSResponseVOSupport(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	public static CSResponseVOSupport of(KPF_RESULT result) {
		return new CSResponseVOSupport(result.code, result.message);
	}
}
