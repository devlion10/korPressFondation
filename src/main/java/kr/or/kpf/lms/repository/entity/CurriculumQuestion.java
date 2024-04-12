package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumQuestion;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 시험 문항 목록 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_QUES_LIST")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfCurriculumQuestion.class)
public class CurriculumQuestion extends CSEntitySupport implements Serializable {
    /** 교육과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 시험 문제 일련 번호 */
    @Id
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 시험 질문 마스터 정보 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="QUES_SN", insertable=false, updatable=false)
    private ExamQuestionMaster examQuestionMaster;
}
