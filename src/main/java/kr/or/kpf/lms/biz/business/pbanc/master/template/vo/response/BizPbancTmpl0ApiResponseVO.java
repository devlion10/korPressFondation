package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.CreateBizPbancTmpl0;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.UpdateBizPbancTmpl0;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Item;
import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Trgt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 사업 공고 템플릿 0 관련 응답 객체
 */
@Schema(name="BizPbancTmpl0ApiResponseVO", description="사업공모 템플릿 0 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl0ApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 기본형 템플릿 일련번호", example="P0T0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 일련번호", example="PAC0000001")
    private String bizPbancNo;

    @Schema(description="사업 공고 기본형 템플릿 추가 입력 필드 여부(0: 없음, 1: 있음)", required = true, example="1")
    private Integer bizPbancTmpl0ItemYn;

    @Schema(description="사업 공고 기본형 템플릿 대상 아이템 여부(0: 전체 리스트, 1: 일부 리스트) ", required = true, example="1")
    private Integer bizPbancTmpl0TrgtYn;

    private List<BizPbancTmpl0Trgt> bizPbancTmpl0Trgts;

    private List<BizPbancTmpl0Item> bizPbancTmpl0Items;
}