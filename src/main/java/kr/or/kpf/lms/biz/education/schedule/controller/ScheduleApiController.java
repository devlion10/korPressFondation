package kr.or.kpf.lms.biz.education.schedule.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.education.schedule.service.ScheduleService;
import kr.or.kpf.lms.biz.education.schedule.vo.request.ScheduleViewRequestVO;
import kr.or.kpf.lms.biz.education.schedule.vo.response.ScheduleViewResponseVO;
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
import java.util.ArrayList;
import java.util.Optional;

/**
 * 교육 신청 API 관련 Controller
 */
@Tag(name = "Education Application", description = "교육 신청 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/education/schedule")
public class ScheduleApiController extends CSApiControllerSupport {

    private final ScheduleService scheduleService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param scheduleViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody ScheduleViewRequestVO scheduleViewRequestVO) {
        return null;
    }

    /**
     * 교육 일정 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @return
     */
    @Tag(name = "Education Application", description = "교육 신청 API")
    @ApiImplicitParams(
            {@ApiImplicitParam(
                    name = "yearAndMonth"
                    , value = "운영 기간(YYYY-MM)"
                    , required = true
                    , dataType = "string"
                    , example = "2022-09"
                    , paramType = "query"
                    , defaultValue = ""),
            })
    @Operation(operationId = "Education Application", summary = "교육 일정 조회", description = "교육 일정 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                          @RequestParam(value="yearAndMonth") String yearAndMonth) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> scheduleService.getEducationSchedule((ScheduleViewRequestVO) params(ScheduleViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }
}
