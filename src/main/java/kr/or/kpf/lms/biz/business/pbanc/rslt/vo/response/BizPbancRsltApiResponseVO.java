package kr.or.kpf.lms.biz.business.pbanc.rslt.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.CreateBizPbancRslt;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.UpdateBizPbancRslt;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 사업 공고 관련 응답 객체
 */
@Schema(name="BizPbancRsltApiResponseVO", description="사업공모 결과 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancRsltApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 결과공고 일련번호", example="PACR000001")
    private String bizPbancRsltNo;

    @Schema(description="사업 공고 일련번호", example="PAC0000003")
    private String bizPbancNo;

    @Schema(description="가승인 기관(신청 기관)", example="ORG0000000")
    private String bizPbancAplyInst;

    @Schema(description="승인 기관", example="ORG0000000")
    private String bizPbancAprvInst;

    @Schema(description="선정 결과 내용", example="평생교실 선정 결과")
    private String bizPbancRsltCn;

    @Schema(description="공지 여부", example="평생교실")
    private Integer bizPbancNtcYn;

    @Schema(description="결과공고 첨부파일", example="")
    private String bizPbancRsltFile;
}