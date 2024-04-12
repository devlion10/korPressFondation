package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 수업 지도안 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLASS_GUIDE")
@Access(value = AccessType.FIELD)
public class ClassGuide extends CSEntitySupport implements Serializable {

    /** 수업 지도안 코드 */
    @Id
    @Column(name="GUI_CD", nullable=false)
    private String classGuideCode;

    /** 수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) ) */
    @Column(name="GUI_TYPE", nullable=false)
    private String classGuideType;

    /** 차수 */
    @Column(name="GUI_ROUND")
    private Integer classGuideRound;

    /** 대상 */
    @Column(name="TARGET", nullable=false)
    private String target;

    /** 대상 - 학년 */
    @Column(name="TARGET_GRADE", nullable=true)
    private String targetGrade;

    /** 활동명/주제 */
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 학습 영역/이렇게 활동해요 */
    @Column(name="LEARN_AREA")
    private String learningArea;

    /** 학습 목표 */
    @Column(name="LEARN_GOAL")
    private String learningGoal;

    /** 학습 자료 */
    @Column(name="LEARN_MTL")
    private String learningMaterial;

    /** 학습 도움말 */
    @Column(name="LEARN_HELP")
    private String learningHelp;

    /** E-NIE 여부 */
    @Column(name="ENIE_YN")
    private String eNIEYn;

    /** 해쉬태그 */
    @Column(name="HASH_TAG")
    private String hashTag;

    /** 관련 과목 */
    @Column(name="RFRN_SBJT")
    private String referenceSubject;

    /** 문제 파악_동기유발 */
    @Column(name="REALIZE_MOTIVATION")
    private String realizeMotivation;

    /** 문제 파악_동기유발_유의점/준비물 */
    @Column(name="REALIZE_MOTIVATION_NOTE")
    private String realizeMotivationNote;

    /** 문제 파악_문제확인/목표제시 */
    @Column(name="REALIZE_PRACTICE")
    private String realizePractice;

    /** 문제 파악_문제확인/목표제시_유의점/준비물 */
    @Column(name="REALIZE_PRACTICE_NOTE")
    private String realizePracticeNote;

    /** 문제 해결_유의점/준비물 */
    @Column(name="SOLUTION_NOTE")
    private String solutionNote;

    /** 문제 해결_기사읽고 생각 넓히기 */
    @Column(name="SOLUTION_THINK")
    private String solutionThink;

    /** 정리_마무리 */
    @Column(name="ARRANGEMENT_SUMMARY")
    private String arrangementSummary;

    /** 정리_유의점/준비물 */
    @Column(name="ARRANGEMENT_NOTE")
    private String arrangementNote;

    /** 정리_도움말 */
    @Column(name="ARRANGEMENT_HELP")
    private String arrangementHelp;

    /** 수업지도안/길라잡이 파일 경로 */
    @Column(name="CLASS_GUIDE_FILE_PATH")
    private String classGuideFilePath;

    /** 수업지도안/길라잡이 파일 크기 */
    @Column(name="CLASS_GUIDE_FILE_SIZE")
    private Long classGuideFileSize;

    /** 활동지 파일 경로 */
    @Column(name="ACTIVITIES_FILE_PATH")
    private String activitiesFilePath;

    /** 활동지 파일 크기 */
    @Column(name="ACTIVITIES_FILE_SIZE")
    private Long activitiesFileSize;

    /** 예시답안 파일 경로 */
    @Column(name="EXAMPLE_ANSWER_FILE_PATH")
    private String exampleAnswerFilePath;

    /** 예시답안 파일 크기 */
    @Column(name="EXAMPLE_ANSWER_FILE_SIZE")
    private Long exampleAnswerFileSize;

    /** 10분 NIE 파일 경로 */
    @Column(name="NIE_FILE_PATH")
    private String nieFilePath;

    /** 10분 NIE 파일 크기 */
    @Column(name="NIE_FILE_SIZE")
    private Long nieFileSize;

    /** 수업지도안 신청 상태(0: 임시저장, 1: 신청(접수)) */
    @Column(name="GUI_APLY_STTS")
    private String classGuideApplyStatus;

    /** 수업지도안 승인 상태(0: 미승인, 1: 승인) */
    @Column(name="GUI_APRV_STTS")
    private String classGuideApprovalStatus;

    /** 수업지도안 조회수 */
    @Column(name="VIEW_CNT")
    private BigInteger viewCount;

    @Transient
    private String userName;

    @Transient
    private List<ClassSubject> classSubjectList;

    /** 수업지도안/길라잡이 첨부파일 */
    @Transient
    private List<ClassGuideFile> guideFileList;

    /** 활동지 첨부파일 */
    @Transient
    private List<ClassGuideFile> activityFileList;

    /** 예시답안 첨부파일 */
    @Transient
    private List<ClassGuideFile> answerFileList;

    /** 10분 NIE 첨부파일 */
    @Transient
    private List<ClassGuideFile> nieFileList;
}
