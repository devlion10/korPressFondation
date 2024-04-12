package kr.or.kpf.lms.biz.user.instructor.history.service;

import kr.or.kpf.lms.biz.user.instructor.history.vo.request.InstructorHistoryApiRequestVO;
import kr.or.kpf.lms.biz.user.instructor.history.vo.request.InstructorHistoryViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.history.vo.response.InstructorHistoryApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonUserRepository;
import kr.or.kpf.lms.repository.InstructorHistRepository;
import kr.or.kpf.lms.repository.entity.InstructorHist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorHistoryService extends CSServiceSupport {

    /** 사용자 관리 공통 */
    private final CommonUserRepository commonUserRepository;
    /** 강사 */
    private final InstructorHistRepository instructorHistRepository;

    /**
     * 강사 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(InstructorHistoryViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 정보 생성
     */
    public InstructorHistoryApiResponseVO createInfo(InstructorHistoryViewRequestVO requestObject) {
        InstructorHist entity = InstructorHist.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        InstructorHistoryApiResponseVO result = InstructorHistoryApiResponseVO.builder().build();
        BeanUtils.copyProperties(instructorHistRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     * 강사 정보 업데이트
     *
     * @param requestObject
     * @return
     */
    public InstructorHistoryApiResponseVO updateInfo(InstructorHistoryApiRequestVO requestObject) {
        return instructorHistRepository.findOne(Example.of(InstructorHist.builder()
                        .instrSerialNo(requestObject.getInstrSerialNo())
                        .build()))
                .map(InstructorHist -> {
                    InstructorHistoryApiResponseVO result = InstructorHistoryApiResponseVO.builder().build();
                    copyNonNullObject(requestObject, InstructorHist);
                    BeanUtils.copyProperties(instructorHistRepository.saveAndFlush(InstructorHist), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 강사 정보 미존재"));
    }

}
