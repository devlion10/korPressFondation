package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 교육 과정 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_MASTER")
@Access(value = AccessType.FIELD)
public class CurriculumMaster extends CSEntitySupport implements Serializable {

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 과정 명 */
    @Column(name="CRCL_NM", nullable = false)
    private String curriculumName;

    /** 교육 과정 카테고리 */
    @Column(name="EDU_CTG", nullable = false)
    private String categoryCode;

    /** 교육 과정 유형 (1: 화상 교육, 2: 집합 교육, 3: 화상 교육 + 집합 교육, 4: 이러닝 교육) */
    @Column(name="EDU_TYPE", nullable = false)
    private String educationType;

    /** 교육 시간(시간단위) */
    @Column(name="EDU_HOUR")
    private Integer educationPerHour;

    /** 교육 시간(분단위) */
    @Column(name="EDU_MINUTE")
    private Integer educationPerMinute;

    /** 담당자 명 */
    @Column(name="PIC_NM")
    private String managerName;

    /** 담당 부서 */
    @Column(name="PIC_DEPT_NM")
    private String managerDepartment;

    /** 집행 타입 */
    @Column(name="ENFRC_TYPE")
    private String enforcementType;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 관리자 승인 여부(N: 미승인, Y: 승인) */
    @Column(name="ADMIN_APL", nullable = false)
    @Convert(converter = BooleanConverter.class)
    private Boolean isAdminApproval;

    /** 교육 목표 */
    @Column(name="EDU_GOAL", nullable = false)
    private String educationGoal;

    /** 교육 대상 (1: 일반인, 2: 언론인, 3: 교원, 4: 학생, 5: 학부모) */
    @Column(name="EDU_TARGET")
    private String educationTarget;

    /** 교육 대상 설명 */
    @Column(name="EDU_TARGET_DES")
    private String educationTargetDescription;

    /** 교육 내용 */
    @Column(name="EDU_CONTENT", nullable = false)
    private String educationContent;

    /** 시험 여부 */
    @Column(name="EXAM_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isExam;

    /** 결과 보고 여부 */
    @Column(name="RESULT_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isResult;

    /** 진도 배점 비율 */
    @Column(name="CMPTN_PRGRS_RATE")
    private Double progressRateOfEnd;

    /** 시험 배점 비율 */
    @Column(name="CMPTN_EXAM_RATE")
    private Double examRateOfEnd;

    /** 과제 배점 비율 */
    @Column(name="CMPTN_ASM_RATE")
    private Double assignmentRateOfEnd;

    /** 완료 진도 점수 */
    @Column(name="CMPTN_PRGRS_SCR")
    private Double progressScoreOfEnd;

    /** 수료 시험 점수 */
    @Column(name="CMPTN_EXAM_SCR")
    private Double examScoreOfEnd;

    /** 수료 과제 점수 */
    @Column(name="CMPTN_ASM_SCR")
    private Double assignmentScoreOfEnd;

    /** 수료 집계 점수 */
    @Column(name="CMPTN_TOT_SCR")
    private Double totalScore;

    /** 차시 진도 기준 */
    @Column(name="PRGR_CMPTN_PRGRS_SCR")
    private Double progressRate;

    /** 시험 시작 가능 학습 경과일 */
    @Column(name="START_EXAM_TERM_CNT")
    private Integer startExamTerm;

    /** 시험 시작 가능 진도율 */
    @Column(name="START_EXAM_RATE")
    private Double startExamRate;

    /** 순차 학습 적용 여부 */
    @Column(name="SEQ_EDU_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isSequentialEducation;

    /** 수료 번호 발급 여부 */
    @Column(name="FNSH_NO_ISSU_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isIssueCertificateOfCompletion;

    /** 재 교육 기한 수 */
    @Column(name="RE_EDU_TERM")
    private Integer reEducationTerm;

    /** 시험 응시 가능 횟수 */
    @Column(name="RE_EXAM_CNT")
    private Integer reExamCount;

    /** 조회 수 */
    @Column(name="VIEW_CNT")
    private BigInteger viewCount;

    /** 신청 승인 유형 코드 (1: 신청 즉시 승인, 2: 관리자 승인) */
    @Column(name="APLY_APPR_TYPE")
    private String applyApprovalType;

    /** 수료 유형 코드 (1: 수료 기준 달성, 2: 교육 종료일 이후) */
    @Column(name="EUD_FNSH_TYPE")
    private String completionType;

    /** 교육 장소 */
    @Column(name="EDU_PLACE")
    private String educationPlace;

    /** CP 코드 */
    @Column(name="CP_CD")
    private String contentsProvider;

    /** 설문 코드 */
    @Column(name="QSTNR_CD")
    private String questionnaireCode;

    /** 신청서 작성 여부 */
    @Column(name="APLY_FORM_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isApplicationForm;

    /** 신청서 파일 경로 */
    @Column(name="APLY_FORM_FILE_PATH")
    private String applicationFormFilePath;

    /** 비고 내용 */
    @Column(name="RMRK_CN")
    private String memo;

    /** 인원수 */
    @Column(name="NOPE")
    private Integer numberOfPeople;

    /** 연계 교육 과정 리스트 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name="CRCL_CD", insertable=false, updatable=false)
    private Set<CurriculumCollaboration> curriculumCollaborationList = new HashSet<>();

    /** 교육 과정 과목 리스트 */
    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="CRCL_CD", insertable=false, updatable=false)
    private Set<CurriculumContents> curriculumContentsList = new HashSet<>();

    /** 교육 과정 시험 리스트 */
    @NotFound(action=NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="CRCL_CD", insertable=false, updatable=false)
    private List<CurriculumExam> curriculumExamList = new ArrayList<>();

    /** 교육 과정 시험 리스트 */
    @NotFound(action=NotFoundAction.IGNORE)
    @OneToMany
    @JoinColumn(name="CRCL_CD", insertable=false, updatable=false)
    private List<CurriculumQuestion> curriculumQuestionList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name="CRCL_CD", insertable=false, updatable=false)
    private List<CurriculumEvaluate> curriculumEvaluateList;

    @OneToMany
    @JoinColumn(name="CRCL_CD", referencedColumnName="CRCL_CD", insertable=false, updatable=false)
    private List<CurriculumReferenceRoom> curriculumReferenceRoomList;
}
