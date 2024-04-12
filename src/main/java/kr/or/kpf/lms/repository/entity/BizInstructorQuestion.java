package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.question.vo.CreateBizInstructorQuestion;
import kr.or.kpf.lms.biz.business.instructor.question.vo.UpdateBizInstructorQuestion;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 강사 지원 문의 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_QSTN")
@Access(value = AccessType.FIELD)
public class BizInstructorQuestion extends CSEntitySupport {
    /** 강사 지원 문의 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_QSTN_NO", nullable = false)
    private String bizInstrQstnNo;

    /** 사업공고 신청 일련번호 */
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 강사 지원 문의 내용 */
    @Column(name = "BIZ_INSTR_QSTN_CN", nullable = false)
    private String bizInstrQstnCn;

    /** 강사 지원 문의 상태 */
    @Column(name = "BIZ_INSTR_QSTN_STTS", nullable = false)
    private Integer bizInstrQstnStts;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_INSTR_QSTN_NO", insertable=false, updatable=false)
    private List<BizInstructorQuestionAnswer> bizInstructorQuestionAnswers = new ArrayList<>();

    @Transient
    private BizOrganizationAply bizOrganizationAply;

    @Transient
    private BizPbancMaster bizPbancMaster;

}
