package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 챕터 마스터 테이블 Entity (연결 테이블 제거)
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CHAP_MASTER")
@Access(value = AccessType.FIELD)
public class ReferenceChapterMaster extends CSEntitySupport implements Serializable {

    /** 챕터 코드 */
    @Id
    @Column(name="CHAP_CD", nullable = false)
    private String chapterCode;

    /** 챕터 명 */
    @Column(name="CHAP_NM", nullable = false)
    private String chapterName;

    /** 챕터 제목 */
    @Column(name="CHAP_TITLE", nullable = false)
    private String chapterTitle;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 교육 강사 */
    @Column(name="LECTURER", nullable = false)
    private String lecturer;

    /** 교육 장소 */
    @Column(name="EDU_PLACE", nullable = false)
    private String educationPlace;

    /** 비고 내용 */
    @Column(name="RMRK_CN")
    private String memo;
}
