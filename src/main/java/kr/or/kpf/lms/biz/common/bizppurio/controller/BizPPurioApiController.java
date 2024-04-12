package kr.or.kpf.lms.biz.common.bizppurio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.common.bizppurio.service.BizPPurioService;
import kr.or.kpf.lms.biz.common.bizppurio.vo.request.BizPPurioApiRequestVO;
import kr.or.kpf.lms.biz.common.bizppurio.vo.response.BizPPurioApiResponseVO;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * 비즈뿌리오 API 관련 Controller
 */
@Tag(name = "Bizppurio Management", description = "비즈뿌리오 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/bizppurio")
public class BizPPurioApiController extends CSApiControllerSupport {

    private final BizPPurioService bizPPurioService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param bizPPurioApiRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPPurioApiResponseVO> swaggerUse(HttpServletRequest request, HttpServletResponse response, @RequestBody BizPPurioApiRequestVO bizPPurioApiRequestVO) {
        return null;
    }

    /**
     * 비즈뿌리오 문자 전송
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Bizppurio Management", description = "비즈뿌리오 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비즈뿌리오 문자 전송", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId="BizPPurio", summary = "비즈뿌리오 문자 전송", description = "비즈뿌리오 문자 전송한다.")
    @PostMapping(path = {"/send-sms"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendSMS(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @NotNull @RequestBody BizPPurioApiRequestVO bizPPurioApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPPurioService.sendSMS(bizPPurioApiRequestVO))
                        .orElse(BizPPurioApiResponseVO.builder().build()));
    }
}
