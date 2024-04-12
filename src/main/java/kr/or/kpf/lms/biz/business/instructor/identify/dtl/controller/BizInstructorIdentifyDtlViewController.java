package kr.or.kpf.lms.biz.business.instructor.identify.dtl.controller;

import kr.or.kpf.lms.biz.business.instructor.identify.dtl.service.BizInstructorIdentifyDtlService;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request.BizInstructorIdentifyDtlViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.service.BizInstructorIdentifyService;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
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
@RequestMapping(value = "/business/instructor/identify/dtl")
public class BizInstructorIdentifyDtlViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/identify/dtl";
    private final BizInstructorIdentifyDtlService bizInstructorIdentifyDtlService;

    @GetMapping("/")
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                .map(searchMap -> bizInstructorIdentifyDtlService.getBizInstructorIdentifyDtlList((BizInstructorIdentifyDtlViewRequestVO) params(BizInstructorIdentifyDtlViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
        return new StringBuilder(BUSINESS).append("bpaInstructorIdentifyDtl").toString();
    }
}
