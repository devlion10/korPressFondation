package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 사업 공고 기본형 템플릿 0 Trgt Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL0_TRGT")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl0Trgt implements Serializable {
    /** 사업 공고 기본형 템플릿 0 Trgt 일련번호 */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "BIZ_PBANC_TMPL0_TRGT_NO", nullable = false)
    private Integer bizPbancTmpl0TrgtNo;

    /** 사업 공고 기본형 템플릿 일련번호 */
    @Column(name = "BIZ_PBANC_TMPL0_NO", nullable = false)
    private String bizPbancTmpl0No;

    /** 사업 공고 기본형 템플릿 대상 목록 코드 */
    @Column(name = "BIZ_PBANC_TMPL0_TRGT_CD", nullable = false)
    private Integer bizPbancTmpl0TrgtCd;

}