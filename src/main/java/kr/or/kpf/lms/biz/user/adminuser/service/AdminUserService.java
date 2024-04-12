package kr.or.kpf.lms.biz.user.adminuser.service;

import kr.or.kpf.lms.biz.user.adminuser.vo.request.AdminUserViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.AdminUserRepository;
import kr.or.kpf.lms.repository.CommonUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 유저 정보 API 관련 Controller
 */
@Service
@RequiredArgsConstructor
public class AdminUserService extends CSServiceSupport {
    /** 사용자 관리 공통 */
    private final CommonUserRepository commonUserRepository;

    /**
     * 어드민 계정 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(AdminUserViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }
}
