package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 상호평가 - 문항 테이블 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_SURVEY_QITEM")
@Access(value = AccessType.FIELD)
public class BizSurveyQitem extends CSEntitySupport implements Serializable {
    /** 상호평가 문항 일련 번호 */
    @Id
    @Column(name = "BIZ_SURVEY_QITEM_NO", nullable = false)
    private String bizSurveyQitemNo;

    /** 상호평가 문항 구분  */
    @Column(name = "BIZ_SURVEY_QITEM_CTGR", nullable = false)
    private Integer bizSurveyQitemCategory;

    /** 상호평가 문항 유형  */
    @Column(name = "BIZ_SURVEY_QITEM_TYPE", nullable = false)
    private Integer bizSurveyQitemType;

    /** 상호평가 문항명 */
    @Column(name = "BIZ_SURVEY_QITEM_NM", nullable = false)
    private String bizSurveyQitemName;

    /** 상호평가 문항 내용 */
    @Column(name = "BIZ_SURVEY_QITEM_CN", nullable = false)
    private String bizSurveyQitemContent;

    /** 상호평가 문항 기타 항목 여부 */
    @Column(name = "BIZ_SURVEY_QITEM_ETC", nullable = false)
    private Integer bizSurveyQitemEtc;

    /** 상호평가 문항 상태 */
    @Column(name = "BIZ_SURVEY_QITEM_STTS", nullable = false)
    private String bizSurveyQitemStatus;

    /** 상호평가 문항 - 선택문항 일련 번호 리스트 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="BIZ_SURVEY_QITEM_NO", insertable=false, updatable=false)
    private List<BizSurveyQitemItem> bizSurveyQitemItems = new ArrayList<>();
}