package kr.or.kpf.lms.biz.business.instructor.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.service.BizInstructorService;
import kr.or.kpf.lms.biz.business.instructor.vo.CreateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.UpdateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.response.BizInstructorApiResponseVO;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.BizInstructorRepository;
import kr.or.kpf.lms.repository.entity.BizInstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Optional;

@Tag(name = "Business Instructor", description = "강사 모집 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor")
public class BizInstructorApiController extends CSViewControllerSupport {
    private final FileMasterService fileMasterService;
    private final BizInstructorService bizInstructorService;
    private final BizInstructorRepository bizInstructorRepository;

    /**
     * 강사 모집 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @Operation(operationId = "Instructor", summary = "강사 모집 조회", description = "강사 신청 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorService.getBizInstructorList((BizInstructorViewRequestVO) params(BizInstructorViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강사 모집 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @Operation(operationId = "Instructor", summary = "강사 모집 상세 조회", description = "강사 모집 상세 조회한다.")
    @GetMapping(value = "/{bizInstrNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorService.getBizInstructor((BizInstructorViewRequestVO) params(BizInstructorViewRequestVO.class, searchMap, null))));

    }

    /**
     * 강사 모집 생성
     *
     * @param request
     * @param response
     * @param bizInstructorApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 모집 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorApiResponseVO.class)))})
    @Operation(operationId="Instructor", summary = "강사 모집 생성", description = "강사 모집 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                @Validated(value = {CreateBizInstructor.class}) @NotNull @RequestBody BizInstructorApiRequestVO bizInstructorApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorService.createBizInstructor(bizInstructorApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "강사모집 생성 실패")));
    }

    /**
     * 강사 모집 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 모집 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorApiResponseVO.class)))})
    @Operation(operationId="Instructor", summary = "강사 모집 업데이트", description = "강사 모집 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructor.class}) @NotNull @RequestBody BizInstructorApiRequestVO bizInstructorApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorService.updateBizInstructor(bizInstructorApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "강사 모집 수정 실패")));
    }

    /**
     * 강사 모집 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 모집 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorApiResponseVO.class)))})
    @Operation(operationId="Instructor", summary = "강사 모집 삭제", description = "강사 모집 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorApiRequestVO bizInstructorApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorService.deleteBizInstructor(bizInstructorApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "강사 모집 삭제 실패")));
    }

    /**
     * 공고 결과 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param bizInstrNo
     * @param attachFile
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 모집 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Business Instructor", summary = "강사 모집 첨부파일 업로드", description = "강사 모집 첨부파일을 업로드한다.")
    @PutMapping(value = "/upload/{bizInstrNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "강사 모집 시리얼 번호") @PathVariable(value = "bizInstrNo", required = true) String bizInstrNo,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorService.fileUpload(bizInstrNo, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "강사 모집 첨부파일 업로드 실패")));
    }

    /**
     * 강사 모집 첨부파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "Business Instructor", description = "강사 모집 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 모집 첨부파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Instructor", summary = "강사 모집 첨부파일 다운로드", description = "강사 모집 첨부파일을 다운로드한다.")
    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {

            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                BizInstructor bizInstructor = bizInstructorRepository.findOne(Example.of(BizInstructor.builder()
                        .bizInstrFile(attachFilePath)
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String originName = attachFilePath;
                if (bizInstructor != null) {
                    if (bizInstructor.getBizInstrFileOrigin() != null)
                        originName = bizInstructor.getBizInstrFileOrigin();
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
