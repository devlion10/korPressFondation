package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.survey.ans.vo.CreateBizSurveyAns;
import kr.or.kpf.lms.biz.business.survey.ans.vo.UpdateBizSurveyAns;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigInteger;

/**
 상호평가 - 답변 테이블 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_SURVEY_ANS")
@Access(value = AccessType.FIELD)
public class BizSurveyAns extends CSEntitySupport implements Serializable {
    /** 상호평가 답변 일련번호 */
    @Id
    @Column(name = "BIZ_SURVEY_ANS_NO", nullable = false)
    private String bizSurveyAnsNo;

    /** 상호평가 문항 일련번호 */
    @Column(name = "BIZ_SURVEY_QITEM_NO", nullable = false)
    private String bizSurveyQitemNo;

    /** 상호평가 일련번호 */
    @Column(name = "BIZ_SURVEY_NO", nullable = false)
    private String bizSurveyNo;

    /** 상호평가 대상 일련번호(강사 - 사업 신청, 기관 - 사업 신청 수업계획서) */
    @Column(name = "BIZ_SURVEY_TRGT_NO", nullable = false)
    private String bizSurveyTrgtNo;

    /** 상호평가 문항 답변 */
    @Column(name = "BIZ_SURVEY_ANS_CN", nullable = false)
    private String bizSurveyAnsCn;

    /** 상호평가 기타 문항 답변 */
    @Column(name = "BIZ_SURVEY_ANS_ETC", nullable = true)
    private String bizSurveyAnsEtc;

}