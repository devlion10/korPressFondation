package kr.or.kpf.lms.biz.mypage.businessstate.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.instructor.identify.service.BizInstructorIdentifyService;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.edithist.service.BizEditHistService;
import kr.or.kpf.lms.biz.business.organization.aply.service.BizOrganizationAplyService;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.rpt.service.BizOrganizationRsltRptService;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.request.BizOrganizationRsltRptViewRequestVO;
import kr.or.kpf.lms.biz.mypage.businessstate.service.MyBusinessStateService;
import kr.or.kpf.lms.biz.mypage.businessstate.vo.request.*;
import kr.or.kpf.lms.biz.user.instructor.service.InstructorService;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 사업참여 현황 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/mypage/business-state")
public class MyBusinessStateViewController extends CSViewControllerSupport {

    private final MyBusinessStateService myBusinessStateService;
    private final BizOrganizationAplyService bizOrganizationAplyService;
    private final BizOrganizationRsltRptService bizOrganizationRsltRptService;
    private final BizInstructorIdentifyService bizInstructorIdentifyService;
    private final InstructorService instructorService;
    private static final String MYPAGE = "views/mypage/";


    private final BizEditHistService bizEditHistService;

    /**
     * 사업 참여 현황 - 신청 중
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping({"/apply"})
    public String getApplyList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            CSSearchMap requestParam = CSSearchMap.of(request);
                            requestParam.put("organizationCode", organizationCode);

                            modelSetting(model, Optional.ofNullable(requestParam)
                                    .map(searchMap -> myBusinessStateService.getApplyList((MyBusinessStateApplyViewRequestVO) params(MyBusinessStateApplyViewRequestVO.class, searchMap, pageable)))
                                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE","BIZ_PBANC_SLCTN_METH", "BIZ_ORG_APLY_STTS"));

                            return new StringBuilder(MYPAGE).append("publicOfferingBusinessApply").toString();
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("publicOfferingBusinessApply").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("publicOfferingBusinessApply").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("publicOfferingBusinessApply").toString();
                }
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

    @GetMapping(path={"/apply/write/{bizOrgAplyNo}"})
    public String getApplyWrite(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                 @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationAplyService.getList((BizOrganizationAplyViewRequestVO) params(BizOrganizationAplyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_SLCTN_METH", "BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobApplyForm").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobApplyForm").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobApplyForm").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobApplyForm").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobApplyForm").toString();
                }
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

    @GetMapping(path={"/apply/view/{bizOrgAplyNo}"})
    public String getApplyView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                               @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationAplyService.getList((BizOrganizationAplyViewRequestVO) params(BizOrganizationAplyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_SLCTN_METH", "BIZ_ORG_APLY_STTS"));
                                return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                }
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

    @GetMapping(path={"/apply/reject/{bizOrgAplyNo}"})
    public String getApplyReject(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                 @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationAplyService.getList((BizOrganizationAplyViewRequestVO) params(BizOrganizationAplyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_SLCTN_METH", "BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobReject").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobReject").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobReject").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobReject").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobReject").toString();
                }
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

    @GetMapping(path={"/apply/update/{bizOrgAplyNo}"})
    public String getApplyUpdate(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                 @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationAplyService.getList((BizOrganizationAplyViewRequestVO) params(BizOrganizationAplyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_SLCTN_METH", "BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                }
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
     * 사업 참여 현황 - 진행 중
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping({"", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                    if (organizationCode != null || !organizationCode.isEmpty()) {
                        CSSearchMap requestParam = CSSearchMap.of(request);
                        requestParam.put("organizationCode", organizationCode);
                        modelSetting(model, Optional.ofNullable(requestParam)
                                .map(searchMap -> myBusinessStateService.getList((MyBusinessStateViewRequestVO) params(MyBusinessStateViewRequestVO.class, searchMap, pageable)))
                                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE","BIZ_INSTR_IDNTY_STTS", "BIZ_ORG_APLY_STTS"));

                        return new StringBuilder(MYPAGE).append("publicOfferingBusiness").toString();
                    } else {
                        model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                        return new StringBuilder(MYPAGE).append("publicOfferingBusiness").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("publicOfferingBusiness").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("publicOfferingBusiness").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/view/{bizOrgAplyNo}"})
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                               @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                model.addAttribute("view", true);

                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationAplyService.getList((BizOrganizationAplyViewRequestVO) params(BizOrganizationAplyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_INSTR_IDNTY_STTS", "BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                }
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

    @GetMapping(path={"/update/{bizOrgAplyNo}"})
    public String getUpdate(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                          @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationAplyService.getList((BizOrganizationAplyViewRequestVO) params(BizOrganizationAplyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_INSTR_IDNTY_STTS", "BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobApplyUpdate").toString();
                }
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

    @GetMapping(path={"/survey/{bizOrgAplyNo}"})
    public String getSurvey(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                     @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            CSSearchMap requestParam = CSSearchMap.of(request);
                            requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                            modelSetting(model, Optional.ofNullable(requestParam)
                                    .map(searchMap -> myBusinessStateService.getSurveyList((MyBusinessStateSurveyViewRequestVO) params(MyBusinessStateSurveyViewRequestVO.class, searchMap, pageable)))
                                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_SURVEY_CTGR","BIZ_SURVEY_QITEM_CTGR", "BIZ_SURVEY_STTS"));

                            return new StringBuilder(MYPAGE).append("pobSurvey").toString();
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobSurvey").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobSurvey").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobSurvey").toString();
                }
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

    @GetMapping({"/confirmation/{bizOrgAplyNo}"})
    public String getListIdentifies(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                     @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                    if (organizationCode != null || !organizationCode.isEmpty()) {
                        String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                        if (userId != null || !userId.isEmpty()) {
                            CSSearchMap requestParam = CSSearchMap.of(request);
                            requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                            requestParam.put("bizOrgAplyPic", userId);
                            modelSetting(model, Optional.ofNullable(requestParam)
                                    .map(searchMap -> myBusinessStateService.getConfirmList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, searchMap, pageable)))
                                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_INSTR_IDNTY_STTS", "BIZ_ORG_APLY_STTS"));

                            return new StringBuilder(MYPAGE).append("pobLectureConfirmMonth").toString();
                        } else {
                            model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                            return new StringBuilder(MYPAGE).append("pobLectureConfirmMonth").toString();
                        }
                    } else {
                        model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                        return new StringBuilder(MYPAGE).append("pobLectureConfirmMonth").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobLectureConfirmMonth").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("pobLectureConfirmMonth").toString();
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
     * 사업 참여 현황 - 완료
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping({"/result"})
    public String getCompleteList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("organizationCode", organizationCode);
                                requestParam.put("bizOrgAplyStts", 2);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> myBusinessStateService.getCompleteList((MyBusinessStateCompleteViewRequestVO) params(MyBusinessStateCompleteViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("CON_TEXT_TYPE", "BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("publicOfferingBusinessComplete").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("publicOfferingBusinessComplete").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("publicOfferingBusinessComplete").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("publicOfferingBusinessComplete").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("publicOfferingBusinessComplete").toString();
                }
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

    @GetMapping(path={"/result/view/{bizOrgAplyNo}"})
    public String getCompleteView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                          @Parameter(description = "사업 신청서 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> myBusinessStateService.getCompleteList((MyBusinessStateCompleteViewRequestVO) params(MyBusinessStateCompleteViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobApplyView").toString();
                }
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

    @GetMapping(path={"/report/{bizOrgRsltRptNo}"})
    public String getMypagePobResultReport(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                           @Parameter(description = "결과 보고서 일련 번호") @PathVariable(value = "bizOrgRsltRptNo", required = true) String bizOrgRsltRptNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgRsltRptNo", bizOrgRsltRptNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationRsltRptService.getList((BizOrganizationRsltRptViewRequestVO) params(BizOrganizationRsltRptViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobResultReport").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobResultReport").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobResultReport").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobResultReport").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobResultReport").toString();
                }
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

    @GetMapping(path={"/report/write/{bizOrgAplyNo}"})
    public String getMypagePobResultReportWrite(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                                @Parameter(description = "사업 신청 일련 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                model.addAttribute("type", "write");

                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgAplyNo", bizOrgAplyNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> myBusinessStateService.getCompleteList((MyBusinessStateCompleteViewRequestVO) params(MyBusinessStateCompleteViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                }
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

    @GetMapping(path={"/report/edit/{bizOrgRsltRptNo}"})
    public String getMypagePobResultReportForm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                               @Parameter(description = "결과 보고서 일련 번호") @PathVariable(value = "bizOrgRsltRptNo", required = true) String bizOrgRsltRptNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                model.addAttribute("type", "edit");

                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizOrgRsltRptNo", bizOrgRsltRptNo);
                                requestParam.put("userId", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizOrganizationRsltRptService.getList((BizOrganizationRsltRptViewRequestVO) params(BizOrganizationRsltRptViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_ORG_APLY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobResultReportForm").toString();
                }
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

    @GetMapping(path={"/confirmation"})
    public String getMypagePobLectureConfirm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();
                        String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                        if (organizationCode != null) {
                            CSSearchMap requestParam = CSSearchMap.of(request);
                            requestParam.put("orgCd", organizationCode);
                            modelSetting(model, Optional.ofNullable(requestParam)
                                    .map(searchMap -> myBusinessStateService.getConfirmList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, searchMap, pageable)))
                                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("CON_TEXT_TYPE", "BIZ_ORG_APLY_STTS","BIZ_INSTR_IDNTY_STTS"));
                        } else {
                            CSSearchMap requestParam = CSSearchMap.of(request);
                            requestParam.put("registUserId", userId);
                            modelSetting(model, Optional.ofNullable(requestParam)
                                    .map(searchMap -> myBusinessStateService.getConfirmList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, searchMap, pageable)))
                                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("CON_TEXT_TYPE", "BIZ_ORG_APLY_STTS","BIZ_INSTR_IDNTY_STTS"));
                        }
                        return new StringBuilder(MYPAGE).append("pobLectureConfirm").toString();
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobLectureConfirm").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobLectureConfirm").toString();
                }
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

    @GetMapping(path={"/confirmation/view/{bizInstrIdntyNo}"})
    public String getMypagePobLectureConfirmView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                                 @Parameter(description = "강의 확인서 일련 번호") @PathVariable(value = "bizInstrIdntyNo", required = true) String bizInstrIdntyNo) {
        try {
            if (authentication != null) {
                if (authenticationInfo().getBusinessAuthority() != null) {
                    if (!authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || !((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                        String organizationCode = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getOrganizationCode();

                        if (organizationCode != null || !organizationCode.isEmpty()) {
                            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                            if (userId != null || !userId.isEmpty()) {
                                CSSearchMap requestParam = CSSearchMap.of(request);
                                requestParam.put("bizInstrIdntyNo", bizInstrIdntyNo);
                                requestParam.put("bizOrgAplyPic", userId);
                                modelSetting(model, Optional.ofNullable(requestParam)
                                        .map(searchMap -> bizInstructorIdentifyService.getList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, searchMap, pageable)))
                                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)),  List.of("BIZ_ORG_APLY_STTS","BIZ_INSTR_IDNTY_STTS"));

                                return new StringBuilder(MYPAGE).append("pobLectureConfirmView").toString();
                            } else {
                                model.addAttribute("authError", "읽을 수 있는 권한이 없습니다.");
                                return new StringBuilder(MYPAGE).append("pobLectureConfirmView").toString();
                            }
                        } else {
                            model.addAttribute("authError", "소속 기관 정보가 확인되지 않습니다.");
                            return new StringBuilder(MYPAGE).append("pobLectureConfirmView").toString();
                        }
                    } else {
                        model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                        return new StringBuilder(MYPAGE).append("pobLectureConfirmView").toString();
                    }
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("pobLectureConfirmView").toString();
                }
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

}
