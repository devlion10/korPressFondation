package kr.or.kpf.lms.biz.business.pbanc.rslt.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.business.pbanc.master.service.BizPbancService;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.service.BizPbancRsltService;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltViewRequestVO;
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
import java.util.Arrays;
import java.util.Optional;

/**
 * 사업 공고 조회 Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/business/pbanc/result")
public class BizPbancRsltViewController extends CSViewControllerSupport {
    private static final String BUSINESS = "views/business/";
    private final BizPbancRsltService bizPbancRsltService;

    @GetMapping("/{bizPbancNo}")
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "사업 공고 일련 번호") @PathVariable(value = "bizPbancNo", required = true) String bizPbancNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("bizPbancNo", bizPbancNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> bizPbancRsltService.getBizPbancRsltList((BizPbancRsltViewRequestVO) params(BizPbancRsltViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(BUSINESS).append("bizPublicAnnounceResult").toString();
    }

}
