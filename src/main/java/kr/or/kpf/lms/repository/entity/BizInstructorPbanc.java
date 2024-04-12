package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfBizInstructorPbanc;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 강사 모집 공고 해당 사업 공고 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_PBANC")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfBizInstructorPbanc.class)
public class BizInstructorPbanc extends CSEntitySupport implements Serializable {

    /** 강사 모집 공고 해당 사업 공고 일련번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", nullable = false)
    private Long sequenceNo;

    /** 강사 모집 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_NO", nullable = false)
    private String bizInstrNo;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 강사 모집 기간 - 시작일 */
    @Column(name = "BIZ_INSTR_RCPT_BGNG", nullable = false)
    private String bizInstrRcptBgng;

    /** 강사 모집 기간 - 종료일 */
    @Column(name = "BIZ_INSTR_RCPT_END", nullable = false)
    private String bizInstrRcptEnd;

    /** 강사 모집 상태 (0: 마감, 1: 모집, 2: 추가모집) */
    @Column(name = "BIZ_INSTR_PBANC_STTS", nullable = false)
    private Integer bizInstrPbancStts;

    @Transient
    private BizInstructor bizInstructor;

    @Transient
    private BizOrganizationAply bizOrganizationAply;

}
