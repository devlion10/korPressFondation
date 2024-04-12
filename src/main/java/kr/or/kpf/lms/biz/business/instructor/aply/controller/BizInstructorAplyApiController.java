package kr.or.kpf.lms.biz.business.instructor.aply.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.aply.service.BizInstructorAplyService;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.CreateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.UpdateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.response.BizInstructorAplyApiResponseVO;
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

@Tag(name = "Business Instructor Aply", description = "강사 신청 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/aply")
public class BizInstructorAplyApiController extends CSViewControllerSupport {
    private final BizInstructorAplyService bizInstructorAplyService;

    /**
     * 강사 모집 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Aply", description = "강사 신청 API")
    @Operation(operationId = "Instructor Aply", summary = "강사 신청 조회", description = "강사 신청 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorAplyService.getBizInstructorAplyList((BizInstructorAplyViewRequestVO) params(BizInstructorAplyViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강사 모집 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Aply", description = "강사 신청 API")
    @Operation(operationId = "Instructor Aply", summary = "강사 신청 상세 조회", description = "강사 신청 상세 조회한다.")
    @GetMapping(value = "/{bizInstrAplyNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorAplyService.getBizInstructorAply((BizInstructorAplyViewRequestVO) params(BizInstructorAplyViewRequestVO.class, searchMap, null))));

    }

    /**
     * 강사 모집 생성(복수)
     *
     * @param request
     * @param response
     * @param bizInstructorAplyApiRequestVOs
     * @return
     */
    @Tag(name = "Business Instructor Aply", description = "강사 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 신청 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorAplyApiResponseVO.class)))})
    @Operation(operationId="Instructor Aply", summary = "강사 신청 생성", description = "강사 신청 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                           @Validated(value = {CreateBizInstructorAply.class}) @NotNull @RequestBody List<BizInstructorAplyApiRequestVO> bizInstructorAplyApiRequestVOs) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAplyService.createBizInstructorAplies(bizInstructorAplyApiRequestVOs))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "강사모집 생성 실패")));
    }

    /**
     * 강사 신청 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Aply", description = "강사 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 신청 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorAplyApiResponseVO.class)))})
    @Operation(operationId="Instructor Aply", summary = "강사 신청 업데이트", description = "강사 신청 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorAplyApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorAply.class}) @NotNull @RequestBody BizInstructorAplyApiRequestVO bizInstructorAplyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAplyService.updateBizInstructorAply(bizInstructorAplyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "강사 신청 수정 실패")));
    }

    /**
     * 강사 신청 업데이트 (복수)
     *
     * @param request
     * @param response
     * @param bizInstructorAplyApiRequestVOs
     * @return
     */
    @Tag(name = "Business Instructor Aply", description = "강사 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 신청 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorAplyApiResponseVO.class)))})
    @Operation(operationId="Instructor Aply", summary = "강사 신청 업데이트", description = "강사 신청 업데이트한다.")
    @PutMapping(value = "/updates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                     @Validated(value = {UpdateBizInstructorAply.class}) @NotNull @RequestBody List<BizInstructorAplyApiRequestVO> bizInstructorAplyApiRequestVOs) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAplyService.updateBizInstructorAplies(bizInstructorAplyApiRequestVOs))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3623, "강사 신청 수정 실패")));
    }

    /**
     * 강사 신청 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Aply", description = "강사 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 신청 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorAplyApiResponseVO.class)))})
    @Operation(operationId="Instructor Aply", summary = "강사 신청 삭제", description = "강사 신청 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorAplyNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorAplyApiRequestVO bizInstructorAplyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAplyService.deleteBizInstructorAply(bizInstructorAplyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "강사 신청 삭제 실패")));
    }
}
