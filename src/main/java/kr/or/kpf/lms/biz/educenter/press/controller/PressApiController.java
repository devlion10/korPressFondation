package kr.or.kpf.lms.biz.educenter.press.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.biz.educenter.press.service.PressService;
import kr.or.kpf.lms.biz.educenter.press.vo.request.PressApiRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.request.PressViewRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.response.PressApiResponseVO;
import kr.or.kpf.lms.biz.educenter.press.vo.response.PressViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import kr.or.kpf.lms.repository.PressReleaseRepository;
import kr.or.kpf.lms.repository.entity.BizOrganizationRsltRpt;
import kr.or.kpf.lms.repository.entity.PressRelease;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 연수원 소개 > 행사소개(보도자료) API 관련 Controller
 */
@Tag(name = "EduCenter", description = "연수원 소개 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/edu-center/press")
public class PressApiController extends CSApiControllerSupport {
    private final FileMasterService fileMasterService;
    private final PressService pressService;
    private final PressReleaseRepository pressReleaseRepository;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param pressViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PressViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody PressViewRequestVO pressViewRequestVO) {
        return null;
    }

    /**
     * 행사소개(보도자료) 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param sequenceNo
     * @param containTextType
     * @param containText
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @Operation(operationId = "Press", summary = "행사소개(보도자료) 조회", description = "행사소개(보도자료) 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="sequenceNo", required = false) BigInteger sequenceNo,
                                          @RequestParam(value="containTextType", required = false) String containTextType,
                                          @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> pressService.getList((PressViewRequestVO) params(PressViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 행사소개(보도자료) 등록 API
     *
     * @param request
     * @param response
     * @param reviewApiRequestVO
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사소개(보도자료) 생성 성공", content = @Content(schema = @Schema(implementation = PressApiResponseVO.class)))})
    @Operation(operationId = "Press", summary = "행사소개(보도자료) 생성", description = "행사소개(보도자료) 데이터를 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PressApiResponseVO> createPress(HttpServletRequest request, HttpServletResponse response,
                                                          @Validated(value = {CreatePress.class}) @NotNull @RequestBody PressApiRequestVO reviewApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(pressService.createPress(reviewApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7101, "행사소개(보도자료) 데이터 생성 실패")));
    }

    public interface CreatePress {}

    /**
     * 행사소개(보도자료) 업데이트 API
     *
     * @param request
     * @param response
     * @param reviewApiRequestVO
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사소개(보도자료) 업데이트 성공", content = @Content(schema = @Schema(implementation = PressApiResponseVO.class)))})
    @Operation(operationId = "Press", summary = "행사소개(보도자료) 업데이트", description = "행사소개(보도자료) 데이터를 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PressApiResponseVO> updatePress(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdatePress.class}) @NotNull @RequestBody PressApiRequestVO reviewApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(pressService.updatePress(reviewApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7102, "행사소개(보도자료) 데이터 업데이트 실패")));
    }
    
    public interface UpdatePress {}

    /**
     * 첨부파일 다운로드 API
     *
     * @param request
     * @param response
     * @param attachFilePath
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사소개(보도자료) 파일 다운로드 성공", content = @Content(schema = @Schema(implementation = PressApiResponseVO.class)))})
    @Operation(operationId = "Press", summary = "행사소개(보도자료) 파일 다운로드", description = "행사소개(보도자료) 파일을다운로드한다.")
    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> fileDownload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "첨부파일 경로") @RequestParam(value = "attachFilePath", required = true) String attachFilePath) {
        return Optional.ofNullable(attachFilePath).map(filePath -> {

            byte[] data = fileMasterService.fileDownload(attachFilePath);
            ByteArrayResource resource = new ByteArrayResource(data);
            try {
                PressRelease pressRelease = pressReleaseRepository.findOne(Example.of(PressRelease.builder()
                        .atchFilePath(attachFilePath)
                        .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9006, "파일 미존재"));
                String originName = attachFilePath;
                if (pressRelease != null) {
                    if (pressRelease.getAtchFileOrigin() != null)
                        originName = pressRelease.getAtchFileOrigin();
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
     * 행사소개(보도자료) 조회수 업데이트
     *
     * @param request
     * @param response
     * @param sequenceNo
     * @return
     */
    @Tag(name = "EduCenter", description = "연수원 소개 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사소개(보도자료) 조회수 업데이트 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Press", summary = "행사소개(보도자료) 조회수 업데이트", description = "행사소개(보도자료) 조회수 업데이트한다.")
    @PutMapping(value = "/update-view-count/{sequenceNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> updateViewCount(HttpServletRequest request, HttpServletResponse response,
                                                               @Parameter(description = "행사소개(보도자료) 시퀀스 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(pressService.updateViewCount(sequenceNo))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7105, "행사소개(보도자료) 조회수 업데이트 실패")));
    }
}
