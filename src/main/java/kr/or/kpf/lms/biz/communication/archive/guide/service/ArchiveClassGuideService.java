package kr.or.kpf.lms.biz.communication.archive.guide.service;

import kr.or.kpf.lms.biz.communication.archive.guide.vo.request.ArchiveClassGuideViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonCommunicationRepository;
import kr.or.kpf.lms.repository.ClassGuideRepository;
import kr.or.kpf.lms.repository.entity.ClassGuide;
import kr.or.kpf.lms.repository.entity.LmsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 참여 / 소통 > 자료실 - 수업지도안 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ArchiveClassGuideService extends CSServiceSupport {

    /** 참여 / 소통 공통 Repository */
    private final CommonCommunicationRepository commonCommunicationRepository;
    /** 자료실 - 수업지도안 Repository */
    private final ClassGuideRepository classGuideRepository;

    /**
     * 참여 / 소통 > 자료실 - 수업지도안 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ArchiveClassGuideViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonCommunicationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 자료실 - 수업지도안 조회수 업데이트
     *
     * @param classGuideCode
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport updateViewCount(String classGuideCode) {
        ClassGuide classGuide = classGuideRepository.findOne(Example.of(ClassGuide.builder().classGuideCode(classGuideCode).build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "존재하지 않는 자료실"));

        /** 조회수 + 1 */
        classGuide.setViewCount(classGuide.getViewCount().add(BigInteger.ONE));
        classGuideRepository.saveAndFlush(classGuide);

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
