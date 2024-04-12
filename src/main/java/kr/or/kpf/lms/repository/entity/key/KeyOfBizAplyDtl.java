package kr.or.kpf.lms.repository.entity.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyOfBizAplyDtl implements Serializable {

    @Column(name = "SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    @Column(name="BIZ_PBANC_TMPL5_NO", nullable = false)
    private BigInteger bizPbancTmpl5No;

}
