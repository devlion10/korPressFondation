package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.DateToStringConverter;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "FORME_BIZLECINFO")
@Access(value = AccessType.FIELD)
public class FormeBizlecinfo implements Serializable {
    @Id
    @Column(name = "BLCI_ID")
    private Long blciId;

    @Column(name = "BNIN_ID")
    private Long bninId;

    @Column(name = "BAIN_ID")
    private Long bainId;

    @Column(name = "BLCI_USER_ID")
    private String blciUserId;

    @Column(name = "BLCI_SPEND")
    private String blciSpend;

    @Column(name = "BLCI_APRVL_DTTM")
    @Convert(converter= DateToStringConverter.class)
    private String blciAprvlDttm;

    @Column(name = "BLCI_USER_NM")
    private String blciUserNm;

    @Column(name = "BAIN_YEAR")
    private String bainYear;

    @Column(name = "BNIN_TITLE")
    private String bninTitle;

    @Column(name = "BNIN_DEGREE")
    private int bninDegree;

    @Column(name = "BNIN_USER_ID")
    private String bainUserId;

    @Column(name = "BAIN_INST_NM")
    private String bainInstNm;

    @Column(name = "BAIN_EDU_SDATE")
    private String bainEduSdate;

    @Column(name = "BAIN_EDU_EDATE")
    private String bainEduEdate;

    @Column(name = "UNTACT_CNT")
    private String untactCnt;

    @Column(name = "UNTACT_NO_CNT")
    private int untactNoCnt;

    @Column(name = "UNTACT_TOTAL_CNT")
    private int untactTotalCnt;

    @Column(name = "BAIN_EDU_TIME")
    private float bainEduTime;

    @Column(name = "BLCI_AREA")
    private String blciArea;

    @Column(name = "BLCI_AREA_NM")
    private String blciAreaNm;

    @Column(name = "BLCI_YYMM")
    private String blciYymm;

    @Column(name = "BLCI_STATUS")
    private String blciStatus;

    @Column(name = "BLCI_REG_DTTM")
    @Convert(converter= DateToStringConverter.class)
    private String blciRegDttm;

    @Column(name = "BLAS_ID")
    private int blasId;

    @Column(name = "BATT_SUM_TIME")
    private int battSumTime;

    @Column(name = "BATT_TIME_COUNT")
    private int battTimeCount;

    @Column(name = "BATT_DATE_COUNT")
    private int battDateCount;

    @Column(name = "BLAS_DISTANCE")
    private float blasDistance;

    @Column(name = "BCDV_ID")
    private int bcdvId;

    @Column(name = "BAIN_CHARGE_NM")
    private String bainChargeNm;

    @Column(name = "BAIN_CHARGE_HP")
    private String bainChargeHp;

    @Transient
    private List<FormeFomBizapplyTtable> formeFomBizapplyTtables = new ArrayList<>();
}
