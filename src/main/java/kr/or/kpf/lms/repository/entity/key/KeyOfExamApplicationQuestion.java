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
public class KeyOfExamApplicationQuestion implements Serializable {

    /** 교육 신청 일련 번호 */
    @Column(name="EDU_APLY_SN")
    private String applicationNo;

    /** 과정 코드 */
    @Column(name="CRCL_CD")
    private String curriculumCode;

    /** 시험 일련 번호 */
    @Column(name="EXAM_SN")
    private String examSerialNo;

    /** 시험 문제 일련 번호 */
    @Column(name="QUES_SN")
    private String questionSerialNo;

    /** 로그인 아이디 */
    @Column(name="LGN_ID")
    private String userId;
}
