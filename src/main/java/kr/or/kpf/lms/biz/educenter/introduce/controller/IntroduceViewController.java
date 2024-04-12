package kr.or.kpf.lms.biz.educenter.introduce.controller;

import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 교육원 소개 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/edu-center/introduce")
public class IntroduceViewController extends CSViewControllerSupport {

    private static final String EDUCENTER = "views/eduCenter/";

    /**
     * 연수원 소개 - 미디어교육원 소개
     */
    @GetMapping(path={""})
    public String introduce(){
        return new StringBuilder(EDUCENTER).append("introduce").toString();
    }

    /**
     * 연수원 소개 - 연수원 규칙
     */
    @GetMapping(path={"/rule"})
    public String rules(){
        return new StringBuilder(EDUCENTER).append("rules").toString();
    }

    /**
     * 사업소개
     */
    @GetMapping(path={"/introduce"})
    public String bizIntroduce(){
        return new StringBuilder(EDUCENTER).append("bizIntroduce").toString();
    }

}
