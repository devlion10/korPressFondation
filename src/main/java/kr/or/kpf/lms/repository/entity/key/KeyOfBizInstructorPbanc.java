package kr.or.kpf.lms.repository.entity.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyOfBizInstructorPbanc implements Serializable {
    /** 강사 모집 사업 공고 일련번호 */
    @Column(name="SEQ_NO")
    private Long sequenceNo;

    /** 강사 모집 일련번호 */
    @Column(name="BIZ_INSTR_NO")
    private String bizInstrNo;
}
