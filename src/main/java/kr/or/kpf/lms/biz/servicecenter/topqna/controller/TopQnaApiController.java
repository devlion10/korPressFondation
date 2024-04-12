package kr.or.kpf.lms.biz.servicecenter.topqna.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.servicecenter.topqna.vo.request.TopQnaViewRequestVO;
import kr.or.kpf.lms.biz.servicecenter.topqna.vo.response.TopQnaViewResponseVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 자주하는 질문 API 관련 Controller
 */
@Tag(name = "Service Center", description = "고객센터 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/service-center/top-qna")
public class TopQnaApiController extends CSViewControllerSupport {

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param noticeViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TopQnaViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody TopQnaViewRequestVO noticeViewRequestVO) {
        return null;
    }
}
