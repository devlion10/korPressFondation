package kr.or.kpf.lms.biz.business.instructor.question.answer.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.response.BizInstructorAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.question.answer.service.BizInstructorQuestionAnswerService;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.CreateBizInstructorQuestionAnswer;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.UpdateBizInstructorQuestionAnswer;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.request.BizInstructorQuestionAnswerApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.request.BizInstructorQuestionAnswerViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.response.BizInstructorQuestionAnswerApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.question.service.BizInstructorQuestionService;
import kr.or.kpf.lms.biz.business.instructor.question.vo.CreateBizInstructorQuestion;
import kr.or.kpf.lms.biz.business.instructor.question.vo.UpdateBizInstructorQuestion;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.response.BizInstructorQuestionApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
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
import java.util.ArrayList;
import java.util.Optional;

@Tag(name = "Business Instructor Question Answer", description = "강사 지원 문의 답변 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/question/answer")
public class BizInstructorQuestionAnswerApiController extends CSViewControllerSupport {
    private final BizInstructorQuestionAnswerService bizInstructorQuestionAnswerService;

    /**
     * 강사 모집 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Question Answer", description = "강사 지원 문의 답변 API")
    @Operation(operationId = "Instructor Question Answer", summary = "강사 지원 문의 답변 조회", description = "강사 지원 문의 답변 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorQuestionAnswerService.getBizInstructorQuestionAnswerList((BizInstructorQuestionAnswerViewRequestVO) params(BizInstructorQuestionAnswerViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강사 지원 문의 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Question Answer", description = "강사 지원 문의 답변 API")
    @Operation(operationId = "Instructor Question Answer", summary = "강사 지원 문의 답변 상세 조회", description = "강사 지원 문의 답변 상세 조회한다.")
    @GetMapping(value = "/{bizInstrQuestionNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorQuestionAnswerService.getBizInstructorQuestionAnswer((BizInstructorQuestionAnswerViewRequestVO) params(BizInstructorQuestionAnswerViewRequestVO.class, searchMap, null))));

    }

    /**
     * 강사 지원 문의 생성
     *
     * @param request
     * @param response
     * @param bizInstructorQuestionAnswerApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Question Answer", description = "강사 지원 문의 답변 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 지원 문의 답변 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorAplyApiResponseVO.class)))})
    @Operation(operationId="Instructor Question Answer", summary = "강사 지원 문의 답변 생성", description = "강사 지원 문의 답변 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorQuestionAnswerApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                               @Validated(value = {CreateBizInstructorQuestionAnswer.class}) @NotNull @RequestBody BizInstructorQuestionAnswerApiRequestVO bizInstructorQuestionAnswerApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorQuestionAnswerService.createBizInstructorQuestionAnswer(bizInstructorQuestionAnswerApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "강사 지원 문의 답변 생성 실패")));
    }

    /**
     * 강사 지원 문의 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorQuestionAnswerApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Question Answer", description = "강사 지원 문의 답변 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 지원 문의 답변 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorQuestionApiResponseVO.class)))})
    @Operation(operationId="Instructor Question Answer", summary = "강사 지원 문의 답변 업데이트", description = "강사 지원 문의 답변 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorQuestionAnswerApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorQuestionAnswer.class}) @NotNull @RequestBody BizInstructorQuestionAnswerApiRequestVO bizInstructorQuestionAnswerApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorQuestionAnswerService.updateBizInstructorQuestionAnswer(bizInstructorQuestionAnswerApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "강사 지원 문의 답변 수정 실패")));
    }

    /**
     * 강사 지원 문의 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorQuestionAnswerApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Question Answer", description = "강사 지원 문의 답변 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 지원 문의 답변 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorQuestionAnswerApiResponseVO.class)))})
    @Operation(operationId="Instructor Question Answer", summary = "강사 지원 문의 답변 삭제", description = "강사 지원 문의 답변 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorQuestionAnsNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorQuestionAnswerApiRequestVO bizInstructorQuestionAnswerApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorQuestionAnswerService.deleteBizInstructorQuestionAnswer(bizInstructorQuestionAnswerApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "강사 지원 문의 답변 삭제 실패")));
    }
}
