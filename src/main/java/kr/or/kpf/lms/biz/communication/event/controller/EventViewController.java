package kr.or.kpf.lms.biz.communication.event.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.communication.event.service.EventService;
import kr.or.kpf.lms.biz.communication.event.vo.request.EventViewRequestVO;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 이벤트/설문 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/communication/event")
public class EventViewController extends CSViewControllerSupport {

    private static final String COMMUNICATION = "views/community/";

    private final EventService eventService;

    /**
     * 이벤트/설문 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {

        modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                .map(searchMap -> eventService.getList((EventViewRequestVO) params(EventViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("event").toString();
    }

    /**
     * 이벤트/설문 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/view/{sequenceNo}"})
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "이벤트 시퀀스 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);

        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> eventService.getList((EventViewRequestVO) params(EventViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("eventView").toString();
    }
}
