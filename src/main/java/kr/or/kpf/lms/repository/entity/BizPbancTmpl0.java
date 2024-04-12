package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 사업 공고 기본형 템플릿 0 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL0")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl0 implements Serializable {
    /** 사업 공고 기본형 템플릿 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_TMPL0_NO", nullable = false)
    private String bizPbancTmpl0No;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 사업 공고 기본형 템플릿 추가 입력 필드 여부(0: 없음, 1: 있음) */
    @Column(name = "BIZ_PBANC_TMPL0_ITEM_YN", nullable = false)
    private Integer bizPbancTmpl0ItemYn;

    @Transient
    private List<BizPbancTmpl0Item> bizPbancTmpl0Items;

    @Transient
    private List<Integer> bizPbancTmpl0Trgts;

}