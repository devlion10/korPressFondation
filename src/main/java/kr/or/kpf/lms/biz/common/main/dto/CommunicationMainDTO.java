package kr.or.kpf.lms.biz.common.main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunicationMainDTO {
    /** 일련번호(Key) */
    private String keyValue;
    /** 카테고리 */
    private String category;
    /** 유형 */
    private String type;
    /** 제목 */
    private String title;
    /** 내용 */
    private String contents;
    /** 작성일 */
    private String createDateTime;
}
