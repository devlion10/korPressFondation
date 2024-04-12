package kr.or.kpf.lms.biz.business.instructor.asgnm.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.asgnm.service.BizInstructorAsgnmService;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.CreateBizInstructorAsgnm;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.UpdateBizInstructorAsgnm;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.request.BizInstructorAsgnmApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.request.BizInstructorAsgnmViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.response.BizInstructorAsgnmApiResponseVO;
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

@Tag(name = "Business Instructor Asgnm", description = "강사 배정 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/asgnm")
public class BizInstructorAsgnmApiController extends CSViewControllerSupport {
    private final BizInstructorAsgnmService bizInstructorAsgnmService;

    /**
     * 강사 배정 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Asgnm", description = "강사 배정 API")
    @Operation(operationId = "Instructor Asgnm", summary = "강사 배정 생성", description = "강사 배정 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorAsgnmService.getBizInstructorAsgnmList((BizInstructorAsgnmViewRequestVO) params(BizInstructorAsgnmViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강사 배정 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Asgnm", description = "강사 배정 API")
    @Operation(operationId = "Instructor Asgnm", summary = "강사 배정 상세 조회", description = "강사 배정 상세 조회한다.")
    @GetMapping(value = "/{bizInstrAsgnmNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorAsgnmService.getBizInstructorAsgnm((BizInstructorAsgnmViewRequestVO) params(BizInstructorAsgnmViewRequestVO.class, searchMap, null))));

    }

    /**
     * 강사 배정 생성
     *
     * @param request
     * @param response
     * @param bizInstructorAsgnmApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Asgnm", description = "강사 배정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 배정 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorAsgnmApiResponseVO.class)))})
    @Operation(operationId="Instructor Asgnm", summary = "강사 배정 생성", description = "강사 배정 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorAsgnmApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                      @Validated(value = {CreateBizInstructorAsgnm.class}) @NotNull @RequestBody BizInstructorAsgnmApiRequestVO bizInstructorAsgnmApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAsgnmService.createBizInstructorAsgnm(bizInstructorAsgnmApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "강사모집 배정 생성 실패")));
    }

    /**
     * 강사 배정 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorAsgnmApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Asgnm", description = "강사 배정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 배정 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorAsgnmApiResponseVO.class)))})
    @Operation(operationId="Instructor Asgnm", summary = "강사 배정 업데이트", description = "강사 배정 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorAsgnmApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorAsgnm.class}) @NotNull @RequestBody BizInstructorAsgnmApiRequestVO bizInstructorAsgnmApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAsgnmService.updateBizInstructorAsgnm(bizInstructorAsgnmApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "강사 배정 수정 실패")));
    }

    /**
     * 강사 배정 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorAsgnmApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Asgnm", description = "강사 배정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 배정 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorAsgnmApiResponseVO.class)))})
    @Operation(operationId="Instructor Asgnm", summary = "강사 배정 삭제", description = "강사 배정 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorAsgnmNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorAsgnmApiRequestVO bizInstructorAsgnmApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorAsgnmService.deleteBizInstructorAsgnm(bizInstructorAsgnmApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "강사 배정 삭제 실패")));
    }
}
