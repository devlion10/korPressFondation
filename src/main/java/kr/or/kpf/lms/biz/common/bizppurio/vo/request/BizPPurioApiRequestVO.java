package kr.or.kpf.lms.biz.common.bizppurio.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="BizPPurioApiRequestVO", description="비즈뿌리오 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BizPPurioApiRequestVO extends CSViewVOSupport {
    /** 발신인 */
    private String sender;
    /** 수신인 */
    private String receiver;
    /** 내용 */
    private String content;
}
