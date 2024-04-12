package kr.or.kpf.lms.biz.mypage.classguide.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.CreateArchiveClassGuide;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.UpdateArchiveClassGuide;
import kr.or.kpf.lms.biz.mypage.classguide.vo.CreateClassGuide;
import kr.or.kpf.lms.biz.mypage.classguide.vo.UpdateClassGuide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Schema(name="ClassGuideApiRequestVO", description="수업지도안 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassGuideApiRequestVO {

    /** 수업 지도안 코드 */
    @Schema(description="수업 지도안 코드", required=true, example="")
    @NotEmpty(groups={CreateArchiveClassGuide.class, UpdateArchiveClassGuide.class}, message="수업 지도안 코드는 필수 입니다.")
    private String classGuideCode;

    /** 수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) ) */
    @Schema(description="수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) )", required=true, example="0")
    @NotEmpty(groups={CreateArchiveClassGuide.class, UpdateArchiveClassGuide.class}, message="수업 지도안 타입 ( 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) )은 필수 입니다.")
    private String classGuideType;

    /** 차수 */
    @Schema(description="차수", required=false)
    private Integer classGuideRound;

    /** 대상 */
    @Schema(description="대상", required=true, example="")
    @NotEmpty(groups={CreateArchiveClassGuide.class, UpdateArchiveClassGuide.class}, message="대상은 필수 입니다.")
    private String target;

    /** 대상 - 학년 */
    @Schema(description="대상 - 학년", required=false, example="")
    private String targetGrade;

    /** 활동명/주제 */
    @Schema(description="활동명/주제", required=true, example="")
    @NotEmpty(groups={CreateArchiveClassGuide.class, UpdateArchiveClassGuide.class}, message="활동명/주제는 필수 입니다.")
    private String title;

    /** 학습 영역/이렇게 활동해요 */
    @Schema(description="학습 영역/이렇게 활동해요", required=false, example="")
    private String learningArea;

    /** 학습 목표 */
    @Schema(description="학습 목표", required=false, example="")
    private String learningGoal;

    /** 학습 자료 */
    @Schema(description="학습 자료", required=false, example="")
    private String learningMaterial;

    /** 학습 도움말 */
    @Schema(description="학습 도움말", required=false, example="")
    private String learningHelp;

    /** E-NIE 여부 */
    @Schema(description="E-NIE 여부", required=false, example="N")
    private String eNIEYn;

    /** 해쉬태그 */
    @Schema(description="해쉬태그", required=false, example="")
    private String hashTag;

    /** 관련 과목 */
    @Schema(description="관련 과목", required=false, example="")
    private String referenceSubject;

    /** 문제 파악_동기유발 */
    @Schema(description="문제 파악_동기유발", required=false, example="")
    private String realizeMotivation;

    /** 문제 파악_동기유발_유의점/준비물 */
    @Schema(description="문제 파악_동기유발_유의점/준비물", required=false, example="")
    private String realizeMotivationNote;

    /** 문제 파악_문제확인/목표제시 */
    @Schema(description="문제 파악_문제확인/목표제시", required=false, example="")
    private String realizePractice;

    /** 문제 파악_문제확인/목표제시_유의점/준비물 */
    @Schema(description="문제 파악_문제확인/목표제시_유의점/준비물", required=false, example="")
    private String realizePracticeNote;

    /** 문제 해결_유의점/준비물 */
    @Schema(description="문제 해결_유의점/준비물", required=false, example="")
    private String solutionNote;

    /** 문제 해결_기사읽고 생각 넓히기 */
    @Schema(description="문제 해결_기사읽고 생각 넓히기", required=false, example="")
    private String solutionThink;

    /** 정리_마무리 */
    @Schema(description="정리_마무리", required=false, example="")
    private String arrangementSummary;

    /** 정리_유의점/준비물 */
    @Schema(description="정리_유의점/준비물", required=false, example="")
    private String arrangementNote;

    /** 정리_도움말 */
    @Schema(description="정리_도움말", required=false, example="")
    private String arrangementHelp;

    /** 수업지도안/길라잡이 파일 경로 */
    @Schema(description="수업지도안/길라잡이 파일 경로", required=false, example="")
    private String classGuideFilePath;

    /** 수업지도안/길라잡이 파일 크기 */
    @Schema(description="수업지도안/길라잡이 파일 크기", required=false, example="")
    private Long classGuideFileSize;

    /** 활동지 파일 경로 */
    @Schema(description="활동지 파일 경로", required=false, example="")
    private String activitiesFilePath;

    /** 활동지 파일 크기 */
    @Schema(description="활동지 파일 크기", required=false, example="")
    private Long activitiesFileSize;

    /** 예시답안 파일 경로 */
    @Schema(description="예시답안 파일 경로", required=false, example="")
    private String exampleAnswerFilePath;

    /** 예시답안 파일 크기 */
    @Schema(description="예시답안 파일 크기", required=false, example="")
    private Long exampleAnswerFileSize;

    /** 10분 NIE 파일 경로 */
    @Schema(description="10분 NIE 파일 경로", required=false, example="")
    private String nieFilePath;

    /** 10분 NIE 파일 크기 */
    @Schema(description="10분 NIE 파일 크기", required=false, example="")
    private Long nieFileSize;

    /** 수업지도안 신청 상태(0: 임시저장, 1: 신청(접수)) */
    @Schema(description="수업지도안 신청 상태(0: 임시저장, 1: 신청(접수))", required=true, example="0")
    @NotEmpty(groups={CreateArchiveClassGuide.class, UpdateArchiveClassGuide.class}, message="수업지도안 신청 상태(0: 임시저장, 1: 신청(접수))는 필수 입니다.")
    private String classGuideApplyStatus;

    /** 수업지도안 승인 상태(0: 미승인, 1: 승인) */
    @Schema(description="수업지도안 승인 상태(0: 미승인, 1: 승인)", required=true, example="0")
    @NotEmpty(groups={CreateArchiveClassGuide.class, UpdateArchiveClassGuide.class}, message="수업지도안 승인 상태(0: 미승인, 1: 승인)는 필수 입니다.")
    private String classGuideApprovalStatus;
}
