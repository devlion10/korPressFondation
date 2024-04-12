package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.repository.entity.key.KeyOfBizInstructorQuestionAnswer;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 강사 지원 문의 답변 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_QSTN_ANS")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfBizInstructorQuestionAnswer.class)
public class BizInstructorQuestionAnswer {
    /** 강사 지원 문의 답변 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_QSTN_ANS_NO", nullable = false)
    private String bizInstrQstnAnsNo;

    /** 강사 지원 문의 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_QSTN_NO", nullable = false)
    private String bizInstrQstnNo;

    /** 강사 지원 문의 답변 내용 */
    @Column(name = "BIZ_INSTR_QSTN_ANS_CN", nullable = false)
    private String bizInstrQstnAnsCn;



}
