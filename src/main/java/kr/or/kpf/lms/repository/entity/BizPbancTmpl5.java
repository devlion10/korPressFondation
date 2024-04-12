package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_TMPL5")
@Access(value = AccessType.FIELD)
public class BizPbancTmpl5 implements Serializable {
    /** 사업 공고 일련 번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="BIZ_PBANC_TMPL5_NO")
    private BigInteger bizPbancTmpl5No;

    /** 사업신청서 항목 순서 */
    @Column(name="BIZ_PBANC_TMPL5_ORDR")
    private Integer bizPbancTmpl5Ordr;

    /** 사업신청서 항목 명 */
    @Column(name="BIZ_PBANC_TMPL5_NAME")
    private String bizPbancTmpl5Name;

    /** 필수입력여부 */
    @Column(name="BIZ_PBANC_TMPL5_NOTNULL")
    private String bizPbancTmpl5Notnull;

    /** 입력 타입 */
    @Column(name="BIZ_PBANC_TMPL5_TYPE")
    private String bizPbancTmpl5Type;

    /** 선택형 선택지 */
    @Column(name="BIZ_PBANC_TMPL5_TYPE_SLCT")
    private String bizPbancTmpl5TypeSlct;

    /** 입력안내문 */
    @Column(name="BIZ_PBANC_TMPL5_ETC")
    private String bizPbancTmpl5Etc;

}