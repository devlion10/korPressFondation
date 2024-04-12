package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfBizAplyDtl;
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
@Table(name = "BIZ_APLY_DTL")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfBizAplyDtl.class)
public class BizAplyDtl {
    /** 사업 공고 신청 - 언론인/기본형 일련 번호 */
    @Id
    @Column(name = "SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    @Id
    @Column(name="BIZ_PBANC_TMPL5_NO", nullable = false)
    private BigInteger bizPbancTmpl5No;


    @Column(name = "BIZ_APLY_DTL_CONT", nullable = true)
    private String bizAplyDtlCont;

    /** 첨부 파일 일련번호 */
    @Column(name="FILE_SN", nullable = true)
    private Long fileSn;

    /*@OneToOne
    @JoinColumn(name="FILE_SN", insertable=false, updatable=false)
    private FileMaster fileMaster;*/
    @OneToOne
    @JoinColumn(name="FILE_SN", insertable=false, updatable=false)
    private BizAplyDtlFile bizAplyDtlFile;

    @Transient
    private BizPbancTmpl5 bizPbancTmpl5;


}
