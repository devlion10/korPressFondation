package kr.or.kpf.lms.biz.business.instructor.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.instructor.service.BizInstructorService;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.response.BizInstructorApiResponseVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.service.InstructorService;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.response.InstructorApiResponseVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.entity.BizInstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/business/instructor")
public class BizInstructorViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/";
    private final BizInstructorService bizInstructorService;
    private final InstructorService instructorService;

    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            String bizAuth = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth();
            if (bizAuth.contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                CSSearchMap param = CSSearchMap.of(request);
                param.put("instrId", userId);
                param.put("instrCategory", Code.INSTR_CTGR.INSTR.enumCode);
                List<InstructorApiResponseVO> instr = instructorService.getAllList((InstructorViewRequestVO) params(InstructorViewRequestVO.class, param, pageable));
                if (instr.size() > 0 && !instr.isEmpty())
                    model.addAttribute("instr", instr.get(0));

                BizInstructor bizInstr = bizInstructorService.getBizInstructor((BizInstructorViewRequestVO) params(BizInstructorViewRequestVO.class, CSSearchMap.of(request), pageable));
                if (bizInstr != null && !bizInstr.equals(null))
                    model.addAttribute("bizInstr", bizInstr);

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                requestParam.put("list", "list");
                modelSetting(model, Optional.ofNullable(param)
                        .map(searchMap -> bizInstructorService.getBizInstructorList((BizInstructorViewRequestVO) params(BizInstructorViewRequestVO.class, requestParam, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE","BIZ_PBANC_INSTR_SLCTN_METH","BIZ_INSTR_STTS", "BIZ_ORG_APLY_STTS", "BIZ_INSTR_APLY_STTS"));
                return new StringBuilder(BUSINESS).append("bpaInstructorRecruit").toString();
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(BUSINESS).append("bpaInstructorRecruit").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping("/view/{sequenceNo}")
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                          @Parameter(description = "일련 번호") @PathVariable(value = "sequenceNo", required = true) String sequenceNo) {
        try {
            String bizAuth = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth();
            if (bizAuth.contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("sequenceNo", sequenceNo);
                requestParam.put("userId", userId);
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> bizInstructorService.getBizInstructorList((BizInstructorViewRequestVO) params(BizInstructorViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_INSTR_SLCTN_METH","BIZ_INSTR_STTS", "BIZ_ORG_APLY_STTS", "BIZ_INSTR_APLY_STTS"));
                return new StringBuilder(BUSINESS).append("bizInstructorAnnounceView").toString();
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(BUSINESS).append("bizInstructorAnnounceView").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "redirect:/login";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("/view/{sequenceNo}/{bizOrgAplyNo}")
    public String getApply(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                           @Parameter(description = "일련 번호") @PathVariable(value = "sequenceNo", required = true) String sequenceNo,
                          @Parameter(description = "사업 신청 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            String bizAuth = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth();
            if (bizAuth.contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                CSSearchMap param = CSSearchMap.of(request);
                param.put("instrId", userId);
                param.put("instrCategory", Code.INSTR_CTGR.INSTR.enumCode);
                List<InstructorApiResponseVO> instr = instructorService.getAllList((InstructorViewRequestVO) params(InstructorViewRequestVO.class, param, pageable));

                if (instr.size() > 0 && !instr.isEmpty())
                    model.addAttribute("instr", instr.get(0));

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("sequenceNo", sequenceNo);
                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                requestParam.put("userId", userId);
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> bizInstructorService.getBizInstructorList((BizInstructorViewRequestVO) params(BizInstructorViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_INSTR_SLCTN_METH","BIZ_INSTR_STTS", "BIZ_ORG_APLY_STTS", "BIZ_INSTR_APLY_STTS"));
                return new StringBuilder(BUSINESS).append("bpaInstructorApply").toString();
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(BUSINESS).append("bpaInstructorApply").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "redirect:/login";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "redirect:/login";
        }
    }
}
