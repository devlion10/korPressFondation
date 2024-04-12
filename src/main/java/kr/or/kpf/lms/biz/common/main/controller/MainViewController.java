package kr.or.kpf.lms.biz.common.main.controller;

import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 공통 Controller (View)
 */
@Controller
@RequiredArgsConstructor
public class MainViewController extends CSViewControllerSupport {

	@GetMapping(path = {"/"})
	public ModelAndView loginForm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("views/common/homeForm");
		return mv;
	}
}
