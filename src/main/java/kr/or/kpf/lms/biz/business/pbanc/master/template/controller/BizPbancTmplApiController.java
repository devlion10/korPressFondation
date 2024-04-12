package kr.or.kpf.lms.biz.business.pbanc.master.template.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.pbanc.master.template.service.BizPbancTmplService;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.*;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response.*;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/pbanc/tmpl")
public class BizPbancTmplApiController extends CSViewControllerSupport {
    private final BizPbancTmplService bizPbancTmplService;

    /**
     * 공고사업 템플릿 생성
     *
     * @param request
     * @param response
     * @param bizPbancTemplApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl0ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 생성", description = "사업 공고 템플릿 생성한다.")
    @PostMapping(value = "/0/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl0ApiResponseVO> createInfoTmpl0(HttpServletRequest request, HttpServletResponse response,
                                                                 @NotNull @RequestBody BizPbancTmpl0ApiRequestVO bizPbancTemplApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.createBizPbancTmpl0(bizPbancTemplApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 템플릿 생성 실패")));
    }

    /**
     * 공고사업 템플릿 생성
     *
     * @param request
     * @param response
     * @param bizPbancTemplApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl1ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 생성", description = "사업 공고 템플릿 생성한다.")
    @PostMapping(value = "/1/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl1ApiResponseVO> createInfoTmpl1(HttpServletRequest request, HttpServletResponse response,
                                                                      @NotNull @RequestBody BizPbancTmpl1ApiRequestVO bizPbancTemplApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.createBizPbancTmpl1(bizPbancTemplApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 템플릿 생성 실패")));
    }

    /**
     * 공고사업 템플릿 생성
     *
     * @param request
     * @param response
     * @param bizPbancTemplApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl2ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 생성", description = "사업 공고 템플릿 생성한다.")
    @PostMapping(value = "/2/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl2ApiResponseVO> createInfoTmpl2(HttpServletRequest request, HttpServletResponse response,
                                                                      @NotNull @RequestBody BizPbancTmpl2ApiRequestVO bizPbancTemplApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.createBizPbancTmpl2(bizPbancTemplApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 템플릿 생성 실패")));
    }

    /**
     * 공고사업 템플릿 생성
     *
     * @param request
     * @param response
     * @param bizPbancTemplApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl3ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 생성", description = "사업 공고 템플릿 생성한다.")
    @PostMapping(value = "/3/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl3ApiResponseVO> createInfoTmpl3(HttpServletRequest request, HttpServletResponse response,
                                                                      @NotNull @RequestBody BizPbancTmpl3ApiRequestVO bizPbancTemplApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.createBizPbancTmpl3(bizPbancTemplApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 템플릿 생성 실패")));
    }

    /**
     * 공고사업 템플릿 생성
     *
     * @param request
     * @param response
     * @param bizPbancTemplApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl4ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 생성", description = "사업 공고 템플릿 생성한다.")
    @PostMapping(value = "/4/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl4ApiResponseVO> createInfoTmpl4(HttpServletRequest request, HttpServletResponse response,
                                                                      @NotNull @RequestBody BizPbancTmpl4ApiRequestVO bizPbancTemplApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.createBizPbancTmpl4(bizPbancTemplApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 템플릿 생성 실패")));
    }

    /**
     * 공고사업 템플릿 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl0ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 업데이트", description = "사업 공고 템플릿 업데이트한다.")
    @PutMapping(value = "/0/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl0ApiResponseVO> updateInfoTmpl0(HttpServletRequest request, HttpServletResponse response,
                                                            @NotNull @RequestBody BizPbancTmpl0ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.updateBizPbancTmpl0(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 템플릿 수정 실패")));
    }

    /**
     * 공고사업 템플릿 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl1ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 업데이트", description = "사업 공고 템플릿 업데이트한다.")
    @PutMapping(value = "/1/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl1ApiResponseVO> updateInfoTmpl1(HttpServletRequest request, HttpServletResponse response,
                                                                 @NotNull @RequestBody BizPbancTmpl1ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.updateBizPbancTmpl1(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 템플릿 수정 실패")));
    }

    /**
     * 공고사업 템플릿 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl2ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 업데이트", description = "사업 공고 템플릿 업데이트한다.")
    @PutMapping(value = "/2/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl2ApiResponseVO> updateInfoTmpl2(HttpServletRequest request, HttpServletResponse response,
                                                                 @NotNull @RequestBody BizPbancTmpl2ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.updateBizPbancTmpl2(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 템플릿 수정 실패")));
    }

    /**
     * 공고사업 템플릿 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl3ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 업데이트", description = "사업 공고 템플릿 업데이트한다.")
    @PutMapping(value = "/3/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl3ApiResponseVO> updateInfoTmpl3(HttpServletRequest request, HttpServletResponse response,
                                                                 @NotNull @RequestBody BizPbancTmpl3ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.updateBizPbancTmpl3(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 템플릿 수정 실패")));
    }

    /**
     * 공고사업 템플릿 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl4ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 업데이트", description = "사업 공고 템플릿 업데이트한다.")
    @PutMapping(value = "/4/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl4ApiResponseVO> updateInfoTmpl4(HttpServletRequest request, HttpServletResponse response,
                                                                 @NotNull @RequestBody BizPbancTmpl4ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.updateBizPbancTmpl4(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 템플릿 수정 실패")));
    }

    /**
     * 공고사업 템플릿 삭제
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl0ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 삭제", description = "사업 공고 템플릿 삭제 한다.")
    @DeleteMapping(value = "/0/delete/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfoTmpl0(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizPbancTmpl0ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.deleteBizPbancTmpl0(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 템플릿 삭제 실패")));
    }

    /**
     * 공고사업 템플릿 삭제
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl1ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 삭제", description = "사업 공고 템플릿 삭제 한다.")
    @DeleteMapping(value = "/1/delete/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfoTmpl1(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody BizPbancTmpl1ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.deleteBizPbancTmpl1(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 템플릿 삭제 실패")));
    }

    /**
     * 공고사업 템플릿 삭제
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl2ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 삭제", description = "사업 공고 템플릿 삭제 한다.")
    @DeleteMapping(value = "/2/delete/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfoTmpl2(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody BizPbancTmpl2ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.deleteBizPbancTmpl2(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 템플릿 삭제 실패")));
    }

    /**
     * 공고사업 템플릿 삭제
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl3ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 삭제", description = "사업 공고 템플릿 삭제 한다.")
    @DeleteMapping(value = "/3/delete/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfoTmpl3(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody BizPbancTmpl3ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.deleteBizPbancTmpl3(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 템플릿 삭제 실패")));
    }

    /**
     * 공고사업 템플릿 삭제
     *
     * @param request
     * @param response
     * @param bizPbancApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl", description = "공고사업 템플릿 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl4ApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 삭제", description = "사업 공고 템플릿 삭제 한다.")
    @DeleteMapping(value = "/4/delete/{bizPbancNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfoTmpl4(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody BizPbancTmpl4ApiRequestVO bizPbancApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmplService.deleteBizPbancTmpl4(bizPbancApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 템플릿 삭제 실패")));
    }
}
