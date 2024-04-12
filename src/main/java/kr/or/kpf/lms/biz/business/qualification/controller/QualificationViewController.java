package kr.or.kpf.lms.biz.business.qualification.controller;

import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/business/qualification")

public class QualificationViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/";

    @GetMapping(path={"", "/", "/page"})
    public String getView() {
        return new StringBuilder(BUSINESS).append("qualificationTest").toString();
    }
}
