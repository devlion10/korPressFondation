package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.controller;

import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.service.BizInstructorClclnDdlnService;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request.BizInstructorClclnDdlnViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/business/instructor/ddln")
public class BizInstructorClclnDdlnViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/ddln";
    private final BizInstructorClclnDdlnService bizInstructorClclnDdlnService;

    @GetMapping("/")
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                .map(searchMap -> bizInstructorClclnDdlnService.getBizInstructorClclnDdlnList((BizInstructorClclnDdlnViewRequestVO) params(BizInstructorClclnDdlnViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
        return new StringBuilder(BUSINESS).append("bpaInstructorClclnDdln").toString();
    }
}
