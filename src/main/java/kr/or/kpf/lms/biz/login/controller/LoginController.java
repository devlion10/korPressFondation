package kr.or.kpf.lms.biz.login.controller;

import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 로그인 관련 Controller
 */
@Controller
@RequiredArgsConstructor
public class LoginController extends CSViewControllerSupport {

	private static final String Login = "views/login/";

	/**
	 * 요청의 세션에서 "AUTHENTICATION_EXCEPTION"속성으로 부터 {@link AuthenticationException}이 있으면 획득
	 * @param request 요청객체
	 * @param removeFromAttribute 세션에서 예외정보를 획득하고 세션속성에서 제거할지 여부
	 * @return 요청의 세션의  "AUTHENTICATION_EXCEPTION"속성으로 부터 취득된 {@link AuthenticationException}, 없으면 null
	 */
	private @Nullable AuthenticationException getAuthenticationException(@NonNull HttpServletRequest request, boolean removeFromAttribute) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return null;
		}
		AuthenticationException ex = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if(ex != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		return ex;
	}

	@GetMapping(path = {"/login"})
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response, Model model) {
		ModelAndView mv = new ModelAndView();
		//이미 로그인 된경우 처리
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		if (principal instanceof User) {
			mv.setViewName("redirect:/");
			return mv;
		} else if (principal instanceof DefaultOAuth2User) {
			mv.setViewName("redirect:/");
			return mv;
		}
		//인증오류가 발생하여 폼이 다시표시되는 경우의 에러메시지 처리
		AuthenticationException ex = getAuthenticationException(request, true/*removeFromAttribute*/);
		if(ex != null) {
			logger.debug("Login Exception: {} - {}", ex.getClass().getCanonicalName(), ex.getMessage(), (logger.isTraceEnabled()) ? ex : null);
			if(ex instanceof BadCredentialsException) {
				mv.addObject("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다.\n5회 실패 시 로그인이 제한됩니다.");
			} else {
				if(ex.getMessage().contains("마이그레이션")) {
					mv.addObject("errorMessage", KPF_RESULT.ERROR0002.message);
					mv.addObject("userId", ex.getMessage().split("<")[1].replaceAll(">", ""));
					//로그인 화면에서 진입하는 루트를 확인하기 위한 세션 값
					HttpSession session = request.getSession();
					session.setAttribute("joinRoot","login");
				} else {
					mv.addObject("errorMessage", ex.getMessage());
				}
			}
		}
		mv.setViewName("views/login/login");
		return mv;
	}

	/**
	 * 아이디/비밀번호 찾기 페이지 호출
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping(path={"/login/find"})
	public String getLoginFind(HttpServletRequest request, Model model) {
		return new StringBuilder(Login).append("findIdPw").toString();
	}

	/**
	 * 아이디 찾기 결과 페이지 호출
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping(path={"/login/find/result/id"})
	public String getLoginFindResultId(HttpServletRequest request, Model model) {
		return new StringBuilder(Login).append("findIdResult").toString();
	}

	/**
	 * 비밀번호 찾기 결과 페이지 호출
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping(path={"/login/find/result/pw"})
	public String getLoginFindResultPw(HttpServletRequest request, Model model) {
		if(!StringUtils.isEmpty(request.getParameter("userId"))) {
			HttpSession session = request.getSession();
			session.setAttribute("userId", request.getParameter("userId"));
		}
		return new StringBuilder(Login).append("findPwResult").toString();
	}
}
