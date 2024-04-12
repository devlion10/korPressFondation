package kr.or.kpf.lms.biz.business.instructor.dist.crtramt.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.service.BizInstructorDistCrtrAmtService;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.CreateBizInstructorDistCrtrAmt;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.request.BizInstructorDistCrtrAmtApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.request.BizInstructorDistCrtrAmtViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.response.BizInstructorDistCrtrAmtApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.UpdateBizInstructorDist;
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

@Tag(name = "Business Instructor Dist Crtr Amt", description = "이동거리 기준단가 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/dist/crtramt")
public class BizInstructorDistCrtrAmtApiController extends CSViewControllerSupport {
    private final BizInstructorDistCrtrAmtService bizInstructorDistCrtrAmtService;

    /**
     * 이동거리 기준단가 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt", description = "이동거리 기준단가 API")
    @Operation(operationId = "Instructor Dist Crtr Amt", summary = "이동거리 기준단가 조회", description = "이동거리 기준단가 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorDistCrtrAmtService.getBizInstructorDistCrtrAmtList((BizInstructorDistCrtrAmtViewRequestVO) params(BizInstructorDistCrtrAmtViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 이동거리 기준단가 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt", description = "이동거리 기준단가 API")
    @Operation(operationId = "Instructor Dist Crtr Amt", summary = "이동거리 기준단가 상세 조회", description = "이동거리 기준단가 상세 조회한다.")
    @GetMapping(value = "/{bizInstrNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorDistCrtrAmtService.getBizInstructorDistCrtrAmt((BizInstructorDistCrtrAmtViewRequestVO) params(BizInstructorDistCrtrAmtViewRequestVO.class, searchMap, null))));

    }

    /**
     * 이동거리 기준단가 생성
     *
     * @param request
     * @param response
     * @param bizInstructorDistCrtrAmtApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt", description = "이동거리 기준단가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이동거리 기준단가 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorDistCrtrAmtApiResponseVO.class)))})
    @Operation(operationId="Instructor Dist", summary = "이동거리 기준단가 생성", description = "이동거리 기준단가 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorDistCrtrAmtApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                            @Validated(value = {CreateBizInstructorDistCrtrAmt.class}) @NotNull @RequestBody BizInstructorDistCrtrAmtApiRequestVO bizInstructorDistCrtrAmtApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorDistCrtrAmtService.createBizInstructorDistCrtrAmt(bizInstructorDistCrtrAmtApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "이동거리 기준단가 생성 실패")));
    }

    /**
     * 이동거리 기준단가 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorDistCrtrAmtApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt", description = "이동거리 기준단가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이동거리 기준단가 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorDistCrtrAmtApiResponseVO.class)))})
    @Operation(operationId="Instructor Dist Crtr Amt", summary = "이동거리 기준단가 업데이트", description = "이동거리 기준단가 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorDistCrtrAmtApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorDist.class}) @NotNull @RequestBody BizInstructorDistCrtrAmtApiRequestVO bizInstructorDistCrtrAmtApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorDistCrtrAmtService.updateBizInstructorDistCrtrAmt(bizInstructorDistCrtrAmtApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "이동거리 기준단가 수정 실패")));
    }

    /**
     * 이동거리 기준단가 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorDistCrtrAmtApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt", description = "이동거리 기준단가 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이동거리 기준단가 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorDistCrtrAmtApiResponseVO.class)))})
    @Operation(operationId="Instructor Dist", summary = "이동거리 기준단가 삭제", description = "이동거리 기준단가 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorDistCrtrAmtNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorDistCrtrAmtApiRequestVO bizInstructorDistCrtrAmtApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorDistCrtrAmtService.deleteBizInstructorDistCrtrAmt(bizInstructorDistCrtrAmtApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "이동거리 기준단가 삭제 실패")));
    }

}
