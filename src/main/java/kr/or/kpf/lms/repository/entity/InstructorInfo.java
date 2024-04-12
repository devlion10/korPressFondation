package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
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
 * 강사 정보 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "INSTRUCTOR_INFO")
@Access(value = AccessType.FIELD)
public class InstructorInfo extends CSEntitySupport implements Serializable {

    /** 강사 정보 일련 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "INSTR_SN", nullable = false)
    private Long instrSerialNo;

    /** 웹 회원 아이디 */
    @Column(name = "USER_ID")
    private String userId;

    /** 강사 구분 */
    @Column(name = "INSTR_CTGR", nullable = false)
    private String instrCategory;

    /** 강사 명 */
    @Column(name = "INSTR_NM", nullable = false)
    private String instrNm;

    /** 강사 생년월일 */
    @Convert(converter= DateYMDToStringConverter.class)
    @Column(name = "INSTR_BRDT", nullable = false)
    private String instrBrdt;

    /** 성별 코드 */
    @Column(name = "GNDR_CD")
    private String gender;

    /** 강사 연락처(핸드폰 번호) */
    @Column(name = "INSTR_TEL", nullable = false)
    private String instrTel;

    /** 강사 이메일 */
    @Column(name = "INSTR_EML", nullable = false)
    private String instrEml;

    /** 강사 상태 (0: 미사용, 1: 사용) */
    @Column(name = "INSTR_STTS", nullable = false)
    private Integer instrStts;

    /** 강사 사진 파일 주소 */
    @Column(name = "INSTR_PCTR", nullable = false)
    private String instrPctr;

    /** 강사 사진 파일 크기 */
    @Column(name = "INSTR_PCTR_SIZE", nullable = false)
    private Long instrPctrSize;

    /** 강사 서명/도장 파일 주소 */
    @Column(name = "INSTR_SGNTR", nullable = false)
    private String instrSgntr;

    /** 강사 서명/도장 파일 크기 */
    @Column(name = "INSTR_SGNTR_SIZE", nullable = false)
    private Long instrSgntrSize;

    /** 강사 자택 우편번호 */
    @Column(name = "INSTR_ZIP_CD", nullable = false)
    private String instrZipCd;

    /** 강사 자택 주소1 */
    @Column(name = "INSTR_ADDR1", nullable = false)
    private String instrAddr1;

    /** 강사 자택 주소2 */
    @Column(name = "INSTR_ADDR2")
    private String instrAddr2;

    /** 강사 강의가능 지역1(0: 선택 없음) */
    @Column(name = "INSTR_LCT_RGN1", nullable = false)
    private String instrLctRgn1;

    /** 강사 강의가능 지역2(0: 선택 없음) */
    @Column(name = "INSTR_LCT_RGN2", nullable = false)
    private String instrLctRgn2;

    /** 강사 최종학력 연도 */
    @Column(name = "INSTR_ACBG_YR", nullable = false)
    private Integer instrAcbgYr;

    /** 강사 최종학력 학교명 */
    @Column(name = "INSTR_ACBG_SCHL_NM", nullable = false)
    private String instrAcbgSchlNm;

    /** 강사 최종학력 전공 */
    @Column(name = "INSTR_ACBG_MJR", nullable = false)
    private String instrAcbgMjr;

    /** 강사 최종학력 학위 */
    @Column(name = "INSTR_ACBG_DGR", nullable = false)
    private String instrAcbgDgr;

    /** 강사 강의 주요 내용 */
    @Column(name = "INSTR_MAIN_CN", nullable = false)
    private String instrMainCn;

    /** 강사 강의분야 대분류 */
    @Column(name = "INSTR_RELM_FRST", nullable = false)
    private String instrRelmFrst;

    /** 강사 강의분야 소분류 */
    @Column(name = "INSTR_RELM_LAST", nullable = false)
    private String instrRelmLast;

    /** 강사 은행 */
    @Column(name = "INSTR_BANK", nullable = false)
    private String instrBank;

    /** 강사 계좌번호 */
    @Column(name = "INSTR_ACTNO", nullable = false)
    private String instrActno;

    /** 강사 소속명 */
    @Column(name = "ORG_NAME", nullable = true)
    private String orgName;

    /** 강사 부서/직급 */
    @Column(name = "DEPARTMENT", nullable = true)
    private String department;

    /** 강사 주요 이력 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="INSTR_SN", insertable=false, updatable=false)
    private List<InstructorHist> instructorHists = new ArrayList<>();

    /** 강사 자격증 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="INSTR_SN", insertable=false, updatable=false)
    private List<InstructorQlfc> instructorQlfcs = new ArrayList<>();

    @Transient
    private List<BizInstructorAply> bizInstructorAplies = new ArrayList<>();
}