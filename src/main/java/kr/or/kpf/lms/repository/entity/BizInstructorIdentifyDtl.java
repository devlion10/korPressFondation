package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.CreateBizInstructorIdentifyDtl;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.UpdateBizInstructorIdentifyDtl;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 강의확인서 강의시간표 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_IDNTY_DTL")
@Access(value = AccessType.FIELD)
public class BizInstructorIdentifyDtl {
    /** 강의확인서 강의시간표 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_IDNTY_DTL_NO", nullable = false)
    private String bizInstrIdntyDtlNo;

    /** 강의확인서 일련번호 */
    @Column(name = "BIZ_INSTR_IDNTY_NO", nullable = false)
    private String bizInstrIdntyNo;

    /** 사업 공고 신청 일련번호 */
    @Column(name = "BIZ_ORG_APLY_DTL_NO", nullable = false)
    private String bizOrgAplyDtlNo;

    /** 강의확인서 강의시간표 인원수 */
    @Column(name = "BIZ_INSTR_IDNTY_DTL_NOPE", nullable = true)
    private Integer bizInstrIdntyDtlNope;

    /** 강의확인서 강의시간표 주제 */
    @Column(name = "BIZ_INSTR_IDNTY_DTL_TPIC", nullable = true)
    private String bizInstrIdntyDtlTpic;

    /** 강의확인서 강의시간표 내용 */
    @Column(name = "BIZ_INSTR_IDNTY_DTL_CN", nullable = true)
    private String bizInstrIdntyDtlCn;

    /** 화상강의 포함 유무 */
    @Column(name = "VDO_LCT_YN", nullable = true)
    private Integer vdoLctYn;

    /** 강의확인서 강사료 거리반영 계산수식 */
    @Column(name = "BIZ_INSTR_IDNTY_FORMULA", nullable = false)
    private String bizInstrIdntyFormula;

    /** 강의확인서 강사료 강의일 중복 */
    @Column(name = "BIZ_INSTR_IDNTY_ETC", nullable = false)
    private String bizInstrIdntyEtc;


    @Transient
    private BizOrganizationAplyDtl bizOrganizationAplyDtl;
}
