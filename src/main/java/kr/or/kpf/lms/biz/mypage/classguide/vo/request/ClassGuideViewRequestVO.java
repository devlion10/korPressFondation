package kr.or.kpf.lms.biz.mypage.classguide.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import javax.persistence.Column;

/**
 * 수업지도안 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ClassGuideViewRequestVO extends CSViewVOSupport {
    /** 검색어 범위 */
    private String containTextType;
    /** 검색에 포함할 단어 */
    private String containText;

    /** 등록자 id */
    private String registUserId;
    /** 수업 지도안 코드 */
    private String classGuideCode;
    /** 수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) ) */
    private String classGuideType;
}