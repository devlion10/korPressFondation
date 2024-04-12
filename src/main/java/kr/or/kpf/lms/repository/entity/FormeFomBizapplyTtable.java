package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.DateToStringConverter;
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
@Table(name = "FORME_FOM_BIZAPPLY_TTABLE")
@Access(value = AccessType.FIELD)
public class FormeFomBizapplyTtable implements Serializable {

    @Id
    @Column(name = "BATT_ID")
    private Long battId;

    @Column(name = "BAIN_ID")
    private Long bainId;

    @Column(name = "BATT_INNING")
    private Integer battInning;

    @Column(name = "BATT_DATE")
    @Convert(converter= DateToStringConverter.class)
    private String battDate;

    @Column(name = "BATT_DWEEK")
    private String battDweek;

    @Column(name = "BATT_START_TIME")
    private String battStartTime;

    @Column(name = "BATT_END_TIME")
    private String battEndTime;

    @Column(name = "BATT_SUM_TIME")
    private Integer battSumTime;


    @Column(name = "BATT_LUSER_ID")
    private String battLuserId;

    @Column(name = "BATT_EDU_TITLE")
    private String battEduTitle;

    @Column(name = "BATT_JOIN_CNT")
    private String battJoinCnt;

    @Column(name = "BATT_PLACE")
    private String battPlace;

    @Column(name = "BATT_CONTENT")
    private String battContent;

    @Column(name = "BATT_REMARKS")
    private String battRemarks;


    @Column(name = "BATT_REG_DTTM")
    @Convert(converter= DateToStringConverter.class)
    private String battRegDttm;

    @Column(name = "BATT_REG_ID")
    private String battRegId;

    @Column(name = "BATT_REG_IP")
    private String battRegIp;

    @Column(name = "BATT_UPDT_DTTM")
    @Convert(converter= DateToStringConverter.class)
    private String battUpdtDttm;

    @Column(name = "BATT_UPDT_ID")
    private String battUpdtId;

    @Column(name = "BATT_UPDT_IP")
    private String battUpdtIp;

    @Column(name = "BATT_DEL_DTTM")
    @Convert(converter= DateToStringConverter.class)
    private String battDelDttm;

    @Column(name = "BATT_DEL_ID")
    private String battDelId;

    @Column(name = "BATT_DEL_IP")
    private String battDelIp;

    @Column(name = "BATT_UNTACT_YN")
    private String battUntactYn;

    @Column(name = "BATT_LUSER_NM")
    private String battLuserNm;
}
