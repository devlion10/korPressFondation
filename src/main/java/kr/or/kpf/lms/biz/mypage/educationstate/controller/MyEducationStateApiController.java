package kr.or.kpf.lms.biz.mypage.educationstate.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.biz.mypage.educationstate.service.MyEducationStateService;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.request.MyEducationStateApiRequestVO;
import kr.or.kpf.lms.biz.mypage.educationstate.vo.response.MyEducationStateViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.CurriculumReferenceRoomRepository;
import kr.or.kpf.lms.repository.entity.CurriculumReferenceRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Example;
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
import java.util.Optional;

/**
 * 교육/수료 현황 Api 관련 Controller
 */
@Tag(name = "My Page", description = "마이페이지 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/mypage/education-state")
public class MyEducationStateApiController extends CSApiControllerSupport {
    private final FileMasterService fileMasterService;
    private final MyEducationStateService myEducationStateService;
    private final CurriculumReferenceRoomRepository curriculumReferenceRoomRepository;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param myEducationStateViewResponseVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MyEducationStateViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody MyEducationStateViewResponseVO myEducationStateViewResponseVO) {
        return null;
    }

    /**
     * 차시 - 절 학습 진행 API (이러닝 교육)
     *
     * @param request
     * @param response
     * @param myEducationStateApiRequestVO
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "차시 - 절 학습 진행 (이러닝)", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "My Page", summary = "절 학습 진행 처리 (이러닝)", description = "절 학습을 진행한다.")
    @PutMapping(value = "/chapter/section/complete", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> submitSectionProgress(HttpServletRequest request, HttpServletResponse response,
                                                               @Validated(value = {SubmitSectionProgress.class}) @NotNull @RequestBody MyEducationStateApiRequestVO myEducationStateApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(myEducationStateService.submitSectionProgress(myEducationStateApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4101, "절 학습 처리 실패")));
    }

    public interface SubmitSectionProgress {}

    /**
     * 시험 - 정답 제출 API
     *
     * @param request
     * @param response
     * @param myEducationStateApiRequestVO
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "시험 - 문제 정답 제출", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "My Page", summary = "시험 - 문제 정답 제출", description = "시험 문제 정답을 제출한다.")
    @PutMapping(value = "/exam/question/submit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> submitQuestionItem(HttpServletRequest request, HttpServletResponse response,
                                                                  @Validated(value = {SubmitQuestionItem.class}) @NotNull @RequestBody MyEducationStateApiRequestVO myEducationStateApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(myEducationStateService.submitQuestionItem(myEducationStateApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4102, "시험 문제 정답 제출 실패")));
    }

    public interface SubmitQuestionItem {}

    /**
     * 교육 평가 제출 API
     *
     * @param request
     * @param response
     * @param myEducationStateApiRequestVO
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 평가 제출", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "My Page", summary = "교육 평가 제출", description = "교육 평가를 제출한다.")
    @PostMapping(value = "/evaluate/submit", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> submitEducationEvaluate(HttpServletRequest request, HttpServletResponse response,
                                                                  @Validated(value = {SubmitEducationEvaluate.class}) @NotNull @RequestBody MyEducationStateApiRequestVO myEducationStateApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(myEducationStateService.submitEducationEvaluate(myEducationStateApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR2025, "강의 평가 제출 실패")));
    }

    public interface SubmitEducationEvaluate {}

    /**
     * 교육 과제 파일 업로드 API
     *
     * @param request
     * @param response
     * @param applicationNo
     * @param attachFile
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 과제 파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "My Page", summary = "교육 과제 파일 업로드", description = "교육 과제 파일 업로드한다.")
    @PutMapping(value = "/assignment/submit/{applicationNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> submitAssignment(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "교육 신청 일련번호") @PathVariable(value = "applicationNo", required = true) String applicationNo,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(myEducationStateService.submitAssignment(applicationNo, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "교육 과제 파일 업로드 실패")));
    }

    /**
     * 자료실 파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "My Page", description = "마이페이지 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자료실 파일 다운로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "My Page", summary = "자료실 파일 다운로드", description = "자료실 파일 다운로드한다.")
    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 명") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {
            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                CurriculumReferenceRoom curriculumReferenceRoom = curriculumReferenceRoomRepository.findOne(Example.of(CurriculumReferenceRoom.builder()
                        .filePath(attachFilePath)
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String originName = attachFilePath;
                if (curriculumReferenceRoom != null) {
                    if (curriculumReferenceRoom.getFileOriginName() != null)
                        originName = curriculumReferenceRoom.getFileOriginName();
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
