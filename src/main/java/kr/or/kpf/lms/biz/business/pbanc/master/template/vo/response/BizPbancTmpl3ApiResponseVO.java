package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 템플릿 3 관련 응답 객체
 */
@Schema(name="BizPbancTmpl3ApiResponseVO", description="사업공모 템플릿 3 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl3ApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 기본형 템플릿 일련번호", example="P3T0000001")
    private String bizPbancTmpl3No;

    @Schema(description="사업 공고 일련번호", example="PAC0000001")
    private String bizPbancNo;

    @Schema(description="자유학기제 사용여부", example="0")
    private Integer bizPbancTmpl3FrdmSmstUseYn;

    @Schema(description="자유학기제 최대시간", example="10")
    private Integer bizPbancTmpl3FrdmSmstMaxHr;

    @Schema(description="자유학년제 사용여부", example="0")
    private Integer bizPbancTmpl3FrdmGrdUseYn;

    @Schema(description="자유학년제 최대시간", example="10")
    private Integer bizPbancTmpl3FrdmGrdMaxHr;
}