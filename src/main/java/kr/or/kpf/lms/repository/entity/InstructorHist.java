package kr.or.kpf.lms.repository.entity;

import lombok.*;

import javax.persistence.*;

/**
 * 강사 주요 이력 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INSTRUCTOR_HIST")
@Access(value = AccessType.FIELD)
public class InstructorHist {
    /** 강사 주요 이력 일련 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", nullable = false)
    private Long sequenceNo;

    /** 강사 일련 번호 */
    @Column(name = "INSTR_SN", nullable = false)
    private Long instrSerialNo;

    /** 주요 이력 연도 */
    @Column(name = "INSTR_HIST_YR", nullable = false)
    private String instrHistYr;

    /** 주요 이력 기관명 */
    @Column(name = "INSTR_HISR_ORG_NM", nullable = false)
    private String instrOrgNm;

    /** 주요 이력 내용 */
    @Column(name = "INSTR_HISR_CN")
    private String instrCn;

}
