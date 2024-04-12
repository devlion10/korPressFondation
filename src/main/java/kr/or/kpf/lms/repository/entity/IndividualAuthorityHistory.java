package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 사업 참여 권한 신청 이력 (개인) 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "INV_AUTH_APLY_HISTORY")
@Access(value = AccessType.FIELD)
public class IndividualAuthorityHistory extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 로그인 아이디 */
    @Column(name = "LGN_ID", nullable = false)
    private String userId;

    /** 사업 참여 권한 타입(SCHOOL: 사업참여자(학교담당자), AGENCY: 사업참여자(기관담당자), INSTR: 미디어강사) */
    @Column(name="BIZ_AUTH", nullable = false)
    private String bizAuthority;

    /** 사업 참여 권한 신청 상태 */
    @Column(name="BIZ_AUTH_STATE", nullable = false)
    private String bizAuthorityApprovalState;

    /** 집 우편번호 */
    @Column(name = "USER_ZIPCD")
    private String userZipCode;

    /** 집 주소 */
    @Column(name = "USER_ADDRESS1")
    private String userAddress1;

    /** 집 주소 상세 */
    @Column(name = "USER_ADDRESS2")
    private String userAddress2;

    /** 사진파일 경로 */
    @Column(name="PIC_FILE_PATH")
    private String pictureFilePath;

    /** 사진파일 사이즈 */
    @Column(name="PIC_FILE_SIZE")
    private Long pictureFileSize;

    /** 서명/도장 파일 경로 */
    @Column(name="SIGN_FILE_PATH")
    private String signFilePath;

    /** 서명/도장 파일 사이즈 */
    @Column(name="SIGN_FILE_SIZE")
    private Long signFileSize;

    /** 강의 가능 지역 */
    @Column(name="REGION", nullable = false)
    private String region;

    /** 졸업년도 */
    @Column(name="GRA_YEAR", nullable = false)
    private String graduationYear;

    /** 학교명 */
    @Column(name="GRA_SCHOOL", nullable = false)
    private String graduationSchool;

    /** 전공 */
    @Column(name="MAJOR", nullable = false)
    private String major;

    /** 학위 */
    @Column(name="DEGREE", nullable = false)
    private String degree;

    /** 강의 주요 내용 */
    @Column(name="COMMENTS", nullable = false)
    private String comments;

    /** 주요 이력 */
    @Column(name="ANTECEDENTS", nullable = false)
    private String antecedents;

    /** 자격증 */
    @Column(name="CERTIFICATE", nullable = false)
    private String certificate;

    /** 은행 */
    @Column(name="BANK", nullable = false)
    private String bank;

    /** 계좌번호 */
    @Column(name="ACCT_NO", nullable = false)
    private String accountNo;
}
