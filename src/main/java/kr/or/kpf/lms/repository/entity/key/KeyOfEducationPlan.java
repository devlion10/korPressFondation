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
public class KeyOfEducationPlan implements Serializable {

    /** 과정 코드 */
    @Column(name="CRCL_CD")
    private String curriculumCode;

    /** 교육 계획 연도 */
    @Column(name="EDU_PLAN_YR")
    private String yearOfEducationPlan;

    /** 교육 계획 연도 단계 수 */
    @Column(name="EDU_PLAN_YR_STEP")
    private String yearOfEducationPlanStep;

    /** 운영 타입(1: 기수 운영, 2: 상시 운영) */
    @Column(name="OPER_TYPE")
    private String operationType;
}
