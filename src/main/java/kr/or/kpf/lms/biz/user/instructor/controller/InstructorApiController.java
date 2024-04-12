package kr.or.kpf.lms.biz.user.instructor.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.user.instructor.service.InstructorService;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorApiRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.response.InstructorApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
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

/**
 * 강사 관리 > 강사 관리 API 관련 Controller
 */
@Tag(name = "Instructor Management", description = "강사 관리 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/instructor")
public class InstructorApiController extends CSApiControllerSupport {

    private final InstructorService instructorService;

    /**
     * 강사 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Instructor Management", description = "강사 관리 API")
    @Operation(operationId = "Instructor Management", summary = "강사 조회", description = "강사 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> instructorService.getList((InstructorViewRequestVO) params(InstructorViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 강사 정보 업데이트
     *
     * @param request
     * @param response
     * @param instructorApiRequestVO
     * @return
     */
    @Tag(name = "Instructor Management", description = "강사 관리 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "강사 정보 수정 성공", content = @Content(schema = @Schema(implementation = InstructorApiResponseVO.class)))})
    @Operation(operationId="Instructor", summary = "강사 정보 업데이트", description = "강사 정보를 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstructorApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                               @Validated(value = {UpdateInstructor.class}) @NotNull @RequestBody InstructorApiRequestVO instructorApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(instructorService.updateInfo(instructorApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "강사 정보 수정 실패")));
    }

    public interface UpdateInstructor {}
}
