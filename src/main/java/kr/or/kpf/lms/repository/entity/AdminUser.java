package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 어드민 사용자 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude={"password", "passwordInfo"})
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ADMIN_USER")
@Access(value = AccessType.FIELD)
public class AdminUser extends CSEntitySupport implements Serializable {

	private static final long serialVersionUID = -6489185686355767298L;

    /** 사용자 일련 번호 */
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

    /** 사용자 명 */
    @Column(name = "USER_NM", nullable = false)
    private String userName;

    /** 권한 그룹 */
    @Column(name = "AUTH_GROUP", nullable = false)
    private String roleGroup;

    /** 허용 IP */
    @Column(name = "APRV_IP_ADDR", nullable = false)
    private String availableIp;

    /** 사용자 전화번호 */
    @Column(name = "USER_TELNO")
    private String phone;

    /** 사용자 이메일 주소 */
    @Column(name = "USER_EML_ADDR")
    private String email;

    /** 잠금 여부 */
    @Column(name = "LOCK_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isLock;

    /** 잠금 일시 */
    @Column(name = "LOCK_DT")
    private LocalDateTime lockDateTime;

    /** 비밀번호 오류 횟수 */
    @Column(name = "PASSWD_FAIL_CNT")
    private Integer passwordFailureCount;

    /** 비밀번호 변경 일자 */
    @Column(name = "PASSWD_CHG_YMD")
    private String passwordChangeDate;

    /** 관리자 상태 (0: 미사용, 1: 사용) */
    @Column(name = "ADMIN_STATE", nullable = false)
    private String state;

    /** 소속 부서 */
    @Column(name = "DEPARTMENT")
    private String department;

    /** 직급 */
    @Column(name = "USER_RANK")
    private String rank;

    /** 직책 */
    @Column(name = "POSITION")
    private String position;

    @Transient
    private String passwordInfo;
}