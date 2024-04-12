package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 사업 공고 기본형 템플릿 1 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL1")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl1 implements Serializable {
    /** 사업 공고 기본형 템플릿 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_TMPL1_NO", nullable = false)
    private String bizPbancTmpl1No;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    @Transient
    private List<Integer> bizPbancTmpl1Trgts;

}