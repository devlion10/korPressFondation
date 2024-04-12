package kr.or.kpf.lms.biz.business.pbanc.rslt.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizAply;
import kr.or.kpf.lms.repository.entity.BizOrganizationAply;
import kr.or.kpf.lms.repository.entity.FileMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.List;

/**
 * 사업 공고 관련 응답 객체
 */
@Schema(name="BizPbancRsltApiResponseVO", description="사업공모 결과 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancRsltCustomApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 결과공고 일련번호", example="")
    private String bizPbancRsltNo;

    @Schema(description="사업 공고 일련번호", example="")
    private String bizPbancNo;

    @Schema(description="가승인 기관(신청 기관)", example="")
    private String bizPbancAplyInst;

    @Schema(description="승인 기관", example="")
    private String bizPbancAprvInst;

    @Schema(description="선정 결과 내용", example="")
    private String bizPbancRsltCn;

    @Schema(description="공지 여부", example="")
    private Integer bizPbancNtcYn;

    @Schema(description="결과공고 첨부파일", example="")
    private String bizPbancRsltFile;

    @Schema(description="결과공고 첨부파일 원본명", example="")
    private String bizPbancRsltFileOrigin;

    @Schema(description="사업 공고 유형", example="")
    private Integer bizPbancType;

    @Schema(description="사업 공고 구분", example="")
    private Integer bizPbancCtgr;

    @Schema(description="사업 공고명", example="")
    private String bizPbancNm;

    @Schema(description="등록자 명", example="")
    private String userName;

    @Schema(description="등록자 아이디", example="")
    private String registUserId;

    @Schema(description="사업 공고 구분명", example="")
    private String bizPbancCtgrNm;

    @Schema(description="사업 공고 신청 공고 리스트(공고 상태 승인)", example="1")
    private List<BizOrganizationAply> bizOrganizationAprvs;

    @Schema(description="사업 공고 신청 공고 리스트(공고 상태 승인)", example="1")
    private List<BizAply> bizAprvs;
}