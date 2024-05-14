package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 웹 회원 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude={"password", "passwordInfo"})
@EntityListeners(AuditingEntityListener.class)
@Table(name = "LMS_USER")
@Access(value = AccessType.FIELD)
public class LmsUser extends CSEntitySupport implements Serializable {

	private static final long serialVersionUID = -6489185686355767298L;

    /** 웹 회원 일련번호 */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "USER_SN")
    private Long userSerialNo;

    /** 로그인 아이디 */
    @Id
    @Column(name = "LGN_ID", nullable = false)
    private String userId;

    /** 비밀번호 */
    @Column(name = "PASSWD", nullable = false)
    private String password;

    /** 간편 로그인 */
    @Column(name= "EASY_LGN_CD")
    private String easyLogin;

    /** 웹 회원 명 */
    @Column(name = "USER_NM", nullable = false)
    private String userName;

    /** 웹 회원 생년월일 */
    @Column(name = "USER_BRDT", nullable = false)
    private String birthDay;

    /** 권한 그룹 */
    @Column(name = "AUTH_GROUP", nullable = false)
    private String roleGroup;

    @Column(name="BIZ_AUTH")
    private String businessAuthority;

    /** 웹 회원 전화번호 */
    @Column(name = "USER_TELNO")
    private String phone;

    /** SMS 수신 동의 여부 */
    @Column(name = "SMS_RCPTN_AGRE_YN", nullable = false)
    @Convert(converter = BooleanConverter.class)
    private Boolean isSmsAgree;

    /** 웹 회원 이메일 주소 */
    @Column(name = "USER_EML_ADDR")
    private String email;

    /** EMAIL 수신 동의 여부 */
    @Column(name = "EML_RCPTN_AGRE_YN", nullable = false)
    @Convert(converter = BooleanConverter.class)
    private Boolean isEmailAgree;

    /** 성별 코드 */
    @Column(name = "GNDR_CD")
    private String gender;

    /** 인증 DN 값 */
    @Column(name = "CERT_DN_VALUE")
    private String certValue;

    /** DI 값 */
    @Column(name = "DI_VALUE")
    private String di;

    /** CI 값 */
    @Column(name = "CI_VALUE")
    private String ci;

    /** 비밀번호 변경 일자 */
    @Column(name = "PASSWD_CHG_YMD")
    private String passwordChangeDate;

    /** 최근 로그인 일시 */
    @Convert(converter= DateToStringConverter.class)
    @Column(name = "LAST_LOGIN")
    private String lastLoginDateTime;

    /** 휴면 계정 전환 일자 */
    @Column(name = "DMC_YMD")
    private String dormancyDate;

    /** 탈퇴 일자 */
    @Column(name = "WHDWL_YMD")
    private String withDrawDate;

    /** 잠금 수 */
    @Column(name = "LOCK_CNT")
    private Integer lockCount;

    /** 잠금 일시 */
    @Column(name = "LOCK_DT")
    private LocalDateTime lockDateTime;

    /** 잠금 여부 */
    @Column(name = "LOCK_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isLock;

    /** 회원 상태 (1: 정상, 2: 휴면, 3: 잠금, 4: 탈퇴) */
    @Column(name = "USER_STATE", nullable = false)
    private String state;

    /** 소속 기관 코드 */
    @Column(name = "ORG_CD")
    private String organizationCode;

    /** 소속 매체 코드 */
    @Column(name = "MDIA_CD")
    private String mediaCode;

    /** 소속 부서 */
    @Column(name = "DEPARTMENT")
    private String department;

    /** 직급 */
    @Column(name = "USER_RANK")
    private String rank;

    /** 직책 */
    @Column(name = "POSITION")
    private String position;

    /** 재직 증명서 및 첨부 파일 경로 */
    @Column(name = "ATCH_FILE_PATH")
    private String attachFilePath;

    /** 재직 증명서 및 첨부 파일 사이즈 */
    @Column(name = "FILE_SIZE")
    private Long fileSize;

    /** 보호자 성명 */
    @Column(name = "PARENT_NM")
    private String parentName;

    /** 보호자 생년월일 */
    @Column(name = "PARENT_BRDT")
    private String parentBirthDay;

    /** 보호자 성별 (1: 남성, 2: 여성) */
    @Column(name = "PARENT_GNDR_CD")
    private String parentGender;

    /** 보호자 전화번호 */
    @Column(name = "NOK_TELNO")
    private String nokPhone;

    /** 보호자 인증 */
    @Column(name = "NOK_DI_VALUE")
    private String nokDI;

    /** 마이그레이션 대상 플래그(Y: 대상, N: 미대상, C: 마이그레이션 완료)*/
    @Column(name = "MG_YN")
    private String migrationFlag;

    /** 언론일 경우 회원 승인 여부 */
    @Column(name = "APPRO_YN")
    private String approFlag;

    /** 언론일 경우 회원 미 승인 사유*/
    @Column(name = "APPRO_YN_RSN_CD")
    private String approYnRsnCd;

    /** 집 우편번호 */
    @Column(name = "USER_ZIPCD")
    private String userZipCode;

    /** 집 주소 */
    @Column(name = "USER_ADDRESS1")
    private String userAddress1;

    /** 집 주소 상세 */
    @Column(name = "USER_ADDRESS2")
    private String userAddress2;

    /** 튜터 여부 */
    @Column(name = "TUTOR_YN")
    private String tutorYn;

    /** 개인 나이스 번호(교원) */
    @Column(name = "PRSN_NICE_NO")
    private String personalNeisNo;

    @Transient
    private String passwordInfo;

    /** 소속 기관 */
    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ORG_CD", referencedColumnName = "ORG_CD", insertable=false, updatable=false)
    private OrganizationInfo organizationInfo;

    /** 매체 기관 */
    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="MDIA_CD", referencedColumnName = "MDIA_CD", insertable=false, updatable=false)
    private OrganizationInfoMedia mediaInfo;

}