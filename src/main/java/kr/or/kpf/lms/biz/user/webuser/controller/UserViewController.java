package kr.or.kpf.lms.biz.user.webuser.controller;

import kr.or.kpf.lms.biz.user.authority.service.AuthorityService;
import kr.or.kpf.lms.biz.user.authority.vo.request.IndividualAuthorityApiRequestVO;
import kr.or.kpf.lms.biz.user.webuser.service.UserService;
import kr.or.kpf.lms.biz.user.webuser.vo.request.UserBizViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.entity.IndividualAuthorityHistory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 사용자 멤버십 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserViewController extends CSViewControllerSupport {

    private static final String ANONYMOUS = "views/anonymous/";
    private static final String USER = "views/member/";
    private static final String MYPAGE = "views/mypage/";

    private final UserService userService;
    private final AuthorityService authorityService;

    /**
     * 회원 가입 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(path={"/join"})
    public String getJoin(HttpServletRequest request, Model model) {
        return new StringBuilder(ANONYMOUS).append("join").toString();
    }

    /**
     * 이용 약관 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(path={"/join/terms"})
    public String getJoinTerm(HttpServletRequest request, Model model) {
        return new StringBuilder(ANONYMOUS).append("joinFormTerms").toString();
    }
    // 14세 미만
    @GetMapping(path={"/join/terms_youth"})
    public String getJoinTermYouth(HttpServletRequest request, Model model) {
        model.addAttribute("youth", "youth");
        return new StringBuilder(ANONYMOUS).append("joinFormTerms").toString();
    }

    /**
     * 이용 약관 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(path={"/join/terms"})
    public String getJoinTermOfMigration(HttpServletRequest request, Model model) {
        model.addAttribute("userId", request.getParameter("userId"));
        return new StringBuilder(ANONYMOUS).append("joinFormTerms").toString();
    }
    // 14세 미만
    @PostMapping(path={"/join/terms_youth"})
    public String getJoinTermOfYouth(HttpServletRequest request, Model model) {
        model.addAttribute("youth", request.getParameter("youth"));
        return new StringBuilder(ANONYMOUS).append("joinFormTerms").toString();
    }

    /**
     * 본인 인증 수단 선택 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(path={"/join/auth"})
    public String getJoinAuth(HttpServletRequest request, Model model) {
        return new StringBuilder(ANONYMOUS).append("joinFormAuth").toString();
    }

    /**
     * 본인 인증 수단 선택 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(path={"/join/auth"})
    public String getJoinAuthOfKeys(HttpServletRequest request, Model model) {
        model.addAttribute("userId", request.getParameter("userId"));
        model.addAttribute("youth", request.getParameter("youth"));
        return new StringBuilder(ANONYMOUS).append("joinFormAuth").toString();
    }

    /**
     * 본인인증 View
     *
     * @param request
     * @return
     */
    @GetMapping(path = {"/join/authentication"})
    public String authenticationForm(HttpServletRequest request, Model model) {
        model.addAttribute("youth", request.getParameter("youth"));
        model.addAttribute(userService.selfAuthentication(request,"join"));
        return new StringBuilder(ANONYMOUS).append("authenticationForm").toString();
    }

    /**
     * 본인인증 결과 View
     *
     * @param request
     * @return
     */
    @GetMapping(path = {"/join/authentication/result"})
    public String authenticationResultForm(HttpServletRequest request, Model model) {
        try{
            model.addAttribute("youth", request.getParameter("youth"));
            model.addAllAttributes(userService.selfAuthenticationResult(request,"join"));
            return new StringBuilder(ANONYMOUS).append("authenticationResultForm").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage() );
            return new StringBuilder(ANONYMOUS).append("authenticationResultForm").toString();
        }
    }

    /**
     * 회원 타입 선택 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(path={"/join/type"})
    public String getJoinType(HttpServletRequest request, Model model) {
        try {
            String userId = request.getParameter("userId");
            if(!StringUtils.isEmpty(userId)) {
                /** 마이그래이션 대상 회원의 연락처와 본인인증 연락처와 다를 경우 에러 처리 */
                if(!userService.getUserInfo(userId).getPhone().equals(request.getParameter("userPhone"))) {
                    throw new KPFException(KPF_RESULT.ERROR0001, "연락처 상이");
                }
                model.addAttribute("userId", request.getParameter("userId"));
            }
            model.addAttribute("migrationYN", request.getParameter("migrationYN"));
            model.addAttribute("userName", request.getParameter("userName"));
            model.addAttribute("userPhone", request.getParameter("userPhone"));
            model.addAttribute("birthDate", request.getParameter("birthDate"));
            model.addAttribute("gender", request.getParameter("gender"));
            return new StringBuilder(ANONYMOUS).append("joinType").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 14세 미만 회원 정보 입력 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(path={"/join/profile/youth"})
    public String getJoinProfileYouth(HttpServletRequest request, Model model) {
        try {
            model.addAttribute("userName", request.getParameter("userName"));
            model.addAttribute("userPhone", request.getParameter("userPhone"));
            model.addAttribute("birthDate", request.getParameter("birthDate"));
            model.addAttribute("gender", request.getParameter("gender"));
            return new StringBuilder(ANONYMOUS).append("joinFormProfileYouth").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 일반 회원 정보 입력 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(path={"/join/profile/normal"})
    public String getJoinProfileNormal(HttpServletRequest request, Model model) {
        try {
            if(!StringUtils.isEmpty(request.getParameter("userId"))) {
                model.addAttribute("content", userService.getUserInfo(request.getParameter("userId")));
                HttpSession session = request.getSession();
                session.setAttribute("userId", request.getParameter("userId"));
            }
            model.addAttribute("userName", request.getParameter("userName"));
            model.addAttribute("userPhone", request.getParameter("userPhone"));
            model.addAttribute("birthDate", request.getParameter("birthDate"));
            model.addAttribute("gender", request.getParameter("gender"));
            return new StringBuilder(ANONYMOUS).append("joinFormProfileNormal").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 언론인 회원 정보 입력 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(path={"/join/profile/journalist"})
    public String getJoinProfileJournalist(HttpServletRequest request, Model model) {
        try {
            if(!StringUtils.isEmpty(request.getParameter("userId"))) {
                model.addAttribute("content", userService.getUserInfo(request.getParameter("userId")));
                HttpSession session = request.getSession();
                session.setAttribute("userId", request.getParameter("userId"));
            }
            model.addAttribute("userName", request.getParameter("userName"));
            model.addAttribute("userPhone", request.getParameter("userPhone"));
            model.addAttribute("birthDate", request.getParameter("birthDate"));
            model.addAttribute("gender", request.getParameter("gender"));

            return new StringBuilder(ANONYMOUS).append("joinFormProfileJournalist").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 회원 가입 성공 페이지 호출
     *
     * @param request
     * @param model
     * @return
     */
    @GetMapping(path={"/join/result"})
    public String getJoinResult(HttpServletRequest request, Model model) {
        return new StringBuilder(USER).append("signIn").toString();
    }

    @GetMapping(path={"/secession"})
    public String getSecession(){
        return new StringBuilder(USER).append("signOut").toString();
    }

    @GetMapping(path={"/secession/result"})
    public String getSecessionResult(){
        return new StringBuilder(USER).append("signOutResult").toString();
    }

    @GetMapping(path={"/dormancy/alert"})
    public String getDormancyAlert(){
        return new StringBuilder(USER).append("dormantAccount").toString();
    }

    @GetMapping(path={"/dormancy/result"})
    public String getDormancyResult(){
        return new StringBuilder(USER).append("dormantAccountForm").toString();
    }

    @GetMapping(path={"/auth-manage"})
    public String getAuthManger(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                String oranizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                requestParam.put("organizationCode", oranizationCode);
                Object dto = userService.getUserBizInfo((UserBizViewRequestVO) params(UserBizViewRequestVO.class, requestParam, null));

                IndividualAuthorityApiRequestVO requestVO = new IndividualAuthorityApiRequestVO();
                requestVO.setUserId(userId);
                IndividualAuthorityHistory instrAuth = authorityService.getIndividualBusinessAuthority(requestVO);

                if (dto != null) {
                    /** 요청 회원 권한 객체 포함 세팅 */
                    model.addAttribute("content", dto);
                } else if (instrAuth != null && !instrAuth.equals(null)) {
                    model.addAttribute("content", userService.getUserInfo(userId));
                    model.addAttribute("instrAuth", instrAuth);
                } else {
                    /** 요청 회원 정보 그대로 재셋팅 */
                    model.addAttribute("content", userService.getUserInfo(userId));
                }

                return new StringBuilder(MYPAGE).append("authManage").toString();
            } else {
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return new StringBuilder(MYPAGE).append("authManage").toString();
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return new StringBuilder(MYPAGE).append("authManage").toString();
        }
    }

    @GetMapping(path={""})
    public String getPersonalInfo(Model model, Authentication authentication){
        try {
            if (authentication != null) {
                return new StringBuilder(MYPAGE).append("personalInformation").toString();
            } else {
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/write"})
    public String getPersonalInfoForm(Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                model.addAttribute("content", userService.getUserInfo(userId));
                return new StringBuilder(MYPAGE).append("personalInformationForm").toString();
            } else {
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }


    /**
     * 본인인증 View
     *
     * @param request
     * @return
     */
    @GetMapping(path = {"/find/authentication"})
    public String authenticationFindForm(HttpServletRequest request, Model model) {
        model.addAttribute("youth", request.getParameter("youth"));
        model.addAttribute(userService.selfAuthentication(request,"find"));
        return new StringBuilder(ANONYMOUS).append("authenticationForm").toString();
    }

    /**
     * 본인인증 결과 View
     *
     * @param request
     * @return
     */
    @GetMapping(path = {"/find/authentication/result"})
    public String authenticationFindResultForm(HttpServletRequest request, Model model) {
        try{
            model.addAttribute("youth", request.getParameter("youth"));
            model.addAllAttributes(userService.selfAuthenticationResult(request,"find"));
            return new StringBuilder(ANONYMOUS).append("authenticationResultForm").toString();
        } catch( Exception e){
            model.addAttribute("errorMessage", e.getMessage() );
            return new StringBuilder(ANONYMOUS).append("authenticationResultForm").toString();
        }
    }
}
