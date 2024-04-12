package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name="BizPbancTmpl0ItemApiRequestVO", description="사업 공고 기본형 템플릿 항목 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl0ItemApiRequestVO {
    @Schema(description="사업 공고 기본형 템플릿 0 Item 일련번호", required = true, example="P0T0000001")
    private String bizPbancTmpl0ItemNo;

    @Schema(description="사업 공고 기본형 템플릿 일련번호", required = true, example="PAC0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 기본형 템플릿 추가 입력 필드명", required = true, example="TEST")
    private String bizPbancTmpl0ItemNm;

    @Schema(description="사업 공고 기본형 템플릿 추가 입력 필드 내용", required = true, example="TEST CONTENT")
    private String bizPbancTmpl0ItemCn;

}
