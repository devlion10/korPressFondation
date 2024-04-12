package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 시험 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EXAM_MASTER")
@Access(value = AccessType.FIELD)
public class ExamMaster extends CSEntitySupport implements Serializable {
    /** 시험 일련 번호 */
    @Id
    @Column(name="EXAM_SN", nullable = false)
    private String examSerialNo;

    /** 시험명 */
    @Column(name="EXAM_NM", nullable = false)
    private String examName;

    /** 시험시간(분단위) */
    @Column(name="EXAM_MINUTE", nullable = false)
    private Integer examMinute;

    /** 시험내용 */
    @Column(name="EXAM_CN", nullable = false)
    private String examContents;

    /** 집계 문제 수 */
    @Column(name="TOT_QUES_CNT", nullable = false)
    private Integer questionTotalCount;

    /** 등급 1 선택 문제 수 */
    @Column(name="GRD_1_CHC_QUES_CNT", nullable = false)
    private Integer gradeFirstSelectQuestionCount;

    /** 등급 2 선택 문항 수 */
    @Column(name="GRD_2_CHC_QUES_CNT", nullable = false)
    private Integer gradeSecondSelectQuestionCount;

    /** 등급 3 선택 문항 수 */
    @Column(name="GRD_3_CHC_QUES_CNT", nullable = false)
    private Integer gradeThirdSelectQuestionCount;

    /** 등급 1 주관식 문항 수 */
    @Column(name="GRD_1_SBJCV_QUES_CNT", nullable = false)
    private Integer gradeFirstSubExQuestionCount;

    /** 등급 2 주관식 문항 수 */
    @Column(name="GRD_2_SBJCV_QUES_CNT", nullable = false)
    private Integer gradeSecondSubExQuestionCount;

    /** 등급 3 주관식 문항 수 */
    @Column(name="GRD_3_SBJCV_QUES_CNT", nullable = false)
    private Integer gradeThirdSubExQuestionCount;

    /** 등급 1 배점 점수 */
    @Column(name="GRD_1_ALTM_SCR", nullable = false)
    private Integer gradeFirstQuestionScore;

    /** 등급 2 배점 점수 */
    @Column(name="GRD_2_ALTM_SCR", nullable = false)
    private Integer gradeSecondQuestionScore;

    /** 등급 3 배점 점수 */
    @Column(name="GRD_3_ALTM_SCR", nullable = false)
    private Integer gradeThirdQuestionScore;

    /** 선택 문항 무작위 여부 */
    @Column(name="CHC_QITEM_RNDM_YN", nullable = false)
    @Convert(converter=BooleanConverter.class)
    private Boolean isSelectQuestionRandom;

    /** 선택 문항 무작위 여부 */
    @Column(name="USE_YN", nullable = false)
    @Convert(converter=BooleanConverter.class)
    private Boolean isUsable;
}
