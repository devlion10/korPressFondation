package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 템플릿 2 관련 응답 객체
 */
@Schema(name="BizPbancTmpl2ApiResponseVO", description="사업공모 템플릿 2 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl2ApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 기본형 템플릿 일련번호", example="P2T0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 일련번호", example="PAC0000001")
    private String bizPbancNo;

    @Schema(description="운영구분 1학기 사용여부", example="0")
    private Integer bizPbancTmpl2Smst1UseYn;

    @Schema(description="운영구분 1학기 최대시간", example="10")
    private Integer bizPbancTmpl2Smst1MaxHr;

    @Schema(description="운영구분 2학기 사용여부", example="0")
    private Integer bizPbancTmpl2Smst2UseYn;

    @Schema(description="운영구분 2학기 최대시간", example="10")
    private Integer bizPbancTmpl2Smst2MaxHr;

    @Schema(description="운영구분 연간 사용여부", example="0")
    private Integer bizPbancTmpl21YearUseYn;

    @Schema(description="운영구분 연간 최대시간", example="10")
    private Integer bizPbancTmpl21YearMaxHr;
}