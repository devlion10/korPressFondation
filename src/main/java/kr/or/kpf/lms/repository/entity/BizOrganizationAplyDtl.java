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
 사업 공고 신청 수업 계획서 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_ORG_APLY_DTL")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfBizOrganizationAplyDtl.class)
public class BizOrganizationAplyDtl extends CSEntitySupport implements Serializable {
    /** 사업 공고 신청 수업 계획서 일련번호 */
    @Id
    @Column(name = "BIZ_ORG_APLY_DTL_NO", nullable = false)
    private String bizOrgAplyDtlNo;

    /** 사업 공고 신청 일련번호 */
    @Id
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 수업 계획서 교육 회차  */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_RND", nullable = false)
    private Integer bizOrgAplyLsnDtlRnd;

    /** 수업 계획서 수업 일자 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_YMD", nullable = false)
    private String bizOrgAplyLsnDtlYmd;

    /** 수업 계획서 수업 주제 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_TPIC", nullable = false)
    private String bizOrgAplyLsnDtlTpic;

    /** 수업 계획서 수업 시수 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_HR", nullable = false)
    private Integer bizOrgAplyLsnDtlHr;

    /** 수업 계획서 수업 시작 시각 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_BGNG_TM", nullable = false)
    private String bizOrgAplyLsnDtlBgngTm;

    /** 수업 계획서 수업 종료 시각 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_END_TM", nullable = false)
    private String bizOrgAplyLsnDtlEndTm;

    /** 수업 계획서 수업 인원 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_NOPE", nullable = false)
    private Integer bizOrgAplyLsnDtlNope;

    /** 수업 계획서 수업 장소 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_PLC", nullable = false)
    private String bizOrgAplyLsnDtlPlc;

    /** 수업 계획서 수업 계획서 비고 */
    @Column(name = "BIZ_ORG_APLY_LSN_DTL_ETC", nullable = false)
    private String bizOrgAplyLsnDtlEtc;

    @Transient
    private List<BizInstructorAsgnm> bizInstructorAsgnms = new ArrayList<>();

    @Transient
    private List<BizInstructorIdentifyDtl> bizInstructorIdentifyDtls = new ArrayList<>();

    @Transient
    private List<BizSurveyAns> bizSurveyAns;

    @Transient
    private BizSurvey bizSurvey;

}