package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 정산 대상 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_CLCLN_TRGT")
@Access(value = AccessType.FIELD)
public class BizInstructorClclnTrgt extends CSEntitySupport {
    /** 정산 대상 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_CLCL_TRGT_NO", nullable = false)
    private String bizInstrClclnTrgtNo;

    /** 정산 마감일 일련번호 */
    @Column(name = "BIZ_INSTR_CLCLN_DDLN_NO", nullable = false)
    private String bizInstrClclnDdlnNo;

    /** 사업 공고 신청 일련번호 */
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 강사 아이디 */
    @Column(name = "INSTR_ID", nullable = false)
    private String instrId;

    /** 정산 대상 상태(0: 미승인, 1: 승인(지출 접수), 2: 지출 완료)  */
    @Column(name = "BIZ_INSTR_CLCL_TRGT_STTS", nullable = false)
    private Integer bizInstrClclnTrgtStts;

}
