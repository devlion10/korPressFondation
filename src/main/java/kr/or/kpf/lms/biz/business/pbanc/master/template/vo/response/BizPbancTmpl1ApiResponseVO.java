package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizPbancTmpl1Trgt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사업 공고 템플릿 1 관련 응답 객체
 */
@Schema(name="BizPbancTmpl1ApiResponseVO", description="사업공모 템플릿 1 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl1ApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 기본형 템플릿 일련번호", example="P1T0000001")
    private String bizPbancTmpl1No;

    @Schema(description="사업 공고 일련번호", example="PAC0000001")
    private String bizPbancNo;

    @Schema(description="사업 공고 기본형 신청 회원 유형", example="1")
    private Integer bizPbancTmpl1TrgtYn;

    private List<BizPbancTmpl1Trgt> bizPbancTmpl1Trgts;
}