package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 수업 지도안 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLASS_GUIDE_SUBJECT")
@Access(value = AccessType.FIELD)
public class ClassGuideSubject implements Serializable {

    /** 일련번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", nullable = false)
    private Long sequenceNo;

    /** 수업 지도안 코드 */
    @Column(name="GUI_CD", nullable=false)
    private String classGuideCode;

    /** 과목 코드 */
    @Column(name="INDIV_CD")
    private String individualCode;
}
