package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(name="BizPbancTmpl0ApiRequestVO", description="사업 공고 템플릿 0 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl0ApiRequestVO {
    @Schema(description="사업 공고 기본형 템플릿 일련번호", required = true, example="P0T0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC0000001")
    private String bizPbancNo;

    @Schema(description="사업 공고 기본형 템플릿 추가 입력 필드 여부(0: 없음, 1: 있음)", required = true, example="")
    private Integer bizPbancTmpl0ItemYn;


    private List<Integer> bizPbancTmpl0Trgts;

    private List<BizPbancTmpl0ItemApiRequestVO> bizPbancTmpl0Items;

}
