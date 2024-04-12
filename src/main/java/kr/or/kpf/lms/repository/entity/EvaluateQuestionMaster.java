package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 강의 평가 질문 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EVAL_QUES_MASTER")
@Access(value = AccessType.FIELD)
public class EvaluateQuestionMaster extends CSEntitySupport implements Serializable {

    /** 평가 일련 번호 */
    @Id
    @Column(name="QUES_SN", nullable=true)
    private String questionSerialNo;

    /** 강의 평가 질문 카테고리 */
    @Column(name="QUES_CTGR", nullable=true)
    private String questionCategory;

    /** 강의 평가 질문 유형 코드 (1: 단일선택, 2: 다중선택, 3: 단답형, 4: 서술형) */
    @Column(name="QUES_TYPE", nullable=true)
    private String questionType;

    /** 강의 평가 질문 제목 */
    @Column(name="QUES_TITLE", nullable=true)
    private String questionTitle;

    /** 문항 수 */
    @Column(name="QITEM_CNT", nullable=true)
    private Integer questionItemCount;

    /** 강의 평가 질문 내용 */
    @Column(name="QUES_CN", nullable=false)
    private String questionContents;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 강의 평가 질문 문항 리스트 */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="QUES_SN", referencedColumnName = "QUES_SN", insertable=false, updatable=false)
    private List<EvaluateQuestionItem> questionItemList;

}
