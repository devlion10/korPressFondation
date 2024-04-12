package kr.or.kpf.lms.biz.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.communication.event.vo.request.EventViewRequestVO;
import kr.or.kpf.lms.biz.search.service.SearchService;
import kr.or.kpf.lms.biz.search.vo.SearchViewRequestVO;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 검색 API 관련 Controller
 */
@Tag(name = "Search", description = "검색 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/search")
public class SearchApiController extends CSApiControllerSupport {

    private final SearchService searchService;

    /**
     * 검색 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Search", description = "검색 API")
    @Operation(operationId = "Total Search", summary = "통합 검색 조회", description = "통합 검색 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="searchText", required = true) String searchText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> searchService.getList((SearchViewRequestVO) params(SearchViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

}
