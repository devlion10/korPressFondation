package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.CreateBizPbancTmpl0;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.CreateBizPbancTmpl2;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.UpdateBizPbancTmpl0;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.UpdateBizPbancTmpl2;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(name="BizPbancTmpl2ApiRequestVO", description="사업 공고 템플릿 2 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl2ApiRequestVO {
    @Schema(description="사업 공고 기본형 템플릿 일련번호", required = true, example="P2T0000001")
    private String bizPbancTmpl2No;

    @Schema(description="사업 공고 일련번호", required = true, example="PAC0000001")
    private String bizPbancNo;

    @Schema(description="운영구분 1학기 사용여부", required = true, example="0")
    private Integer bizPbancTmpl2Smst1UseYn;

    @Schema(description="운영구분 1학기 최대시간", required = true, example="10")
    private Integer bizPbancTmpl2Smst1MaxHr;

    @Schema(description="운영구분 2학기 사용여부", required = true, example="0")
    private Integer bizPbancTmpl2Smst2UseYn;

    @Schema(description="운영구분 2학기 최대시간", required = true, example="10")
    private Integer bizPbancTmpl2Smst2MaxHr;

    @Schema(description="운영구분 연간 사용여부", required = true, example="0")
    private Integer bizPbancTmpl21YearUseYn;

    @Schema(description="운영구분 연간 최대시간", required = true, example="10")
    private Integer bizPbancTmpl21YearMaxHr;

}
