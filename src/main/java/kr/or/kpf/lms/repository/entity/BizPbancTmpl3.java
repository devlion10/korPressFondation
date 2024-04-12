package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 사업 공고 기본형 템플릿 3 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL3")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl3 implements Serializable {
    /** 사업 공고 기본형 템플릿 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_TMPL3_NO", nullable = false)
    private String bizPbancTmpl3No;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 자유학기제 사용여부 */
    @Column(name = "BIZ_PBANC_TMPL3_FRDM_SMST_USE_YN", nullable = false)
    private Integer bizPbancTmpl3FrdmSmstUseYn;

    /** 자유학기제 최대시간 */
    @Column(name = "BIZ_PBANC_TMPL3_FRDM_SMST_MAX_HR", nullable = false)
    private Integer bizPbancTmpl3FrdmSmstMaxHr;

    /** 자유학년제 사용여부 */
    @Column(name = "BIZ_PBANC_TMPL3_FRDM_GRD_USE_YN", nullable = false)
    private Integer bizPbancTmpl3FrdmGrdUseYn;

    /** 자유학년제 최대시간 */
    @Column(name = "BIZ_PBANC_TMPL3_FRDM_GRD_MAX_HR", nullable = false)
    private Integer bizPbancTmpl3FrdmGrdMaxHr;

}