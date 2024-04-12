package kr.or.kpf.lms.biz.mypage.educationstate.service;

import com.google.gson.Gson;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.request.MyEducationStateApiRequestVO;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.request.MyEducationStateViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 신청 중인 나의 교육 과정 관련 Service
 */
@Service
@RequiredArgsConstructor
public class MyEducationStateService extends CSServiceSupport {

    private final CommonMyPageRepository commonMyPageRepository;

    private final EducationPlanRepository educationPlanRepository;

    private final CurriculumMasterRepository curriculumMasterRepository;
    private final CurriculumApplicationMasterRepository curriculumApplicationMasterRepository;

    private final CurriculumApplicationContentsRepository curriculumApplicationContentsRepository;
    private final ContentsApplicationChapterRepository contentsApplicationChapterRepository;
    private final ChapterApplicationSectionRepository chapterApplicationSectionRepository;

    private final CurriculumApplicationLectureRepository curriculumApplicationLectureRepository;

    private final CurriculumApplicationExamRepository curriculumApplicationExamRepository;
    private final ExamApplicationQuestionRepository examApplicationQuestionRepository;

    private final CurriculumApplicationEvaluateRepository curriculumApplicationEvaluateRepository;
    private final CurriculumApplicationEvaluateCommentRepository curriculumApplicationEvaluateCommentRepository;

    /**
     * 교육/수료 현황 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getMyEducationStateList(MyEducationStateViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 챕터만 반환
     *
     * @param requestObject
     * @return
     */
    public  List<ContentsApplicationChapter> getMyEducationChapterList(MyEducationStateViewRequestVO requestObject) {
        List<ContentsApplicationChapter> chpter = new ArrayList<>();
        Page<?> dtos = commonMyPageRepository.findEntityList(requestObject);
        if(!dtos.isEmpty()){
            CurriculumApplicationMaster cont = (CurriculumApplicationMaster) dtos.getContent().get(0);
            List<CurriculumApplicationContents> contents = cont.getCurriculumApplicationContentsList();
            List<ContentsApplicationChapter> chapters = contents.get(0).getContentsApplicationChapterList();
            for(ContentsApplicationChapter chp : chapters){
                if(chp.getChapterCode().equals(requestObject.getChapterCode())){
                    chpter.add(chp);
                }
            }
        }

        return chpter;
    }

    /**
     * 차시 - 절 학습 진행 (이러닝 교육)
     *
     * @param myEducationStateApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport submitSectionProgress(MyEducationStateApiRequestVO myEducationStateApiRequestVO) {
        ChapterApplicationSection entity = ChapterApplicationSection.builder().build();
        copyNonNullObject(myEducationStateApiRequestVO, entity);
        /** 회원 ID 추가 셋팅 */
        entity.setUserId(authenticationInfo().getUserId());

