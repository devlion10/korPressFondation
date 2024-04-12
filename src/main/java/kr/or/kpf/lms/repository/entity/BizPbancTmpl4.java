package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 사업 공고 기본형 템플릿 4 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL4")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl4 implements Serializable {
    /** 사업 공고 기본형 템플릿 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_TMPL4_NO", nullable = false)
    private String bizPbancTmpl4No;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 자유학기제 사용여부 */
    @Column(name = "BIZ_PBANC_TMPL4_FRDM_SMST_USE_YN", nullable = false)
    private Integer bizPbancTmpl4FrdmSmstUseYn;

    /** 자유학기제 최대시간 */
    @Column(name = "BIZ_PBANC_TMPL4_FRDM_SMST_MAX_HR", nullable = false)
    private Integer bizPbancTmpl4FrdmSmstMaxHr;

}