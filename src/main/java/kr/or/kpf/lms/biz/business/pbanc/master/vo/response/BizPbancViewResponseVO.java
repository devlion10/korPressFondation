package kr.or.kpf.lms.biz.business.pbanc.master.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 관련 응답 객체
 */
@Schema(name="BizPbancViewResponseVO", description="사업공모 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancViewResponseVO extends CSResponseVOSupport {

    @Schema(description="사업명", example="미디어교육1")
    private String bizName;

    @Schema(description="신규 공고 여부")
    private Boolean isNew;
}