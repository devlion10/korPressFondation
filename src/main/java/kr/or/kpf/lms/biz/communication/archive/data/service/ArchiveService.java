package kr.or.kpf.lms.biz.communication.archive.data.service;

import kr.or.kpf.lms.biz.communication.archive.data.vo.request.ArchiveViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonCommunicationRepository;
import kr.or.kpf.lms.repository.EducationDataRepository;
import kr.or.kpf.lms.repository.entity.LmsData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 참여 / 소통 > 자료실 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ArchiveService extends CSServiceSupport {

    /** 참여 / 소통 공통 Repository */
    private final CommonCommunicationRepository commonCommunicationRepository;
    /** 자료실 Repository */
    private final EducationDataRepository educationDataRepository;

    /**
     * 참여 / 소통 > 자료실 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ArchiveViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonCommunicationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 자료실 조회수 업데이트
     *
     * @param sequenceNo
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport updateViewCount(BigInteger sequenceNo) {
        LmsData lmsData = educationDataRepository.findOne(Example.of(LmsData.builder().sequenceNo(sequenceNo).build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "존재하지 않는 자료실"));

        /** 조회수 + 1 */
        lmsData.setViewCount(lmsData.getViewCount().add(BigInteger.ONE));
        educationDataRepository.saveAndFlush(lmsData);

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
