package kr.or.kpf.lms.biz.mypage.classguide.classsubject.controller;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.service.ClassSubjectService;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.CreateClassSubject;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request.ClassSubjectApiRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request.ClassSubjectViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.response.ClassSubjectApiResponseVO;
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
 * 수업지도안 교과 교과 API 관련 Controller
 */
@Tag(name = "My Page", description = "마이페이지 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/mypage/class-subject")
public class ClassSubjectApiController extends CSApiControllerSupport {

    private final ClassSubjectService classSubjectService;

    @Tag(name = "My Page", description = "마이페이지 API")
    @Operation(operationId = "ClassSubject", summary = "수업지도안 교과 조회", description = "수업지도안 교과를 조회한다.")
    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getClassSubjectList(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> classSubjectService.getList((ClassSubjectViewRequestVO) params(ClassSubjectViewRequestVO.class, searchMap, pageable)))
                        .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
    }
}
