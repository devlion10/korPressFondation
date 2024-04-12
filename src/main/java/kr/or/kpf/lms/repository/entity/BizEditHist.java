package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_EDIT_HIST")
@Access(value = AccessType.FIELD)
public class BizEditHist extends CSEntitySupport implements Serializable {

    /** 사업 관련 변경 이력 관리 일련 번호 */
    @Id
    @Column(name = "BIZ_EDIT_HIST_NO", nullable = false)
    private String bizEditHistNo;

    /** 사업 관련 변경 이력 관리 해당 일련 번호 */
    @Column(name = "BIZ_EDIT_HIST_TRGT_NO", nullable = false)
    private String bizEditHistTrgtNo;

    /** 사업 관련 변경 이력 관리 유형(0: 삭제, 1: 추가, 2: 수정) */
    @Column(name = "BIZ_EDIT_HIST_TYPE", nullable = false)
    private Integer bizEditHistType;

    /** 사업 관련 변경 이력 관리 이전 내역 */
    @Column(name = "BIZ_EDIT_HIST_BFR", nullable = false)
    private String bizEditHistBfr;

    /** 사업 관련 변경 이력 관리 이후 내역 */
    @Column(name = "BIZ_EDIT_HIST_AFTR", nullable = false)
    private String bizEditHistAftr;

    /** 사업 관련 변경 이력 관리 상태 (변경 요청 승인 시 변경, 0: 미승인, 1: 승인, 2: 반려) */
    @Column(name = "BIZ_EDIT_HIST_STTS", nullable = false)
    private Integer bizEditHistStts;

}
