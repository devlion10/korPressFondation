package kr.or.kpf.lms.biz.mypage.instructorstate.vo.request;


import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 강의이력 화면 검색 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FormeBizlecinfoViewRequestVO extends CSViewVOSupport {

    /** 강사명 */
    private String blciUserNm;

    /** 강사아이디 */
    private String blciUserId;

    /** 사업명 */
    private String bninTitle;

    /** 기관명 */
    private String bainInstNm;

    /** 사업 차수 */
    private Integer bninDegree;

    /** 강의확인서 연 */
    private String year;

    /** 강의확인서 월 */
    private String month;

}