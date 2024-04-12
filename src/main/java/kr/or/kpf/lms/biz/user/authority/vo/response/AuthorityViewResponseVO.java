package kr.or.kpf.lms.biz.user.authority.vo.response;

import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 사용자 관리 > 권한 관리 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/user/authority")
public class AuthorityViewResponseVO extends CSApiControllerSupport {

}
