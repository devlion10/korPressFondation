package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 사업 공고 기본형 템플릿 2 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL2")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl2 implements Serializable {
    /** 사업 공고 기본형 템플릿 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_TMPL2_NO", nullable = false)
    private String bizPbancTmpl2No;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 운영구분 1학기 사용여부 */
    @Column(name = "BIZ_PBANC_TMPL2_SMST1_USE_YN", nullable = false)
    private Integer bizPbancTmpl2Smst1UseYn;

    /** 운영구분 1학기 최대시간 */
    @Column(name = "BIZ_PBANC_TMPL2_SMST1_MAX_HR", nullable = false)
    private Integer bizPbancTmpl2Smst1MaxHr;

    /** 운영구분 2학기 사용여부 */
    @Column(name = "BIZ_PBANC_TMPL2_SMST2_USE_YN", nullable = false)
    private Integer bizPbancTmpl2Smst2UseYn;

    /** 운영구분 2학기 최대시간 */
    @Column(name = "BIZ_PBANC_TMPL2_SMST2_MAX_HR", nullable = false)
    private Integer bizPbancTmpl2Smst2MaxHr;

    /** 운영구분 연간 사용여부 */
    @Column(name = "BIZ_PBANC_TMPL2_1YEAR_USE_YN", nullable = false)
    private Integer bizPbancTmpl21YearUseYn;

    /** 운영구분 연간 최대시간 */
    @Column(name = "BIZ_PBANC_TMPL2_1YEAR_MAX_HR", nullable = false)
    private Integer bizPbancTmpl21YearMaxHr;

}