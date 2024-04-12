package kr.or.kpf.lms.biz.business.pbanc.master.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.pbanc.master.service.BizPbancService;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancCustomViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.service.BizPbancRsltService;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.service.InstructorService;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.entity.InstructorInfo;
import kr.or.kpf.lms.repository.entity.OrganizationInfo;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 사업 공고 조회 Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/business/pbanc")
public class BizPbancViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/";
    private final BizPbancService bizPbancService;
    private final InstructorService instructorService;

    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            if (authentication != null) {
                String role = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getRoleGroup();
                String bizAuth = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth();
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                if (role.contains(Code.WEB_USER_ROLE.JOURNALIST.enumCode)) {
                    requestParam.put("auth", "Y");
                    requestParam.put("userId", userId);
                    requestParam.put("loginOrgCd", organizationCode);
                } else {
                    if (bizAuth == null || bizAuth.equals(null) || bizAuth.isEmpty()) {
                        requestParam.put("auth", "N");
                    } else {
                        if (bizAuth.contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                            requestParam.put("auth", "N");
                        } else {
                            requestParam.put("userId", userId);
                            requestParam.put("loginOrgCd", organizationCode);
                            requestParam.put("auth", "Y");
                        }
                    }
                }
            } else {
                requestParam.put("auth", "N");
            }

            modelSetting(model, Optional.ofNullable(requestParam)
                    .map(searchMap -> bizPbancService.getBizPbancList((BizPbancViewRequestVO) params(BizPbancViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE", "BIZ_PBANC_TYPE","BIZ_PBANC_CTGR","BIZ_PBANC_STTS"));
            return new StringBuilder(BUSINESS).append("bizPublicAnnounce").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "redirect:/login";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("/view/{bizPbancNo}")
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                          @Parameter(description = "사업 공고 일련 번호") @PathVariable(value = "bizPbancNo", required = true) String bizPbancNo) {
        try {
            CSSearchMap requestParam = CSSearchMap.of(request);
            requestParam.put("bizPbancNo", bizPbancNo);

            if (authentication != null) {
                String role = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getRoleGroup();
                String bizAuth = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth();
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                if (role.contains(Code.WEB_USER_ROLE.JOURNALIST.enumCode)) {
                    requestParam.put("auth", "Y");
                    requestParam.put("loginOrgCd", organizationCode);
                    requestParam.put("userId", userId);
                } else {
                    if (bizAuth == null || bizAuth.equals(null) || bizAuth.isEmpty()) {
                        requestParam.put("auth", "N");
                    } else {
                        if (bizAuth.contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                            requestParam.put("auth", "N");
                        } else {
                            requestParam.put("userId", userId);
                            requestParam.put("loginOrgCd", organizationCode);
                            requestParam.put("auth", "Y");
                        }
                    }
                }
            } else {
                requestParam.put("auth", "N");
            }

            modelSetting(model, Optional.ofNullable(requestParam)
                    .map(searchMap -> bizPbancService.getBizPbancList((BizPbancViewRequestVO) params(BizPbancViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),List.of("BIZ_PBANC_TYPE","BIZ_PBANC_CTGR","BIZ_PBANC_STTS","BIZ_PBANC_SLCTN_METH","BIZ_ORG_APLY_STTS"));
            return new StringBuilder(BUSINESS).append("bizPublicAnnounceView").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "redirect:/login";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "redirect:/login";
        }
    }

    @GetMapping("/apply/{bizPbancNo}")
    public String getForm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                          @Parameter(description = "사업 공고 일련 번호") @PathVariable(value = "bizPbancNo", required = true) String bizPbancNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                requestParam.put("loginOrgCd", organizationCode);
                requestParam.put("bizPbancNo", bizPbancNo);
                requestParam.put("auth", "Y");
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> bizPbancService.getBizPbancList((BizPbancViewRequestVO) params(BizPbancViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_TYPE","BIZ_PBANC_CTGR","BIZ_PBANC_STTS","BIZ_PBANC_SLCTN_METH","BIZ_ORG_APLY_STTS"));
                return new StringBuilder(BUSINESS).append("bizPublicApplyForm").toString();
            } else {
                model.addAttribute("errorMessage", ".");
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "redirect:/login";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "redirect:/login";
        }
    }


    @GetMapping("/apply/5/{bizPbancNo}")
    public String getForm5(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                          @Parameter(description = "사업 공고 일련 번호") @PathVariable(value = "bizPbancNo", required = true) String bizPbancNo) {
        try {
            if (authentication != null) {

                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                requestParam.put("loginOrgCd", organizationCode);
                requestParam.put("bizPbancNo", bizPbancNo);
                requestParam.put("auth", "Y");
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> bizPbancService.getBizPbancList((BizPbancViewRequestVO) params(BizPbancViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_TYPE","BIZ_PBANC_CTGR","BIZ_PBANC_STTS","BIZ_PBANC_SLCTN_METH","BIZ_ORG_APLY_STTS"));
                return new StringBuilder(BUSINESS).append("bizPublicApplyForm5").toString();
            } else {
                model.addAttribute("errorMessage", ".");
                return "redirect:/login";
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "redirect:/login";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "redirect:/login";
        }
    }
    @GetMapping("/pop/instr")
    public String getInstrPopup(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("instrCategory", Code.INSTR_CTGR.INSTR.enumCode);
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> instructorService.getList((InstructorViewRequestVO) params(InstructorViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE","CON_DATE_TYPE"));
                return new StringBuilder("views/pop/").append("popOrgAplyInstr").toString();
            } else {
                model.addAttribute("errorMessage", ".");
                return "redirect:/login";
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
