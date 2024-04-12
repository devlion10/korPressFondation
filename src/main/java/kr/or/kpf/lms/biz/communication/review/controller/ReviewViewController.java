package kr.or.kpf.lms.biz.communication.review.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.communication.review.service.ReviewService;
import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewViewRequestVO;
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
import java.util.Arrays;
import java.util.Optional;

/**
 * 참여 / 소통 > 교육 후기방 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/communication/review")
public class ReviewViewController extends CSViewControllerSupport {

    private static final String COMMUNICATION = "views/community/";

    private final ReviewService reviewService;

    /**
     * 참여 / 소통 > 교육 후기방 조회
     */
    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                .map(searchMap -> reviewService.getList((ReviewViewRequestVO) params(ReviewViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "CON_TEXT_TYPE", "EDU_REVIEW_TYPE"));
        return new StringBuilder(COMMUNICATION).append("eduReview").toString();
    }

    /**
     * 참여 / 소통 > 교육 후기방 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/view/{sequenceNo}"})
    public String eduReviewView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                @Parameter(description = "교육 후기 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        /** 이전글 이후글이 필요한 경우 페이징으로 조회 */
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> reviewService.getList((ReviewViewRequestVO) params(ReviewViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "EDU_REVIEW_TYPE"));
        return new StringBuilder(COMMUNICATION).append("eduReviewView").toString();
    }
}
