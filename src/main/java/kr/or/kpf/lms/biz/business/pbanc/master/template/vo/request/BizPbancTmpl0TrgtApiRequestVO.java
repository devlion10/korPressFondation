package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name="BizPbancTmpl0TrgtApiRequestVO", description="사업 공고 기본형 템플릿 대상 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl0TrgtApiRequestVO {
    @Schema(description="사업 공고 기본형 템플릿 대상 일련번호", required = true, example="1")
    private Integer bizPbancTmpl0TrgtNo;

    @Schema(description="사업 공고 템플릿 0 일련번호", required = true, example="PAC0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 기본형 템플릿 대상 코드", required = false, example="0")
    private Integer bizPbancTmpl0TrgtCd;

}
