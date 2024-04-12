package kr.or.kpf.lms.biz.education.anonymous.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.education.anonymous.service.AnonymousEducationService;
import kr.or.kpf.lms.biz.education.anonymous.vo.request.AnonymousEducationViewRequestVO;
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
 * 비로그인 교육 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/education")
public class AnonymousEducationViewController extends CSViewControllerSupport {

    private static final String EDUCATION = "views/mypage/";

    private final AnonymousEducationService anonymousEducationService;

    /**
     * 비로그인 이러닝 교육 - 교육 페이지(진도 목차)
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/e-learning/{chapterCode}"})
    public String getELearningChapterLauncher(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                              @Parameter(description = "챕터 번호") @PathVariable(value = "chapterCode", required = true) String chapterCode) {
        try {
            CSSearchMap csSearchMap = CSSearchMap.of(request);
            csSearchMap.put("chapterCode", chapterCode);
            modelSetting(model, Optional.ofNullable(csSearchMap)
                    .map(searchMap -> anonymousEducationService.getList((AnonymousEducationViewRequestVO) params(AnonymousEducationViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("EDU_TARGET"));
            model.addAttribute("applicationNo", null);
            model.addAttribute("chapterCode", chapterCode);
            return new StringBuilder(EDUCATION).append("edu/eduLauncher").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
}
