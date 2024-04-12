package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 기관 정보 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ORGANIZATION_INFO")
@Access(value = AccessType.FIELD)
public class OrganizationInfo extends CSEntitySupport implements Serializable {

    /** 소속 기관 코드 */
    @Id
    @Column(name = "ORG_CD", nullable = false)
    private String organizationCode;

    /** 소속 기관 명 */
    @Column(name="ORG_NAME")
    private String organizationName;

    /** 소속 기관 대표자 명 */
    @Column(name="ORG_RPRT_NM")
    private String organizationRepresentativeName;

    /** 소속 기관 서브 기관 명 */
    @Column(name="ORG_SUB_NAME")
    private String organizationSubName;

    /** 소속 기관 사업자 등록번호 */
    @Column(name="BIZ_LICENSE_NO")
    private String bizLicenseNumber;

    /** 기관 타입 (1: 매체사, 2: 기관, 3: 학교) */
    @Column(name="ORG_TYPE")
    private String organizationType;

    /** 소속 기관 우편번호 */
    @Column(name="ORG_ZIP_CD")
    private String organizationZipCode;

    /** 소속 기관 주소1 */
    @Column(name="ORG_ADDRESS1")
    private String organizationAddress1;

    /** 소속 기관 주소2 */
    @Column(name="ORG_ADDRESS2")
    private String organizationAddress2;

    /** 소속 기관 연락처 */
    @Column(name="ORG_TEL_NO")
    private String organizationTelNumber;

    /** 소속 기관 팩스 번호 */
    @Column(name="ORG_FAX_NO")
    private String organizationFaxNumber;

    /** 소속 기관 홈페이지 주소 */
    @Column(name="ORG_HOMEPAGE")
    private String organizationHomepage;

    /** 첨부 파일 경로 */
    @Schema(description="첨부 파일 경로")
    @Column(name="ATCH_FILE_PATH")
    private String attachFilePath;

    /** 첨부 파일 사이즈 */
    @Schema(description="첨부 파일 사이즈")
    @Column(name="FILE_SIZE")
    private Long fileSize;

    /** 사용 여부 */
    @Schema(description="첨부 파일 사이즈")
    @Column(name="USE_YN", nullable = false)
    private String isUsable;
}
