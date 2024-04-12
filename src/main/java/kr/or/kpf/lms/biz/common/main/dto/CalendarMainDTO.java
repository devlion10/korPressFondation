package kr.or.kpf.lms.biz.common.main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarMainDTO {
    /** 일련번호(Key) */
    private String keyValue;
    /** 카테고리 */
    private String category;
    /** 공모사업 or 교육과정 유형 */
    private String type;
    /** 제목 */
    private String title;
    /** 신청 시작 일시 */
    private String applyBeginDateTime;
    /** 신청 종료 일시 */
    private String applyEndDateTime;
    /** 접수 시작 일자 */
    private String receiptBeginDate;
    /** 접수 종료 일자 */
    private String receiptEndDate;
    /** 내용 */
    private String contents;
}
