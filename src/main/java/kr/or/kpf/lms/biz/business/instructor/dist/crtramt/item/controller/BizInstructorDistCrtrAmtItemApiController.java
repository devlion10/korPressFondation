package kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.service.BizInstructorDistCrtrAmtItemService;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.CreateBizInstructorDistCrtrAmtItem;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.UpdateBizInstructorDistCrtrAmtItem;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request.BizInstructorDistCrtrAmtItemApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request.BizInstructorDistCrtrAmtItemViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.response.BizInstructorDistCrtrAmtItemApiResponseVO;
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

@Tag(name = "Business Instructor Dist Crtr Amt Item", description = "이동거리 기준단가 항목 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/dist/crtramt/item")
public class BizInstructorDistCrtrAmtItemApiController extends CSViewControllerSupport {
    private final BizInstructorDistCrtrAmtItemService bizInstructorDistCrtrAmtItemService;

    /**
     * 이동거리 기준단가 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt Item", description = "이동거리 기준단가 항목 API")
    @Operation(operationId = "Instructor Dist Crtr Amt", summary = "이동거리 기준단가 조회", description = "이동거리 기준단가 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorDistCrtrAmtItemService.getBizInstructorDistCrtrAmtItemList((BizInstructorDistCrtrAmtItemViewRequestVO) params(BizInstructorDistCrtrAmtItemViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 이동거리 기준단가 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt Item", description = "이동거리 기준단가 항목 API")
    @Operation(operationId = "Instructor Dist Crtr Amt", summary = "이동거리 기준단가 상세 조회", description = "이동거리 기준단가 상세 조회한다.")
    @GetMapping(value = "/{bizInstrNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorDistCrtrAmtItemService.getBizInstructorDistCrtrAmtItem((BizInstructorDistCrtrAmtItemViewRequestVO) params(BizInstructorDistCrtrAmtItemViewRequestVO.class, searchMap, null))));

    }

    /**
     * 이동거리 기준단가 생성
     *
     * @param request
     * @param response
     * @param bizInstructorDistCrtrAmtItemApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt Item", description = "이동거리 기준단가 항목 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이동거리 기준단가 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorDistCrtrAmtItemApiResponseVO.class)))})
    @Operation(operationId="Instructor Dist", summary = "이동거리 기준단가 생성", description = "이동거리 기준단가 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorDistCrtrAmtItemApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                                @Validated(value = {CreateBizInstructorDistCrtrAmtItem.class}) @NotNull @RequestBody BizInstructorDistCrtrAmtItemApiRequestVO bizInstructorDistCrtrAmtItemApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorDistCrtrAmtItemService.createBizInstructorDistCrtrAmtItem(bizInstructorDistCrtrAmtItemApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "이동거리 기준단가 생성 실패")));
    }

    /**
     * 이동거리 기준단가 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorDistCrtrAmtItemApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt Item", description = "이동거리 기준단가 항목 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이동거리 기준단가 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorDistCrtrAmtItemApiResponseVO.class)))})
    @Operation(operationId="Instructor Dist Crtr Amt", summary = "이동거리 기준단가 업데이트", description = "이동거리 기준단가 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorDistCrtrAmtItemApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorDistCrtrAmtItem.class}) @NotNull @RequestBody BizInstructorDistCrtrAmtItemApiRequestVO bizInstructorDistCrtrAmtItemApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorDistCrtrAmtItemService.updateBizInstructorDistCrtrAmtItem(bizInstructorDistCrtrAmtItemApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "이동거리 기준단가 수정 실패")));
    }

    /**
     * 이동거리 기준단가 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorDistCrtrAmtItemApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Dist Crtr Amt Item", description = "이동거리 기준단가 항목 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이동거리 기준단가 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorDistCrtrAmtItemApiResponseVO.class)))})
    @Operation(operationId="Instructor Dist", summary = "이동거리 기준단가 삭제", description = "이동거리 기준단가 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorDistCrtrAmtItemNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorDistCrtrAmtItemApiRequestVO bizInstructorDistCrtrAmtItemApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorDistCrtrAmtItemService.deleteBizInstructorDistCrtrAmtItem(bizInstructorDistCrtrAmtItemApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "이동거리 기준단가 삭제 실패")));
    }

}
