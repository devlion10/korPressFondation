package kr.or.kpf.lms.biz.servicecenter.myqna.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.servicecenter.myqna.service.MyQnaService;
import kr.or.kpf.lms.biz.servicecenter.myqna.vo.request.MyQnaViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 1:1 문의 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/service-center/my-qna")
public class MyQnaViewController extends CSViewControllerSupport {

    private static final String MY_QNA = "views/support/";

    private final MyQnaService myQnaService;

    /**
     * 고객센터 > 1:1 문의 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model, Authentication authentication){
        try {
            modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                    .map(searchMap -> myQnaService.getMyQnaList((MyQnaViewRequestVO) params(MyQnaViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("CON_TEXT_TYPE", "QNA_TYPE", "QNA_STATE"));
            return new StringBuilder(MY_QNA).append("myQna").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 고객센터 > 1:1 문의 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/view/{sequenceNo}"})
    public String getView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                @Parameter(description = "1:1문의 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> myQnaService.getMyQnaList((MyQnaViewRequestVO) params(MyQnaViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(MY_QNA).append("myQnaView").toString();
    }

    /**
     * 고객센터 > 1:1 문의 작성
     *
     */
    @GetMapping(path={"/write"})
    public String getWrite() {
        return new StringBuilder(MY_QNA).append("myQnaForm").toString();
    }

    /**
     * 고객센터 > 1:1 문의 수정
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/write/{sequenceNo}"})
    public String getEdit(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "1:1문의 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> myQnaService.getMyQnaList((MyQnaViewRequestVO) params(MyQnaViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(MY_QNA).append("myQnaForm").toString();
    }

}
