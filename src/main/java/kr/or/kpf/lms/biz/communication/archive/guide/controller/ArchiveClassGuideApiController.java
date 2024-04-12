package kr.or.kpf.lms.biz.communication.archive.guide.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.communication.archive.guide.service.ArchiveClassGuideService;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.request.ArchiveClassGuideViewRequestVO;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.response.ArchiveClassGuideViewResponseVO;
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
 * 참여 / 소통 > 자료실 - 수업지도안 API 관련 Controller
 */
@Tag(name = "Communication", description = "참여 / 소통 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/communication/class-guide")
public class ArchiveClassGuideApiController extends CSApiControllerSupport {

    private final ArchiveClassGuideService archiveClassGuideService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param reviewViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArchiveClassGuideViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody ArchiveClassGuideViewRequestVO reviewViewRequestVO) {
        return null;
    }

    /**
     * 자료실 - 수업지도안 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param classGuideCode
     * @param containTextType
     * @param containText
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @Operation(operationId = "EducationPlan", summary = "자료실 - 수업지도안 조회", description = "자료실 - 수업지도안 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="classGuideCode", required = false) String classGuideCode,
                                          @RequestParam(value="containTextType", required = false) String containTextType,
                                          @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> archiveClassGuideService.getList((ArchiveClassGuideViewRequestVO) params(ArchiveClassGuideViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 자료실 조회수 업데이트
     *
     * @param request
     * @param response
     * @param classGuideCode
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자료실 조회수 - 수업지도안 업데이트 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "EducationPlan", summary = "자료실 - 수업지도안 조회수 업데이트", description = "자료실 - 수업지도안 조회수 업데이트한다.")
    @PutMapping(value = "/update-view-count/{classGuideCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> updateViewCount(HttpServletRequest request, HttpServletResponse response,
                                                               @Parameter(description = "수업지도안 일련 번호") @PathVariable(value = "classGuideCode", required = true) String classGuideCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(archiveClassGuideService.updateViewCount(classGuideCode))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7105, "자료실 - 수업지도안 조회수 업데이트 실패")));
    }
}
