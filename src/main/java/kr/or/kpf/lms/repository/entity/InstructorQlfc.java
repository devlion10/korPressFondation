package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import lombok.*;

import javax.persistence.*;

/**
 * 강사 자격증 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INSTRUCTOR_QLFC")
@Access(value = AccessType.FIELD)
public class InstructorQlfc {
    /** 강사 자격증 일련 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", nullable = false)
    private Long sequenceNo;

    /** 강사 일련 번호 */
    @Column(name = "INSTR_SN", nullable = false)
    private Long instrSerialNo;

    /** 자격증 취득 일자 */
    @Convert(converter= DateYMDToStringConverter.class)
    @Column(name = "INSTR_QLFC_YMD", nullable = false)
    private String instrQlfcYmd;

    /** 자격증 명 */
    @Column(name = "INSTR_QLFC_NM", nullable = false)
    private String instrQlfcNm;

    /** 자격증 번호 */
    @Column(name = "INSTR_QLFC_NO", nullable = false)
    private String instrQlfcNo;

    /** 자격증 발급기관 */
    @Column(name = "INSTR_QLFC_INST", nullable = false)
    private String instrQlfcInst;

}
