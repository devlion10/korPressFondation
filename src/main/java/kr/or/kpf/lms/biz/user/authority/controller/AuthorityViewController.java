package kr.or.kpf.lms.biz.user.authority.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.user.webuser.service.UserService;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * 회원 가입 > 회원정보 입력(권한신청) View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/user/authority")
public class AuthorityViewController extends CSViewControllerSupport {

    private static final String ANONYMOUS = "views/anonymous/";

    private final UserService userService;

    /**
     * 회원정보 입력(권한신청) 화면 호출
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path = { "/{userId}"})
    public String getAuthority(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                               @Parameter(description = "회원 ID") @PathVariable(value = "userId", required = true) String userId) {
        try {
            /** 요청 회원 정보 그대로 재셋팅 */
            model.addAttribute("content", userService.getUserInfo(userId));
            /** 사용할 공통 코드 정보 셋팅 */
            commonCodeSetting(model, Arrays.asList("BIZ_AUTH"));
            switch (Code.BIZ_AUTH.enumOfCode(request.getParameter("bizAuthority"))) {
                case AGENCY:
                    return new StringBuilder(ANONYMOUS).append("joinFormAuthCompany").toString();
                case SCHOOL:
                    return new StringBuilder(ANONYMOUS).append("joinFormAuthSchool").toString();
                case INSTR:
                    return new StringBuilder(ANONYMOUS).append("joinFormAuthInstructor").toString();
                default:
                    return new StringBuilder(ANONYMOUS).append("joinFormAuthInstructor").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
}
