package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 이동거리 기준단가 항목 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_DIST_CRTR_AMT_ITEM")
@Access(value = AccessType.FIELD)
public class BizInstructorDistCrtrAmtItem {
    /** 이동거리 기준단가 항목 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_ITEM_NO", nullable = false)
    private String bizInstrDistCrtrAmtItemNo;

    /** 이동거리 기준단가 일련번호 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_NO", nullable = false)
    private String bizInstrDistCrtrAmtNo;

    /** 이동거리 기준단가 항목 최대값 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_ITEM_UP", nullable = false)
    private Integer bizInstrDistCrtrAmtItemUp;

    /** 이동거리 기준단가 항목 최소값 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_ITEM_LOWR", nullable = false)
    private Integer bizInstrDistCrtrAmtItemLowr;

    /** 이동거리 기준단가 항목 배율 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_ITEM_RATE", nullable = false)
    private Double bizInstrDistCrtrAmtItemRate;

    /** 이동거리 기준단가 가격 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_ITEM_VALUE", nullable = false)
    private Integer bizInstrDistCrtrAmtItemValue;

}
