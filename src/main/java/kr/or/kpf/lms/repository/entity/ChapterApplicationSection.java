package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfChapterApplicationSection;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 챕터 신청 절 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CHAP_APLY_SECTION")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfChapterApplicationSection.class)
public class ChapterApplicationSection extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 교육 신청 일련 번호 */
    @Id
    @Column(name="EDU_APLY_SN", nullable=false)
    private String applicationNo;

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable=false)
    private String curriculumCode;

    /** 콘텐츠 코드 */
    @Id
    @Column(name="CTS_CD", nullable=false)
    private String contentsCode;

    /** 챕터 코드 */
    @Id
    @Column(name="CHAP_CD", nullable=false)
    private String chapterCode;

    /** 절 코드 */
    @Id
    @Column(name="SEC_CD", nullable = false)
    private String sectionCode;

    /** 로그인 아이디 */
    @Id
    @Column(name="LGN_ID", nullable=false)
    private String userId;

    /** 정렬 번호 */
    @Column(name="SEC_SORT_NO", nullable=false)
    private Integer sortNo;

    /** 현재 진도율 */
    @Column(name="PRGRS_RATE", nullable=false)
    private Double progressRate;

    /** 완료 여부 */
    @Column(name="CMPTN_YN", nullable=false)
    @Convert(converter= BooleanConverter.class)
    private Boolean isComplete;

    /** 절 교육 시작 일시 */
    @Column(name="BGNG_DT")
    @Convert(converter= DateToStringConverter.class)
    private String beginDateTime;

    /** 완료 일시 */
    @Column(name="CMPTN_DT")
    @Convert(converter= DateToStringConverter.class)
    private String completeDateTime;

    @OneToOne
    @JoinColumn(name="SEC_CD", insertable = false, updatable = false)
    private ReferenceSectionMaster sectionMaster;
}
