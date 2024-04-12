package kr.or.kpf.lms.biz.education.anonymous.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.education.anonymous.service.AnonymousEducationService;
import kr.or.kpf.lms.biz.education.anonymous.vo.response.AnonymousEducationViewResponseVO;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
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
import java.util.Optional;

/**
 * 교육 신청 API 관련 Controller
 */
@Tag(name = "Education Application", description = "교육 신청 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/education")
public class AnonymousEducationApiController extends CSApiControllerSupport {

    private final AnonymousEducationService anonymousEducationService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param educationApplicationViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnonymousEducationViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody EducationApplicationViewRequestVO educationApplicationViewRequestVO) {
        return null;
    }

    /**
     * 비회원 교육 조회수 업데이트
     *
     * @param request
     * @param response
     * @param curriculumCode
     * @return
     */
    @Tag(name = "Education", description = "교육 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비회원 교육 조회수 업데이트 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Anonymous", summary = "비회원 교육 조회수 업데이트", description = "비회원 교육 조회수 업데이트한다.")
    @PutMapping(value = "/update-view-count/{curriculumCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> updateViewCount(HttpServletRequest request, HttpServletResponse response,
                                                               @Parameter(description = "과정 마스터 일련번호") @PathVariable(value = "curriculumCode", required = true) String curriculumCode) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(anonymousEducationService.updateViewCount(curriculumCode))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7105, "비회원 교육 조회수 업데이트 실패")));
    }
}
