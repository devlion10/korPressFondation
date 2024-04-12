package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 사업 공고 미디어교육 평생교실 템플릿 대상 아이템 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL1_TRGT")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl1Trgt implements Serializable {
    /** 사업 공고 미디어교육 평생교실 템플릿 Trgt 일련번호 */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "BIZ_PBANC_TMPL1_TRGT_NO", nullable = false)
    private Integer bizPbancTmpl1TrgtNo;

    /** 사업 공고 미디어교육 평생교실 템플릿 일련번호 */
    @Column(name = "BIZ_PBANC_TMPL1_NO", nullable = false)
    private String bizPbancTmpl1No;

    /** 사업 공고 미디어교육 평생교실 템플릿 대상 코드 */
    @Column(name = "BIZ_PBANC_TMPL1_TRGT_CD", nullable = false)
    private Integer bizPbancTmpl1TrgtCd;

}