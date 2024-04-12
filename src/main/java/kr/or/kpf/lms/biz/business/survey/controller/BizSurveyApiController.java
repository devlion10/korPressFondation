package kr.or.kpf.lms.biz.business.survey.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.survey.service.BizSurveyService;
import kr.or.kpf.lms.biz.business.survey.vo.CreateBizSurvey;
import kr.or.kpf.lms.biz.business.survey.vo.UpdateBizSurvey;
import kr.or.kpf.lms.biz.business.survey.vo.request.BizSurveyApiRequestVO;
import kr.or.kpf.lms.biz.business.survey.vo.request.BizSurveyViewRequestVO;
import kr.or.kpf.lms.biz.business.survey.vo.response.BizSurveyApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
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
import java.util.Optional;

@Tag(name = "Survey", description = "상호평가 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/survey")
public class BizSurveyApiController extends CSViewControllerSupport {
    private final BizSurveyService bizSurveyService;

    /**
     * 상호평가 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Survey", description = "상호평가 API")
    @Operation(operationId = "Survey", summary = "상호평가 조회", description = "상호평가 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizSurveyService.getBizSurveyList((BizSurveyViewRequestVO) params(BizSurveyViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강사 모집 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Survey", description = "상호평가 API")
    @Operation(operationId = "Survey", summary = "상호평가 상세 조회", description = "상호평가 상세 조회한다.")
    @GetMapping(value = "/{SurveyNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizSurveyService.getBizSurvey((BizSurveyViewRequestVO) params(BizSurveyViewRequestVO.class, searchMap, null))));

    }

    /**
     * 상호평가 생성
     *
     * @param request
     * @param response
     * @param bizSurveyApiRequestVO
     * @return
     */
    @Tag(name = "Survey", description = "상호평가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호평가 생성 성공", content = @Content(schema = @Schema(implementation = BizSurveyApiResponseVO.class)))})
    @Operation(operationId="Survey", summary = "상호평가 생성", description = "상호평가 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizSurveyApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {CreateBizSurvey.class}) @NotNull @RequestBody BizSurveyApiRequestVO bizSurveyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyService.createBizSurvey(bizSurveyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3581, "상호평가 생성 실패")));
    }

    /**
     * 상호평가 업데이트
     *
     * @param request
     * @param response
     * @param bizSurveyApiRequestVO
     * @return
     */
    @Tag(name = "Survey", description = "상호평가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호평가 수정 성공", content = @Content(schema = @Schema(implementation = BizSurveyApiResponseVO.class)))})
    @Operation(operationId="Survey", summary = "상호평가 업데이트", description = "상호평가 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizSurveyApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                             @Validated(value = {UpdateBizSurvey.class}) @NotNull @RequestBody BizSurveyApiRequestVO bizSurveyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyService.updateBizSurvey(bizSurveyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3583, "상호평가 수정 실패")));
    }

    /**
     * 상호평가 삭제
     *
     * @param request
     * @param response
     * @param bizSurveyApiRequestVO
     * @return
     */
    @Tag(name = "Survey", description = "상호평가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호평가 삭제 성공", content = @Content(schema = @Schema(implementation = BizSurveyApiResponseVO.class)))})
    @Operation(operationId="Survey", summary = "상호평가 삭제", description = "상호평가 삭제 한다.")
    @DeleteMapping(value = "/delete/{SurveyNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody BizSurveyApiRequestVO bizSurveyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyService.deleteBizSurvey(bizSurveyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3584, "상호평가 삭제 실패")));
    }
}
