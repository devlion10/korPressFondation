package kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.service;

import kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.request.ClassGuideSubjectApiRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.request.ClassGuideSubjectViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.response.ClassGuideSubjectApiResponseVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.ClassGuideSubjectRepository;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import kr.or.kpf.lms.repository.entity.ClassGuideSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 수업지도안 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ClassGuideSubjectService extends CSServiceSupport {
    private final CommonMyPageRepository commonMyPageRepository;
    private final ClassGuideSubjectRepository classGuideSubjectRepository;

    /**
     * 수업지도안 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ClassGuideSubjectViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 수업지도안 생성
     *
     * @param classGuideSubjectApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public ClassGuideSubjectApiResponseVO createInfo(ClassGuideSubjectApiRequestVO classGuideSubjectApiRequestVO) {
        ClassGuideSubject entity = ClassGuideSubject.builder().build();
        copyNonNullObject(classGuideSubjectApiRequestVO, entity);

        ClassGuideSubjectApiResponseVO result = ClassGuideSubjectApiResponseVO.builder().build();
        BeanUtils.copyProperties(classGuideSubjectRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     * 수업지도안 업데이트
     *
     * @param requestVO
     * @return
     */
    public ClassGuideSubjectApiResponseVO updateInfo(ClassGuideSubjectApiRequestVO requestVO) {
        return classGuideSubjectRepository.findOne(Example.of(ClassGuideSubject.builder().sequenceNo(requestVO.getSequenceNo()).build()))
                .map(classGuideSubject -> {
                    copyNonNullObject(requestVO, classGuideSubject);

                    ClassGuideSubjectApiResponseVO result = ClassGuideSubjectApiResponseVO.builder().build();
                    BeanUtils.copyProperties(classGuideSubjectRepository.saveAndFlush(classGuideSubject), result);

                    return result;
                })
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4005, "해당 수업지도안 미존재"));
    }

    /**
     수업지도안 정보 삭제
     */
    public CSResponseVOSupport deleteInfo(ClassGuideSubjectApiRequestVO requestObject) {
        classGuideSubjectRepository.delete(classGuideSubjectRepository.findById(requestObject.getSequenceNo())
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4007, "이미 삭제된 수업지도안 입니다.")));

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
