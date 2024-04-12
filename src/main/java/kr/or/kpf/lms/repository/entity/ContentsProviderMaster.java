package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.config.security.vo.RoleGroup;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CTS_PROVIDER_MASTER")
@Access(value = AccessType.FIELD)
public class ContentsProviderMaster extends CSEntitySupport implements Serializable {

    /** CP 코드 */
    @Id
    @Column(name="CP_CD")
    private String contentsProviderCode;

    /** 기관 명 */
    @Column(name="INST_NM")
    private String organizationName;

    /** 권한 그룹 */
    @Column(name="AUTH_CD")
    private RoleGroup roleGroup;

    /** 담당자 명 */
    @Column(name="PIC_NM")
    private String managerName;

    /** 담당자 휴대전화번호 */
    @Column(name="PIC_MBL_TELNO")
    private String managerPhoneNo;

    /** 담당자 이메일 주소 */
    @Column(name="PIC_EML_ADDR")
    private String managerEmail;

    /** 담당자 부서 명 */
    @Column(name="PIC_DEPT_NM")
    private String managerDepartment;

    /** 담당자 직위 명 */
    @Column(name="PIC_JBPS_NM")
    private String managerPosition;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 기관 사업자등록번호 */
    @Column(name="INST_BRNO")
    private String businessLicenseNumber;
}
