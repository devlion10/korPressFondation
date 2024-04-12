package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 수업 지도안 교과 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLASS_SUBJECT")
@Access(value = AccessType.FIELD)
public class ClassSubject extends CSEntitySupport implements Serializable {

    /** 개별 코드 */
    @Id
    @Column(name="INDIV_CD")
    private String individualCode;

    /** 상위 개별 코드 */
    @Column(name="UP_INDIV_CD")
    private String upIndividualCode;

    /** 내용 */
    @Column(name="CN")
    private String content;

    /** 깊이 */
    @Column(name="DPTH")
    private Integer depth;

    /** 순서 */
    @Column(name="ORDR")
    private Integer order;

    /** 설명 */
    @Column(name="DSCR")
    private String description;

    @Transient
    private String codeInfo;
}
