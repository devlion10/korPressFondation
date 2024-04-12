package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumApplicationEvaluate;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 교육 과정 신청 평가(후기) 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_APLY_EVAL")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfCurriculumApplicationEvaluate.class)
public class CurriculumApplicationEvaluate extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO")
    private BigInteger sequenceNo;

    /** 교육 신청 일련 번호 */
    @Id
    @Column(name="EDU_APLY_SN")
    private String applicationNo;

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD")
    private String curriculumCode;

    /** 강의 평가 일련 번호 */
    @Id
    @Column(name="EVAL_SN")
    private String evaluateSerialNo;

    /** 강의 평가 질문 일련 번호 */
    @Id
    @Column(name="QUES_SN")
    private String questionSerialNo;

    /** 강의 평가 질문 문항 일련 번호 */
    @Column(name="ANS_ITEM_SN")
    private String answerQuestionItemSerialNo;

    /** 강의 평가 질문 문항 */
    @Column(name="ANS_ITEM_VALUE")
    private String answerQuestionItemValue;

    /** 로그인 아이디 */
    @Id
    @Column(name = "LGN_ID")
    private String userId;
}
