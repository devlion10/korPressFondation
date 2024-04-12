package kr.or.kpf.lms.biz.business.instructor.aply.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.instructor.aply.service.BizInstructorAplyService;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/business/instructor")
public class BizInstructorAplyViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/";
    private final BizInstructorAplyService bizInstructorAplyService;

    @GetMapping("/apply/{bizInstrAplyNo}")
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "강사 신청 일련 번호") @PathVariable(value = "bizInstrAplyNo", required = true) String bizInstrAplyNo) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("bizInstrAplyNo", bizInstrAplyNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> bizInstructorAplyService.getBizInstructorAplyList((BizInstructorAplyViewRequestVO) params(BizInstructorAplyViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), List.of("BIZ_PBANC_INSTR_SLCTN_METH","BIZ_INSTR_STTS", "BIZ_ORG_APLY_STTS", "BIZ_INSTR_APLY_STTS"));
        return new StringBuilder(BUSINESS).append("").toString();
    }
}
