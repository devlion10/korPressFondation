package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 강사 모집 공고 신청 정보(수업계획서) Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_APLY")
@Access(value = AccessType.FIELD)
public class BizInstructorAply extends CSEntitySupport implements Serializable {

    /** 강사 모집 공고 신청 정보 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_APLY_NO", nullable = false)
    private String bizInstrAplyNo;

    /** 강사 모집 공고 일련번호 */
    @Column(name = "BIZ_INSTR_NO", nullable = false)
    private String bizInstrNo;

    /** 사업공고 신청 일련번호 */
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 강사 모집 공고 신청 강사명 */
    @Column(name = "BIZ_INSTR_APLY_INSTR_NM", nullable = true)
    private String bizInstrAplyInstrNm;

    /** 강사 모집 공고 신청 강사 아이디 */
    @Column(name = "BIZ_INSTR_APLY_INSTR_ID", nullable = false)
    private String bizInstrAplyInstrId;

    /** 강사 모집 공고 신청 강사 지원 조건 - 순위 */
    @Column(name = "BIZ_INSTR_APLY_CNDT_ORDR", nullable = true)
    private Integer bizInstrAplyCndtOrdr;

    /** 강사 모집 공고 신청 강사 지원 조건 - 거리 */
    @Column(name = "BIZ_INSTR_APLY_CNDT_DIST", nullable = true)
    private float bizInstrAplyCndtDist;

    /** 강사 모집 공고 신청 강사 배정 점수(선정 점수) */
    @Column(name = "BIZ_INSTR_APLY_SLCTN_SCR", nullable = true)
    private Integer bizInstrAplySlctnScr;

    /** 강사 모집 공고 신청 강사 배정 점수(선정 조건) */
    @Column(name = "BIZ_INSTR_APLY_SLCTN_CNDT", nullable = true)
    private Integer bizInstrAplySlctnCndt;

    /** 강사 모집 공고 신청 강사 코멘트 */
    @Column(name = "BIZ_INSTR_APLY_CMNT", nullable = true)
    private String bizInstrAplyCmnt;

    /** 강사 모집 공고 신청 강사 정보 상태 0: 임시저장, 1: 접수, 2: 미승인, 3: 승인 */
    @Column(name = "BIZ_INSTR_APLY_STTS", nullable = false)
    private Integer bizInstrAplyStts;

    @Transient
    private BizInstructor bizInstructor;

    @Transient
    private BizInstructorPbanc bizInstructorPbanc;

    @Transient
    private BizOrganizationAply bizOrganizationAply;

    @Transient
    private BizPbancMaster bizPbancMaster;

    // 거리 증빙 상태
    @Transient
    private Integer bizInstructorDistStts;

}