package kr.or.kpf.lms.biz.communication.archive.data.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.archive.data.vo.CreateArchive;
import kr.or.kpf.lms.biz.communication.archive.data.vo.UpdateArchive;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.common.support.Code;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;

@Schema(name="ArchiveViewRequestVO", description="자료실 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @Schema(description="검색어 범위")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @Schema(description="검색에 포함할 단어")
    private String containText;

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 팀 구분 */
    @Schema(description="팀 구분")
    private String teamCategory;

    /** 자료 구분 */
    @Schema(description="자료 구분", required=true, example="")
    private String materialCategory;

    /** 자료 유형(검색용) */
    @Schema(description="자료 유형(검색용)")
    private String types;

    /** 자료 유형 */
    @Schema(description="자료 유형")
    private String materialType;

    /** Pre */
    @Schema(description="Pre")
    private String preSequenceNo;

    /** Next */
    @Schema(description="Next")
    private String nextSequenceNo;

    /** 현재 페이지 */
    @Schema(description="현재 페이지")
    private Integer currentPage;

    /** 현재 페이지 사이즈 */
    @Schema(description="현재 페이지 사이즈")
    private Integer currentSize;

    /** 전체 페이지 사이즈 */
    @Schema(description="전체 페이지 사이즈")
    private Integer totalPages;
}
