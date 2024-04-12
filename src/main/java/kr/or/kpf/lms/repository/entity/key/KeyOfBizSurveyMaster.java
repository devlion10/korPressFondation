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
public class KeyOfBizSurveyMaster implements Serializable {

    /** 상호평가 일련번호 */
    @Column(name="SEQ_NO")
    private Long sequenceNo;

    /** 상호평가 평가지 일련 번호 */
    @Column(name = "BIZ_SURVEY_NO", nullable = false)
    private String bizSurveyNo;

}
