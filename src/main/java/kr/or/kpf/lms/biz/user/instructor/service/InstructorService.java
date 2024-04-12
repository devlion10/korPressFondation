package kr.or.kpf.lms.biz.user.instructor.service;

import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorApiRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.request.InstructorViewRequestVO;
import kr.or.kpf.lms.biz.user.instructor.vo.response.InstructorApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonUserRepository;
import kr.or.kpf.lms.repository.InstructorInfoRepository;
import kr.or.kpf.lms.repository.entity.InstructorInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorService extends CSServiceSupport {

    /** 사용자 관리 공통 */
    private final CommonUserRepository commonUserRepository;
    /** 강사 */
    private final InstructorInfoRepository instructorInfoRepository;

    /**
     * 강사 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(InstructorViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }


    public InstructorInfo getInstrInfo(String userId){
        List<InstructorInfo> instr = instructorInfoRepository.findAll(Example.of(InstructorInfo.builder().userId(userId).build()));
        if(instr!= null && instr.size()>0){
            return instr.get(instr.size()-1);
        }else{
            return null;
        }
    }
    /**
     * 강사 정보 전체 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> List<T> getAllList(InstructorViewRequestVO requestObject) {
        return (List<T>) Optional.ofNullable(commonUserRepository.allEntityList(requestObject))
                .orElse(new ArrayList<>());
    }

    /**
     강사 정보 생성
     */
    public InstructorApiResponseVO createInfo(InstructorApiRequestVO requestObject) {
        InstructorInfo entity = InstructorInfo.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        InstructorApiResponseVO result = InstructorApiResponseVO.builder().build();
        BeanUtils.copyProperties(instructorInfoRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     * 강사 정보 업데이트
     *
     * @param requestObject
     * @return
     */
    public InstructorApiResponseVO updateInfo(InstructorApiRequestVO requestObject) {
        return instructorInfoRepository.findOne(Example.of(InstructorInfo.builder()
                        .instrSerialNo(requestObject.getInstrSerialNo())
                        .build()))
                .map(InstructorInfo -> {
                    InstructorApiResponseVO result = InstructorApiResponseVO.builder().build();
                    copyNonNullObject(requestObject, InstructorInfo);
                    BeanUtils.copyProperties(instructorInfoRepository.saveAndFlush(InstructorInfo), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 강사 정보 미존재"));
    }

}
