package kr.or.kpf.lms.biz.servicecenter.notice.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import javax.persistence.Column;
import java.math.BigInteger;

/**
 * 공지사항 관련 요청 객체
 */
@Schema(name="NoticeViewRequestVO", description="공지사항 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class NoticeViewRequestVO extends CSViewVOSupport {

    /** 시리얼 번호 */
    @Schema(description="시리얼 번호")
    private String noticeSerialNo;

    /** 공지사항 타입 */
    @Schema(description="공지사항 타입")
    private String noticeType;

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
