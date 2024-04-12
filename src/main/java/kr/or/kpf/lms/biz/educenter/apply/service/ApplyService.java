package kr.or.kpf.lms.biz.educenter.apply.service;

import kr.or.kpf.lms.biz.educenter.apply.vo.request.ApplyApiRequestVO;
import kr.or.kpf.lms.biz.educenter.apply.vo.request.ApplyViewRequestVO;
import kr.or.kpf.lms.biz.educenter.apply.vo.response.ApplyApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonEducationCenterRepository;
import kr.or.kpf.lms.repository.EduPlaceAplyRepository;
import kr.or.kpf.lms.repository.entity.EduPlaceAply;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * 연수원 소개 > 교육장 사용 신청 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ApplyService extends CSServiceSupport {

    /** 연수원 소개 공통 Repository */
    private final CommonEducationCenterRepository commonEducationCenterRepository;
    /** 교육장 사용 신청 Repository */
    private final EduPlaceAplyRepository eduPlaceAplyRepository;

    /**
     * 연수원 소개 > 교육장 사용 신청 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ApplyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonEducationCenterRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 교육장 사용 신청 생성
     *
     * @param applyApiRequestVO
     * @return
     */
    public ApplyApiResponseVO createApply(ApplyApiRequestVO applyApiRequestVO) {
        EduPlaceAply entity = EduPlaceAply.builder().build();
        copyNonNullObject(applyApiRequestVO, entity);
        
        ApplyApiResponseVO result = ApplyApiResponseVO.builder().build();

        BeanUtils.copyProperties(eduPlaceAplyRepository.saveAndFlush(entity), result);
        return result;
    }

    /**
     * 교육장 사용 신청 업데이트
     *
     * @param applyApiRequestVO
     * @return
     */
    public ApplyApiResponseVO updateApply(ApplyApiRequestVO applyApiRequestVO) {
        return eduPlaceAplyRepository.findOne(Example.of(EduPlaceAply.builder().sequenceNo(applyApiRequestVO.getSequenceNo()).build()))
                .map(eduPlaceAply -> {

                    copyNonNullObject(applyApiRequestVO, eduPlaceAply);

                    ApplyApiResponseVO result = ApplyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(eduPlaceAplyRepository.saveAndFlush(eduPlaceAply), result);

                    return result;
                })
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "해당 교육장 사용 신청 미존재"));
    }
}
