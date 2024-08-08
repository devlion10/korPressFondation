package kr.or.kpf.lms.biz.communication.link.controller;

import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 외부 콘텐츠 연결 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/communication/link")
public class LinkViewController extends CSViewControllerSupport {

    private static final String COMMUNICATION = "views/community/";

    /**
     * 외부 콘텐츠 연결(뉴스알고, e-NIE)
      (뉴스알고 서비스 종료 2024-07-12)
     */

    /*@GetMapping(path={"/news"})
    public String newsKnows(){
        return new StringBuilder(COMMUNICATION).append("newsKnows").toString();
    }*/
    @GetMapping(path={"/e-nie"})
    public String eNIE(){
        return new StringBuilder(COMMUNICATION).append("e-NIE").toString();
    }
    @GetMapping(path={"/linkset"})
    public String linkset(){
        return new StringBuilder(COMMUNICATION).append("linkset").toString();
    }
}
