package kr.or.kpf.lms.biz.communication.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.communication.event.service.EventService;
import kr.or.kpf.lms.biz.communication.event.vo.request.EventViewRequestVO;
import kr.or.kpf.lms.biz.communication.event.vo.response.EventViewResponseVO;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
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
 * 참여 / 소통 > 이벤트 API 관련 Controller
 */
@Tag(name = "Communication", description = "참여 / 소통 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/communication/event")
public class EventApiController extends CSApiControllerSupport {

    private final EventService eventService;

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
    public ResponseEntity<EventViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody EventViewRequestVO reviewViewRequestVO) {
        return null;
    }

    /**
     * 이벤트 조회 API
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
    @Operation(operationId = "EducationPlan", summary = "이벤트 조회", description = "이벤트 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="sequenceNo", required = false) BigInteger sequenceNo,
                                          @RequestParam(value="containTextType", required = false) String containTextType,
                                          @RequestParam(value="containText", required = false) String containText) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> eventService.getList((EventViewRequestVO) params(EventViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }
}
