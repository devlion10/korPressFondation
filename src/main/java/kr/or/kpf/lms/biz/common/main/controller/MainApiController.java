package kr.or.kpf.lms.biz.common.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.main.service.MainService;
import kr.or.kpf.lms.biz.common.main.vo.request.MainViewRequestVO;
import kr.or.kpf.lms.biz.common.main.vo.response.MainApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 메인 통합 API 관련 Controller
 */
@Tag(name = "Main Management", description = "메인 통합 API")
@RestController
@Validated
@RequiredArgsConstructor
public class MainApiController extends CSApiControllerSupport {

    private final MainService mainService;

    /**
     * 메인 통합 조회
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Main Management", description = "메인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 통합 조회 성공", content = @Content(schema = @Schema(implementation = MainApiResponseVO.class)))})
    @Operation(operationId="Main", summary = "메인 통합 조회", description = "메인 통합 조회한다.")
    @GetMapping(path = {"/api/main"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMainSectionInfo(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> mainService.getMainSectionList((MainViewRequestVO) params(MainViewRequestVO.class, searchMap, pageable)))
                        .orElse(MainApiResponseVO.builder().build()));
    }

    /**
     * 메인 캘린더 조회
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Main Management", description = "메인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 캘린더 조회 성공", content = @Content(schema = @Schema(implementation = MainApiResponseVO.class)))})
    @Operation(operationId="Main", summary = "메인 캘린더 조회", description = "메인 캘린더 조회한다.")
    @GetMapping(path = {"/api/main/calendar"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMainCalendar(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> mainService.getMainCalendar((MainViewRequestVO) params(MainViewRequestVO.class, searchMap, pageable)))
                        .orElse(MainApiResponseVO.builder().build()));
    }

    /**
     * 메인 참여/소통 조회
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Main Management", description = "메인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 참여/소통 조회 성공", content = @Content(schema = @Schema(implementation = MainApiResponseVO.class)))})
    @Operation(operationId="Main", summary = "메인 참여/소통 조회", description = "메인 참여/소통 조회한다.")
    @GetMapping(path = {"/api/main/communication"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMainCommunication(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> mainService.getMainCommunication((MainViewRequestVO) params(MainViewRequestVO.class, searchMap, pageable)))
                        .orElse(MainApiResponseVO.builder().build()));
    }

    /**
     * 메인 배너 조회
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Main Management", description = "메인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메인 배너/팝업 조회 성공", content = @Content(schema = @Schema(implementation = MainApiResponseVO.class)))})
    @Operation(operationId="Main", summary = "메인 배너/팝업 조회", description = "메인 배너/팝업 조회한다.")
    @GetMapping(path = {"/api/main/display"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMainDisplay(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> mainService.getMainDisplay((MainViewRequestVO) params(MainViewRequestVO.class, searchMap, pageable)))
                        .orElse(MainApiResponseVO.builder().build()));
    }
}
