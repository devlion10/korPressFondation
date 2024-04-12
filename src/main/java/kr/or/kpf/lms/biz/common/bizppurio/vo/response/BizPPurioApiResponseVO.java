package kr.or.kpf.lms.biz.common.bizppurio.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.Builder;
import lombok.Data;

@Schema(name="BizPPurioApiResponseVO", description="비즈뿌리오 관련 응답 객체")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BizPPurioApiResponseVO extends CSResponseVOSupport {}
