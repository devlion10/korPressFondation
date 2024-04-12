package kr.or.kpf.lms.biz.business.survey.ans.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.survey.ans.vo.CreateBizSurveyAns;
import kr.or.kpf.lms.biz.business.survey.ans.vo.UpdateBizSurveyAns;
import kr.or.kpf.lms.biz.business.survey.ans.vo.request.BizSurveyAnsApiRequestVO;
import kr.or.kpf.lms.biz.business.survey.ans.vo.request.BizSurveyAnsViewRequestVO;
import kr.or.kpf.lms.biz.business.survey.ans.vo.response.BizSurveyAnsApiResponseVO;
import kr.or.kpf.lms.biz.business.survey.ans.service.BizSurveyAnsService;
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
import java.util.List;
import java.util.Optional;

@Tag(name = "Survey Ans", description = "상호 평가 응답 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/survey/ans")
public class BizSurveyAnsApiController extends CSViewControllerSupport {
    private final BizSurveyAnsService bizSurveyAnsService;


    /**
     * 상호 평가 응답 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Survey Ans", description = "상호 평가 응답 API")
    @Operation(operationId = "Survey Ans", summary = "상호 평가 응답 조회", description = "상호 평가 응답 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizSurveyAnsService.getBizSurveyAnsList((BizSurveyAnsViewRequestVO) params(BizSurveyAnsViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 상호 평가 응답 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Survey Ans", description = "상호 평가 응답 API")
    @Operation(operationId = "Survey Ans", summary = "상호 평가 응답 상세 조회", description = "상호 평가 응답 상세 조회한다.")
    @GetMapping(value = "/{bizSurveyAnsNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizSurveyAnsService.getBizSurveyAns((BizSurveyAnsViewRequestVO) params(BizSurveyAnsViewRequestVO.class, searchMap, null))));
    }

    /**
     * 상호 평가 응답 생성
     *
     * @param request
     * @param response
     * @param bizSurveyAnsApiRequestVO
     * @return
     */
    @Tag(name = "Survey Ans", description = "상호 평가 응답 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호 평가 응답 생성 성공", content = @Content(schema = @Schema(implementation = BizSurveyAnsApiResponseVO.class)))})
    @Operation(operationId="Survey Ans", summary = "상호 평가 응답 생성", description = "상호 평가 응답 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizSurveyAnsApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                @Validated(value = {CreateBizSurveyAns.class}) @NotNull @RequestBody BizSurveyAnsApiRequestVO bizSurveyAnsApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyAnsService.createBizSurveyAns(bizSurveyAnsApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3661, "상호 평가 응답 생성 실패")));
    }

    /**
     * 상호 평가 응답 복수
     *
     * @param request
     * @param response
     * @param bizSurveyAnsApiRequestVO
     * @return
     */
    @Tag(name = "Survey Ans", description = "상호 평가 응답 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호 평가 응답 생성 성공", content = @Content(schema = @Schema(implementation = BizSurveyAnsApiResponseVO.class)))})
    @Operation(operationId="Survey Ans", summary = "상호 평가 응답 생성(복수)", description = "상호 평가 응답 생성한다.")
    @PostMapping(value = "/creates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createInfoAll(HttpServletRequest request, HttpServletResponse response,
                                                                @Validated(value = {CreateBizSurveyAns.class}) @NotNull @RequestBody List<BizSurveyAnsApiRequestVO> bizSurveyAnsApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyAnsService.createBizSurveyAnsAll(bizSurveyAnsApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3661, "상호 평가 응답 생성 실패")));
    }

    /**
     * 상호 평가 응답 업데이트
     *
     * @param request
     * @param response
     * @param bizSurveyAnsApiRequestVO
     * @return
     */
    @Tag(name = "Survey Ans", description = "상호 평가 응답 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호 평가 응답 수정 성공", content = @Content(schema = @Schema(implementation = BizSurveyAnsApiResponseVO.class)))})
    @Operation(operationId="Survey Ans", summary = "상호 평가 응답 업데이트", description = "상호 평가 응답 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizSurveyAnsApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdateBizSurveyAns.class}) @NotNull @RequestBody BizSurveyAnsApiRequestVO bizSurveyAnsApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyAnsService.updateBizSurveyAns(bizSurveyAnsApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3663, "상호 평가 응답 수정 실패")));
    }

    /**
     * 상호 평가 응답 삭제
     *
     * @param request
     * @param response
     * @param bizSurveyAnsApiRequestVO
     * @return
     */
    @Tag(name = "Survey Ans", description = "상호 평가 응답 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "상호 평가 응답 삭제 성공", content = @Content(schema = @Schema(implementation = BizSurveyAnsApiResponseVO.class)))})
    @Operation(operationId="Survey Ans", summary = "상호 평가 응답 삭제", description = "상호 평가 응답 삭제 한다.")
    @DeleteMapping(value = "/delete/{bizSurveyAnsNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizSurveyAnsApiRequestVO bizSurveyAnsApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizSurveyAnsService.deleteBizSurveyAns(bizSurveyAnsApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3664, "상호 평가 응답 삭제 실패")));
    }
}
