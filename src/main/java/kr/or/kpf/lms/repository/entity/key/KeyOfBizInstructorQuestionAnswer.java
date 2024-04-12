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
public class KeyOfBizInstructorQuestionAnswer implements Serializable {
    /** 강사 지원 문의 답변 일련번호 */
    @Column(name="BIZ_INSTR_QSTN_ANS_NO")
    private String bizInstrQstnAnsNo;

    /** 강사 지원 문의 일련번호 */
    @Column(name="BIZ_INSTR_QSTN_NO")
    private String bizInstrQstnNo;
}
