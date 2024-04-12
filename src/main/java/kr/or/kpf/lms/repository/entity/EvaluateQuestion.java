package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfEvaluateQuestion;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 강의 평가 질문 목록 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EVAL_QUES_LIST")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfEvaluateQuestion.class)
public class EvaluateQuestion extends CSEntitySupport implements Serializable {

    /** 강의평가 일련 번호 */
    @Id
    @Column(name="EVAL_SN", nullable = false)
    private String evaluateSerialNo;

    /** 강의평가 질문 일련 번호 */
    @Id
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 강의평가 질문 정렬 번호 */
    @Column(name="QUES_SORT_NO", nullable=false)
    private Integer sortNo;

    /** 강의평가 질문 마스터 정보 */
    @OneToOne
    @JoinColumn(name="QUES_SN", insertable=false, updatable=false)
    private EvaluateQuestionMaster evaluateQuestionMaster;
}

