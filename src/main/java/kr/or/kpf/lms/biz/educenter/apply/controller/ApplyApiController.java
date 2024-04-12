package kr.or.kpf.lms.biz.educenter.apply.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.educenter.apply.service.ApplyService;
import kr.or.kpf.lms.biz.educenter.apply.vo.CreateApply;
import kr.or.kpf.lms.biz.educenter.apply.vo.UpdateApply;
import kr.or.kpf.lms.biz.educenter.apply.vo.request.ApplyApiRequestVO;
import kr.or.kpf.lms.biz.educenter.apply.vo.request.ApplyViewRequestVO;
import kr.or.kpf.lms.biz.educenter.apply.vo.response.ApplyApiResponseVO;
import kr.or.kpf.lms.biz.educenter.apply.vo.response.ApplyViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 연수원 소개 > 교육장 사용 신청 API 관련 Controller
 */
@Tag(name = "EduCenter", description = "연수원 소개 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/edu-center/apply")
public class ApplyApiController extends CSApiControllerSupport {

    private final ApplyService applyService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param applyViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplyViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody ApplyViewRequestVO applyViewRequestVO) {
        return null;
    }

    /**
     * 교육장 사용 신청 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param sequenceNo
     * @param containTextType
     * @param containText
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @Operation(operationId = "Apply", summary = "교육장 사용 신청 조회", description = "교육장 사용 신청 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="sequenceNo", required = false) BigInteger sequenceNo,
                                          @RequestParam(value="containTextType", required = false) String containTextType,
                                          @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> applyService.getList((ApplyViewRequestVO) params(ApplyViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 교육장 사용 신청 등록 API
     *
     * @param request
     * @param response
     * @param reviewApiRequestVO
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육장 사용 신청 생성 성공", content = @Content(schema = @Schema(implementation = ApplyApiResponseVO.class)))})
    @Operation(operationId = "Apply", summary = "교육장 사용 신청 생성", description = "교육장 사용 신청 데이터를 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplyApiResponseVO> createApply(HttpServletRequest request, HttpServletResponse response,
                                                          @Validated(value = {CreateApply.class}) @NotNull @RequestBody ApplyApiRequestVO reviewApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(applyService.createApply(reviewApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7101, "교육장 사용 신청 데이터 생성 실패")));
    }

    /**
     * 교육장 사용 신청 업데이트 API
     *
     * @param request
     * @param response
     * @param reviewApiRequestVO
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육장 사용 신청 업데이트 성공", content = @Content(schema = @Schema(implementation = ApplyApiResponseVO.class)))})
    @Operation(operationId = "Apply", summary = "교육장 사용 신청 업데이트", description = "교육장 사용 신청 데이터를 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApplyApiResponseVO> updateApply(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdateApply.class}) @NotNull @RequestBody ApplyApiRequestVO reviewApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(applyService.updateApply(reviewApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7102, "교육장 사용 신청 데이터 업데이트 실패")));
    }
}
