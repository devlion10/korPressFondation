package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 매체 정보 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ORGANIZATION_INFO_MDIA")
@Access(value = AccessType.FIELD)
public class OrganizationInfoMedia extends CSEntitySupport implements Serializable {

    /** 매체코드 */
    @Id
    @Column(name = "MDIA_CD", nullable = false)
    private String mediaCode;

    /** 매체사코드 */
    @Column(name = "ORG_CD")
    private String organizationCode;

    /** 매체사명 */
    @Column(name="MDIA_NM", nullable = false)
    private String mediaName;

    /** 매체 대분류 */
    @Column(name="MDIA_CLS_CD1")
    private String mediaClsName1;

    /** 매체 중분류 */
    @Column(name="MDIA_CLS_CD2")
    private String mediaClsName2;

    /** 지역코드 */
    @Column(name="AREA_CLS")
    private String mediaArea;

    /** 전사매체사용여부 */
    @Schema(description="전사매체사용여부")
    @Column(name="ABM_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean mediaAbmYN;

    /** 전사매체 코드 */
    @Column(name="ABM_MDIA_CD")
    private String mediaAbmCode;

    /** 삭제 여부 */
    @Schema(description="사용 여부")
    @Column(name="DEL_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isDeleted;

    /** 사용 여부 */
    @Schema(description="사용 여부")
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 소속 기관 */
    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ORG_CD", referencedColumnName = "ORG_CD", insertable=false, updatable=false)
    private OrganizationInfo organizationInfo;
}
