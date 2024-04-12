package kr.or.kpf.lms.biz.education.application.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.education.application.service.EducationApplicationService;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationApiRequestVO;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
import kr.or.kpf.lms.biz.education.application.vo.response.EducationApplicationApiResponseVO;
import kr.or.kpf.lms.biz.education.application.vo.response.EducationApplicationViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/**
 * 교육 신청 API 관련 Controller
 */
@Tag(name = "Education Application", description = "교육 신청 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/education/application")
public class EducationApplicaitonApiController extends CSApiControllerSupport {

    private final EducationApplicationService educationApplicationService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param educationApplicationViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EducationApplicationViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody EducationApplicationViewRequestVO educationApplicationViewRequestVO) {
        return null;
    }

    /**
     * 교육 신청 일정 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Education Application", description = "교육 신청 API")
    @ApiImplicitParams(
        {@ApiImplicitParam(
            name = "startDate"
            , value = "조회 시작일자(YYYY-MM-DD)"
            , required = true
            , dataType = "string"
            , example = "2022-09-01"
            , paramType = "query"
            , defaultValue = ""),
        @ApiImplicitParam(
            name = "endDate"
            , value = "조회 종료일자(YYYY-MM-DD)"
            , required = true
            , dataType = "string"
            , example = "2022-09-30"
            , paramType = "query"
            , defaultValue = "")
    })
    @Operation(operationId = "EducationApplication", summary = "교육 신청 일정 조회", description = "교육 신청 일정 조회한다.")
    @GetMapping(path = {"", "/", "/page"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
        @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> resultPaging(educationApplicationService.getList((EducationApplicationViewRequestVO) params(EducationApplicationViewRequestVO.class, searchMap, pageable)), Arrays.asList("APLYABLE_TYPE", "EDU_TYPE")))
                        .orElse(new HashMap<>()));
    }

    public interface CreateEducationApplication {}

    /**
     * 교육 신청 API
     *
     * @param request
     * @param response
     * @param educationApplicationApiRequestVO
     * @param applyFile
     * @return
     */
    @Tag(name = "Education Application", description = "교육 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 신청 성공", content = @Content(schema = @Schema(implementation = EducationApplicationApiResponseVO.class)))})
    @Operation(operationId = "EducationApplication", summary = "교육 신청", description = "교육 신청 데이터를 생성한다.")
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<EducationApplicationApiResponseVO> createEducationApplication(HttpServletRequest request, HttpServletResponse response,
                                                                                        @Validated(value = {CreateEducationApplication.class}) @NotNull @RequestPart(required = true, value = "requestObject") EducationApplicationApiRequestVO educationApplicationApiRequestVO,
                                                                                        @RequestPart(required = false, value = "applyFile") MultipartFile applyFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(educationApplicationService.createEducationApplication(educationApplicationApiRequestVO, applyFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3021, "교육 과정 신청 데이터 생성 실패")));
    }

    /**
     * 교육 신청 취소 API
     *
     * @param request
     * @param response
     * @param applicationNo
     * @return
     */
    @Tag(name = "Education Application", description = "교육 신청 취소 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 신청 취소 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "EducationApplication", summary = "교육 신청 취소", description = "교육 신청 데이터를 취소한다.")
    @PreAuthorize("isAuthenticated()")
    @PutMapping(value = "/cancel/{applicationNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> createEducationApplication(HttpServletRequest request, HttpServletResponse response,
            @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(educationApplicationService.cancelEducationApplication(applicationNo))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3025, "교육 과정 신청 취소 실패")));
    }
}
