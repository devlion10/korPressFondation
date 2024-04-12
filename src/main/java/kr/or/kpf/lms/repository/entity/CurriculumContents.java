package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumContents;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 교육 과정 콘텐츠 목록 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_CTS_LIST")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfCurriculumContents.class)
public class CurriculumContents extends CSEntitySupport implements Serializable {

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 콘텐츠 코드 */
    @Id
    @Column(name="CTS_CD", nullable = false)
    private String contentsCode;

    /** 콘텐츠 정렬 번호 */
    @Column(name="CTS_SORT_NO", nullable=false)
    private Integer sortNo;

    /** 교육 콘텐츠 마스터 정보 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="CTS_CD", insertable=false, updatable=false)
    private ContentsMaster contentsMaster;
}
