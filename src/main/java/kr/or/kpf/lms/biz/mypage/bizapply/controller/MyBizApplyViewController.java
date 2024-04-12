package kr.or.kpf.lms.biz.mypage.bizapply.controller;

import kr.or.kpf.lms.biz.mypage.bizapply.service.MyBizApplyService;
import kr.or.kpf.lms.biz.mypage.bizapply.vo.request.MyBizApplyViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/mypage/bizapply")
public class MyBizApplyViewController extends CSViewControllerSupport {

    private final MyBizApplyService myBizApplyService;
    private static final String MYPAGE = "views/mypage/";

    /**
     * 사업신청내역 - 신청 중
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping({""})
    public String getApplyList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                requestParam.put("bizAplyType", "free");
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> myBizApplyService.getApplyList((MyBizApplyViewRequestVO) params(MyBizApplyViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));

                return new StringBuilder(MYPAGE).append("bizAplyFree").toString();
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
     * 사업신청내역 - 신청 중
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping({"/view/{sequenceNo}"})
    public String getApply(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                           @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        try {
            if (authentication != null) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                requestParam.put("bizAplyType", "free");
                requestParam.put("sequenceNo", sequenceNo);

                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> myBizApplyService.getApplyList((MyBizApplyViewRequestVO) params(MyBizApplyViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
                return new StringBuilder(MYPAGE).append("bizAplyFreeForm").toString();
            } else {
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            System.out.println(e2.toString());
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }
}
