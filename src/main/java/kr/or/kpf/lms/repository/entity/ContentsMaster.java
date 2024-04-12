package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 콘텐츠 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CTS_MASTER")
@Access(value = AccessType.FIELD)
public class ContentsMaster extends CSEntitySupport implements Serializable {

    /** 콘텐츠 코드 */
    @Id
    @Column(name="CTS_CD", nullable = false)
    private String contentsCode;

    /** 콘텐츠 명 */
    @Column(name="CTS_NM", nullable = false)
    private String contentsName;

    /** 콘텐츠 카테고리 코드 */
    @Column(name="CTS_CTGR", nullable = false)
    private String categoryCode;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 기관 코드 */
    @Column(name="ORG_CD", nullable = false)
    private String organizationCode;

    /** 썸네일 파일 경로 */
    @Column(name="THMBN_FILE_PATH")
    private String thumbnailFilePath;

    /** 너비 */
    @Column(name="WDTH")
    private Integer width;

    /** 높이 */
    @Column(name="HGHT")
    private Integer height;

    /** 교육 시간 (분단위) */
    @Column(name="EDU_PER_MINUTE")
    private Integer educationPerMinute;

    /** 비고 내용 */
    @Column(name="RMRK_CN")
    private String memo;

    /** 교육 과정 챕터 리스트 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="CTS_CD", insertable=false, updatable=false)
    private List<ContentsChapter> contentsChapterList;
}
