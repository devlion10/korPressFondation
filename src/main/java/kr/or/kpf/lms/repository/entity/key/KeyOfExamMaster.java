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
public class KeyOfExamMaster implements Serializable {

    /** 시험 일련 번호 */
    @Column(name="EXAM_SN")
    private Integer examSerialNo;

    /** 과정 코드 */
    @Column(name="CRCL_CD")
    private String curriculumCode;
}
