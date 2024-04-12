package kr.or.kpf.lms.biz.education.application.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.education.application.service.EducationApplicationService;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 교육 신청 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/education/application")
public class EducationApplicationViewController extends CSViewControllerSupport {

    private static final String EDUCATION_APPLICATION = "views/education/";

    private final EducationApplicationService educationApplicationService;

    /**
     * 교육 신청 > 화상/집합 교육 과정 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/lecture"})
    public String getLecture(HttpServletRequest request, @PageableDefault Pageable pageable, Model model){
        try {
            CSSearchMap csSearchMap = CSSearchMap.of(request);
            /** 교육 과정 유형이 존재하지 않을 경우 화상 + 집합 교육 코드값 셋팅 */
            if(!Optional.ofNullable(csSearchMap.get("educationType")).isPresent()) csSearchMap.put("educationType", "0");
            modelSetting(model, Optional.ofNullable(csSearchMap)
                    .map(searchMap -> educationApplicationService.getList((EducationApplicationViewRequestVO) params(EducationApplicationViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("EDU_TYPE", "EDU_CTG", "APLYABLE_TYPE", "PROVINCE_CD"));
            return new StringBuilder(EDUCATION_APPLICATION).append("eduLecture").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 교육 신청 > 화상/집합 교육 과정 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/lecture/view/{educationPlanCode}"})
    public String getLectureView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                 @Parameter(description = "교육 과정 코드") @PathVariable(value = "educationPlanCode", required = true) String educationPlanCode) {
        try {
            CSSearchMap csSearchMap = CSSearchMap.of(request);
            csSearchMap.put("educationPlanCode", educationPlanCode);
            modelSetting(model, Optional.ofNullable(csSearchMap)
                .map(searchMap -> educationApplicationService.getIncludeCollaborationEducationList((EducationApplicationViewRequestVO) params(EducationApplicationViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("EDU_TARGET"));
        return new StringBuilder(EDUCATION_APPLICATION).append("eduLectureView").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 교육 신청 > 이러닝 교육 과정 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning"})
    public String getElearning(HttpServletRequest request, @PageableDefault Pageable pageable, Model model){
        try {
            CSSearchMap csSearchMap = CSSearchMap.of(request);
            csSearchMap.put("educationType", "3");
            modelSetting(model, Optional.ofNullable(csSearchMap)
                    .map(searchMap -> educationApplicationService.getList((EducationApplicationViewRequestVO) params(EducationApplicationViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("EDU_TYPE", "EDU_CTG", "APLYABLE_TYPE", "BRANCH_CD", "EDU_TARGET", "PROVINCE_CD"));
            return new StringBuilder(EDUCATION_APPLICATION).append("eduELearning").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 교육 신청 > 이러닝 교육 과정 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/view/{educationPlanCode}"})
    public String getElearningView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                   @Parameter(description = "교육 과정 개설 코드") @PathVariable(value = "educationPlanCode", required = true) String educationPlanCode) {
        try {
            CSSearchMap csSearchMap = CSSearchMap.of(request);
            csSearchMap.put("educationPlanCode", educationPlanCode);
            modelSetting(model, Optional.ofNullable(csSearchMap)
                    .map(searchMap -> educationApplicationService.getIncludeCollaborationEducationList((EducationApplicationViewRequestVO) params(EducationApplicationViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("EDU_TARGET"));
            return new StringBuilder(EDUCATION_APPLICATION).append("eduELearningView").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
}
