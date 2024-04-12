package kr.or.kpf.lms.biz.common.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllMainDTO {
    /** 카테고리 */
    private String categoryName;
    /** 최상위 여부 */
    @JsonIgnore
    @Builder.Default
    private Boolean isTop = false;
    /** 일련번호(Key) */
    private String keyValue;
    /** 제목 */
    private String title;
    /** 사업 공고 템플릿 유형 */
    private Integer bizPbancType;
    /** 사업 상태 (0: 모집 중, 1: 모집 마감) */
    private Integer bizPbancStts;
    /** 공지사항 유형 */
    private String noticeType;
    /** 공지사항 유형 명 */
    private String noticeTypeName;
    /** 등록 일시 */
    private String createDateTime;
}
