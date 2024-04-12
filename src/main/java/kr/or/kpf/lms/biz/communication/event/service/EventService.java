package kr.or.kpf.lms.biz.communication.event.service;

import kr.or.kpf.lms.biz.communication.event.vo.request.EventViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonCommunicationRepository;
import kr.or.kpf.lms.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 참여 / 소통 > 이벤트관련 Service
 */
@Service
@RequiredArgsConstructor
public class EventService extends CSServiceSupport {
    /** 참여 / 소통 공통 Repository */
    private final CommonCommunicationRepository commonCommunicationRepository;

    /**
     * 참여 / 소통 > 이벤트조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(EventViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonCommunicationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }
}
