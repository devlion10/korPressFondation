package kr.or.kpf.lms.biz.business.organization.aply.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.apply.service.BizAplyService;
import kr.or.kpf.lms.biz.business.apply.vo.request.BizAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.apply.vo.response.BizAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.service.BizInstructorService;
import kr.or.kpf.lms.biz.business.instructor.vo.CreateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.UpdateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.response.BizInstructorApiResponseVO;
import kr.or.kpf.lms.biz.business.organization.aply.service.BizOrganizationAplyService;
import kr.or.kpf.lms.biz.business.organization.aply.vo.CreateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.organization.aply.vo.UpdateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.response.BizOrganizationAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
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
import org.springframework.lang.Nullable;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "Business Organization Aply", description = "사업 공고 신청 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/organization/aply")
public class BizOrganizationAplyApiController extends CSViewControllerSupport {
    private final BizOrganizationAplyService bizOrganizationAplyService;
    private final BizAplyService bizAplyService;

    /**
     * 사업 공고 신청 생성
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Organization Aply", description = "사업 공고 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 생성 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply", summary = "사업 공고 신청 생성", description = "사업 공고 신청 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizOrganizationAplyApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                @Validated(value = {CreateBizOrganizationAply.class}) @NotNull @RequestBody BizOrganizationAplyApiRequestVO bizOrganizationAplyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyService.createBizOrganizationAply(bizOrganizationAplyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3549, "사업 공고 신청에 실패하였거나 소속 기관에서 이미 등록한 사업입니다. 마이페이지에서 먼저 확인해 주세요.")));
    }

    /**
     * 사업 공고 신청 업데이트
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Organization Aply", description = "사업 공고 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 수정 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply", summary = "사업 공고 신청 업데이트", description = "사업 공고 신청 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizOrganizationAplyApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizOrganizationAply.class}) @NotNull @RequestBody BizOrganizationAplyApiRequestVO bizOrganizationAplyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyService.updateBizOrganizationAply(bizOrganizationAplyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3543, "사업 공고 신청 수정 실패")));
    }

    /**
     * 사업 공고 신청 삭제
     *
     * @param request
     * @param response
     * @param bizOrganizationAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Organization Aply", description = "사업 공고 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 삭제 성공", content = @Content(schema = @Schema(implementation = BizOrganizationAplyApiResponseVO.class)))})
    @Operation(operationId="Business Organization Aply", summary = "사업 공고 신청 삭제", description = "사업 공고 신청 삭제 한다.")
    @DeleteMapping(value = "/delete/{bizOrgAplyNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizOrganizationAplyApiRequestVO bizOrganizationAplyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyService.deleteBizOrganizationAply(bizOrganizationAplyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3544, "사업 공고 신청 삭제 실패")));
    }

    /**
     * 사업 공고 신청 신청서 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param bizOrgAplyNo
     * @param attachFile
     * @return
     */
    @Tag(name = "Business Organization Aply", description = "사업 공고 신청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Business Organization Aply", summary = "사업 공고 신청 첨부파일 업로드", description = "사업 공고 신청 첨부파일을 업로드한다.")
    @PutMapping(value = "/upload/{bizOrgAplyNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "사업 공고 신청 시리얼 번호") @PathVariable(value = "bizOrgAplyNo", required = true) String bizOrgAplyNo,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizOrganizationAplyService.fileUpload(bizOrgAplyNo, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "사업 공고 첨부파일 업로드 실패")));
    }


    /**
     * 사업 공고 신청 - 자유형 생성
     *
     * @param request
     * @param response
     * @param bizAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Aply", description = "사업 공고 신청 - 언론인/기본형 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 - 언론인/기본형 생성 성공", content = @Content(schema = @Schema(implementation = BizAplyApiRequestVO.class)))})
    @Operation(operationId="Business Aply", summary = "사업 공고 신청 - 언론인/기본형 생성", description = "사업 공고 신청 - 언론인/기본형 생성한다.")
    @PostMapping(value = "/5/create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BizAplyApiResponseVO> createFree(HttpServletRequest request, HttpServletResponse response,
                                                           @RequestPart("aply") BizAplyApiRequestVO bizAplyApiRequestVO,
                                                           @Nullable @RequestPart("file") List<MultipartFile> file) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizAplyService.createBizAply(bizAplyApiRequestVO, file))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3541, "사업 공고 신청 - 언론인/기본형 생성 실패")));
    }


    /**
     * 사업 공고 신청 - 자유형 생성
     *
     * @param request
     * @param response
     * @param bizAplyApiRequestVO
     * @return
     */
    @Tag(name = "Business Aply", description = "사업 공고 신청 - 언론인/기본형 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 - 언론인/기본형 생성 성공", content = @Content(schema = @Schema(implementation = BizAplyApiRequestVO.class)))})
    @Operation(operationId="Business Aply", summary = "사업 공고 신청 - 언론인/기본형 생성", description = "사업 공고 신청 - 언론인/기본형 생성한다.")
    @PutMapping(value = "/5/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BizAplyApiResponseVO> updateFree(HttpServletRequest request, HttpServletResponse response,
                                                           @RequestPart("aply") BizAplyApiRequestVO bizAplyApiRequestVO,
                                                           @Nullable @RequestPart("file") List<MultipartFile> file) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizAplyService.updateBizAply(bizAplyApiRequestVO, file))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3541, "사업 공고 신청 - 언론인/기본형 생성 실패")));
    }
}