        ChapterApplicationSection chapterApplicationSection = chapterApplicationSectionRepository.findOne(Example.of(entity))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4101, "존재하지 않는 교육 신청 절 정보"));
        /** 절 진도율 */
        chapterApplicationSection.setProgressRate(myEducationStateApiRequestVO.getSectionProgressRate());

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
        String today = localDateTime.format(formatter);

        if(StringUtils.isEmpty(chapterApplicationSection.getBeginDateTime())) {
            /** 절 시작 일시 */
            chapterApplicationSection.setBeginDateTime(today);
        }

        if(myEducationStateApiRequestVO.getSectionProgressRate().equals(Double.parseDouble("100"))) {
            /** 절 완료 처리 */
            chapterApplicationSection.setIsComplete(true);
            /** 절 완료 일시 */
            chapterApplicationSection.setCompleteDateTime(today);
        }
        chapterApplicationSectionRepository.saveAndFlush(chapterApplicationSection);

        /** 교육 과정 중 콘텐츠 정보 관련 업데이트 */
        curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                .applicationNo(myEducationStateApiRequestVO.getApplicationNo())
                .userId(authenticationInfo().getUserId())
                .build())).ifPresentOrElse(curriculumApplicationMaster -> {
            Map<String, String> progressTotalMap = curriculumApplicationMaster.getCurriculumApplicationContentsList().stream()
                .map(curriculumApplicationContents -> {
                    Map<String, Long> sectionTotalMap = curriculumApplicationContents.getContentsApplicationChapterList().stream()
                        .map(contentsApplicationChapter -> {
                            /** 차시의 시작 일자가 비어있을 경우 최초 진입으로 판단하여 시작일시 셋팅 */
                            if(StringUtils.isEmpty(contentsApplicationChapter.getChapterBeginDateTime())
                                    && contentsApplicationChapter.getChapterCode().equals(myEducationStateApiRequestVO.getChapterCode())) {
                                contentsApplicationChapter.setChapterBeginDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                contentsApplicationChapterRepository.saveAndFlush(contentsApplicationChapter);
                            }
                            /** 차시의 절 갯수 */
                            long sectionCount = contentsApplicationChapter.getChapterApplicationSectionList().size();
                            /** 차시의 완료된 절 갯수 */
                            long completeSectionCount = contentsApplicationChapter.getChapterApplicationSectionList().stream().filter(ChapterApplicationSection::getIsComplete).count();
                            /** 차시의 절을 모두 완료하였을 경우 차시 완료 처리 */
                            if (!contentsApplicationChapter.getIsComplete() && (sectionCount == completeSectionCount)) {
                                contentsApplicationChapter.setIsComplete(true);
                                contentsApplicationChapter.setCompleteDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                                contentsApplicationChapterRepository.saveAndFlush(contentsApplicationChapter);
                            }
                            Map<String, Long> sectionMap = new HashMap<>();
                            sectionMap.put("sectionCount", sectionCount);
                            sectionMap.put("completeSectionCount", completeSectionCount);
                            return sectionMap;
                        }).collect(Collectors.toList()).stream()
                            .reduce((before, current) -> {
                                long sectionCount = before.get("sectionCount") + current.get("sectionCount");
                                long completeSectionCount = before.get("completeSectionCount") + current.get("completeSectionCount");
                                Map<String, Long> sectionMap = new HashMap<>();
                                sectionMap.put("sectionCount", sectionCount);
                                sectionMap.put("completeSectionCount", completeSectionCount);
                                return sectionMap;
                            }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4103, "수강 점수 계산 오류"));

                    Long progressScore =  Math.round(100 / Double.parseDouble(sectionTotalMap.get("sectionCount").toString()) *  sectionTotalMap.get("completeSectionCount"));
                    Double progressRate =  Double.parseDouble(sectionTotalMap.get("completeSectionCount").toString()) / Double.parseDouble(sectionTotalMap.get("sectionCount").toString()) * 100;

                    CurriculumMaster master = curriculumMasterRepository.findOne(Example.of(CurriculumMaster.builder()
                            .curriculumCode(curriculumApplicationMaster.getCurriculumCode())
                            .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3004, "교육 과정 정보를 확인할 수 없습니다."));

                    curriculumApplicationContents.setProgressScore(Math.toIntExact(progressScore));
                    double end = master.getProgressRate();
                    if(progressScore.doubleValue() >= end) {
                        curriculumApplicationContents.setIsComplete(true);
                    }
                    curriculumApplicationContentsRepository.saveAndFlush(curriculumApplicationContents);

                    Map<String, String> progressMap = new HashMap<>();
                    progressMap.put("progressScore", progressScore.toString());
                    progressMap.put("progressRate", progressRate.toString());
                    return progressMap;
                }).findFirst().orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4103, "수강 점수 계산 오류"));

            curriculumApplicationMaster.setProgressRate(Double.valueOf(progressTotalMap.get("progressRate")));
            curriculumApplicationMaster.setProgressScore(Integer.valueOf(progressTotalMap.get("progressScore")));

            /** 교육 수료 조건 여부 확인 및 수료 처리 */
            isComplete(curriculumApplicationMaster);
            curriculumApplicationMasterRepository.saveAndFlush(curriculumApplicationMaster);
        }, () -> {
            throw new KPFException(KPF_RESULT.ERROR3025, "존재하지 않는 교육 과정 신청 정보");
        });
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 시험 시작 및 시험 관련 데이터 조회
     *
     * @param myEducationStateApiRequestVO
     * @return
     * @param <T>
     */
    @Transactional(rollbackOn = {Exception.class})
    public <T> Page<T> takeExam(MyEducationStateViewRequestVO myEducationStateApiRequestVO) {
        CurriculumApplicationExam curriculumApplicationExam =  curriculumApplicationExamRepository.findOne(Example.of(CurriculumApplicationExam.builder()
                        .applicationNo(myEducationStateApiRequestVO.getApplicationNo())
                        .userId(authenticationInfo().getUserId())
                .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3044, "존재하지 않는 시험 정보"));
        /** 시험 시작 일시 */
        curriculumApplicationExam.setBeginDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        curriculumApplicationExamRepository.saveAndFlush(curriculumApplicationExam);

        return getMyEducationStateList(myEducationStateApiRequestVO);
    }

    /**
     * 시험 - 문제 정답 제출
     *
     * @param myEducationStateApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport submitQuestionItem(MyEducationStateApiRequestVO myEducationStateApiRequestVO) {
        ExamApplicationQuestion entity = ExamApplicationQuestion.builder().build();
        copyNonNullObject(myEducationStateApiRequestVO, entity);
        /** 회원 ID 추가 셋팅 */
        entity.setUserId(authenticationInfo().getUserId());

        ExamApplicationQuestion imsiExamApplicationQuestion = examApplicationQuestionRepository.findOne(Example.of(entity))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4102, "존재하지 않는 교육 신청 시험 정보"));

        /** 시험 문제 응시 처리 */
        imsiExamApplicationQuestion.setIsComplete(true);
        /** 시험 문제 응시 일시 */
        imsiExamApplicationQuestion.setCompleteDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        /** 시험 문제 응시 답 */
        imsiExamApplicationQuestion.setAnswerQuestionItemValue(new Gson().toJson(myEducationStateApiRequestVO.getAnswerQuestionItemValue()));
        ExamApplicationQuestion examApplicationQuestion = examApplicationQuestionRepository.saveAndFlush(imsiExamApplicationQuestion);

        /** 시험 응시 정보 업데이트(시험 점수, 기타 등등...) */
        curriculumApplicationExamRepository.findOne(Example.of(CurriculumApplicationExam.builder()
                        .applicationNo(myEducationStateApiRequestVO.getApplicationNo())
                        .examSerialNo(myEducationStateApiRequestVO.getExamSerialNo())
                        .userId(authenticationInfo().getUserId())
                .build())).ifPresentOrElse(curriculumApplicationExam -> {
            Integer examScore = curriculumApplicationExam.getExamApplicationQuestionList().stream()
                .filter(ExamApplicationQuestion::getIsComplete)
                .mapToInt(subData -> {   /** 시험 문제 정답 여부 확인 */
                    /** 시험 문제 정답 추출 */
                    List<String> correctAnswer = subData.getExamQuestionMaster().getQuestionItemList().stream()
                            .filter(ExamQuestionItem::getIsCorrectAnswer)
                            .map(ExamQuestionItem::getQuestionItemValue)
                            .collect(Collectors.toList());
                    if (new Gson().fromJson(subData.getAnswerQuestionItemValue(), List.class).size() != correctAnswer.size()) { /** 정답 수가 일치하지 않을 경우 */
                        return 0;
                    } else if(new Gson().fromJson(subData.getAnswerQuestionItemValue(), List.class).stream()
                            .filter(o -> !correctAnswer.contains(o)).count() > 0) { /** 정답이 일치하지 않을 경우 */
                        return 0;
                    } else { /** 정답이 일치할 경우 */
                        return Code.QUE_LEVEL.enumOfCode(subData.getExamQuestionMaster().getQuestionLevel()).score;
                    }
                }).sum();

            curriculumApplicationExam.setExamScore(examScore);

            /** 시험 문제 전체 응시 여부 확인 */
            if(examApplicationQuestionRepository.findAll(Example.of(ExamApplicationQuestion.builder()
                    .applicationNo(myEducationStateApiRequestVO.getApplicationNo())
                    .examSerialNo(myEducationStateApiRequestVO.getExamSerialNo())
                    .userId(authenticationInfo().getUserId())
                    .build())).stream().allMatch(ExamApplicationQuestion::getIsComplete)) {
                curriculumApplicationExam.setIsComplete(true);
                curriculumApplicationExam.setCompleteDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                CurriculumApplicationMaster curriculumApplicationMaster = curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                        .applicationNo(myEducationStateApiRequestVO.getApplicationNo())
                        .userId(authenticationInfo().getUserId())
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3025, "존재하지 않는 교육 과정 신청 정보"));
                curriculumApplicationMaster.setExamScore(examScore);
                curriculumApplicationMasterRepository.saveAndFlush(curriculumApplicationMaster);

                /** 교육 수료 조건 여부 확인 및 수료 처리 */
                isComplete(curriculumApplicationMaster);
            }
            /** 시험 응시 정보 업데이트 */
            curriculumApplicationExamRepository.saveAndFlush(curriculumApplicationExam);
        }, () -> {
            throw new KPFException(KPF_RESULT.ERROR4102, "시험 응시 정보 미존재.");
        });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 강의 평가 제출
     *
     * @param myEducationStateApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport submitEducationEvaluate(MyEducationStateApiRequestVO myEducationStateApiRequestVO) {

        CurriculumApplicationEvaluateComment curriculumApplicationEvaluateComment = CurriculumApplicationEvaluateComment.builder().build();

        Optional.of(curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                                .applicationNo(myEducationStateApiRequestVO.getApplicationNo())
                                .build()))
                        .orElseThrow(() -> new RuntimeException("강의 평가 대상 교육 진행 정보가 존재하지 않습니다.")))
            .ifPresent(curriculumApplicationMaster -> {
                if(curriculumApplicationMaster.getCurriculumApplicationEvaluateList().size() > 0) {
                    throw new RuntimeException("이미 진행한 강의 평가 정보가 존재합니다.");
                }
                curriculumApplicationEvaluateComment.setApplicationNo(curriculumApplicationMaster.getApplicationNo());
                Optional.ofNullable(curriculumApplicationMaster.getEducationPlan().getCurriculumMaster().getCurriculumEvaluateList().get(0))
                    .ifPresent(curriculumEvaluate -> {
                        curriculumApplicationEvaluateComment.setCurriculumCode(curriculumEvaluate.getCurriculumCode());
                        curriculumApplicationEvaluateComment.setEvaluateSerialNo(curriculumEvaluate.getEvaluateSerialNo());

                        if(curriculumEvaluate.getEvaluateMaster().getEvaluateQuestionList().stream()
                            .mapToInt(evaluateQuestion -> {
                                CurriculumApplicationEvaluate curriculumApplicationEvaluate = CurriculumApplicationEvaluate.builder().build();
                                BeanUtils.copyProperties(curriculumApplicationEvaluateComment, curriculumApplicationEvaluate);
                                return myEducationStateApiRequestVO.getAnswerEvaluateInfo().stream()
                                    .filter(answerEvaluateVO -> answerEvaluateVO.getQuestionSerialNo().equals(evaluateQuestion.getQuestionSerialNo()))
                                    .mapToInt(answerEvaluateVO -> {
                                        if (evaluateQuestion.getEvaluateQuestionMaster().getQuestionType().equals("0")) {
                                            curriculumApplicationEvaluate.setAnswerQuestionItemValue(new Gson().toJson(answerEvaluateVO.getQuestionItemSerialNo()));
                                        } else {
                                            switch (Code.QUES_TYPE.enumOfCode(evaluateQuestion.getEvaluateQuestionMaster().getQuestionType())) {
                                                case ONE: /** 단일 문항 */
                                                    curriculumApplicationEvaluate.setAnswerQuestionItemSerialNo(new Gson().toJson(answerEvaluateVO.getQuestionItemSerialNo()));
                                                    curriculumApplicationEvaluate.setAnswerQuestionItemValue(new Gson().toJson(Collections.singletonList(evaluateQuestion.getEvaluateQuestionMaster().getQuestionItemList().stream()
                                                            .filter(evaluateQuestionItem -> evaluateQuestionItem.getQuestionItemSerialNo().equals(answerEvaluateVO.getQuestionItemSerialNo().get(0)))
                                                            .findAny()
                                                            .orElseThrow(() -> new RuntimeException("정의되지 않은 강의 평가 질문 문항입니다."))
                                                            .getQuestionItemValue())));
                                                    break;
                                                case MANY:  /** 다중 문항 */
                                                    curriculumApplicationEvaluate.setAnswerQuestionItemSerialNo(new Gson().toJson(answerEvaluateVO.getQuestionItemSerialNo()));
                                                    curriculumApplicationEvaluate.setAnswerQuestionItemValue(new Gson().toJson(answerEvaluateVO.getQuestionItemSerialNo().stream()
                                                        .map(subData -> evaluateQuestion.getEvaluateQuestionMaster().getQuestionItemList().stream()
                                                                .filter(evaluateQuestionItem -> evaluateQuestionItem.getQuestionItemSerialNo().equals(subData))
                                                                .findAny().orElseThrow(() -> new RuntimeException("정의되지 않은 강의 평가 질문 문항입니다.")).getQuestionItemValue())
                                                        .collect(Collectors.toList())));
                                                    break;
                                                case SHORT: case LONG:    /** 단답형 또는, 서술형 */
                                                    curriculumApplicationEvaluate.setAnswerQuestionItemValue(new Gson().toJson(answerEvaluateVO.getQuestionItemSerialNo()));
                                                    break;
                                            }
                                        }
                                        curriculumApplicationEvaluate.setQuestionSerialNo(answerEvaluateVO.getQuestionSerialNo());
                                        curriculumApplicationEvaluate.setUserId(authenticationInfo().getUserId());
                                        curriculumApplicationEvaluateRepository.saveAndFlush(curriculumApplicationEvaluate);
                                        return NumberUtils.INTEGER_ONE;
                                    }).sum();
                                }).sum() != curriculumEvaluate.getEvaluateMaster().getEvaluateQuestionList().size()) {
                                    throw new RuntimeException("설문 문항 수량이 일치하지 않습니다.");
                                } else {
                                    if(curriculumEvaluate.getEvaluateMaster().getIsUsableOtherComments()) {
                                        curriculumApplicationEvaluateComment.setAnswerComment(myEducationStateApiRequestVO.getAnswerComments());
                                        curriculumApplicationEvaluateComment.setUserId(authenticationInfo().getUserId());
                                        curriculumApplicationEvaluateCommentRepository.saveAndFlush(curriculumApplicationEvaluateComment);
                                    }
                                }
                    });
            });
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 집합 / 화상 교육 - 출석 진행 페이지
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    @Transactional(rollbackOn = {Exception.class})
    public <T> Page<T> lectureAttend(MyEducationStateViewRequestVO requestObject) {
        Optional.ofNullable(curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                        .educationPlanCode(educationPlanRepository.findOne(Example.of(EducationPlan.builder()
                                        .educationPlanCode(requestObject.getEducationPlanCode())
                                        .build())).orElseThrow(() -> new RuntimeException("존재하지 않는 교육일정입니다."))
                                .getEducationPlanCode())
                        .userId(authenticationInfo().getUserId())
            .build())).orElseThrow(() -> new RuntimeException("강의 출석에 실패하였습니다.")))
            .ifPresent(curriculumApplicationMaster -> {

                Optional.of(curriculumApplicationLectureRepository.findOne(Example.of(CurriculumApplicationLecture.builder()
                                .applicationNo(curriculumApplicationMaster.getApplicationNo())
                                .curriculumCode(curriculumApplicationMaster.getCurriculumCode())
                                .lectureCode(requestObject.getLectureCode())
                                .userId(authenticationInfo().getUserId())
                                .build())).orElseThrow(() -> new RuntimeException("신청한 강의가 아닙니다.")))
                    .ifPresent(curriculumApplicationLecture -> {
                        if(curriculumApplicationLecture.getIsAttend()) {
                            throw new RuntimeException("이미 출석 체크를 진행한 강의입니다.");
                        } else {
                            curriculumApplicationLecture.setIsAttend(true);
                            curriculumApplicationLectureRepository.saveAndFlush(curriculumApplicationLecture);
                        }
                    });

                List<CurriculumApplicationLecture> curriculumApplicationLectureList = curriculumApplicationLectureRepository.findAll(Example.of(CurriculumApplicationLecture.builder()
                                .applicationNo(requestObject.getApplicationNo())
                                .curriculumCode(curriculumApplicationMaster.getCurriculumCode())
                                .userId(authenticationInfo().getUserId())
                                .build()));

                long totalLectureCount = curriculumApplicationLectureList.size();
                long attendLectureCount = curriculumApplicationLectureList.stream().filter(CurriculumApplicationLecture::getIsAttend).count();

                curriculumApplicationMaster.setProgressScore(
                        (int) Math.round(100 / Double.parseDouble(Long.toString(totalLectureCount)) * Double.parseDouble(Long.toString(attendLectureCount))));
                curriculumApplicationMaster.setProgressRate(
                        Double.parseDouble(Long.toString(attendLectureCount)) / Double.parseDouble(Long.toString(totalLectureCount)) * 100);

                /** 교육 수료 조건 여부 확인 및 수료 처리 */
                isComplete(curriculumApplicationMaster);
                curriculumApplicationMasterRepository.saveAndFlush(curriculumApplicationMaster);
            });

        return this.getMyEducationStateList(requestObject);
    }

    /**
     * 교육 과제 파일 업로드
     *
     * @param applicationNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport submitAssignment(String applicationNo, MultipartFile attachFile) {
        /** 교육 과정 신청(진행) 데이터 */
        CurriculumApplicationMaster curriculumApplicationMaster = curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                        .applicationNo(applicationNo)
                        .userId(authenticationInfo().getUserId())
                        .build()))
                .orElseThrow(() -> new RuntimeException("과제 제출 대상 교육 진행 정보가 존재하지 않습니다."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                            .append(appConfig.getUploadFile().getAssignmentFolder()).append("/")
                            .append(curriculumApplicationMaster.getEducationPlan().getEducationPlanCode()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getAssignmentFolder())
                                    .append("/")
                                    .append(curriculumApplicationMaster.getEducationPlan().getEducationPlanCode())
                                    .append("/")
                                    .append("결과 보고서_" + authenticationInfo().getUserId() + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            curriculumApplicationMaster.setAssignmentPath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            curriculumApplicationMaster.setAssigmentFileSize(file.getSize());
                            curriculumApplicationMaster.setAssigmentFileSubmitDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        } catch (IOException e) {
                            throw new KPFException(KPF_RESULT.ERROR9005, "파일 업로드 실패");
                        }
                    } catch (IOException e2) {
                        throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
                    }
                });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 교육 수료 조건 여부 확인 및 수료 처리(교육 완료 처리)
     *
     * @param curriculumApplicationMaster
     */
    @Transactional(rollbackOn = {Exception.class})
    void isComplete(CurriculumApplicationMaster curriculumApplicationMaster) {
        if(!curriculumApplicationMaster.getIsComplete().equals("Y")) { /** 수료 처리중, 미수료 상태일 경우 */
            Double progressScoreOfEnd = curriculumApplicationMaster.getEducationPlan().getCurriculumMaster().getProgressScoreOfEnd();
            Double examScoreOfEnd = curriculumApplicationMaster.getEducationPlan().getCurriculumMaster().getExamScoreOfEnd();

            if (Double.parseDouble(curriculumApplicationMaster.getProgressScore().toString()) >= progressScoreOfEnd &&
                    Double.parseDouble(curriculumApplicationMaster.getExamScore().toString()) >= examScoreOfEnd) {
                curriculumApplicationMaster.setIsComplete("Y");
                curriculumApplicationMaster.setCompleteDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                curriculumApplicationMaster.setEducationState(Code.EDU_STATE.END.enumCode);
                curriculumApplicationMasterRepository.saveAndFlush(curriculumApplicationMaster);
            }
        }
    }
}
