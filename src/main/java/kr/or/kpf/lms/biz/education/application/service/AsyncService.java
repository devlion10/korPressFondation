package kr.or.kpf.lms.biz.education.application.service;

import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsyncService extends CSServiceSupport {

    private final CurriculumApplicationMasterRepository curriculumApplicationMasterRepository;
    private final CurriculumApplicationContentsRepository curriculumApplicationContentsRepository;
    private final ContentsApplicationChapterRepository contentsApplicationChapterRepository;
    private final ChapterApplicationSectionRepository chapterApplicationSectionRepository;
    private final CurriculumApplicationExamRepository curriculumApplicationExamRepository;
    private final CurriculumQuestionRepository curriculumQuestionRepository;
    private final CurriculumApplicationLectureRepository curriculumApplicationLectureRepository;
    private final ExamApplicationQuestionRepository examApplicationQuestionRepository;

    @Async
    @Transactional(noRollbackFor = {Exception.class}, propagation = Propagation.REQUIRES_NEW)
    public CompletableFuture<CurriculumApplicationMaster> generateContentsAndExam(EducationPlan educationPlan, CurriculumApplicationMaster curriculumApplicationMaster) {
        /** 회원 ID */
        String userId = curriculumApplicationMaster.getUserId();
        /** 교육 신청 일련 번호 */
        String applicationNo = curriculumApplicationMaster.getApplicationNo();

        if(!Code.APLY_APPR_TYPE.ADMIN.equals(Code.APLY_APPR_TYPE.enumOfCode(educationPlan.getCurriculumMaster().getApplyApprovalType()))) { /** 관리자 승인이 필요하지 않은 교육 과정일 경우 */
            /** 교육과정에 콘텐츠 정보가 연결되어 있을 경우에만... 즉, 이러닝 교육일 경우에만 해당 */
            if(educationPlan.getCurriculumMaster().getCurriculumContentsList() != null && educationPlan.getCurriculumMaster().getCurriculumContentsList().size() > 0) {
                /** 신청한 교육 과정에 해당하는 나만의 교육 콘텐츠 목록 생성 */
                curriculumApplicationContentsRepository.saveAllAndFlush(educationPlan.getCurriculumMaster().getCurriculumContentsList().stream()
                        .filter(subData -> subData != null && subData.getContentsMaster() != null)
                        .map(subData -> {
                            /** 신청한 콘텐츠에 해당하는 나만의 챕터 목록 생성 */
                            contentsApplicationChapterRepository.saveAllAndFlush(subData.getContentsMaster().getContentsChapterList().stream()
                                    .filter(imsiChapterData -> imsiChapterData != null && imsiChapterData.getChapterMaster() != null)
                                    .map(imsiChapterData -> {
                                        ContentsApplicationChapter contentsApplicationChapter = ContentsApplicationChapter.builder().build();
                                        BeanUtils.copyProperties(imsiChapterData, contentsApplicationChapter);
                                        BeanUtils.copyProperties(imsiChapterData.getChapterMaster(), contentsApplicationChapter);
                                        contentsApplicationChapter.setCurriculumCode(educationPlan.getCurriculumMaster().getCurriculumCode());
                                        contentsApplicationChapter.setApplicationNo(applicationNo);
                                        contentsApplicationChapter.setUserId(userId);
                                        /** 나만의 챕터 목록에 해당하는 섹션(절)정보 목록 생성 */
                                        chapterApplicationSectionRepository.saveAllAndFlush(imsiChapterData.getChapterMaster().getSectionMasterList().stream()
                                                .filter(imsiSectionData -> imsiSectionData != null)
                                                .map(imsiSectionData -> {
                                                    ChapterApplicationSection chapterApplicationSection = ChapterApplicationSection.builder().build();
                                                    BeanUtils.copyProperties(imsiSectionData, chapterApplicationSection);
                                                    chapterApplicationSection.setCurriculumCode(educationPlan.getCurriculumCode());
                                                    chapterApplicationSection.setContentsCode(imsiChapterData.getContentsCode());
                                                    chapterApplicationSection.setApplicationNo(applicationNo);
                                                    chapterApplicationSection.setUserId(userId);
                                                    chapterApplicationSection.setProgressRate(0.0);
                                                    chapterApplicationSection.setIsComplete(false);
                                                    return chapterApplicationSection;
                                                }).collect(Collectors.toList()));
                                        return contentsApplicationChapter;
                                    }).collect(Collectors.toList()));

                            CurriculumApplicationContents curriculumApplicationContents = CurriculumApplicationContents.builder().build();
                            BeanUtils.copyProperties(subData, curriculumApplicationContents);
                            BeanUtils.copyProperties(subData.getContentsMaster(), curriculumApplicationContents);
                            curriculumApplicationContents.setCurriculumCode(educationPlan.getCurriculumMaster().getCurriculumCode());
                            curriculumApplicationContents.setApplicationNo(applicationNo);
                            curriculumApplicationContents.setUserId(userId);
                            return curriculumApplicationContents;
                        }).collect(Collectors.toList()));

                /** 신청한 교육 과정에 해당하는 나만의 시험 목록 생성 - 필요 시 비활성화 */
                curriculumApplicationExamRepository.saveAllAndFlush(educationPlan.getCurriculumMaster().getCurriculumExamList().stream()
                        .filter(subData -> subData != null && subData.getExamMaster() != null)
                        .map(subData -> {
                            CurriculumApplicationExam curriculumApplicationExam = CurriculumApplicationExam.builder().build();
                            BeanUtils.copyProperties(subData, curriculumApplicationExam);
                            BeanUtils.copyProperties(subData.getExamMaster(), curriculumApplicationExam);
                            curriculumApplicationExam.setCurriculumCode(educationPlan.getCurriculumMaster().getCurriculumCode());
                            curriculumApplicationExam.setApplicationNo(applicationNo);
                            curriculumApplicationExam.setUserId(userId);
                            return curriculumApplicationExam;
                        }).collect(Collectors.toList()));

                /** 신청한 교육 과정에 해당하는 나만의 시험 문제 생성 */
                educationPlan.getCurriculumMaster().getCurriculumExamList().forEach(data -> {
                    List<ExamApplicationQuestion> examApplicationQuestionList = new ArrayList<>();
                    /** 난이도 상 */
                    Optional.ofNullable(data.getExamMaster().getGradeFirstSelectQuestionCount())
                            .filter(questionCount -> questionCount > 0)
                            .map(questionCount -> difficultyLevelExamList(data.getCurriculumCode(), Code.QUE_LEVEL.HIGH, questionCount).stream()
                                    .map(highQuestion -> ExamApplicationQuestion.builder()
                                            .applicationNo(applicationNo)
                                            .curriculumCode(data.getCurriculumCode())
                                            .examSerialNo(data.getExamSerialNo())
                                            .questionSerialNo(highQuestion.getQuestionSerialNo())
                                            .userId(userId)
                                            .isComplete(false)
                                            .build())
                                    .collect(Collectors.toList()))
                            .ifPresent(examApplicationQuestionList::addAll);
                    /** 난이도 중 */
                    Optional.ofNullable(data.getExamMaster().getGradeSecondSelectQuestionCount())
                            .filter(questionCount -> questionCount > 0)
                            .map(questionCount -> difficultyLevelExamList(data.getCurriculumCode(), Code.QUE_LEVEL.MEDIUM, questionCount).stream()
                                    .map(mediumQuestion -> ExamApplicationQuestion.builder()
                                            .applicationNo(applicationNo)
                                            .curriculumCode(data.getCurriculumCode())
                                            .examSerialNo(data.getExamSerialNo())
                                            .questionSerialNo(mediumQuestion.getQuestionSerialNo())
                                            .userId(userId)
                                            .isComplete(false)
                                            .build())
                                    .collect(Collectors.toList()))
                            .ifPresent(examApplicationQuestionList::addAll);
                    /** 난이도 하 */
                    Optional.ofNullable(data.getExamMaster().getGradeThirdSelectQuestionCount())
                            .filter(questionCount -> questionCount > 0)
                            .map(questionCount -> difficultyLevelExamList(data.getCurriculumCode(), Code.QUE_LEVEL.LOW, questionCount).stream()
                                    .map(lowQuestion -> ExamApplicationQuestion.builder()
                                            .applicationNo(applicationNo)
                                            .curriculumCode(data.getCurriculumCode())
                                            .examSerialNo(data.getExamSerialNo())
                                            .questionSerialNo(lowQuestion.getQuestionSerialNo())
                                            .userId(userId)
                                            .isComplete(false)
                                            .build())
                                    .collect(Collectors.toList()))
                            .ifPresent(examApplicationQuestionList::addAll);

                    AtomicInteger sortNo = new AtomicInteger(0);
                    examApplicationQuestionRepository.saveAllAndFlush(examApplicationQuestionList.parallelStream().collect(Collectors.toList())
                            .stream().peek(myExamInfo -> myExamInfo.setSortNo(sortNo.incrementAndGet()))
                            .collect(Collectors.toList()));
                });
            } else {    /** 이러닝 교육이 아닌 교육일 경우.... 집합 교육 or 화상 교육 */
                CurriculumApplicationLecture curriculumApplicationLecture = CurriculumApplicationLecture.builder()
                        .applicationNo(applicationNo)
                        .curriculumCode(educationPlan.getCurriculumCode())
                        .userId(userId)
                        .isAttend(false)
                        .build();
                educationPlan.getLectureMasterList().forEach(lectureMaster -> {
                    curriculumApplicationLecture.setLectureCode(lectureMaster.getLectureCode());
                    curriculumApplicationLectureRepository.saveAndFlush(curriculumApplicationLecture);
                });
            }
        }
        return CompletableFuture.completedFuture(curriculumApplicationMaster);
    }

    /**
     * 난이도 별 시험 문제 추출
     *
     * @param curriculumCode
     * @param level
     * @param count
     * @return
     */
    public List<CurriculumQuestion> difficultyLevelExamList(String curriculumCode, Code.QUE_LEVEL level, Integer count) {
        return curriculumQuestionRepository.findAll(Example.of(CurriculumQuestion.builder()
                        .curriculumCode(curriculumCode)
                        .build())).parallelStream()
                .filter(mappingInfo -> level.equals(Code.QUE_LEVEL.enumOfCode(mappingInfo.getExamQuestionMaster().getQuestionLevel())))
                .distinct()
                .limit(count)
                .collect(Collectors.toList());
    }
}
