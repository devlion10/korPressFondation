package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.organization.aply.vo.CreateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.organization.aply.vo.UpdateBizOrganizationAply;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 사업 공고 신청 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_ORG_APLY")
@Access(value = AccessType.FIELD)
public class BizOrganizationAply extends CSEntitySupport implements Serializable {
    /** 사업 공고 신청 일련번호 */
    @Id
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 기관 일련번호  */
    @Column(name = "ORG_CD", nullable = false)
    private String orgCd;

    /** 사업공고 신청 기관 대표자명 */
    @Column(name = "BIZ_ORG_APLY_RPRSV_NM", nullable = false)
    private String bizOrgAplyRprsvNm;

    /** 사업공고 신청 총 시간 */
    @Column(name = "BIZ_ORG_APLY_TIME", nullable = true)
    private double bizOrgAplyTime;

    /** 사업공고 신청 최초 총 시간 */
    @Column(name = "BIZ_ORG_APLY_TIME_FRST", nullable = true)
    private double bizOrgAplyTimeFrst;

    /** 사업 공고 신청 지역 */
    @Column(name = "BIZ_ORG_APLY_RGN", nullable = false)
    private String bizOrgAplyRgn;

    /** 사업 공고 신청 담당자 아이디 */
    @Column(name = "BIZ_ORG_APLY_PIC", nullable = false)
    private String bizOrgAplyPic;

    /** 사업 공고 신청 담당자 이름 */
    @Column(name = "BIZ_ORG_APLY_PIC_NM", nullable = false)
    private String bizOrgAplyPicNm;

    /** 사업 공고 신청 담당자 직위 */
    @Column(name = "BIZ_ORG_APLY_PIC_JBGD", nullable = false)
    private String bizOrgAplyPicJbgd;

    /** 사업 공고 신청 담당자 이메일 */
    @Column(name = "BIZ_ORG_APLY_PIC_EML", nullable = false)
    private String bizOrgAplyPicEml;

    /** 사업 공고 신청 담당자 번호 */
    @Column(name = "BIZ_ORG_APLY_PIC_TELNO", nullable = false)
    private String bizOrgAplyPicTelno;

    /** 사업 공고 신청 담당자 핸드폰번호 */
    @Column(name = "BIZ_ORG_APLY_PIC_MPNO", nullable = false)
    private String bizOrgAplyPicMpno;

    /** 사업 공고 신청 접수 상태 - 0: 임시저장, 1: 신청, 2: 승인, 3: 반려 */
    @Column(name = "BIZ_ORG_APLY_STTS", nullable = false)
    private Integer bizOrgAplyStts;

    /** 사업 공고 신청 접수 상태 변경 사유 */
    @Column(name = "BIZ_ORG_APLY_STTS_CMNT", nullable = true)
    private String bizOrgAplySttsCmnt;

    /** 사업 공고 신청 변경 가능 여부 */
    @Column(name = "BIZ_ORG_APLY_CHG_YN", nullable = false)
    private Integer bizOrgAplyChgYn;

    /** 운영 구분 */
    @Column(name = "BIZ_ORG_APLY_OPER_CTGR", nullable = false)
    private String bizOrgAplyOperCtgr;

    /** 수업 형태(운영 방법) */
    @Column(name = "BIZ_ORG_APLY_OPER_METH", nullable = false)
    private String bizOrgAplyOperMeth;

    /** 수업 계획 - 교육기간 - 시작일 */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_BGNG", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizOrgAplyLsnPlanBgng;

    /** 수업 계획 - 교육기간 - 종료일 */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_END", nullable = false)
    @Convert(converter=DateYMDToStringConverter.class)
    private String bizOrgAplyLsnPlanEnd;

    /** 수업 대상 */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_TRGT", nullable = false)
    private String bizOrgAplyLsnPlanTrgt;

    /** 회차당 교육 인원 */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_NOPE", nullable = false)
    private Integer bizOrgAplyLsnPlanNope;

    /** 설명1(교육의 방향성 및 신청한 이유) */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_DSCR1", nullable = false)
    private String bizOrgAplyLsnPlanDscr1;

    /** 설명2(미디어교육 운영 현황) */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_DSCR2", nullable = false)
    private String bizOrgAplyLsnPlanDscr2;

    /** 설명3(향후 활용계획 및 수업을 통한 기대효과) */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_DSCR3", nullable = false)
    private String bizOrgAplyLsnPlanDscr3;

    /** 건의사항 - 희망강사 */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_ETC_INSTR")
    private String bizOrgAplyLsnPlanEtcInstr;

    /** 기타 의견 */
    @Column(name = "BIZ_ORG_APLY_LSN_PLAN_ETC")
    private String bizOrgAplyLsnPlanEtc;

    /** 신문 신청 여부(없음/0: N, 1: Y) */
    @Column(name = "BIZ_ORG_APLY_PEPR_YN")
    private Integer bizOrgAplyPeprYn;

    /** 사업 공고 신청 첨부파일 */
    @Column(name = "BIZ_ORG_APLY_FILE")
    private String bizOrgAplyFile;

    @NotFound(action= NotFoundAction.IGNORE)
    @OrderBy(value = "bizOrgAplyLsnDtlRnd ASC")
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_ORG_APLY_NO", insertable=false, updatable=false)
    private List<BizOrganizationAplyDtl> bizOrganizationAplyDtls = new ArrayList<>();

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_PBANC_NO", insertable=false, updatable=false)
    private BizPbancMaster bizPbancMaster;

    @Transient
    private OrganizationInfo organizationInfo;

    @Transient
    private OrganizationAuthorityHistory organization;

    @Transient
    private BizOrganizationRsltRpt bizOrganizationRsltRpt;

}