package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfContentsChapter;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 콘텐츠 챕터 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CTS_CHAP_LIST")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfContentsChapter.class)
public class ContentsChapter extends CSEntitySupport implements Serializable {

    /** 콘텐츠 코드 */
    @Id
    @Column(name="CTS_CD", nullable = false)
    private String contentsCode;

    /** 챕터 코드 */
    @Id
    @Column(name="CHAP_CD", nullable = false)
    private String chapterCode;

    /** 챕터 정렬 번호 */
    @Column(name="CHAP_SORT_NO", nullable=false)
    private Integer sortNo;

    /** 챕터 마스터 정보 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CHAP_CD", insertable=false, updatable=false)
    private ChapterMaster chapterMaster;
}