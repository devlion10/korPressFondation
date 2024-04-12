package kr.or.kpf.lms.biz.educenter.press.service;

import kr.or.kpf.lms.biz.educenter.press.vo.request.PressApiRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.request.PressViewRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.response.PressApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonEducationCenterRepository;
import kr.or.kpf.lms.repository.PressReleaseRepository;
import kr.or.kpf.lms.repository.entity.PressRelease;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 연수원 소개 > 행사소개(보도자료) 관련 Service
 */
@Service
@RequiredArgsConstructor
public class PressService extends CSServiceSupport {

    /** 연수원 소개 공통 Repository */
    private final CommonEducationCenterRepository commonEducationCenterRepository;
    /** 행사소개(보도자료) Repository */
    private final PressReleaseRepository pressReleaseRepository;

    /**
     * 연수원 소개 > 행사소개(보도자료) 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(PressViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonEducationCenterRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 행사소개(보도자료) 생성
     *
     * @param pressApiRequestVO
     * @return
     */
    public PressApiResponseVO createPress(PressApiRequestVO pressApiRequestVO) {
        PressRelease entity = PressRelease.builder().build();
        copyNonNullObject(pressApiRequestVO, entity);

        /** 조회수 '0' 셋팅 */
        entity.setViewCount(BigInteger.ZERO);

        PressApiResponseVO result = PressApiResponseVO.builder().build();

        BeanUtils.copyProperties(pressReleaseRepository.saveAndFlush(entity), result);
        return result;
    }

    /**
     * 행사소개(보도자료) 업데이트
     *
     * @param pressApiRequestVO
     * @return
     */
    public PressApiResponseVO updatePress(PressApiRequestVO pressApiRequestVO) {
        return pressReleaseRepository.findOne(Example.of(PressRelease.builder().sequenceNo(pressApiRequestVO.getSequenceNo()).build()))
                .map(educationPress -> {

                    copyNonNullObject(pressApiRequestVO, educationPress);

                    PressApiResponseVO result = PressApiResponseVO.builder().build();
                    BeanUtils.copyProperties(pressReleaseRepository.saveAndFlush(educationPress), result);

                    return result;
                })
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "해당 행사소개(보도자료) 미존재"));
    }

    /**
     * 행사소개(보도자료) 조회수 업데이트
     *
     * @param sequenceNo
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport updateViewCount(BigInteger sequenceNo) {

        PressRelease educationPress = pressReleaseRepository.findOne(Example.of(PressRelease.builder().sequenceNo(sequenceNo).build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "존재하지 않는 행사소개(보도자료)"));

        /** 조회수 + 1 */
        educationPress.setViewCount(educationPress.getViewCount().add(BigInteger.ONE));
        pressReleaseRepository.saveAndFlush(educationPress);

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
