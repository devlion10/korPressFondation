package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 사업 공고 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_MASTER")
@Access(value = AccessType.FIELD)
public class BizPbancMaster extends CSEntitySupport implements Serializable {
    /** 사업 공고 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 사업 공고 템플릿 유형 */
    @Column(name = "BIZ_PBANC_TYPE", nullable = false)
    private Integer bizPbancType;

    /** 사업 구분 */
    @Column(name = "BIZ_PBANC_CTGR", nullable = false)
    private Integer bizPbancCtgr;

    /** 사업 구분 (Main View Type - 1: 사회미디어, 2: 학교미디어)  */
    @Column(name = "BIZ_PBANC_CTGR_SUB", nullable = false)
    private Integer bizPbancCtgrSub;

    /** 사업명 */
    @Column(name = "BIZ_PBANC_NM", nullable = false)
    private String bizPbancNm;

    /** 최대 시간 */
    @Column(name = "BIZ_PBANC_MAX_TM", nullable = false)
    private Integer bizPbancMaxTm;

    /** 사업년도 */
    @Column(name = "BIZ_PBANC_YR", nullable = false)
    private Integer bizPbancYr;

    /** 사업 공고 차수 */
    @Column(name = "BIZ_PBANC_RND", nullable = false)
    private Integer bizPbancRnd;

    /** 최대 사업참여기관 */
    @Column(name = "BIZ_PBANC_MAX_INST", nullable = false)
    private Integer bizPbancMaxInst;

    /** 사업 선정방식 */
    @Column(name = "BIZ_PBANC_SLCTN_METH", nullable = false)
    private Integer bizPbancSlctnMeth;

    /** 강사 배정방식 */
    @Column(name = "BIZ_PBANC_INSTR_SLCTN_METH", nullable = false)
    private Integer bizPbancInstrSlctnMeth;

    /** 사업 상태 (0: 모집 중, 1: 모집 마감) */
    @Column(name = "BIZ_PBANC_STTS", nullable = false)
    private Integer bizPbancStts;

    /** 사업 접수기간 - 시작일 */
    @Column(name = "BIZ_PBANC_RCPT_BGNG", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRcptBgng;

    /** 사업 접수기간 - 종료일 */
    @Column(name = "BIZ_PBANC_RCPT_END", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRcptEnd;

    /** 사업 지원기간 - 시작일 */
    @Column(name = "BIZ_PBANC_SPRT_BGNG", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancSprtBgng;

    /** 사업 지원기간 - 종료일 */
    @Column(name = "BIZ_PBANC_SPRT_END", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancSprtEnd;

    /** 사업공고 결과발표일(없을 시 미정) */
    @Column(name = "BIZ_PBANC_RSLT_YMD", nullable = false)
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRsltYmd;

    /** 사업내용 */
    @Column(name = "BIZ_PBANC_CN", nullable = false)
    private String bizPbancCn;

    /** 사업공고 신문 신청 가능 여부(없음/0: N, 1: Y) */
    @Column(name = "BIZ_PBANC_PEPR_YN", nullable = true)
    private Integer bizPbancPeprYn;

    /** 사업 공고 담당자 연락처 */
    @Column(name = "BIZ_PBANC_PIC_TEL", nullable = false)
    private String bizPbancPicTel;

    /** 상위 노출 여부 */
    @Schema(description="상위 노출 여부")
    @Column(name="TOP_YN")
    private String isTop;

    /** 신규 여부 */
    @Transient
    @Builder.Default
    private Boolean isNew = false;

}