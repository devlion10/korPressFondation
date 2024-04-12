package kr.or.kpf.lms.biz.common.main.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;

@Data
@Builder
public class BizMainDTO {
    /** 카테고리(미디어교육 카테고리의 세부 카테고리명을 지정 - 학교미디어, 사회미디어 */
    private String categoryName;
    /** 사업 공고 일련번호 */
    private String bizPbancNo;
    /** 사업명 */
    private String bizPbancNm;
    /** 사업 공고 템플릿 유형 */
    private Integer bizPbancType;
    @JsonIgnore
    /** 사업 구분(사업 구분 '미디어교육'으로 설정하기 위한 값으로, 데이터 표현은 하지 않음 */
    private Integer bizPbancCtgr;
    /** 사업 상태 (0: 모집 중, 1: 모집 마감, 9: 임시저장) */
    private Integer bizPbancStts;
    /** 등록 일시 */
    private String createDateTime;
}
