package kr.or.kpf.lms.biz.common.page.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.common.page.service.PageService;
import kr.or.kpf.lms.biz.common.page.vo.request.PageViewRequestVO;
import kr.or.kpf.lms.biz.common.page.vo.response.PageViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/**
 * 공통 > 문서 API 관련 Controller
 */
@Tag(name = "Common", description = "공통 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/documents")
public class PageApiController extends CSApiControllerSupport {

    private final PageService pageService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param pageViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody PageViewRequestVO pageViewRequestVO) {
        return null;
    }

    /**
     * 문서 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Common", description = "공통 API")
    @Operation(operationId = "Documents", summary = "문서 조회", description = "문서 데이터를 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> resultPaging(pageService.getList((PageViewRequestVO) params(PageViewRequestVO.class, searchMap, pageable)), Arrays.asList()))
                        .orElse(new HashMap<>()));
    }
}
