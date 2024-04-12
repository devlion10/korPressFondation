package kr.or.kpf.lms.biz.common.upload.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.biz.common.upload.vo.CreateFileMaster;
import kr.or.kpf.lms.biz.common.upload.vo.UpdateFileMaster;
import kr.or.kpf.lms.biz.common.upload.vo.request.FileMasterApiRequestVO;
import kr.or.kpf.lms.biz.common.upload.vo.request.FileMasterViewRequestVO;
import kr.or.kpf.lms.biz.common.upload.vo.response.FileMasterApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.ExamQuestionItemRepository;
import kr.or.kpf.lms.repository.ExamQuestionMasterRepository;
import kr.or.kpf.lms.repository.FileMasterRepository;
import kr.or.kpf.lms.repository.entity.ExamQuestionItem;
import kr.or.kpf.lms.repository.entity.ExamQuestionMaster;
import kr.or.kpf.lms.repository.entity.FileMaster;
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

@Tag(name = "Common File Upload", description = "파일 업로드 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/common/upload")
public class FileMasterApiController extends CSViewControllerSupport {
    private final FileMasterService fileMasterService;
    private final FileMasterRepository fileMasterRepository;

    private final ExamQuestionMasterRepository examQuestionMasterRepository;
    private final ExamQuestionItemRepository examQuestionItemRepository;

    private static final String QUES_ATTACH_FILE_TAG = "_QUES";
    private static final String QUES_ITEM_ATTACH_FILE_TAG = "_ITEM";


    /**
     * 파일 업로드 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @Operation(operationId = "Common File Upload", summary = "파일 업로드 조회", description = "파일 업로드 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> fileMasterService.getFileMasterList((FileMasterViewRequestVO) params(FileMasterViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 파일 업로드 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @Operation(operationId = "Common File Upload", summary = "파일 업로드 상세 조회", description = "파일 업로드 상세 조회한다.")
    @GetMapping(value = "/{fileSn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> fileMasterService.getFileMaster((FileMasterViewRequestVO) params(FileMasterViewRequestVO.class, searchMap, null))));

    }

    /**
     * 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param attachFileSn
     * @param attachFile
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Common File Upload", summary = "첨부파일 업로드", description = "첨부파일을 업로드한다.")
    @PutMapping(value = "/attach/{attachFileSn}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 일련번호") @PathVariable(value = "attachFileSn", required = true) String attachFileSn,
                                                          @Parameter(description = "첨부파일 서비스 타입(NOTICE, PBANC, SURVEY...)") @RequestParam(value = "attachType", required = true) String attachType,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(fileMasterService.fileUpload(attachType, attachFileSn, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "첨부파일 업로드 실패")));
    }

    /**
     * 파일 업로드 생성
     *
     * @param request
     * @param response
     * @param fileMasterApiRequestVO
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 생성 성공", content = @Content(schema = @Schema(implementation = FileMasterApiResponseVO.class)))})
    @Operation(operationId="Common File Upload", summary = "파일 업로드 생성", description = "파일 업로드 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileMasterApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @Validated(value = {CreateFileMaster.class}) @NotNull @RequestBody FileMasterApiRequestVO fileMasterApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(fileMasterService.createFileMaster(fileMasterApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "파일 업로드 생성 실패")));
    }

    /**
     * 파일 업로드 업데이트
     *
     * @param request
     * @param response
     * @param fileMasterApiRequestVO
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 수정 성공", content = @Content(schema = @Schema(implementation = FileMasterApiResponseVO.class)))})
    @Operation(operationId="Common File Upload", summary = "파일 업로드 업데이트", description = "파일 업로드 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileMasterApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdateFileMaster.class}) @NotNull @RequestBody FileMasterApiRequestVO fileMasterApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(fileMasterService.updateFileMaster(fileMasterApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "파일 업로드 수정 실패")));
    }

    /**
     * 파일 업로드 삭제
     *
     * @param request
     * @param response
     * @param fileMasterApiRequestVO
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 삭제 성공", content = @Content(schema = @Schema(implementation = FileMasterApiResponseVO.class)))})
    @Operation(operationId="Common File Upload", summary = "파일 업로드 삭제", description = "파일 업로드 삭제 한다.")
    @DeleteMapping(value = "/delete/{fileSn}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody FileMasterApiRequestVO fileMasterApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(fileMasterService.deleteFileMaster(fileMasterApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "파일 업로드 삭제 실패")));
    }

    /**
     * 첨부파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "Common File Upload", description = "파일 업로드 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 첨부파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Common File Upload", summary = "파일 업로드 첨부파일 다운로드", description = "파일 업로드 첨부파일을 다운로드한다.")
    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {

            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                String originName = attachFilePath;
                if (attachFilePath.contains(appConfig.getUploadFile().getExamFolder())) {
                    if (attachFilePath.contains(QUES_ATTACH_FILE_TAG)) {
                        ExamQuestionMaster examQuestionMaster = examQuestionMasterRepository.findOne(Example.of(ExamQuestionMaster.builder()
                                .questionFilePath(attachFilePath)
                                .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));

                        if (examQuestionMaster != null) {
                            if (examQuestionMaster.getQuestionFileOrigin() != null)
                                originName = examQuestionMaster.getQuestionFileOrigin();
                        }
                    } else if (attachFilePath.contains(QUES_ITEM_ATTACH_FILE_TAG)) {
                        ExamQuestionItem examQuestionItem = examQuestionItemRepository.findOne(Example.of(ExamQuestionItem.builder()
                                .questionItemFilePath(attachFilePath)
                                .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));

                        if (examQuestionItem != null) {
                            if (examQuestionItem.getQuestionItemFileOrigin() != null)
                                originName = examQuestionItem.getQuestionItemFileOrigin();
                        }
                    }
                } else if (attachFilePath.contains(appConfig.getUploadFile().getUploadFolder())){
                    FileMaster fileMaster = fileMasterRepository.findOne(Example.of(FileMaster.builder()
                            .filePath(attachFilePath)
                            .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));

                    if (fileMaster != null) {
                        if (fileMaster.getOriginalFileName() != null)
                            originName = fileMaster.getOriginalFileName();
                    }
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
