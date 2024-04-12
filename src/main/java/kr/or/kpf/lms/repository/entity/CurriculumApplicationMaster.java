package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumApplicationMaster;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 교육 과정 신청 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_APLY_MASTER")
@Access(value = AccessType.FIELD)
public class CurriculumApplicationMaster extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 교육 신청 일련 번호 */
    @Column(name="EDU_APLY_SN")
    private String applicationNo;

    /** 교육 계획 코드 */
    @Column(name="PLAN_CD", nullable=false)
    private String educationPlanCode;

    /** 과정 코드 */
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 연수 이수 번호 */
    @Column(name="PRGR_COM_NO")
    private String programCompleteNumber;

    /** 유저 ID */
    @Column(name="LGN_ID", nullable = false)
    private String userId;

    /** 신청서 파일 경로 */
    @Column(name="APLY_DOC_PATH")
    private String applicationFilePath;

    /** 선택 교육 유형 */
    @Column(name="SET_EDU_TYPE")
    private String setEducationType;

    /** 숙박 여부 */
    @Column(name="ACCOM_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isAccommodation;

    /** 관리자 승인 여부 */
    @Column(name="ADM_APL_STATE", nullable = false)
    private String adminApprovalState;

    /** 교육 상태(1: 신청 심사 중, 2: 교육 진행 중, 3: 교육 완료) */
    @Column(name="EDU_STATE", nullable = false)
    private String educationState;

    /** 현재 진도율 */
    @Column(name="PRGRS_RATE")
    private Double progressRate;

    /** 현재 진도 점수 */
    @Column(name="PRGRS_SCR")
    private Integer progressScore;

    /** 현재 시험 점수 */
    @Column(name="EXAM_SCR")
    private Integer examScore;

    /** 현재 과제 점수 */
    @Column(name="ASM_SCR")
    private Integer assignmentScore;

    /** 과제 파일 경로 */
    @Column(name="ASM_FILE_PATH")
    private String assignmentPath;

    /** 과제 제출 일시 */
    @Column(name="ASM_FILE_SUB_DT")
    @Convert(converter=DateToStringConverter.class)
    private String assigmentFileSubmitDateTime;

    /** 과제 파일 사이즈 */
    @Column(name="ASM_FILE_SIZE")
    private Long assigmentFileSize;

    /** 교육 시작 일시 */
    @Column(name="OPER_BGNG_DT")
    @Convert(converter=DateToStringConverter.class)
    private String operationBeginDateTime;

    /** 교육 종료 일시 */
    @Column(name="OPER_END_DT")
    @Convert(converter=DateToStringConverter.class)
    private String operationEndDateTime;

    /** 완료 여부(수료 여부) */
    @Column(name="COMPLETE_YN", nullable=false)
    private String isComplete;

    /** 완료 일시(수료 일시) */
    @Column(name = "COMPLETE_DT")
    @Convert(converter= DateToStringConverter.class)
    private String completeDateTime;

    /** 복습 가능 일시 */
    @Transient
    private String reviewDateTime;

    @NotFound(action=NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="PLAN_CD", referencedColumnName = "PLAN_CD", insertable=false, updatable=false)
    private EducationPlan educationPlan;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "EDU_APLY_SN", referencedColumnName = "EDU_APLY_SN", insertable = false, updatable = false),
            @JoinColumn(name = "CRCL_CD", referencedColumnName = "CRCL_CD", insertable = false, updatable = false),
            @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", insertable = false, updatable = false)
    })
    private List<CurriculumApplicationContents> curriculumApplicationContentsList;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "EDU_APLY_SN", referencedColumnName = "EDU_APLY_SN", insertable = false, updatable = false),
            @JoinColumn(name = "CRCL_CD", referencedColumnName = "CRCL_CD", insertable = false, updatable = false),
            @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", insertable = false, updatable = false)
    })
    private List<CurriculumApplicationExam> curriculumApplicationExamList;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "EDU_APLY_SN", referencedColumnName = "EDU_APLY_SN", insertable = false, updatable = false),
            @JoinColumn(name = "CRCL_CD", referencedColumnName = "CRCL_CD", insertable = false, updatable = false),
            @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", insertable = false, updatable = false)
    })
    private List<CurriculumApplicationEvaluate> curriculumApplicationEvaluateList;

    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "EDU_APLY_SN", referencedColumnName = "EDU_APLY_SN", insertable = false, updatable = false),
            @JoinColumn(name = "CRCL_CD", referencedColumnName = "CRCL_CD", insertable = false, updatable = false),
            @JoinColumn(name = "LGN_ID", referencedColumnName = "LGN_ID", insertable = false, updatable = false)
    })
    private List<CurriculumApplicationLecture> curriculumApplicationLectureList;

    @ManyToOne
    @JoinColumn(name="LGN_ID", updatable = false, insertable = false)
    private LmsUser lmsUser;

    @PostPersist
    public void generateApplicationNo() {
        String prefix = "APLY";
        String paddedSequence = String.format("%0" + 7 + "d", this.sequenceNo);
        this.applicationNo = prefix + paddedSequence;
    }
}