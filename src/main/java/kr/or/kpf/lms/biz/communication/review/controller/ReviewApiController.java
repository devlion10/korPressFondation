package kr.or.kpf.lms.biz.communication.review.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.communication.review.service.ReviewService;
import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewApiRequestVO;
import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewViewRequestVO;
import kr.or.kpf.lms.biz.communication.review.vo.response.ReviewApiResponseVO;
import kr.or.kpf.lms.biz.communication.review.vo.response.ReviewViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 참여 / 소통 > 교육 후기방 API 관련 Controller
 */
@Tag(name = "Communication", description = "참여 / 소통 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/communication/review")
public class ReviewApiController extends CSApiControllerSupport {
    private final ReviewService reviewService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param reviewViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody ReviewViewRequestVO reviewViewRequestVO) {
        return null;
    }

    /**
     * 교육 후기방 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param sequenceNo
     * @param containTextType
     * @param containText
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @Operation(operationId = "Review", summary = "교육 후기방 조회", description = "교육 후기방 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="sequenceNo", required = false) BigInteger sequenceNo,
                                          @RequestParam(value="containTextType", required = false) String containTextType,
                                          @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> reviewService.getList((ReviewViewRequestVO) params(ReviewViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }
    public interface UpdateReview {}
    public interface CreateReview {}

    /**
     * 교육 후기 조회수 업데이트
     *
     * @param request
     * @param response
     * @param sequenceNo
     * @return
     */
    @Tag(name = "Communication", description = "참여 / 소통 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "교육 후기 조회수 업데이트 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Review", summary = "교육 후기 조회수 업데이트", description = "교육 후기 조회수 업데이트한다.")
    @PutMapping(value = "/update-view-count/{sequenceNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> updateViewCount(HttpServletRequest request, HttpServletResponse response,
                                                               @Parameter(description = "교육 후기 시퀀스 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(reviewService.updateViewCount(sequenceNo))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7105, "교육 후기 조회수 업데이트 실패")));
    }
}
