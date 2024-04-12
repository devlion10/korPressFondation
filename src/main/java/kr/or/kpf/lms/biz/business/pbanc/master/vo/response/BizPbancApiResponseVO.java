package kr.or.kpf.lms.biz.business.pbanc.master.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

/**
 * 사업 공고 관련 응답 객체
 */
@Schema(name="BizPbancApiResponseVO", description="사업공모 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 일련번호", example="1")
    private String bizPbancNo;

    @Schema(description="사업 공고 템플릿 유형", example="1")
    private Integer bizPbancType;

    @Schema(description="사업 구분", example="1")
    private Integer bizPbancCtgr;

    @Schema(description="사업명", example="1")
    private String bizPbancNm;

    @Schema(description="최대 시간", example="1")
    private Integer bizPbancMaxTm;

    @Schema(description="사업년도", example="1")
    private Integer bizPbancYr;

    @Schema(description="사업 공고 차수", example="1")
    private Integer bizPbancRnd;

    @Schema(description="최대 사업참여 기관", example="1")
    private Integer bizPbancMaxInst;

    @Schema(description="사업 선정 방식", example="1")
    private Integer bizPbancSlctnMeth;

    @Schema(description="강사 배정 방식", example="1")
    private Integer bizPbancInstrSlctnMeth;

    @Schema(description="사업 상태", example="1")
    private Integer bizPbancStts;

    @Schema(description="사업 접수기간 - 시작일", example="1")
    private String bizPbancRcptBgng;

    @Schema(description="사업 접수기간 - 종료일", example="1")
    private String bizPbancRcptEnd;

    @Schema(description="사업 지원기간 - 시작일", example="1")
    private String bizPbancSprtBgng;

    @Schema(description="사업 지원기간 - 종료일", example="1")
    private String bizPbancSprtEnd;

    @Schema(description="사업공고 결과발표일(없을 시 미정)", example="1")
    private String bizPbancRsltYmd;

    @Schema(description="사업 내용", example="1")
    private String bizPbancCn;

    @Schema(description="사업공고 신문 신청 가능 여부(없음/0: N, 1: Y)", required = false, example="사업공고 신문 신청 가능 여부 입니다.")
    private Integer bizPbancPeprYn;

    @Schema(description="사업 공고 담당자 연락처", required = true, example="사업 공고 담당자 연락처 입니다.")
    private String bizPbancPicTel;

}