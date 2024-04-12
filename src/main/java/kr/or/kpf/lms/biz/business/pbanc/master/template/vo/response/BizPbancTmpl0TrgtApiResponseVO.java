package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 템플릿 0 관련 응답 객체
 */
@Schema(name="BizPbancTmpl0ApiResponseVO", description="사업공모 템플릿 0 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl0TrgtApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 기본형 템플릿 대상 일련번호", example="1")
    private Integer bizPbancTmpl0TrgtNo;

    @Schema(description="사업 공고 템플릿 0 일련번호", example="PAC0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 기본형 템플릿 대상 코드", example="0")
    private Integer bizPbancTmpl0TrgtCd;

}