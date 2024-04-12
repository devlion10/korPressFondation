package kr.or.kpf.lms.biz.communication.event.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import java.math.BigInteger;

@Schema(name="EventViewRequestVO", description="이벤트 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventViewRequestVO extends CSViewVOSupport {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목")
    private String title;

    /** 타입(1: 이벤트, 2: 설문) */
    @Schema(description="타입(1: 이벤트, 2: 설문)")
    private String type;

    /** 검색어 범위 */
    @Schema(description="검색어 범위")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @Schema(description="검색에 포함할 단어")
    private String containText;

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
