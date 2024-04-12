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
public class KeyOfCurriculumApplicationLecture implements Serializable {

    /** 교육 신청 일련 번호 */
    @Column(name="EDU_APLY_SN")
    private String applicationNo;

    /** 과정 코드 */
    @Column(name="CRCL_CD")
    private String curriculumCode;

    /** 강의 코드 */
    @Column(name="LECTURE_CD")
    private String lectureCode;

    /** 로그인 아이디 */
    @Column(name = "LGN_ID")
    private String userId;
}
