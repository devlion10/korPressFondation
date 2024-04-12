package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfBizSurveyMaster;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 상호평가 테이블 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_SURVEY_MASTER")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfBizSurveyMaster.class)
public class BizSurveyMaster implements Serializable {
    /** 상호평가 마스터 일련 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO", nullable = false)
    private Long sequenceNo;

    /** 상호평가 평가지 일련 번호 */
    @Id
    @Column(name = "BIZ_SURVEY_NO", nullable = false)
    private String bizSurveyNo;

    /** 상호평가 문항 일련 번호 */
    @Column(name = "BIZ_SURVEY_QITEM_NO", nullable = false)
    private String bizSurveyQitemNo;

}