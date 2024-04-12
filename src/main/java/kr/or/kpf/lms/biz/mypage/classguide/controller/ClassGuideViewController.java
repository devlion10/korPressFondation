package kr.or.kpf.lms.biz.mypage.classguide.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.service.ClassGuideService;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
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
 * 수업지도안 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/mypage/class-guide")
public class ClassGuideViewController extends CSViewControllerSupport {

    private static final String MYPAGE = "views/mypage/";

    private final ClassGuideService classGuideService;

    /**
     * 마이페이지 > 수업지도안 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(value = {"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                String tutorYN = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getTutor();
                if (tutorYN != null && !tutorYN.isEmpty()) {
                    if (tutorYN.contains("Y")) {
                        String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                        CSSearchMap requestParam = CSSearchMap.of(request);
                        requestParam.put("registUserId", userId);
                        modelSetting(model, Optional.ofNullable(requestParam)
                                .map(searchMap -> classGuideService.getList((ClassGuideViewRequestVO) params(ClassGuideViewRequestVO.class, searchMap, pageable)))
                                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("CON_TEXT_TYPE, GUI_TYPE"));
                        return new StringBuilder(MYPAGE).append("eduPlan").toString();
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("eduPlan").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("eduPlan").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("eduPlan").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/view/{classGuideCode}"})
    public String getEduPlanView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                 @Parameter(description = "수업지도안 일련 번호") @PathVariable(value = "classGuideCode", required = true) String classGuideCode) {
        try {
            if (authentication != null) {
                String tutorYN = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getTutor();
                if (tutorYN != null && !tutorYN.isEmpty()) {
                    if (tutorYN.contains("Y")) {
                        String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                        CSSearchMap requestParam = CSSearchMap.of(request);
                        requestParam.put("registUserId", userId);
                        requestParam.put("classGuideCode", classGuideCode);
                        modelSetting(model, Optional.ofNullable(requestParam)
                                .map(searchMap -> classGuideService.getList((ClassGuideViewRequestVO) params(ClassGuideViewRequestVO.class, searchMap, pageable)))
                                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
                        return new StringBuilder(MYPAGE).append("eduPlanView").toString();
                    }
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("eduPlanView").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("eduPlanView").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("eduPlanView").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/edit/{classGuideCode}"})
    public String getEduPlanEdit(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                 @Parameter(description = "수업지도안 일련 번호") @PathVariable(value = "classGuideCode", required = true) String classGuideCode) {
        try {
            if (authentication != null) {
                String tutorYN = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getTutor();
                if (tutorYN != null && !tutorYN.isEmpty()) {
                    if (tutorYN.contains("Y")) {
                        String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                        CSSearchMap requestParam = CSSearchMap.of(request);
                        requestParam.put("registUserId", userId);
                        requestParam.put("classGuideCode", classGuideCode);
                        modelSetting(model, Optional.ofNullable(requestParam)
                                .map(searchMap -> classGuideService.getList((ClassGuideViewRequestVO) params(ClassGuideViewRequestVO.class, searchMap, pageable)))
                                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
                        return new StringBuilder(MYPAGE).append("eduPlanForm").toString();
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("eduPlanForm").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("eduPlanForm").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("eduPlanForm").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/write"})
    public String getEduPlanForm(Authentication authentication, Model model) {
        try {
            if (authentication != null) {
                String tutorYN = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getTutor();
                if (tutorYN != null && !tutorYN.isEmpty()) {
                    if (tutorYN.contains("Y")) {
                        return new StringBuilder(MYPAGE).append("eduPlanForm").toString();
                    }
                    return "redirect:/login";
                } else {
                    return "redirect:/login";
                }
            } else {
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
}
