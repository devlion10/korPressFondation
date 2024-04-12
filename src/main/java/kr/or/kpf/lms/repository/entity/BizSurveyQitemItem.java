package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.repository.entity.key.KeyOfBizSurveyQitemItem;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 상호평가 - 문항 - 선택문항 테이블 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_SURVEY_QITEM_ITEM")
@Access(value = AccessType.FIELD)
@IdClass(KeyOfBizSurveyQitemItem.class)
public class BizSurveyQitemItem implements Serializable {
    /** 상호평가 문항 - 선택문항 일련 번호 */
    @Id
    @Column(name = "BIZ_SURVEY_QITEM_ITEM_NO", nullable = false)
    private String bizSurveyQitemItemNo;

    /** 상호평가 문항 일련 번호 */
    @Id
    @Column(name = "BIZ_SURVEY_QITEM_NO", nullable = false)
    private String bizSurveyQitemNo;

    /** 상호평가 문항 - 선택문항 내용  */
    @Column(name = "BIZ_SURVEY_QITEM_ITEM_CN", nullable = false)
    private String bizSurveyQitemItemCn;

    /** 상호평가 문항 - 선택문항 점수  */
    @Column(name = "BIZ_SURVEY_QITEM_ITEM_SCR", nullable = true)
    private Integer bizSurveyQitemItemScr;

}