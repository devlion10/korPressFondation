package kr.or.kpf.lms.biz.user.instructor.qualification.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.user.instructor.service.InstructorService;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 강사 관리 > 강사 관리 API 관련 Controller
 */
@Tag(name = "Instructor Management", description = "강사 관리 API")
@RestController
@Validated
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_SUPER') or hasRole('ROLE_ADMIN_USER')")
@RequestMapping(value = "/api/instructor/qualification")
public class InstructorQualificationApiController extends CSApiControllerSupport { }
