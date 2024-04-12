package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumApplicationEvaluateComment;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 교육 과정 설문 및 평가 의견 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_APLY_EVAL_COMM")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfCurriculumApplicationEvaluateComment.class)
public class CurriculumApplicationEvaluateComment extends CSEntitySupport implements Serializable {

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

    /** 로그인 아이디 */
    @Id
    @Column(name="LGN_ID")
    private String userId;

    /** 회원 의견 */
    @Column(name="ANS_COMMENT")
    private String answerComment;
}
