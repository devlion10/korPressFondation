package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumCollaboration;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 연계 교육 과정 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_LIST")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfCurriculumCollaboration.class)
public class CurriculumCollaboration extends CSEntitySupport implements Serializable {

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 참조 과정 코드 */
    @Id
    @Column(name="RFRN_CRCL_CD", nullable = false)
    private String referenceCurriculumCode;

    /** 연계 교육 과정 마스터 정보 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RFRN_CRCL_CD", referencedColumnName="CRCL_CD", insertable=false, updatable=false)
    private ReferenceCurriculumMaster referenceCurriculumMaster;

    /** 연계 교육 과정의 진행 중인 교육 일정 */
    @Transient
    private List<EducationPlan> educationPlanList;
}
