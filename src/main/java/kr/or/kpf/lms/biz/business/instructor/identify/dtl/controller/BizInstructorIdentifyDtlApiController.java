package kr.or.kpf.lms.biz.business.instructor.identify.dtl.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.service.BizInstructorIdentifyDtlService;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.CreateBizInstructorIdentifyDtl;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.UpdateBizInstructorIdentifyDtl;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request.BizInstructorIdentifyDtlApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request.BizInstructorIdentifyDtlViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.response.BizInstructorIdentifyDtlApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.identify.service.BizInstructorIdentifyService;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.CreateBizInstructorIdentify;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.UpdateBizInstructorIdentify;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.response.BizInstructorIdentifyApiResponseVO;
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

@Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/identify/dtl")
public class BizInstructorIdentifyDtlApiController extends CSViewControllerSupport {
    private final BizInstructorIdentifyDtlService bizInstructorIdentifyDtlService;

    /**
     * 강의확인서 강의시간표 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
    @Operation(operationId = "Instructor Identify Detail", summary = "강의확인서 강의시간표 생성", description = "강의확인서 강의시간표 신청 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorIdentifyDtlService.getBizInstructorIdentifyDtlList((BizInstructorIdentifyDtlViewRequestVO) params(BizInstructorIdentifyDtlViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강의확인서 강의시간표 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
    @Operation(operationId = "Instructor Identify Detail", summary = "강의확인서 강의시간표 상세 조회", description = "강의확인서 강의시간표 상세 조회한다.")
    @GetMapping(value = "/{bizInstrIdntyDtlNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorIdentifyDtlService.getBizInstructorIdentifyDtl((BizInstructorIdentifyDtlViewRequestVO) params(BizInstructorIdentifyDtlViewRequestVO.class, searchMap, null))));

    }

    /**
     * 강의확인서 강의시간표 생성
     *
     * @param request
     * @param response
     * @param bizInstructorIdentifyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의확인서 강의시간표 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorIdentifyDtlApiResponseVO.class)))})
    @Operation(operationId="Instructor Identify Detail", summary = "강의확인서 강의시간표 생성", description = "강의확인서 강의시간표 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorIdentifyDtlApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                @Validated(value = {CreateBizInstructorIdentifyDtl.class}) @NotNull @RequestBody BizInstructorIdentifyDtlApiRequestVO bizInstructorIdentifyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorIdentifyDtlService.createBizInstructorIdentifyDtl(bizInstructorIdentifyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "강의확인서 강의시간표 생성 실패")));
    }

    /**
     * 강의확인서 강의시간표 생성
     *
     * @param request
     * @param response
     * @param bizInstructorIdentifyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의확인서 강의시간표(복수) 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorIdentifyDtlApiResponseVO.class)))})
    @Operation(operationId="Instructor Identify Detail", summary = "강의확인서 강의시간표(복수) 생성", description = "강의확인서 강의시간표(복수) 생성한다.")
    @PostMapping(value = "/creates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorIdentifyDtlApiResponseVO> createInfoList(HttpServletRequest request, HttpServletResponse response,
                                                                            @Validated(value = {CreateBizInstructorIdentifyDtl.class}) @NotNull @RequestBody List<BizInstructorIdentifyDtlApiRequestVO> bizInstructorIdentifyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorIdentifyDtlService.createBizInstructorIdentifyDtlList(bizInstructorIdentifyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "강의확인서 강의시간표 생성 실패")));
    }
    /**
     * 강의확인서 강의시간표 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorIdentifyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의확인서 강의시간표 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorIdentifyDtlApiResponseVO.class)))})
    @Operation(operationId="Instructor Identify Detail", summary = "강의확인서 강의시간표 업데이트", description = "강의확인서 강의시간표 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorIdentifyDtlApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorIdentifyDtl.class}) @NotNull @RequestBody BizInstructorIdentifyDtlApiRequestVO bizInstructorIdentifyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorIdentifyDtlService.updateBizInstructorIdentifyDtl(bizInstructorIdentifyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "강의확인서 강의시간표 수정 실패")));
    }

    /**
     * 강의확인서 강의시간표 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorIdentifyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Identify Detail", description = "강의확인서 강의시간표 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강의확인서 강의시간표 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorIdentifyDtlApiResponseVO.class)))})
    @Operation(operationId="Instructor Identify Detail", summary = "강의확인서 강의시간표 삭제", description = "강의확인서 강의시간표 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorIdentifyDtlNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorIdentifyDtlApiRequestVO bizInstructorIdentifyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorIdentifyDtlService.deleteBizInstructorIdentifyDtl(bizInstructorIdentifyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "강의확인서 강의시간표 삭제 실패")));
    }
}
