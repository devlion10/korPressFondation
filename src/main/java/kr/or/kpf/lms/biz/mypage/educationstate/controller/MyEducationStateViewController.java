package kr.or.kpf.lms.biz.mypage.educationstate.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.mypage.educationstate.service.MyEducationStateService;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.request.MyEducationStateViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.entity.ContentsApplicationChapter;
import kr.or.kpf.lms.repository.entity.CurriculumApplicationMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 교육/수료 현황 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/mypage/education-state")
public class MyEducationStateViewController extends CSViewControllerSupport {
    private static final String VIEWS_MYPAGE = "views/mypage/";
    private static final String VIEWS_POPUP = "views/pop/";
    private final MyEducationStateService myEducationStateService;

    @GetMapping(path={"/view"})
    public String getEduView(){
        return new StringBuilder(VIEWS_MYPAGE).append("eduView").toString();
    }

    /**
     * 마이페이지 > 교육/수료 현황 조회 (교육 신청 중... 관리자 심사 대기 중...)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/apply"})
    public String getEducationApplication(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationState", Code.EDU_STATE.WAIT.enumCode);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("eduApplying").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 마이페이지 > 교육/수료 현황 조회 (교육 진행 중)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"", "/page"})
    public String getEducationProceeding(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationState", Code.EDU_STATE.PROCEEDING.enumCode);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("eduStudying").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 마이페이지 > 교육/수료 현황 조회 (교육 완료)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/result"})
    public String getEducationResult(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationState", Code.EDU_STATE.END.enumCode);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("eduResult").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 마이페이지 > 교육/수료 현황 조회 (교육 완료)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/result/eval/{applicationNo}"})
    public String getEducationEvaluatePopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationState", Code.EDU_STATE.END.enumCode);
            requestParam.put("applicationNo", applicationNo);
            Page<Object> curriculumEvaluateList = myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, requestParam, pageable));
            CurriculumApplicationMaster curriculumApplicationMasters = (CurriculumApplicationMaster) curriculumEvaluateList.getContent().get(0);
            model.addAttribute("content", curriculumApplicationMasters.getEducationPlan().getCurriculumMaster().getCurriculumEvaluateList());
            return new StringBuilder(VIEWS_POPUP).append("lectureEvaluation").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
    /**
     * 마이페이지 > 교육/수료 현황 조회 (수료증)
     */
    @GetMapping(path={"/result/certification"})
    public String getEducationCertification(Model model) {
        try {
            return new StringBuilder(VIEWS_MYPAGE).append("pop/certification").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 마이페이지 > 교육/수료 현황 조회 (이수증)
     */
    @GetMapping(path={"/result/certification/teacher"})
    public String getTeacherEducationCertification(Model model) {
        try {
            return new StringBuilder(VIEWS_MYPAGE).append("pop/certi_achievement").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 마이페이지 > 교육/수료 현황 조회 (이러닝 수료증)
     */
    @GetMapping(path={"/result/certification/e-learning"})
    public String getElearningCertification(Model model) {
        try {
            return new StringBuilder(VIEWS_MYPAGE).append("pop/certification_eLearning").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }



    /**
     * 이러닝 교육 - 교육 페이지(메인)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/main/{applicationNo}"})
    public String getELearningMain(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                   @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationType", Code.EDU_TYPE.E_LEARNING.enumCode);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/eLearning").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 이러닝 교육 - 교육 페이지(진도 목차)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/list/{applicationNo}"})
    public String getELearningChapterInfo(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                          @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationType", Code.EDU_TYPE.E_LEARNING.enumCode);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/eLearningList").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 이러닝 교육 - 교육 페이지(진도 목차)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/list/{applicationNo}/{chapterCode}"})
    public String getELearningChapterLauncher(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                          @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo,
                                          @Parameter(description = "챕터 번호") @PathVariable(value = "chapterCode", required = true) String chapterCode) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationType", Code.EDU_TYPE.E_LEARNING.enumCode);
            requestParam.put("applicationNo", applicationNo);
            requestParam.put("chapterCode", chapterCode);

            model.addAttribute("chapterCode", chapterCode);
            model.addAttribute("applicationNo", applicationNo);

            List<ContentsApplicationChapter> chaptersers = myEducationStateService.getMyEducationChapterList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, requestParam, pageable));
            model.addAttribute("content", chaptersers);

            return new StringBuilder(VIEWS_MYPAGE).append("edu/eduLauncher").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
    /**
     * 이러닝 교육 - 시험 목록 페이지
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/exam/{applicationNo}"})
    public String getELearningExamPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                        @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationType", Code.EDU_TYPE.E_LEARNING.enumCode);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/eLearningExam").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 이러닝 교육 - 시험 페이지
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/exam/view"})
    public String getELearningExamViewPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationType", Code.EDU_TYPE.E_LEARNING.enumCode);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.takeExam((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/eLearningExamView").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 이러닝 교육 - 자료실
     *
     * @param request
     * @param pageable
     * @param model
     * @param applicationNo
     * @return
     */
    @GetMapping(path={"/e-learning/archive/{applicationNo}"})
    public String getELearningArchivePopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                           @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationType", Code.EDU_TYPE.E_LEARNING.enumCode);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/archive").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
    @GetMapping(path={"/e-learning/archive/view/{referenceSequenceNo}"})
    public String getELearningArchiveViewPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                               @Parameter(description = "자료실 일련번호") @PathVariable(value = "referenceSequenceNo", required = true) BigInteger referenceSequenceNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("referenceSequenceNo", referenceSequenceNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/archiveView").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 화상 교육 - 교육 페이지(메인)
     *
     * @param request
     * @param pageable
     * @param model
     * @param applicationNo
     * @return
     */
    @GetMapping(path={"/e-lecture/main/{applicationNo}"})
    public String getELectureMain(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                 @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/ELecture").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/e-lecture/archive/{applicationNo}"})
    public String getELectureArchivePopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                         @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/archive").toString();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/e-lecture/archive/view/{referenceSequenceNo}"})
    public String getELectureArchiveViewPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                             @Parameter(description = "자료실 일련번호") @PathVariable(value = "referenceSequenceNo", required = true) BigInteger referenceSequenceNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("referenceSequenceNo", referenceSequenceNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/archiveView").toString();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/error/errorForm";
        }
    }

    /**
     * 집합 교육 - 교육 페이지(메인)
     *
     * @param request
     * @param pageable
     * @param model
     * @param applicationNo
     * @return
     */
    @GetMapping(path={"/lecture/main/{applicationNo}"})
    public String getLectureMain(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                 @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/Lecture").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 집합 교육 - 출석 진행 페이지
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/lecture/attend/{educationPlanCode}/{lectureCode}"})
    public String getLectureAttendPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                        @Parameter(description = "교육 계획 코드") @PathVariable(value = "educationPlanCode", required = true) String educationPlanCode,
                                        @Parameter(description = "강의 코드") @PathVariable(value = "lectureCode", required = true) String lectureCode) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("educationPlanCode", educationPlanCode);
            requestParam.put("lectureCode", lectureCode);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.lectureAttend((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/lectureAttend").toString();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/lecture/archive/{applicationNo}"})
    public String getLectureArchivePopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                         @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("applicationNo", applicationNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/archive").toString();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/lecture/archive/view/{referenceSequenceNo}"})
    public String getLectureArchiveViewPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                             @Parameter(description = "자료실 일련번호") @PathVariable(value = "referenceSequenceNo", required = true) BigInteger referenceSequenceNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("referenceSequenceNo", referenceSequenceNo);
            modelSetting(model, Optional.of(requestParam)
                    .map(searchMap -> myEducationStateService.getMyEducationStateList((MyEducationStateViewRequestVO) params(MyEducationStateViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CTS_CTGR", "EDU_TYPE", "ADM_APL_STATE", "EDU_STATE"));
            return new StringBuilder(VIEWS_MYPAGE).append("edu/archiveView").toString();
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "views/error/errorForm";
        }
    }
}
