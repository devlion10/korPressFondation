package kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 수업지도안 API 관련 Controller
 */
@Tag(name = "My Page", description = "마이페이지 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/mypage/class-guide-subject")
public class ClassGuideSubjectApiController extends CSApiControllerSupport {
    public interface CreateClassGuideSubject {}
}
