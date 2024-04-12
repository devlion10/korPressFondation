package kr.or.kpf.lms.biz.communication.archive.guide.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.communication.archive.guide.service.ArchiveClassGuideService;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.request.ArchiveClassGuideViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
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
 * 참여 / 소통 > 교육 후기방 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/communication/archive")
public class ArchiveClassGuideViewController extends CSViewControllerSupport {

    private static final String COMMUNICATION = "views/community/";

    private final ArchiveClassGuideService archiveClassGuideService;

    /**
     * 참여 / 소통 > 자료실 > 수업 지도안 > 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) 조회
     */
    @GetMapping(path={"/class-guide"})
    public String getTeacherList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                .map(searchMap -> archiveClassGuideService.getList((ArchiveClassGuideViewRequestVO) params(ArchiveClassGuideViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("GUI_TYPE"));
        return new StringBuilder(COMMUNICATION).append("eduPlan").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 수업 지도안 > 1: 교사, 2: 학부모, 3: 기타(다문화/유아/일반) 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param classGuideCode
     * @return
     */
    @GetMapping(path={"/class-guide/view/{classGuideCode}"})
    public String getTeacherView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                @Parameter(description = "수업 지도안 코드") @PathVariable(value = "classGuideCode", required = true) String classGuideCode) {
        CSSearchMap csSearchMap = CSSearchMap.of(request);
        csSearchMap.put("classGuideCode", classGuideCode);
        modelSetting(model,  Optional.ofNullable(csSearchMap)
                .map(searchMap -> archiveClassGuideService.getList((ArchiveClassGuideViewRequestVO) params(ArchiveClassGuideViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("eduPlanView").toString();
    }
}
