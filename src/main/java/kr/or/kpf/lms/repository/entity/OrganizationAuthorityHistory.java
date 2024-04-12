package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 사업 참여 권한 신청 이력 (기관 및 학교) 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ORG_AUTH_APLY_HISTORY")
@Access(value = AccessType.FIELD)
public class OrganizationAuthorityHistory extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 로그인 아이디 */
    @Column(name = "LGN_ID", nullable = false)
    private String userId;

    /** 소속 기관 코드 */
    @Column(name="ORG_CD", nullable = false)
    private String organizationCode;

    /** 소속 기관 명 */
    @Column(name="ORG_NAME", nullable = false)
    private String organizationName;

    /** 소속 기관 서브 기관 명 */
    @Column(name="ORG_SUB_NAME")
    private String organizationSubName;

    /** 사업 참여 권한 타입(SCHOOL: 사업참여자(학교담당자), AGENCY: 사업참여자(기관담당자), INSTR: 미디어강사) */
    @Column(name="BIZ_AUTH", nullable = false)
    private String bizAuthority;

    /** 사업 참여 권한 신청 상태 */
    @Column(name="BIZ_AUTH_STATE", nullable = false)
    private String bizAuthorityApprovalState;

    /** 소속 기관 사업자 등록번호 */
    @Column(name="BIZ_LICENSE_NO", nullable = true)
    private String bizLicenseNumber;

    /** 기관 대표 */
    @Column(name="ORG_REP", nullable = true)
    private String representative;

    /** 소속 기관 우편번호 */
    @Column(name="ORG_ZIP_CD", nullable = true)
    private String organizationZipCode;

    /** 소속 기관 주소1 */
    @Column(name="ORG_ADDRESS1", nullable = true)
    private String organizationAddress1;

    /** 소속 기관 주소2 */
    @Column(name="ORG_ADDRESS2")
    private String organizationAddress2;

    /** 기관 홈페이지 */
    @Column(name="HOMEPAGE")
    private String homepage;

    /** 소속 기관 연락처 */
    @Column(name="ORG_TEL_NO")
    private String organizationTelNumber;

    /** 소속 기관 팩스 번호 */
    @Column(name="ORG_FAX_NO")
    private String organizationFaxNumber;

    /** 학습 수 */
    @Column(name="LEARN_CNT")
    private Integer learningCount;

    /** 학생 수 */
    @Column(name="STUDENT_NUM")
    private Integer numberStudents;

    /** 소속 부서 */
    @Column(name="DEPARTMENT")
    private String department;

    /** 담당자 직급 */
    @Column(name="USER_RANK")
    private String rank;

    /** 담당자 직책 */
    @Column(name="POSITION")
    private String position;

    /** 담당 콘텐츠 */
    @Column(name="CONTENTS")
    private String charge;

    /** 담당 콘텐츠 */
    @Column(name="TEL_NO")
    private String chargeTelNumber;
}
