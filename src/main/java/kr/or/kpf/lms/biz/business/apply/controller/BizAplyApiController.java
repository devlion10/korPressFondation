package kr.or.kpf.lms.biz.business.apply.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.apply.service.BizAplyService;
import kr.or.kpf.lms.biz.business.apply.vo.response.BizAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.CreateBizOrganizationAply;
import kr.or.kpf.lms.biz.business.apply.vo.request.BizAplyApiRequestVO;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.BizAplyDtlFileRepository;
import kr.or.kpf.lms.repository.BizAplyRepository;
import kr.or.kpf.lms.repository.entity.BizAply;
import kr.or.kpf.lms.repository.entity.BizAplyDtlFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@Tag(name = "Business Aply", description = "사업 공고 신청 - 언론인/기본형 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/journalist/aply")
public class BizAplyApiController extends CSViewControllerSupport {
    private final FileMasterService fileMasterService;
    private final BizAplyService bizAplyService;
    private final BizAplyDtlFileRepository bizAplyDtlFileRepository;

    /**
     * 사업 공고 신청 - 언론인/기본형 생성
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
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizAplyApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                           @Validated(value = {CreateBizOrganizationAply.class}) @NotNull @RequestBody BizAplyApiRequestVO bizAplyApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizAplyService.createBizAply(bizAplyApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3541, "사업 공고 신청 - 언론인/기본형 생성 실패")));
    }
    public interface CreateBizAply {}


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
     * 사업 공고 신청 - 언론인/기본형 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param sequenceNo
     * @param attachFile
     * @return
     */
    @Tag(name = "Business Aply", description = "사업 공고 신청 - 언론인/기본형 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 - 언론인/기본형 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Business Aply", summary = "사업 공고 신청 - 언론인/기본형 첨부파일 업로드", description = "사업 공고 신청 - 언론인/기본형 첨부파일을 업로드한다.")
    @PutMapping(value = "/upload/{sequenceNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "사업 공고 신청 - 언론인/기본형 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizAplyService.fileUpload(sequenceNo, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "사업 공고 첨부파일 - 언론인/기본형 업로드 실패")));
    }

    /**
     * 사업 공고 신청 - 언론인/기본형 첨부파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "Business Aply", description = "사업 공고 신청 - 언론인/기본형 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 신청 - 언론인/기본형 첨부파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Business Aply", summary = "사업 공고 신청 - 언론인/기본형 첨부파일 다운로드", description = "사업 공고 신청 - 언론인/기본형 첨부파일을 다운로드한다.")
    @GetMapping(value = "/dtl/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {
            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                BizAplyDtlFile bizAplyDtlFile = bizAplyDtlFileRepository.findOne(Example.of(BizAplyDtlFile.builder()
                        .filePath(attachFilePath)
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String originName = attachFilePath;
                if (bizAplyDtlFile != null) {
                    if (bizAplyDtlFile.getOriginalFileName() != null)
                        originName = bizAplyDtlFile.getOriginalFileName();
                }

                return ResponseEntity.status(HttpStatus.OK)
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(originName, "UTF-8") + "\"")
                        .body(resource);
            } catch (UnsupportedEncodingException e) {
                throw new KPFException(KPF_RESULT.ERROR9006, "파일명 URL 인코드 실패");
            }
        }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 패스 미존재"));
    }
}
