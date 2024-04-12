package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 절 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "SECTION_MASTER")
@Access(value = AccessType.FIELD)
public class ReferenceSectionMaster extends CSEntitySupport implements Serializable {

    /** 절 코드 */
    @Id
    @Column(name="SEC_CD", nullable = false)
    private String sectionCode;

    /** 챕터 코드 */
    @Column(name="CHAP_CD", nullable = false)
    private String chapterCode;

    /** 챕터 명 */
    @Column(name="SEC_NM", nullable = false)
    private String sectionName;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 영상/페이지 링크 */
    @Column(name="LINK")
    private String link;

    /** 교육 시간 (분단위) */
    @Column(name="EDU_PER_MINUTE")
    private Integer educationPerMinute;

    /** 절 정렬 번호 */
    @Column(name="SEC_SORT_NO")
    private Integer sortNo;

    /** 비고 내용 */
    @Column(name="RMRK_CN")
    private String memo;
}
