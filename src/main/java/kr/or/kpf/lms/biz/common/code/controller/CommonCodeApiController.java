package kr.or.kpf.lms.biz.common.code.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.code.service.CommonCodeService;
import kr.or.kpf.lms.biz.common.code.vo.request.CommonCodeViewRequestVO;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Optional;


/**
 * 공통 API
 */
@Tag(name = "Common", description = "공통 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/system/code")
public class CommonCodeApiController extends CSApiControllerSupport {

    private final CommonCodeService commonService;

    /**
     * 공통 코드 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param upIndividualCode
     * @param codeName
     * @return
     */
    @Tag(name = "Common", description = "공통 API")
    @ApiImplicitParams(
        {@ApiImplicitParam(
                name = "codeName"
                , value = "상위 코드명"
                , required = false
                , dataType = "string"
                , example = "교육"
                , paramType = "query"
                , defaultValue = ""),
        @ApiImplicitParam(
                name = "upIndividualCode"
                , value = "상위 코드"
                , required = false
                , dataType = "string"
                , example = "CODE00008"
                , paramType = "query"
                , defaultValue = "")
        })
    @Operation(operationId = "System", summary = "공통 코드 조회 ", description = "공통 코드 조회 데이터를 생성한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
        @RequestParam(value="upIndividualCode", required = false) String upIndividualCode, @RequestParam(value="codeName", required = false) String codeName) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> commonService.getList((CommonCodeViewRequestVO) params(CommonCodeViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }
}
