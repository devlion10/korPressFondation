package kr.or.kpf.lms.biz.search.controller;

import kr.or.kpf.lms.biz.search.service.SearchService;
import kr.or.kpf.lms.biz.search.vo.SearchViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 이벤트/설문 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchViewController extends CSViewControllerSupport {

    private static final String SEARCH = "views/search/";
    private final SearchService searchService;

    /**
     * 통합 검색 조회
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, Model model, @PageableDefault Pageable pageable) {

        modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                .map(searchMap -> searchService.getList((SearchViewRequestVO) params(SearchViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));

        model.addAttribute("searchText", request.getParameter("searchText"));

        return new StringBuilder(SEARCH).append("resultView").toString();
    }


}
