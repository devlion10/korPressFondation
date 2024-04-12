package kr.or.kpf.lms.biz.communication.review.service;

import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewApiRequestVO;
import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewViewRequestVO;
import kr.or.kpf.lms.biz.communication.review.vo.response.ReviewApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonCommunicationRepository;
import kr.or.kpf.lms.repository.EducationReviewRepository;
import kr.or.kpf.lms.repository.entity.EducationReview;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 참여 / 소통 > 교육 후기방 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ReviewService extends CSServiceSupport {

    /** 참여 / 소통 공통 Repository */
    private final CommonCommunicationRepository commonCommunicationRepository;
    /** 교육 후기 Repository */
    private final EducationReviewRepository educationReviewRepository;

    /**
     * 참여 / 소통 > 교육 후기방 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ReviewViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonCommunicationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 교육 후기 조회수 업데이트
     *
     * @param sequenceNo
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport updateViewCount(BigInteger sequenceNo) {

        EducationReview educationReview = educationReviewRepository.findOne(Example.of(EducationReview.builder().sequenceNo(sequenceNo).build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "존재하지 않는 교육 후기"));

        /** 조회수 + 1 */
        educationReview.setViewCount(educationReview.getViewCount().add(BigInteger.ONE));
        educationReviewRepository.saveAndFlush(educationReview);

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
