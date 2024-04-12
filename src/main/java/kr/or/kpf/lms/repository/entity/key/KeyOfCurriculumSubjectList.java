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
public class KeyOfCurriculumSubjectList implements Serializable {

    /** 과정 코드 */
    @Column(name="CRS_CD")
    private String curriculumCode;

    /** 과목 코드 */
    @Column(name="SBJC_CD")
    private String subjectCode;
}
