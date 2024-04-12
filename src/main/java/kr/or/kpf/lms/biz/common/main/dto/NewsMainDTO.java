package kr.or.kpf.lms.biz.common.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class NewsMainDTO {
    /** 카테고리 */
    @JsonIgnore
    @Builder.Default
    private String categoryName = "뉴스";
    /** 행사소개(보도자료) 일련번호 */
    private BigInteger sequenceNo;
    /** 제목 */
    private String title;
    /** 등록 일시 */
    private String createDateTime;
}
