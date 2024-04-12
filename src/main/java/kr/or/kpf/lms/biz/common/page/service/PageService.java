package kr.or.kpf.lms.biz.common.page.service;

import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.common.page.vo.request.PageViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 홈페이지 관리 > 문서 관련 Service
 */
@Service
@RequiredArgsConstructor
public class PageService extends CSServiceSupport {

    /** 홈페이지 관리 공통 Repository */
    private final CommonRepository commonRepository;

    /**
     * 문서 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(PageViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

}
