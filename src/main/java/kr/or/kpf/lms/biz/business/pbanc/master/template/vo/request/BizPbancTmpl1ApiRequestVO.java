package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(name="BizPbancTmpl1ApiRequestVO", description="사업 공고 템플릿 1 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl1ApiRequestVO {
    @Schema(description="사업 공고 기본형 템플릿 일련번호", required = true, example="P1T0000001")
    private String bizPbancTmpl1No;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC0000001")
    private String bizPbancNo;

    private List<Integer> bizPbancTmpl1Trgts;

}
