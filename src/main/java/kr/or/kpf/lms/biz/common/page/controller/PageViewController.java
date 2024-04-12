package kr.or.kpf.lms.biz.common.page.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorViewRequestVO;
import kr.or.kpf.lms.biz.common.page.service.PageService;
import kr.or.kpf.lms.biz.common.page.vo.request.PageViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 약관 Controller (View)
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/terms")
public class PageViewController extends CSViewControllerSupport {
	private static final String TERMS = "views/terms/";
	private final PageService pageService;

	/** 개인정보 처리 방침 */
	@GetMapping("/privacy-policy")
	public String getPrivacy(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
		CSSearchMap requestParam = CSSearchMap.of(request);
		requestParam.put("documentType", "2");
		modelSetting(model, Optional.ofNullable(requestParam)
				.map(searchMap -> pageService.getList((PageViewRequestVO) params(PageViewRequestVO.class, searchMap, pageable)))
				.orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
		return new StringBuilder(TERMS).append("privacyPolicy").toString();
	}

	/** 이용약관 */
	@GetMapping("/of-use")
	public String getOfUse(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
		CSSearchMap requestParam = CSSearchMap.of(request);
		requestParam.put("documentType", "1");
		modelSetting(model, Optional.ofNullable(requestParam)
				.map(searchMap -> pageService.getList((PageViewRequestVO) params(PageViewRequestVO.class, searchMap, pageable)))
				.orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
		return new StringBuilder(TERMS).append("termsOfUse").toString();
	}

	/** 개인정보 동의서 */
	@GetMapping("/personal-agreement")
	public String getPersonalAgreement(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
		CSSearchMap requestParam = CSSearchMap.of(request);
		requestParam.put("documentType", "3");
		modelSetting(model, Optional.ofNullable(requestParam)
				.map(searchMap -> pageService.getList((PageViewRequestVO) params(PageViewRequestVO.class, searchMap, pageable)))
				.orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
		return new StringBuilder(TERMS).append("personalInformation").toString();
	}

	/** 개인정보 제 3자 제공 */
	@GetMapping("/personal-information")
	public String getPersonalInfo(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
		CSSearchMap requestParam = CSSearchMap.of(request);
		requestParam.put("documentType", "4");
		modelSetting(model, Optional.ofNullable(requestParam)
				.map(searchMap -> pageService.getList((PageViewRequestVO) params(PageViewRequestVO.class, searchMap, pageable)))
				.orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
		return new StringBuilder(TERMS).append("personalInformation").toString();
	}

	/** 고유 식별 정보 처리 동의 */
	@GetMapping("/identification-information")
	public String getIdentification(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
		CSSearchMap requestParam = CSSearchMap.of(request);
		requestParam.put("documentType", "0");
		modelSetting(model, Optional.ofNullable(requestParam)
				.map(searchMap -> pageService.getList((PageViewRequestVO) params(PageViewRequestVO.class, searchMap, pageable)))
				.orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
		return new StringBuilder(TERMS).append("identificationInformation").toString();
	}
}
