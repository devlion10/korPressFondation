package kr.or.kpf.lms.biz.servicecenter.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.servicecenter.notice.service.NoticeService;
import kr.or.kpf.lms.biz.servicecenter.notice.vo.request.NoticeViewRequestVO;
import kr.or.kpf.lms.biz.servicecenter.notice.vo.response.NoticeViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
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
import java.util.Optional;

/**
 * 공지사항 API 관련 Controller
 */
@Tag(name = "Service Center", description = "고객센터 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/service-center/notice")
public class NoticeApiController extends CSApiControllerSupport {

    private final NoticeService noticeService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param noticeViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoticeViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody NoticeViewRequestVO noticeViewRequestVO) {
        return null;
    }

    /**
     * 공지사항 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param noticeSerialNo
     * @param containTextType
     * @param containText
     * @return
     */
    @Tag(name = "Service Center", description = "고객센터 API")
    @Operation(operationId = "Notice", summary = "공지사항 조회", description = "공지사항 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="noticeSerialNo", required = false) BigInteger noticeSerialNo,
                                          @RequestParam(value="containTextType", required = false) String containTextType,
                                          @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> noticeService.getNotice((NoticeViewRequestVO) params(NoticeViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }

    /**
     * 공지사항 조회수 업데이트
     *
     * @param request
     * @param response
     * @param noticeSerialNo
     * @return
     */
    @Tag(name = "Service Center", description = "고객센터 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "공지사항 조회수 업데이트 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "Notice", summary = "공지사항 조회수 업데이트", description = "공지사항 조회수 업데이트한다.")
    @PutMapping(value = "/update-view-count/{noticeSerialNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> updateViewCount(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "공지사항 시리얼 번호") @PathVariable(value = "noticeSerialNo", required = true) String noticeSerialNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(noticeService.updateViewCount(noticeSerialNo))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7042, "공지사항 조회수 업데이트 실패")));
    }
}
