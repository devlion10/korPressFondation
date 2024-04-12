package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name="BizPbancTmpl1TrgtApiRequestVO", description="사업 공고 미디어교육 템플릿 대상 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl1TrgtApiRequestVO {
    @Schema(description="사업 공고 미디어교육 템플릿 대상 일련번호", required = true, example="1")
    private Integer bizPbancTmpl1TrgtNo;

    @Schema(description="사업 공고 템플릿 1 일련번호", required = true, example="PAC0000001")
    private String bizPbancTmpl1No;

    @Schema(description="사업 공고 미디어교육 템플릿 대상 코드", required = false, example="0")
    private Integer bizPbancTmpl1TrgtCd;

}
