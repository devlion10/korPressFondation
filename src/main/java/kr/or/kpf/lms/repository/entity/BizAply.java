package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 사업 공고 신청 - 언론인 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_APLY")
@Access(value = AccessType.FIELD)
public class BizAply extends CSEntitySupport implements Serializable {
    /** 사업 공고 신청 - 언론인/기본형 일련 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 사업 공고 일련 번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 기관 일련번호  */
    @Column(name = "ORG_CD", nullable = false)
    private String orgCd;

    /** 사업 공고 신청 - 언론인/기본형 타입(journalist: 언론인(개인), general: 기본형(단체)) */
    @Column(name = "BIZ_APLY_TYPE", nullable = false)
    private String bizAplyType;

    /** 사업 공고 신청 - 언론인/기본형 첨부파일 경로 */
    @Column(name = "BIZ_APLY_FILE")
    private String bizAplyFile;

    /** 사업 공고 신청 - 언론인/기본형 첨부파일 사이즈 */
    @Column(name = "BIZ_APLY_FILE_SIZE")
    private Long bizAplyFileSize;

    /** 사업 공고 신청 - 언론인/기본형 첨부파일 원본명 */
    @Column(name = "BIZ_APLY_FILE_ORGN")
    private String bizAplyFileOrigin;

    /** 사업 공고 신청 - 언론인/기본형 신청자 아이디 */
    @Column(name = "BIZ_APLY_USER_ID")
    private String bizAplyUserID;

    /** 사업 공고 신청 담당자 이름 */
    @Column(name = "BIZ_APLY_USER_NM", nullable = true)
    private String bizAplyUserNm;

    /** 사업 공고 신청 소속기관 이름 */
    @Column(name = "ORG_NM", nullable = true)
    private String orgName;

    /** 사업 공고 신청 담당자 부서 */
    @Column(name = "BIZ_APLY_USER_DPTM", nullable = true)
    private String bizAplyUserDptm;
    /** 사업 공고 신청 담당자 직위 */
    @Column(name = "BIZ_APLY_USER_JBGD", nullable = true)
    private String bizAplyUserJbgd;

    /** 사업 공고 신청 담당자 이메일 */
    @Column(name = "BIZ_APLY_USER_EML", nullable = true)
    private String bizAplyUserEml;

    /** 사업 공고 신청 담당자 번호 */
    @Column(name = "BIZ_APLY_USER_TELNO", nullable = true)
    private String bizAplyUserTelno;

    /** 신청서 상태(1: 신청, 3: 반려, 5: 가승인, 7: 승인, 9: 종료) */
    @Column(name = "BIZ_APLY_STTS", nullable = true)
    private Integer bizAplyStts;

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_PBANC_NO", insertable=false, updatable=false)
    private BizPbancMaster bizPbancMaster;

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_APLY_USER_ID", insertable=false, updatable=false)
    private LmsUser lmsUser;

    @Transient
    private List<BizAplyDtl> bizAplyDtls;

    @Transient
    private List<BizPbancTmpl5> bizPbancTmpl5;

    @Transient
    private OrganizationInfo organizationInfo;

}