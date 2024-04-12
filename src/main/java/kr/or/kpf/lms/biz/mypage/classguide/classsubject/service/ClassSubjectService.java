package kr.or.kpf.lms.biz.mypage.classguide.classsubject.service;

import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request.ClassSubjectApiRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.request.ClassSubjectViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.classsubject.vo.response.ClassSubjectApiResponseVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.ClassSubjectRepository;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import kr.or.kpf.lms.repository.entity.ClassSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
public class ClassSubjectService extends CSServiceSupport {

    private static final String PREFIX_SUBJECT = "SBJC";

    private final CommonMyPageRepository commonMyPageRepository;
    private final ClassSubjectRepository classSubjectRepository;

    /**
     * 수업지도안 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ClassSubjectViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 수업지도안 생성
     *
     * @param classSubjectApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public ClassSubjectApiResponseVO createClassSubject(ClassSubjectApiRequestVO classSubjectApiRequestVO) {
        ClassSubject entity = ClassSubject.builder().build();
        copyNonNullObject(classSubjectApiRequestVO, entity);

        ClassSubjectApiResponseVO result = ClassSubjectApiResponseVO.builder().build();
        entity.setIndividualCode(commonMyPageRepository.generateClassSubjectCode(PREFIX_SUBJECT));
        BeanUtils.copyProperties(classSubjectRepository.saveAndFlush(entity), result);
        return result;

    }
}
