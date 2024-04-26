package kr.or.kpf.lms.biz.mypage.instructorstate.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.service.BizInstructorClclnDdlnService;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.response.BizInstructorClclnDdlnApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.service.BizInstructorDistCrtrAmtService;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.request.BizInstructorDistCrtrAmtViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.service.BizInstructorIdentifyService;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionViewRequestVO;
import kr.or.kpf.lms.biz.mypage.instructorstate.service.MyInstructorStateService;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.request.*;
import kr.or.kpf.lms.biz.user.instructor.service.InstructorService;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.response.InstructorApiResponseVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.entity.BizInstructorAply;
import kr.or.kpf.lms.repository.entity.BizInstructorIdentify;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.list.UnmodifiableList;
import org.springframework.data.domain.Page;
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
import java.time.LocalDate;
import java.util.*;

/**
 * 강사참여 현황 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/mypage/instructor-state")
public class MyInstructorStateViewController extends CSViewControllerSupport {

    private static final String MYPAGE = "views/mypage/";
    private final MyInstructorStateService myInstructorStateService;
    private final BizInstructorIdentifyService bizInstructorIdentifyService;
    private final BizInstructorClclnDdlnService bizInstructorClclnDdlnService;
    private final BizInstructorDistCrtrAmtService bizInstructorDistCrtrAmtService;
    private final InstructorService instructorService;


    /**
     * 강사 모집
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/recruit"})
    public String getMypageInstructorRecruit(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    requestParam.put("bizInstrPbancStts", 1);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getApplyList((MyInstructorStateApplyViewRequestVO) params(MyInstructorStateApplyViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE", "BIZ_INSTR_STTS", "BIZ_INSTR_APLY_STTS"));
                    return new StringBuilder(MYPAGE).append("InstructorRecruit").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("InstructorRecruit").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("InstructorRecruit").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/recruit/result"})
    public String getMypageInstructorRecruitResult(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    requestParam.put("bizInstrAplyStts", 1);
                    requestParam.put("bizInstrPbancStts", 1);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getApplyResultList((MyInstructorStateApplyResultViewRequestVO) params(MyInstructorStateApplyResultViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE", "BIZ_INSTR_STTS", "BIZ_INSTR_APLY_STTS"));



                    return new StringBuilder(MYPAGE).append("InstructorRecruitComplete").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("InstructorRecruitComplete").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("InstructorRecruitComplete").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/recruit/result/{bizInstrAplyNo}"})
    public String getMypageApplyView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                               @Parameter(description = "강의 신청서 일련 번호") @PathVariable(value = "bizInstrAplyNo", required = true) String bizInstrAplyNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    requestParam.put("bizInstrAplyNo", bizInstrAplyNo);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getApplyResultList((MyInstructorStateApplyResultViewRequestVO) params(MyInstructorStateApplyResultViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_STTS", "BIZ_INSTR_APLY_STTS"));
                    return new StringBuilder(MYPAGE).append("irApply").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irApply").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irApply").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/distance/{bizInstrAplyNo}"})
    public String getMypagedistanceForm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                        @Parameter(description = "강사 신청 일련 번호") @PathVariable(value = "bizInstrAplyNo", required = true) String bizInstrAplyNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {

                    int year = LocalDate.now().getYear();
                    CSSearchMap requestValue = CSSearchMap.of(request);
                    requestValue.put("bizInstrDistCrtrAmtYr", year);
                    Page<?> clclDdlnAmt = bizInstructorDistCrtrAmtService.getBizInstructorDistCrtrAmtList((BizInstructorDistCrtrAmtViewRequestVO) params(BizInstructorDistCrtrAmtViewRequestVO.class, requestValue, pageable));
                    model.addAttribute("clclDdlnAmt", clclDdlnAmt);

                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    requestParam.put("bizInstrAplyNo", bizInstrAplyNo);
                    requestParam.put("bizInstrAplyStts", 2);

                    CSSearchMap param = CSSearchMap.of(request);
                    String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                    param.put("instrId", userId);
                    param.put("instrCategory", Code.INSTR_CTGR.INSTR.enumCode);
                    List<InstructorApiResponseVO> instr = instructorService.getAllList((InstructorViewRequestVO) params(InstructorViewRequestVO.class, param, pageable));

                    if (instr.size() > 0 && !instr.isEmpty())
                        model.addAttribute("instr", instr.get(0));

                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getApplyResultList((MyInstructorStateApplyResultViewRequestVO) params(MyInstructorStateApplyResultViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS"));
                    return new StringBuilder(MYPAGE).append("irDistanceForm").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irDistanceForm").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irDistanceForm").toString();
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
     * 강의 현황
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/lecture"})
    public String getMypageInstructor(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                    BizInstructorClclnDdlnApiResponseVO bizInstructorClclnDdln = bizInstructorClclnDdlnService.findOneByYearAndMonth();

                    if (bizInstructorClclnDdln != null && !bizInstructorClclnDdln.equals(null))
                        model.addAttribute("bizInstructorClclnDdln", bizInstructorClclnDdln);

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    requestParam.put("bizInstrAplyStts", 2);
                    requestParam.put("standClclnDdlnNearDate", bizInstructorClclnDdln);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getList((MyInstructorStateViewRequestVO) params(MyInstructorStateViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList(""));
                    return new StringBuilder(MYPAGE).append("irEduStatus").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irEduStatus").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduStatus").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/lecture/confirm/write/{bizInstrAplyNo}"})
    public String getMypageInstructorIdntyWrite(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication, @PathVariable(value = "bizInstrAplyNo", required = true) String bizInstrAplyNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();


                    /* 거리 비교할 dtl 조회 */
                    CSSearchMap requestAnother = CSSearchMap.of(request);
                    requestAnother.put("bizInstrAplyInstrId", registUserId);
                    requestAnother.put("bizInstrAplyStts", 2);
                    requestAnother.put("notBizInstrAplyNo", bizInstrAplyNo);
                    Pageable anotherPage = Pageable.ofSize(50);
                    Page<?> anotherIdentify = myInstructorStateService.getList((MyInstructorStateViewRequestVO) params(MyInstructorStateViewRequestVO.class, requestAnother, anotherPage));
                    model.addAttribute("anotherIdentify", anotherIdentify);

                    /* 강사료 정산기준 조회 */
                    int year = LocalDate.now().getYear();
                    CSSearchMap requestValue = CSSearchMap.of(request);
                    requestValue.put("bizInstrDistCrtrAmtYr", year);
                    Page<?> clclDdlnAmt = bizInstructorDistCrtrAmtService.getBizInstructorDistCrtrAmtList((BizInstructorDistCrtrAmtViewRequestVO) params(BizInstructorDistCrtrAmtViewRequestVO.class, requestValue, pageable));
                    model.addAttribute("clclDdlnAmt", clclDdlnAmt);

                    /* 강사신청정보 content 검색 */
                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    requestParam.put("bizInstrAplyStts", 2);
                    requestParam.put("bizInstrAplyNo", bizInstrAplyNo);
                    requestParam.put("standClclnDdlnNearDate", bizInstructorClclnDdlnService.findOneByYearAndMonth());
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getList((MyInstructorStateViewRequestVO) params(MyInstructorStateViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_INSTR_IDNTY_STTS"));
                    return new StringBuilder(MYPAGE).append("irEduConfirmForm").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irEduConfirmForm").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduConfirmForm").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/lecture/confirm/edit/{bizInstrIdntyNo}"})
    public String getMypageInstructorIdntyForm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                               @Parameter(description = "강의 확인서 일련 번호") @PathVariable(value = "bizInstrIdntyNo", required = true) String bizInstrIdntyNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("registUserId", registUserId);
                    requestParam.put("bizInstrIdntyStts", 1);
                    requestParam.put("bizInstrIdntyNo", bizInstrIdntyNo);

                    /* 거리 비교할 dtl 조회 */
                    Page <?> bizIdntyList = bizInstructorIdentifyService.getList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, requestParam, pageable));
                    BizInstructorIdentify bizIdnty = (BizInstructorIdentify) bizIdntyList.getContent().get(0);
                    CSSearchMap requestAnother = CSSearchMap.of(request);
                    requestAnother.put("bizInstrAplyInstrId", registUserId);
                    requestAnother.put("notBizInstrAplyNo", bizIdnty.getBizInstructorAply().getBizInstrAplyNo());
                    requestAnother.put("searchYm", bizIdnty.getBizInstrIdntyYm().substring(0,4)+"-"+bizIdnty.getBizInstrIdntyYm().substring(4,6));
                    requestAnother.put("bizInstrAplyStts", 2);
                    Pageable anotherPage = Pageable.ofSize(50);
                    Page<?> anotherIdentify = myInstructorStateService.getList((MyInstructorStateViewRequestVO) params(MyInstructorStateViewRequestVO.class, requestAnother, anotherPage));
                    model.addAttribute("anotherIdentify", anotherIdentify);


                    int year = LocalDate.now().getYear();
                    CSSearchMap requestValue = CSSearchMap.of(request);
                    requestValue.put("bizInstrDistCrtrAmtYr", year);
                    Page<?> clclDdlnAmt = bizInstructorDistCrtrAmtService.getBizInstructorDistCrtrAmtList((BizInstructorDistCrtrAmtViewRequestVO) params(BizInstructorDistCrtrAmtViewRequestVO.class, requestValue, pageable));
                    model.addAttribute("clclDdlnAmt", clclDdlnAmt);

                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> bizInstructorIdentifyService.getList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_INSTR_IDNTY_STTS"));
                    return new StringBuilder(MYPAGE).append("irEduConfirmForm").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irEduConfirmForm").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduConfirmForm").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/lecture/confirm/view/{bizInstrIdntyNo}"})
    public String getMypageInstructorIdntyView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                               @Parameter(description = "강의 확인서 일련 번호") @PathVariable(value = "bizInstrIdntyNo", required = true) String bizInstrIdntyNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("registUserId", registUserId);
                    requestParam.put("bizInstrIdntyStts", 1);
                    requestParam.put("bizInstrIdntyNo", bizInstrIdntyNo);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> bizInstructorIdentifyService.getList((BizInstructorIdentifyViewRequestVO) params(BizInstructorIdentifyViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_INSTR_IDNTY_STTS"));
                    return new StringBuilder(MYPAGE).append("irEduConfirmView").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irEduConfirmView").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduConfirmView").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/lecture/complete"})
    public String getMypageInstructorComplete(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        if (authenticationInfo().getBusinessAuthority() != null) {
            if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                CSSearchMap requestParam = CSSearchMap.of(request);
                requestParam.put("userId", userId);
                modelSetting(model, Optional.ofNullable(requestParam)
                        .map(searchMap -> myInstructorStateService.getCompleteList((MyInstructorStateCompleteViewRequestVO) params(MyInstructorStateCompleteViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_SURVEY_CTGR", "BIZ_SURVEY_STTS"));
                return new StringBuilder(MYPAGE).append("irEduStatusComplete").toString();
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduStatusComplete").toString();
            }
        } else {
            model.addAttribute("authError", "접근이 제한된 페이지입니다.");
            return new StringBuilder(MYPAGE).append("irEduStatusComplete").toString();
        }
    }

    @GetMapping(path={"/lecture/forme"})
    public String getMypageInstructorFormE(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
                    model.addAttribute("instructorInfo",instructorService.getInstrInfo(userId));
                    return new StringBuilder(MYPAGE).append("irEduStatusFormE").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irEduStatusFormE").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduStatusFormE").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/calculate"})
    public String getMypageInstructorCalcuate(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("registUserId", registUserId);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getCalculateList((MyInstructorStateCalculateViewRequestVO) params(MyInstructorStateCalculateViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS"));
                    return new StringBuilder(MYPAGE).append("irEduStatusCalculate").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irEduStatusCalculate").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irEduStatusCalculate").toString();
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
     * 강사 지원 문의
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/qna")
    public String getMypageIrQna(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("registUserId", registUserId);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getBizInstructorQuestionList((BizInstructorQuestionViewRequestVO) params(BizInstructorQuestionViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("CON_TEXT_TYPE", "BIZ_INSTR_APLY_STTS", "BIZ_INSTR_QSTN_STTS","QNA_TYPE"));
                    return new StringBuilder(MYPAGE).append("irInstructorQna").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irInstructorQna").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irInstructorQna").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/qna/write"})
    public String getMypageIrQnaWrite(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication){
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("bizInstrAplyInstrId", registUserId);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getApplyResultList((MyInstructorStateApplyResultViewRequestVO) params(MyInstructorStateApplyResultViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_INSTR_QSTN_STTS","QNA_TYPE"));
                    return new StringBuilder(MYPAGE).append("irInstructorQnaForm").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irInstructorQnaForm").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irInstructorQnaForm").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/qna/write/{bizInstrQstnNo}"})
    public String getMypageIrQnaForm(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                     @Parameter(description = "강사 지원 문의 일련 번호") @PathVariable(value = "bizInstrQstnNo", required = true) String bizInstrQstnNo) {
        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("registUserId", registUserId);
                    requestParam.put("bizInstrQstnNo", bizInstrQstnNo);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getBizInstructorQuestionList((BizInstructorQuestionViewRequestVO) params(BizInstructorQuestionViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_INSTR_QSTN_STTS","QNA_TYPE"));
                    return new StringBuilder(MYPAGE).append("irInstructorQnaForm").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irInstructorQnaForm").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irInstructorQnaForm").toString();
            }
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    @GetMapping(path={"/qna/view/{bizInstrQstnNo}"})
    public String getMypageIrQnaView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication,
                                     @Parameter(description = "강사 지원 문의 일련 번호") @PathVariable(value = "bizInstrQstnNo", required = true) String bizInstrQstnNo) {

        try {
            if (authenticationInfo().getBusinessAuthority() != null) {
                if (authenticationInfo().getBusinessAuthority().contains(Code.BIZ_AUTH.INSTR.enumCode) || ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getBizAuth().contains(Code.BIZ_AUTH.INSTR.enumCode)) {
                    String registUserId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();

                    CSSearchMap requestParam = CSSearchMap.of(request);
                    requestParam.put("registUserId", registUserId);
                    requestParam.put("bizInstrQstnNo", bizInstrQstnNo);
                    modelSetting(model, Optional.ofNullable(requestParam)
                            .map(searchMap -> myInstructorStateService.getBizInstructorQuestionList((BizInstructorQuestionViewRequestVO) params(BizInstructorQuestionViewRequestVO.class, searchMap, pageable)))
                            .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_INSTR_APLY_STTS", "BIZ_INSTR_QSTN_STTS","QNA_TYPE"));
                    return new StringBuilder(MYPAGE).append("irInstructorQnaView").toString();
                } else {
                    model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                    return new StringBuilder(MYPAGE).append("irInstructorQnaView").toString();
                }
            } else {
                model.addAttribute("authError", "접근이 제한된 페이지입니다.");
                return new StringBuilder(MYPAGE).append("irInstructorQnaView").toString();
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
