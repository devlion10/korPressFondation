package kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 템플릿 0 관련 응답 객체
 */
@Schema(name="BizPbancTmpl0ApiResponseVO", description="사업공모 템플릿 0 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancTmpl0ItemApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 기본형 템플릿 0 Item 일련번호", example="P0T0000001")
    private String bizPbancTmpl0ItemNo;

    @Schema(description="사업 공고 기본형 템플릿 일련번호", example="PAC0000001")
    private String bizPbancTmpl0No;

    @Schema(description="사업 공고 기본형 템플릿 추가 입력 필드명", example="0")
    private String bizPbancTmpl0ItemNm;

    @Schema(description="사업 공고 기본형 템플릿 추가 입력 필드 내용", example="0")
    private String bizPbancTmpl0ItemCn;

}