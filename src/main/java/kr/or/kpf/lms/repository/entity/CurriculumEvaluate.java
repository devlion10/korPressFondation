package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumEvaluate;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 교육 과정 연계 설문 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_EVAL_LIST")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfCurriculumEvaluate.class)
public class CurriculumEvaluate extends CSEntitySupport implements Serializable {

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 설문 코드 */
    @Id
    @Column(name="EVAL_SN", nullable = false)
    private String evaluateSerialNo;

    /** 설문 정렬번호 */
    @Column(name="EVAL_SORT_NO", nullable = false)
    private Integer sortNo;

    /** 강의평가 마스터 정보 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="EVAL_SN", insertable=false, updatable=false)
    private EvaluateMaster evaluateMaster;
}
