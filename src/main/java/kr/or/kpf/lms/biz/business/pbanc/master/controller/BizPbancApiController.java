package kr.or.kpf.lms.biz.business.pbanc.master.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.pbanc.master.service.BizPbancService;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.CreateBizPbanc;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.UpdateBizPbanc;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancApiResponseVO;
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

@Tag(name = "Business Pbanc", description = "공고사업 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/pbanc")
public class BizPbancApiController extends CSViewControllerSupport {
    private final BizPbancService bizPbancService;


    /**
     * 공고사업 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @Operation(operationId = "Business Pbanc", summary = "사업 공고 조회", description = "사업 공고 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizPbancService.getBizPbancList((BizPbancViewRequestVO) params(BizPbancViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 공고사업 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Pbanc", description = "공고사업 API")
    @Operation(operationId = "Business Pbanc", summary = "공고사업 상세 조회", description = "공고사업 상세 조회한다.")
    @GetMapping(value = "/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizPbancService.getBizPbanc((BizPbancViewRequestVO) params(BizPbancViewRequestVO.class, searchMap, null))));

    }

    /**
     * 공고사업 생성
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc", summary = "사업 공고 생성", description = "사업 공고 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {CreateBizPbanc.class}) @NotNull @RequestBody BizPbancApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancService.createBizPbanc(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 생성 실패")));
    }

    /**
     * 공고사업 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc", summary = "사업 공고 업데이트", description = "사업 공고 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdateBizPbanc.class}) @NotNull @RequestBody BizPbancApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancService.updateBizPbanc(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 수정 실패")));
    }

    /**
     * 공고사업 삭제
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc", summary = "사업 공고 삭제", description = "사업 공고 삭제 한다.")
    @DeleteMapping(value = "/delete/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizPbancApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancService.deleteBizPbanc(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 삭제 실패")));
    }
}
