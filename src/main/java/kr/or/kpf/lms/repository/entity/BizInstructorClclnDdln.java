package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.DateHMSToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 정산 마감일 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_CLCLN_DDLN")
@Access(value = AccessType.FIELD)
public class BizInstructorClclnDdln extends CSEntitySupport {
    /** 정산 마감일 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_CLCLN_DDLN_NO", nullable = false)
    private String bizInstrClclnDdlnNo;

    /** 정산 마감일 해당 연월 - 연 */
    @Column(name = "BIZ_INSTR_CLCLN_DDLN_YR", nullable = false)
    private Integer bizInstrClclnDdlnYr;

    /** 정산 마감일 해당 연월 - 월 */
    @Column(name = "BIZ_INSTR_CLCLN_DDLN_MM", nullable = false)
    private Integer bizInstrClclnDdlnMm;

    /** 정산 마감일 연월일  */
    @Column(name = "BIZ_INSTR_CLCLN_DDLN_VALUE", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizInstrClclnDdlnValue;

    /** 정산 마감일 시각  */
    @Column(name = "BIZ_INSTR_CLCLN_DDLN_TM", nullable = true)
    @Convert(converter= DateHMSToStringConverter.class)
    private String bizInstrClclnDdlnTm;

}
