package kr.or.kpf.lms.biz.communication.suggestion.controller;

import kr.or.kpf.lms.biz.communication.suggestion.service.SuggestionService;
import kr.or.kpf.lms.biz.communication.suggestion.vo.request.SuggestionViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 교육 주제 제안 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/communication/edu-suggestion")
public class SuggestionViewController extends CSViewControllerSupport {

    private static final String COMMUNICATION = "views/community/";

    private final SuggestionService suggestionService;

    /**
     * 참여 / 소통 > 교육 주제 제안 선택
     *
     */
    @GetMapping(path={"", "/", "page"})
    public String getView() {
        return new StringBuilder(COMMUNICATION).append("proposition").toString();
    }

    /**
     * 참여 / 소통 > 교육 주제 제안 언론인 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/journalist"})
    public String propositionJournalist(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication){
        String role = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getRoleGroup();
        CSSearchMap requestParam = CSSearchMap.of(request);

        if (role.equals(Code.WEB_USER_ROLE.JOURNALIST.enumCode)) {
            requestParam.put("suggestionType", "1");
            modelSetting(model, Optional.ofNullable(requestParam)
                    .map(searchMap -> suggestionService.getSuggestionList((SuggestionViewRequestVO) params(SuggestionViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        } else {
            requestParam.put("suggestionType", "0");
            modelSetting(model, Optional.ofNullable(requestParam)
                    .map(searchMap -> suggestionService.getSuggestionList((SuggestionViewRequestVO) params(SuggestionViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        }
        return new StringBuilder(COMMUNICATION).append("propView").toString();
    }

    /**
     * 참여 / 소통 > 교육 주제 제안 시민 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/public"})
    public String propositionPublic(HttpServletRequest request, @PageableDefault Pageable pageable, Model model){
        val nlString = System.getProperty("line.separator").toString();
        model.addAttribute("nlString", nlString);

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("suggestionType", "2");
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> suggestionService.getSuggestionList((SuggestionViewRequestVO) params(SuggestionViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("propView").toString();
    }

}
