package kr.or.kpf.lms.biz.educenter.apply.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.educenter.apply.service.ApplyService;
import kr.or.kpf.lms.biz.educenter.apply.vo.request.ApplyViewRequestVO;
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
import java.math.BigInteger;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 교육원 사용 신청 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/edu-center/apply")
public class ApplyViewController extends CSViewControllerSupport {

    private static final String EDUCENTER = "views/eduCenter/";
    private final ApplyService applyService;

    /**
     * 교육장 사용 신청 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Principal principal) {
        try {


            modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                    .map(searchMap -> applyService.getList((ApplyViewRequestVO) params(ApplyViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));

            return new StringBuilder(EDUCENTER).append("useApplication").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 교육원 사용 신청 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/view/{sequenceNo}"})
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "교육원 사용 신청 시퀀스 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("sequenceNo", sequenceNo);
            modelSetting(model, Optional.ofNullable(requestParam)
                    .map(searchMap -> applyService.getList((ApplyViewRequestVO) params(ApplyViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
            return new StringBuilder(EDUCENTER).append("useApplicationView").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping("?tab=write")
    public String getvView() {
        return "redirect:/write";
    }

    /**
     * 교육원 사용 신청 작성
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/write"})
    public String getWrite(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        return new StringBuilder(EDUCENTER).append("useApplicationForm").toString();
    }
}
