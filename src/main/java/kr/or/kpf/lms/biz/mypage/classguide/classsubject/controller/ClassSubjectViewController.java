package kr.or.kpf.lms.biz.mypage.classguide.classsubject.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.service.ClassSubjectService;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request.ClassSubjectViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.service.ClassGuideService;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
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
@RequestMapping(value = "/mypage/class-subject")
public class ClassSubjectViewController extends CSViewControllerSupport {

    private static final String MYPAGE_SUBJECT = "views/mypage/";

    private final ClassSubjectService classSubjectService;

    /**
     * 마이페이지 > 수업지도안 과목 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(value = {"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            String tutorYN = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getTutor();

            if (authentication != null) {
                CSSearchMap requestParam = CSSearchMap.of(request);
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> classSubjectService.getList((ClassSubjectViewRequestVO) params(ClassSubjectViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
                return new StringBuilder("views/pop/").append("addSubject").toString();
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
