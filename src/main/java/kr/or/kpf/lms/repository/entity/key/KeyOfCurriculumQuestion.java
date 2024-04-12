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
public class KeyOfCurriculumQuestion implements Serializable {
    /** 교육과정 코드 */
    @Column(name="CRCL_CD")
    private String curriculumCode;

    /** 시험 문제 일련 번호 */
    @Column(name="QUES_SN")
    private String questionSerialNo;
}