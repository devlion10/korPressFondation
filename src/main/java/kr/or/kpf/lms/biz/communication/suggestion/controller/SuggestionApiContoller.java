package kr.or.kpf.lms.biz.communication.suggestion.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.communication.suggestion.service.SuggestionService;
import kr.or.kpf.lms.biz.communication.suggestion.vo.request.SuggestionApiRequestVO;
import kr.or.kpf.lms.biz.communication.suggestion.vo.request.SuggestionViewRequestVO;
import kr.or.kpf.lms.biz.communication.suggestion.vo.response.SuggestionApiResponseVO;
import kr.or.kpf.lms.biz.communication.suggestion.vo.response.SuggestionViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Optional;

/**
 * 교육 주제 제안 API 관련 Controller
 */
@Tag(name = "Communication", description = "참여 / 소통 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/communication/edu-suggestion")
public class SuggestionApiContoller extends CSApiControllerSupport {

    private final SuggestionService suggestionService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param suggestionViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuggestionViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody SuggestionViewRequestVO suggestionViewRequestVO) {
        return null;
    }

    /**
     * 교육 주제 제안 등록 API
     *
     * @param request
     * @param response
     * @param suggestionApiRequestVO
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 주제 제안 등록 성공", content = @Content(schema = @Schema(implementation = SuggestionApiResponseVO.class)))})
    @Operation(operationId = "Communication Suggestion", summary = "교육 주제 제안 등록", description = "교육 주제 제안 등록 데이터를 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SuggestionApiResponseVO> createSuggestion(HttpServletRequest request, HttpServletResponse response,
        @Validated(value = {CreateSuggestion.class}) @NotNull @RequestBody SuggestionApiRequestVO suggestionApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(suggestionService.createSuggestion(suggestionApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7081, "교육 주제 제안 정보 생성 실패")));
    }

    public interface CreateSuggestion {}

    /**
     * 교육 주제 제안 업데이트 API
     *
     * @param request
     * @param response
     * @param suggestionApiRequestVO
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 주제 제안 업데이트 성공", content = @Content(schema = @Schema(implementation = SuggestionApiResponseVO.class)))})
    @Operation(operationId = "Communication Suggestion", summary = "교육 주제 제안 업데이트", description = "교육 주제 제안 데이터를 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuggestionApiResponseVO> updateSuggestion(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdateSuggestion.class}) @NotNull @RequestBody SuggestionApiRequestVO suggestionApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(suggestionService.updateSuggestion(suggestionApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7082, "교육 주제 제안 데이터 업데이트 실패")));
    }

    public interface UpdateSuggestion {}

    /**
     * 교육 주제 제안 삭제 API
     *
     * @param request
     * @param response
     * @param sequenceNo
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 주제 제안 삭제", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Communication Suggestion", summary = "교육 주제 제안 삭제", description = "교육 주제 제안 데이터를 삭제한다.")
    @DeleteMapping(value = "/delete/{sequenceNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteSuggestion(HttpServletRequest request, HttpServletResponse response,
                                                                    @Parameter(description = "교육 주제 제안 시퀀스 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(suggestionService.deleteSuggestion(sequenceNo))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7083, "교육 주제 제안 삭제 실패")));
    }
}
