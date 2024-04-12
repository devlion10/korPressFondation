package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 사업 공고 기본형 템플릿 0 Item Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL0_ITEM")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl0Item implements Serializable {
    /** 사업 공고 기본형 템플릿 0 Item 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_TMPL0_ITEM_NO", nullable = false)
    private String bizPbancTmpl0ItemNo;

    /** 사업 공고 기본형 템플릿 일련번호 */
    @Column(name = "BIZ_PBANC_TMPL0_NO", nullable = false)
    private String bizPbancTmpl0No;

    /** 사업 공고 기본형 템플릿 추가 입력 필드명 */
    @Column(name = "BIZ_PBANC_TMPL0_ITEM_NM", nullable = false)
    private String bizPbancTmpl0ItemNm;

    /** 사업 공고 기본형 템플릿 추가 입력 필드 내용 */
    @Column(name = "BIZ_PBANC_TMPL0_ITEM_CN", nullable = false)
    private String bizPbancTmpl0ItemCn;


}