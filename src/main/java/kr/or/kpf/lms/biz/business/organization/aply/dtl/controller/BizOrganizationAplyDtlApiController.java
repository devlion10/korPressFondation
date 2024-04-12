package kr.or.kpf.lms.biz.business.organization.aply.dtl.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.service.BizOrganizationAplyDtlService;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.CreateBizOrganizationAplyDtl;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.UpdateBizOrganizationAplyDtl;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.request.BizOrganizationAplyDtlApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.request.BizOrganizationAplyDtlViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.vo.response.BizOrganizationAplyDtlApiResponseVO;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/organization/aply/dtl")
public class BizOrganizationAplyDtlApiController extends CSViewControllerSupport {
    private final BizOrganizationAplyDtlService bizOrganizationAplyDtlService;

    /**
     * 사업 공고 신청 수업계획서 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @Operation(operationId = "Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 상세 조회", description = "사업 공고 신청 수업계획서 상세 조회한다.")
    @GetMapping(value = "/{bizOrgAplyDtlNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizOrganizationAplyDtlService.getBizInstructorAplyDtl((BizOrganizationAplyDtlViewRequestVO) params(BizOrganizationAplyDtlViewRequestVO.class, searchMap, null))));

    }

    /**
     * 수업계획서 (복수) 생성
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyDtlApiRequestVOs
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 수업계획서 생성 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyDtlApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 (복수) 생성", description = "사업 공고 신청 수업계획서 (복수) 생성한다.")
    @PostMapping(value = "/creates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizOrganizationAplyDtlApiResponseVO> createInfos(HttpServletRequest request, HttpServletResponse response,
                                                                          @Validated(value = {CreateBizOrganizationAplyDtl.class}) @NotNull @RequestBody List<BizOrganizationAplyDtlApiRequestVO> bizOrganizationAplyDtlApiRequestVOs) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyDtlService.createBizOrganizationAplyDtls(bizOrganizationAplyDtlApiRequestVOs))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3561, "사업 공고 신청 생성 실패")));
    }

    /**
     * 수업계획서 생성
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 수업계획서 생성 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyDtlApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 생성", description = "사업 공고 신청 수업계획서 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizOrganizationAplyDtlApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                @Validated(value = {CreateBizOrganizationAplyDtl.class}) @NotNull @RequestBody BizOrganizationAplyDtlApiRequestVO bizOrganizationAplyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyDtlService.createBizOrganizationAplyDtl(bizOrganizationAplyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3561, "사업 공고 신청 생성 실패")));
    }

    /**
     * 수업계획서 업데이트
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 수업계획서 수정 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyDtlApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 업데이트", description = "사업 공고 신청 수업계획서 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizOrganizationAplyDtlApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizOrganizationAplyDtl.class}) @NotNull @RequestBody BizOrganizationAplyDtlApiRequestVO bizOrganizationAplyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyDtlService.updateBizOrganizationAplyDtl(bizOrganizationAplyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3563, "사업 공고 신청 수업계획서 수정 실패")));
    }

    /**
     * 수업계획서 업데이트 (다건)
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyDtlApiRequestVOs
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 수업계획서 수정 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyDtlApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 업데이트 (다건)", description = "사업 공고 신청 수업계획서 업데이트한다.")
    @PutMapping(value = "/updates", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BizOrganizationAplyDtlApiResponseVO>> updateInfos(HttpServletRequest request, HttpServletResponse response,
                                                                          @Validated(value = {UpdateBizOrganizationAplyDtl.class}) @NotNull @RequestBody List<BizOrganizationAplyDtlApiRequestVO> bizOrganizationAplyDtlApiRequestVOs) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyDtlService.updateBizOrganizationAplyDtls(bizOrganizationAplyDtlApiRequestVOs))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3563, "사업 공고 신청 수업계획서 수정 실패")));
    }

    /**
     * 수업계획서 삭제
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyDtlApiRequestVO
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 모집 수업계획서 삭제 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyDtlApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 삭제", description = "사업 공고 신청 수업계획서 삭제 한다.")
    @DeleteMapping(value = "/delete/{bizOrgAplyDtlNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizOrganizationAplyDtlApiRequestVO bizOrganizationAplyDtlApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyDtlService.deleteBizOrganizationAplyDtl(bizOrganizationAplyDtlApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3564, "수업계획서 신청 삭제 실패")));
    }

    /**
     * 수업계획서 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Organization Aply Dtl", description = "사업 공고 신청 수업계획서 API")
    @Operation(operationId = "Business Organization Aply Dtl", summary = "사업 공고 신청 수업계획서 조회", description = "사업 공고 신청 수업계획서 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizOrganizationAplyDtlService.getBizOrganizationAplyDtlList((BizOrganizationAplyDtlViewRequestVO) params(BizOrganizationAplyDtlViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

}
