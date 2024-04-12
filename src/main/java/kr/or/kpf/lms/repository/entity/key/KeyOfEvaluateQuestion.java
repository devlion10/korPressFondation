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
public class KeyOfEvaluateQuestion implements Serializable {

    /** 강의평가 일련 번호 */
    @Column(name="EVAL_SN")
    private String evaluateSerialNo;

    /** 강의평가 질문 일련 번호 */
    @Column(name="QUES_SN")
    private String questionSerialNo;
}