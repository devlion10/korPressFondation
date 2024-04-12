package kr.or.kpf.lms.biz.mypage.educationstate.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import java.math.BigInteger;

/**
 * 나의 교육 현황 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyEducationStateViewRequestVO extends CSViewVOSupport {
    /** 교육 상태(1: 신청 심사 중, 2: 교육 진행 중, 3: 교육 종료) */
    private String educationState;
    /** 교육 타입(1: 화상 교육, 2: 집합 교육, 3: 이러닝 교육, 4: 이러닝 교육) */
    private String educationType;
    /** 교육 신청 일련번호 */
    private String applicationNo;
    /** 교육 계획 코드 */
    private String educationPlanCode;
    /** 강의 코드 */
    private String lectureCode;
    /** 자료실 일련번호 */
    private BigInteger referenceSequenceNo;
    /** 챕터 일련번호 */
    private String chapterCode;

    /** 과정 코드 */
    private String curriculumCode;

    /** 조회 연도 */
    private String year;
}
