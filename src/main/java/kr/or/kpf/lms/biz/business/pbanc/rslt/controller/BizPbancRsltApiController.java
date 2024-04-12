package kr.or.kpf.lms.biz.business.pbanc.rslt.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.pbanc.rslt.service.BizPbancRsltService;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.CreateBizPbancRslt;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.UpdateBizPbancRslt;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.response.BizPbancRsltApiResponseVO;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.BizPbancResultRepository;
import kr.or.kpf.lms.repository.entity.BizPbancResult;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Optional;

@Tag(name = "Business Pbanc Result", description = "공고사업 결과 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/pbanc/rslt")
public class BizPbancRsltApiController extends CSViewControllerSupport {
    private final FileMasterService fileMasterService;
    private final BizPbancRsltService bizPbancRsltService;
    private final BizPbancResultRepository bizPbancResultRepository;


    /**
     * 공고사업 결과 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @Operation(operationId = "Business Pbanc Result", summary = "사업 공고 결과 조회", description = "사업 공고 결과 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizPbancRsltService.getBizPbancRsltList((BizPbancRsltViewRequestVO) params(BizPbancRsltViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 사업 공고 결과 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @Operation(operationId = "Business Pbanc Result", summary = "사업 공고 결과 상세 조회", description = "사업 공고 결과 상세 조회한다.")
    @GetMapping(value = "/{bizPbancRsltNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizPbancRsltService.getBizPbancRslt((BizPbancRsltViewRequestVO) params(BizPbancRsltViewRequestVO.class, searchMap, null))));

    }

    /**
     * 공고 결과 생성
     *
     * @param request
     * @param response
     * @param bizPbancRsltApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 결과 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancRsltApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Result", summary = "사업 공고 결과 생성", description = "사업 공고 결과 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancRsltApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                       @Validated(value = {CreateBizPbancRslt.class}) @NotNull @RequestBody BizPbancRsltApiRequestVO bizPbancRsltApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancRsltService.createBizPbancRslt(bizPbancRsltApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3541, "사업 공고 결과 생성 실패")));
    }

    /**
     * 공고 결과 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancRsltApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 결과 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancRsltApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Result", summary = "사업 공고 결과 업데이트", description = "사업 공고 결과 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancRsltApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                       @Validated(value = {UpdateBizPbancRslt.class}) @NotNull @RequestBody BizPbancRsltApiRequestVO bizPbancRsltApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancRsltService.updateBizPbancRslt(bizPbancRsltApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3543, "사업 공고 결과 수정 실패")));
    }

    /**
     * 공고 결과 삭제
     *
     * @param request
     * @param response
     * @param bizPbancRsltApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 결과 성공", content = @Content(schema = @Schema(implementation = BizPbancRsltApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Result", summary = "사업 공고 결과 삭제", description = "사업 공고 결과 삭제 한다.")
    @DeleteMapping(value = "/delete/{bizPbancRsltNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody BizPbancRsltApiRequestVO bizPbancRsltApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancRsltService.deleteBizPbancRslt(bizPbancRsltApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3544, "사업 공고 결과 삭제 실패")));
    }

    /**
     * 공고 결과 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param bizOrgAplyNo
     * @param attachFile
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 결과 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Business Pbanc Result", summary = "사업 공고 결과 첨부파일 업로드", description = "사업 공고 결과 첨부파일을 업로드한다.")
    @PutMapping(value = "/upload/{bizPbancRsltNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "사업 공고 결과 시리얼 번호") @PathVariable(value = "bizPbancRsltNo", required = true) String bizOrgAplyNo,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancRsltService.fileUpload(bizOrgAplyNo, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "사업 공고 결과 첨부파일 업로드 실패")));
    }

    /**
     * 공고 결과 첨부파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "Business Pbanc", description = "사업 공고 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 결과 첨부파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Business Pbanc Result", summary = "사업 공고 결과 첨부파일 다운로드", description = "사업 공고 결과 첨부파일을 다운로드한다.")
    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {

            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                BizPbancResult bizPbancResult = bizPbancResultRepository.findOne(Example.of(BizPbancResult.builder()
                                .bizPbancRsltFile(attachFilePath)
                                .build()))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String fileName = "파일명";
                if (bizPbancResult != null) {
                    fileName = bizPbancResult.getBizPbancRsltFileOrigin();
                }

                return ResponseEntity.status(HttpStatus.OK)
                        .contentLength(data.length)
                        .header("Content-type", "application/octet-stream")
                        .header("Content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"")
                        .body(resource);
            } catch (UnsupportedEncodingException e) {
                throw new KPFException(KPF_RESULT.ERROR9006, "파일명 URL 인코드 실패");
            }
        }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 패스 미존재"));
    }

}
