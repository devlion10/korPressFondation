package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumExam;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 교육 과정 연계 시험 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_EXAM_LIST")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfCurriculumExam.class)
public class CurriculumExam extends CSEntitySupport implements Serializable {

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 연계 시험 일련 번호 */
    @Id
    @Column(name="EXAM_SN", nullable = false)
    private String examSerialNo;

    /** 시험 정렬 번호 */
    @Column(name="EXAM_SORT_NO")
    private Integer sortNo;

    /** 연계 시험 과정 마스터 정보 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="EXAM_SN", insertable=false, updatable=false)
    private ExamMaster examMaster;
}