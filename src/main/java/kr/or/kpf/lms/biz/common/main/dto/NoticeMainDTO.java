package kr.or.kpf.lms.biz.common.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeMainDTO {
    /** 카테고리 */
    @Builder.Default
    private String categoryName = "공지사항";
    /** 최상위 여부 */
    @JsonIgnore
    @Builder.Default
    private Boolean isTop = false;
    /** 공지사항 일련번호 */
    private String noticeSerialNo;
    /** 제목 */
    private String title;
    /** 등록 일시 */
    private String createDateTime;
    /** 공지사항 유형 */
    private String noticeType;
    /** 공지사항 유형 명 */
    private String noticeTypeName;
}
