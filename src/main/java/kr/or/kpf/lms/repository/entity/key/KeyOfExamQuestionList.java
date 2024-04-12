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
public class KeyOfExamQuestionList implements Serializable {

    /** 과정 코드 */
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 시험 문제 일련 번호 */
    @Column(name="EXAM_QUES_SN", nullable = false)
    private String questionSerialNo;
}
