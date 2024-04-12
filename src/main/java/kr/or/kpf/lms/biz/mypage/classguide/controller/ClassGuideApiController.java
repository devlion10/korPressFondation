package kr.or.kpf.lms.biz.mypage.classguide.controller;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.biz.mypage.classguide.service.ClassGuideService;
import kr.or.kpf.lms.biz.mypage.classguide.vo.CreateClassGuide;
import kr.or.kpf.lms.biz.mypage.classguide.vo.UpdateClassGuide;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideApiRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.response.ClassGuideApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.ClassGuideFileRepository;
import kr.or.kpf.lms.repository.ClassGuideRepository;
import kr.or.kpf.lms.repository.entity.ClassGuide;
import kr.or.kpf.lms.repository.entity.ClassGuideFile;
import kr.or.kpf.lms.repository.entity.CurriculumReferenceRoom;
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
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 수업지도안 API 관련 Controller
 */
@Tag(name = "My Page", description = "마이페이지 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/mypage/class-guide")
public class ClassGuideApiController extends CSApiControllerSupport {
    private final FileMasterService fileMasterService;
    private final ClassGuideService classGuideService;
    private final ClassGuideFileRepository classGuideFileRepository;

    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiImplicitParams(
        {@ApiImplicitParam(
                name = "startDate"
                , value = "조회 시작일자(YYYY-MM-DD)"
                , required = false
                , dataType = "string"
                , example = "2022-09-01"
                , paramType = "query"
                , defaultValue = ""),
        @ApiImplicitParam(
                name = "endDate"
                , value = "조회 종료일자(YYYY-MM-DD)"
                , required = false
                , dataType = "string"
                , example = "2022-09-30"
                , paramType = "query"
                , defaultValue = ""),
        @ApiImplicitParam(
                name = "containTextType"
                , value = "검색어 범위"
                , required = false
                , paramType = "query"
                , defaultValue = ""),
        @ApiImplicitParam(
                name = "containText"
                , value = "검색어"
                , required = false
                , paramType = "query"
                , defaultValue = "")
    })
    @Operation(operationId = "ClassGuide", summary = "수업지도안 조회", description = "수업지도안을 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getClassGuideList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
        @RequestParam(value="startDate", required = false) String startDate, @RequestParam(value="endDate", required = false) String endDate,
        @RequestParam(value="containTextType", required = false) String containTextType,
        @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> classGuideService.getList((ClassGuideViewRequestVO) params(ClassGuideViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 수업지도안 생성 API
     *
     * @param request
     * @param response
     * @param classGuideApiRequestVO
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 생성 성공", content = @Content(schema = @Schema(implementation = ClassGuideApiResponseVO.class)))})
    @Operation(operationId = "ClassGuide", summary = "수업지도안 생성", description = "수업지도안 데이터를 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClassGuideApiResponseVO> createClassGuide(HttpServletRequest request, HttpServletResponse response,
                                                                    @Validated(value = {CreateClassGuide.class}) @NotNull @RequestBody ClassGuideApiRequestVO classGuideApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(classGuideService.createClassGuide(classGuideApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4001, "수업지도안 생성 실패")));
    }

    /**
     * 수업지도안 업데이트 API
     *
     * @param request
     * @param response
     * @param classGuideApiRequestVO
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 업데이트 성공", content = @Content(schema = @Schema(implementation = ClassGuideApiResponseVO.class)))})
    @Operation(operationId = "ClassGuide", summary = "수업지도안 업데이트", description = "수업지도안 데이터를 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClassGuideApiResponseVO> updateClassGuide(HttpServletRequest request, HttpServletResponse response,
                                                                              @Validated(value = {UpdateClassGuide.class}) @NotNull @RequestBody ClassGuideApiRequestVO classGuideApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(classGuideService.updateInfo(classGuideApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7102, "수업지도안 데이터 업데이트 실패")));
    }

    /**
     * 수업지도안 삭제
     *
     * @param request
     * @param response
     * @param classGuideApiRequestVO
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 삭제 성공", content = @Content(schema = @Schema(implementation = ClassGuideApiResponseVO.class)))})
    @Operation(operationId="ClassGuide", summary = "수업지도안 삭제", description = "수업지도안 삭제 한다.")
    @DeleteMapping(value = "/delete/{classGuideCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody ClassGuideApiRequestVO classGuideApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(classGuideService.deleteInfo(classGuideApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3544, "수업지도안 삭제 실패")));
    }

    /**
     * 수업지도안 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param classGuideCode
     * @param attachFile
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "ClassGuide", summary = "수업지도안 첨부파일 업로드", description = "수업지도안 첨부파일을 업로드한다.")
    @PutMapping(value = "/upload/{classGuideCode}/{fileType}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "수업지도안 시리얼 번호") @PathVariable(value = "classGuideCode", required = true) String classGuideCode,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile,
                                                          @Parameter(description = "수업지도안 파일 유형(1:수업지도안/길라잡이, 2:활동지, 3:예시답안, 4:10분 NIE)") @PathVariable(value = "fileType", required = true) String fileType) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(classGuideService.fileUpload(classGuideCode, attachFile, fileType))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "수업지도안 첨부파일 업로드 실패")));
    }

    /**
     * 수업지도안 첨부파일 다중 업로드 API
     *
     * @param request
     * @param response
     * @param classGuideCode
     * @param attachFiles
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "ClassGuide", summary = "수업지도안 첨부파일 업로드", description = "수업지도안 첨부파일을 업로드한다.")
    @PutMapping(value = "/uploads/{classGuideCode}/{fileType}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUploads(HttpServletRequest request, HttpServletResponse response,
                                                           @Parameter(description = "수업지도안 시리얼 번호") @PathVariable(value = "classGuideCode", required = true) String classGuideCode,
                                                           @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) List<MultipartFile> attachFiles,
                                                           @Parameter(description = "수업지도안 파일 유형 (TEACH: 수업지도안/길라잡이, ACTIVITY: 활동지, ANSWER: 예시답안, NIE: 10분 NIE)") @PathVariable(value = "fileType", required = true) String fileType) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(classGuideService.multifileUpload(classGuideCode, attachFiles, fileType))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "수업지도안 첨부파일 업로드 실패")));
    }

    /**
     * 수업지도안 첨부파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 첨부파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "ClassGuide", summary = "수업지도안 첨부파일 다운로드", description = "수업지도안 첨부파일을 다운로드한다.")
    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {

            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                ClassGuideFile classGuideFile = classGuideFileRepository.findOne(Example.of(ClassGuideFile.builder()
                        .filePath(attachFilePath)
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String originName = attachFilePath;
                if (classGuideFile != null) {
                    if (classGuideFile.getOriginalFileName() != null)
                        originName = classGuideFile.getOriginalFileName();
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

    /**
     * 수업지도안 첨부파일 다운로드 및 조회수 count API
     *
     * @param request
     * @param response
     * @param sequenceNo
     * @param attachFilePath
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수업지도안 첨부파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "ClassGuide", summary = "수업지도안 첨부파일 다운로드", description = "수업지도안 첨부파일을 다운로드한다.")
    @GetMapping(value = "/download/count")
    public ResponseEntity<ByteArrayResource> fileCountDownload(HttpServletRequest request, HttpServletResponse response,
                                                               @Parameter(description = "첨부파일 번호") @RequestParam(value = "sequenceNo", required = true) BigInteger sequenceNo,
                                                               @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(sequenceNo).map(filePath -> {

            byte[] data = classGuideService.fileCountDownload(sequenceNo, attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                ClassGuideFile classGuideFile = classGuideFileRepository.findOne(Example.of(ClassGuideFile.builder()
                        .filePath(attachFilePath)
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String originName = attachFilePath;
                if (classGuideFile != null) {
                    if (classGuideFile.getOriginalFileName() != null)
                        originName = classGuideFile.getOriginalFileName();
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

    /**
     * 수업지도안 파일 삭제
     *
     * @param request
     * @param response
     * @param sequenceNo
     * @return
     */
    @Tag(name = "My Page", description = "수업지도안 파일 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 삭제 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId="ClassGuide", summary = "수업지도안 파일 삭제", description = "수업지도안 파일 삭제 한다.")
    @DeleteMapping(value = "/upload/delete/{sequenceNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteFile(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "삭제할 파일 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(classGuideService.deleteFile(sequenceNo))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9007, "수업지도안 파일 삭제 실패")));
    }
}
