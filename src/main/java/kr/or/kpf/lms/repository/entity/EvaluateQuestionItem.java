package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 강의 평가 질문 문항 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EVAL_QUES_ITEM")
@Access(value = AccessType.FIELD)
public class EvaluateQuestionItem extends CSEntitySupport implements Serializable {

    /** 강의 평가 질문 문항 일련 번호 */
    @Id
    @Column(name="ITEM_SN", nullable = false)
    private String questionItemSerialNo;

    /** 강의 평가 질문 일련 번호 */
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 강의 평가 질문 문항 순서*/
    @Column(name="ITEM_SORT_NO", nullable = false)
    private Integer questionSortNo;

    /** 강의 평가 질문 문항 */
    @Column(name="ITEM_VALUE", nullable = false)
    private String questionItemValue;
}