package kr.or.kpf.lms.biz.user.adminuser.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.user.adminuser.service.AdminUserService;
import kr.or.kpf.lms.biz.user.adminuser.vo.request.AdminUserViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 사용자 관리 > 어드민 계정 관리 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/user/admin-user")
public class AdminUserViewController extends CSViewControllerSupport {
    private static final String ADMIN_USER = "views/user/admin/";
    private final AdminUserService adminUserService;

    /**
     * 사용자 관리 > 어드민 계정 목록 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path = {"", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        try {
            modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                    .map(searchMap -> adminUserService.getList((AdminUserViewRequestVO) params(AdminUserViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("WEB_USER_ROLE", "BIZ_AUTH", "USER_STATE"));
            return new StringBuilder(ADMIN_USER).append("admin").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 사용자 관리 > 어드민 계정 등록
     */
    @GetMapping(path = {"/"})
    public String getWrite() {
        return new StringBuilder(ADMIN_USER).append("adminForm").toString();
    }

    /**
     * 사용자 관리 > 어드민 계정 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path = {"/{userSerialNo}"})
    public String getForm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "관리자 일련 번호") @PathVariable(value = "userSerialNo", required = true) Long userSerialNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("userSerialNo", userSerialNo);
            modelSetting(model, Optional.ofNullable(requestParam)
                    .map(searchMap -> adminUserService.getList((AdminUserViewRequestVO) params(AdminUserViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
            return new StringBuilder(ADMIN_USER).append("adminForm").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
}
