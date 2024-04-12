package kr.or.kpf.lms.biz.user.instructor.qualification.service;

import kr.or.kpf.lms.biz.user.instructor.qualification.vo.request.InstructorQualificationViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.qualification.vo.request.InstructorQulificationApiRequestVO;
import kr.or.kpf.lms.biz.user.instructor.qualification.vo.response.InstructorQualificationApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonUserRepository;
import kr.or.kpf.lms.repository.InstructorQlfcRepository;
import kr.or.kpf.lms.repository.entity.InstructorQlfc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorQualificationService extends CSServiceSupport {

    /** 사용자 관리 공통 */
    private final CommonUserRepository commonUserRepository;
    /** 강사 */
    private final InstructorQlfcRepository instructorQlfcRepository;

    /**
     * 강사 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(InstructorQualificationViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 정보 생성
     */
    public InstructorQualificationApiResponseVO createInfo(InstructorQulificationApiRequestVO requestObject) {
        InstructorQlfc entity = InstructorQlfc.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        InstructorQualificationApiResponseVO result = InstructorQualificationApiResponseVO.builder().build();
        BeanUtils.copyProperties(instructorQlfcRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     * 강사 정보 업데이트
     *
     * @param requestObject
     * @return
     */
    public InstructorQualificationApiResponseVO updateInfo(InstructorQulificationApiRequestVO requestObject) {
        return instructorQlfcRepository.findOne(Example.of(InstructorQlfc.builder()
                        .instrSerialNo(requestObject.getInstrSerialNo())
                        .build()))
                .map(InstructorQlfc -> {
                    InstructorQualificationApiResponseVO result = InstructorQualificationApiResponseVO.builder().build();
                    copyNonNullObject(requestObject, InstructorQlfc);
                    BeanUtils.copyProperties(instructorQlfcRepository.saveAndFlush(InstructorQlfc), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 강사 정보 미존재"));
    }

}
