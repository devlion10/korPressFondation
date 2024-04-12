package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfExamQuestion;
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
@Table(name = "EXAM_QUES_LIST")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfExamQuestion.class)
public class ExamQuestion extends CSEntitySupport implements Serializable {
    /** 교육과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 시험 일련번호 */
    @Id
    @Column(name="EXAM_SN", nullable = false)
    private String examSerialNo;

    /** 시험 문제 일련번호 */
    @Id
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 콘텐츠 정렬 번호 */
    @Column(name="QUES_SORT_NO", nullable=false)
    private Integer sortNo;
}
