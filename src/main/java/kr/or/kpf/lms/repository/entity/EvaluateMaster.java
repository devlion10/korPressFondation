package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 강의 평가 마스터 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EVAL_MASTER")
@Access(value = AccessType.FIELD)
public class EvaluateMaster extends CSEntitySupport implements Serializable {

    /** 강의 평가 일련 번호 */
    @Id
    @Column(name="EVAL_SN", nullable = false)
    private String evaluateSerialNo;

    /** 강의 평가 제목 */
    @Column(name="EVAL_TITLE", nullable = false)
    private String evaluateTitle;

    /** 강의 평가 타입 */
    @Column(name="EVAL_TYPE", nullable = false)
    private String evaluateType;

    /** 강의 평가 내용 */
    @Column(name="EVAL_CN", nullable = false)
    private String evaluateContents;

    /** 기타의견 사용 여부 */
    @Column(name="OTHER_COM_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsableOtherComments;

    /** 사용 여부 */
    @Column(name="USE_YN", nullable = false)
    @Convert(converter= BooleanConverter.class)
    private Boolean isUsable;

    @OneToMany
    @OrderBy(value = "sortNo ASC")
    @JoinColumn(name="EVAL_SN", referencedColumnName="EVAL_SN")
    private List<EvaluateQuestion> evaluateQuestionList;
}
