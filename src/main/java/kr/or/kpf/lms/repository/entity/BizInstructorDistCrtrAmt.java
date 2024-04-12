package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 이동거리 기준단가 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_DIST_CRTR_AMT")
@Access(value = AccessType.FIELD)
public class BizInstructorDistCrtrAmt extends CSEntitySupport {
    /** 이동거리 기준단가 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_NO", nullable = false)
    private String bizInstrDistCrtrAmtNo;

    /** 이동거리 기준단가 연도 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_YR", nullable = false)
    private Integer bizInstrDistCrtrAmtYr;

    /** 이동거리 기준단가 값 */
    @Column(name = "BIZ_INSTR_DIST_CRTR_AMT_VALUE", nullable = false)
    private Integer bizInstrDistCrtrAmtValue;

    @NotFound(action= NotFoundAction.IGNORE)
    @OrderBy(value = "bizInstrDistCrtrAmtItemLowr ASC")
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_INSTR_DIST_CRTR_AMT_NO", insertable=false, updatable=false)
    private List<BizInstructorDistCrtrAmtItem> bizInstructorDistCrtrAmtItems = new ArrayList<>();

}
