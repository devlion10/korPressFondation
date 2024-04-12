package kr.or.kpf.lms.biz.user.webuser.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.Builder;
import lombok.Data;

@Schema(name="JoinTermsApiResponseVO", description="약관동의 API 관련 응답 객체")
@Data
@Builder
public class JoinTermsApiResponseVO extends CSResponseVOSupport {}
