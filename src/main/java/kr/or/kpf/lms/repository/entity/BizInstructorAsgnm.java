package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfBizOrganizationAplyDtl;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 강사 모집 공고 강사 배정 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_ASGNM")
@Access(value = AccessType.FIELD)
public class BizInstructorAsgnm extends CSEntitySupport implements Serializable {

    /** 강사 모집 공고 강사 배정 정보 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_ASGNM_NO", nullable = false)
    private String bizInstrAsgnmNo;

    /** 강사 모집 공고 일련번호 */
    @Column(name = "BIZ_INSTR_NO", nullable = false)
    private String bizInstrNo;

    /** 사업 공고 신청 일련번호 */
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 사업 공고 신청 수업 계획서 일련번호 */
    @Column(name = "BIZ_ORG_APLY_DTL_NO", nullable = false)
    private String bizOrgAplyDtlNo;

    /** 강사 모집 공고 신청 정보 일련번호  */
    @Column(name = "BIZ_INSTR_APLY_NO", nullable = false)
    private String bizInstrAplyNo;

    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name="BIZ_INSTR_APLY_NO", updatable = false, insertable = false)
    private BizInstructorAply bizInstructorAply;

    @Transient
    private BizOrganizationAplyDtl bizOrganizationAplyDtl;

    @Transient
    private BizInstructorIdentifyDtl bizInstructorIdentifyDtl;

    @Transient
    private BizSurvey bizSurvey;

    @Transient
    private List<BizSurveyAns> bizSurveyAnsList;

}