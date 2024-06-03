package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 교육 계획 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EDU_PLAN_LIST")
@Access(value = AccessType.FIELD)
public class EducationPlan extends CSEntitySupport implements Serializable {

    /** 교육 계획 코드 */
    @Id
    @Column(name="PLAN_CD", nullable = false)
    private String educationPlanCode;

    /** 과정 코드 */
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 연수 과정 번호 */
    @Column(name="PRGR_NO")
    private String programNumber;

    /** 교육 계획 연도 */
    @Column(name="EDU_PLAN_YR", nullable = false)
    private String yearOfEducationPlan;

    /** 교육 계획 연도 단계 수 */
    @Column(name="EDU_PLAN_YR_STEP", nullable = false)
    private String yearOfEducationPlanStep;

    /** 운영 타입(1: 기수 운영, 2: 상시 운영) */
    @Column(name="OPER_TYPE", nullable = false)
    private String operationType;

    /** 교육 운영 지사 */
    @Column(name="PROVINCE")
    private String province;

    /** 썸네일 파일 경로 */
    @Column(name="THMBN_FILE_PATH")
    private String thumbnailFilePath;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 상단 고정 여부 */
    @Column(name="TOP_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isTop;

    /** 상시 교육 기한 수 */
    @Column(name="ATTM_EDU_TERM_CNT")
    private Integer alwaysEducationTerm;

    /** 신청 시작 일시 */
    @Column(name="APLY_BGNG_DT")
    @Convert(converter= DateToStringConverter.class)
    private String applyBeginDateTime;

    /** 신청 종료 일시 */
    @Column(name="APLY_END_DT")
    @Convert(converter=DateToStringConverter.class)
    private String applyEndDateTime;

    /** 취소 시작 일시 */
    @Column(name="RTRCN_BGNG_DT")
    @Convert(converter=DateToStringConverter.class)
    private String cancelBeginDateTime;

    /** 취소 종료 일시 */
    @Column(name="RTRCN_END_DT")
    @Convert(converter=DateToStringConverter.class)
    private String cancelEndDateTime;

    /** 운영 시작 일시 */
    @Column(name="OPER_BGNG_DT")
    @Convert(converter=DateToStringConverter.class)
    private String operationBeginDateTime;

    /** 운영 종료 일시 */
    @Column(name="OPER_END_DT")
    @Convert(converter=DateToStringConverter.class)
    private String operationEndDateTime;

    /** 교육 강사 */
    @Column(name="LECTURER")
    private String lecturer;

    /** 강의 명 */
    @Column(name="LECTURE_TITLE")
    private String lectureName;

    /** 교육 장소 */
    @Column(name="EDU_PLACE")
    private String educationPlace;

    /** 정원 */
    @Column(name="NOPE")
    private Integer numberOfPeople;

    /** 병행교육시 정원(집합) */
    @Column(name="NOPE_PRL")
    private Integer numberOfPeopleParallel;

    /** 숙박 제공 여부 */
    @Column(name="ACCOM_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isAccommodation;

    /** 복습제공 여부 */
    @Column(name="REVIEW_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isReview;

    /** 복습 기간(월단위) */
    @Column(name="REVIEW_TERM")
    private Integer availableReviewTerm;

    /** 복습 영상 경로 */
    @Column(name="REVIEW_PATH")
    private String reviewPath;

    /** 화상 강의 진행 */
    @Column(name="VIDEO_LECTURE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isVideoLecture;

    /** 화상 강의 경로 */
    @Column(name="VIDEO_LECTURE_PATH")
    private String videoLecturePath;

    /** 담당자(아이디) */
    @Column(name="EDU_PLAN_PIC")
    private String eduPlanPic;

    /** 담당자 연락처 */
    @Column(name="PIC_TEL")
    private String picTel;

    /** EducationPlan 기준, 교육과정 정보 */
    @OneToOne
    @JoinColumn(name="CRCL_CD", insertable=false, updatable=false)
    private CurriculumMaster curriculumMaster;

    /** 일반 교육 강의 마스터 */
    @OneToMany
    @JoinColumn(name="PLAN_CD", insertable=false, updatable=false)
    List<LectureMaster> lectureMasterList;

    /** 첨부파일 */
    @Transient
    List<FileMaster> fileMasters;

    /** 교육 과정 신청 가능 여부(1: 신청 불가, 2: 신청 마감, 3: 신청 가능, 4: 신청 완료) */
    @Transient
    private String availableApplicationType;

    /** 조회 기준 신청 인원 */
    @Transient
    private Integer currentNumberOfPeople;

    /** 병행강의 조회 기준 신청 인원 */
    @Transient
    private Integer currentNumberOfPeopleParallel;

    /** 담당자 정보 */
    @Transient
    private String adminInfo;
}
