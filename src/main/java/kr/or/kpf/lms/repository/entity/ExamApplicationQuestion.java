package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfExamApplicationQuestion;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 교육 과정 신청 시험 문항 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EXAM_APLY_QUES")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfExamApplicationQuestion.class)
public class ExamApplicationQuestion extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 교육 신청 일련 번호 */
    @Id
    @Column(name="EDU_APLY_SN", nullable=false)
    private String applicationNo;

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable=false)
    private String curriculumCode;

    /** 시험 일련 번호 */
    @Id
    @Column(name="EXAM_SN", nullable = false)
    private String examSerialNo;

    /** 시험 문제 일련 번호 */
    @Id
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 로그인 아이디 */
    @Id
    @Column(name="LGN_ID", nullable=false)
    private String userId;

    /** 정렬 번호 */
    @Column(name="QUES_SORT_NO", nullable=false)
    private Integer sortNo;

    /** 시험 문제 응답 문항 일련 번호 */
    @Column(name="ANS_ITEM_SN")
    private String answerQuestionItemSerialNo;

    /** 시험 문제 응답 문항(주관식, 서술형일 경우 답) */
    @Column(name="ANS_ITEM_VALUE")
    private String answerQuestionItemValue;

    /** 완료 여부 */
    @Column(name="CMPTN_YN", nullable=false)
    @Convert(converter= BooleanConverter.class)
    private Boolean isComplete;

    /** 완료 일시 */
    @Column(name="CMPTN_DT")
    @Convert(converter= DateToStringConverter.class)
    private String completeDateTime;

    @OneToOne
    @JoinColumn(name="QUES_SN", updatable = false, insertable = false)
    ExamQuestionMaster examQuestionMaster;
}
