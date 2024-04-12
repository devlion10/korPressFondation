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
public class KeyOfQuestionItem implements Serializable {

    /** 일련 번호 */
    @Column(name="QUES_SN")
    private String questionSerialNo;

    /** 시험 문제 문항 일련 번호 */
    @Column(name="ITEM_SN")
    private String questionItemSerialNo;
}
