package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.service.BizInstructorClclnDdlnService;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.CreateBizInstructorClclnDdln;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.UpdateBizInstructorClclnDdln;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request.BizInstructorClclnDdlnApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request.BizInstructorClclnDdlnViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.response.BizInstructorClclnDdlnApiResponseVO;
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

@Tag(name = "Business Instructor Clcln Ddln", description = "정산 마감일 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/instructor/ddln")
public class BizInstructorClclnDdlnApiController extends CSViewControllerSupport {
    private final BizInstructorClclnDdlnService bizInstructorClclnDdlnService;

    /**
     * 정산 마감일 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Business Instructor Clcln Ddln", description = "정산 마감일 API")
    @Operation(operationId = "Instructor Clcln Ddln", summary = "정산 마감일 생성", description = "정산 마감일 신청 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorClclnDdlnService.getBizInstructorClclnDdlnList((BizInstructorClclnDdlnViewRequestVO) params(BizInstructorClclnDdlnViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 정산 마감일 상세 조회 API
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Clcln Ddln", description = "정산 마감일 API")
    @Operation(operationId = "Instructor Clcln Ddln", summary = "정산 마감일 상세 조회", description = "정산 마감일 상세 조회한다.")
    @GetMapping(value = "/{bizInstrClclnDdlnNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getObject(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> bizInstructorClclnDdlnService.getBizInstructorClclnDdln((BizInstructorClclnDdlnViewRequestVO) params(BizInstructorClclnDdlnViewRequestVO.class, searchMap, null))));

    }

    /**
     * 정산 마감일 생성
     *
     * @param request
     * @param response
     * @param bizInstructorClclnDdlnApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Clcln Ddln", description = "정산 마감일 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 마감일 생성 성공", content = @Content(schema = @Schema(implementation = BizInstructorClclnDdlnApiResponseVO.class)))})
    @Operation(operationId="Instructor Clcln Ddln", summary = "정산 마감일 생성", description = "정산 마감일 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorClclnDdlnApiResponseVO> createInfo(HttpServletRequest request, HttpServletResponse response,
                                                                          @Validated(value = {CreateBizInstructorClclnDdln.class}) @NotNull @RequestBody BizInstructorClclnDdlnApiRequestVO bizInstructorClclnDdlnApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorClclnDdlnService.createBizInstructorClclnDdln(bizInstructorClclnDdlnApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "정산 마감일 생성 실패")));
    }

    /**
     * 정산 마감일 업데이트
     *
     * @param request
     * @param response
     * @param bizInstructorClclnDdlnApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Clcln Ddln", description = "정산 마감일 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 마감일 수정 성공", content = @Content(schema = @Schema(implementation = BizInstructorClclnDdlnApiResponseVO.class)))})
    @Operation(operationId="Instructor Clcln Ddln", summary = "정산 마감일 업데이트", description = "정산 마감일 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorClclnDdlnApiResponseVO> updateInfo(HttpServletRequest request, HttpServletResponse response,
                                                                 @Validated(value = {UpdateBizInstructorClclnDdln.class}) @NotNull @RequestBody BizInstructorClclnDdlnApiRequestVO bizInstructorClclnDdlnApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorClclnDdlnService.updateBizInstructorClclnDdln(bizInstructorClclnDdlnApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3603, "정산 마감일 수정 실패")));
    }

    /**
     * 정산 마감일 삭제
     *
     * @param request
     * @param response
     * @param bizInstructorClclnDdlnApiRequestVO
     * @return
     */
    @Tag(name = "Business Instructor Clcln Ddln", description = "정산 마감일 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정산 마감일 삭제 성공", content = @Content(schema = @Schema(implementation = BizInstructorClclnDdlnApiResponseVO.class)))})
    @Operation(operationId="Instructor Clcln Ddln", summary = "정산 마감일 삭제", description = "정산 마감일 삭제 한다.")
    @DeleteMapping(value = "/delete/{instuctorClclnDdlnNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizInstructorClclnDdlnApiRequestVO bizInstructorClclnDdlnApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorClclnDdlnService.deleteBizInstructorClclnDdln(bizInstructorClclnDdlnApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3604, "정산 마감일 삭제 실패")));
    }


    /**
     * 정산 마감일 생성
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "Business Instructor Clcln Ddln", description = "현재 기준 인접 데이터 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "현재 기준 인접 데이터 조회 성공")})
    @Operation(operationId="Instructor Clcln Ddln", summary = "현재 기준 인접 데이터 조회", description = "현재 기준 인접 데이터 조회한다.")
    @GetMapping(value = "/search/nearDate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizInstructorClclnDdlnApiResponseVO> selectInfoNearDate(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizInstructorClclnDdlnService.selectBizInstructorClclnDdlnByNearDate())
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3601, "현재 기준 인접 데이터 조회 실패")));
    }

}
