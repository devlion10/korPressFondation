package kr.or.kpf.lms.biz.servicecenter.topqna.service;

import kr.or.kpf.lms.biz.servicecenter.topqna.vo.request.TopQnaViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonServiceCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 자주묻는 질문 관련 Service
 */
@Service
@RequiredArgsConstructor
public class TopQnaService extends CSServiceSupport {

    private final CommonServiceCenterRepository commonServiceCenterRepository;

    /**
     * 고객센터 > 자주묻는 질문 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getTopQnaList(TopQnaViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonServiceCenterRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }
}
