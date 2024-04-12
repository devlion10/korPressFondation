package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.service;

import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request.BizInstructorClclnDdlnApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request.BizInstructorClclnDdlnViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.response.BizInstructorClclnDdlnApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstructorClclnDdlnRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorClclnDdln;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorClclnDdlnService extends CSServiceSupport {
    private static final String PREFIX_INSTR_CLCLN_DDLN = "BICD";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorClclnDdlnRepository bizInstructorClclnDdlnRepository;

    /**
     정산 마감일 정보 생성
     */
    public BizInstructorClclnDdlnApiResponseVO createBizInstructorClclnDdln(BizInstructorClclnDdlnApiRequestVO requestObject) {
        BizInstructorClclnDdln entity = BizInstructorClclnDdln.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrClclnDdlnNo(commonBusinessRepository.generateCode(PREFIX_INSTR_CLCLN_DDLN));
        BizInstructorClclnDdlnApiResponseVO result = BizInstructorClclnDdlnApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizInstructorClclnDdlnRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     정산 마감일 정보 업데이트
     */
    public BizInstructorClclnDdlnApiResponseVO updateBizInstructorClclnDdln(BizInstructorClclnDdlnApiRequestVO requestObject) {
        return bizInstructorClclnDdlnRepository.findById(requestObject.getBizInstrClclnDdlnNo())
                .map(bizInstructorClclnDdln -> {
                    copyNonNullObject(requestObject, bizInstructorClclnDdln);

                    BizInstructorClclnDdlnApiResponseVO result = BizInstructorClclnDdlnApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstructorClclnDdlnRepository.saveAndFlush(bizInstructorClclnDdln), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 마감일 미존재"));
    }

    /**
     정산 마감일 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorClclnDdln(BizInstructorClclnDdlnApiRequestVO requestObject) {
        bizInstructorClclnDdlnRepository.delete(bizInstructorClclnDdlnRepository.findById(requestObject.getBizInstrClclnDdlnNo())
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 마감일 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     정산 마감일 리스트
     */
    public <T> Page<T> getBizInstructorClclnDdlnList(BizInstructorClclnDdlnViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     정산 마감일 상세
     */
    public <T> T getBizInstructorClclnDdln(BizInstructorClclnDdlnViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     정산 마감일 정보 생성
     */
    public BizInstructorClclnDdlnApiResponseVO selectBizInstructorClclnDdlnByNearDate() {
        LocalDate today = LocalDate.now();
        BizInstructorClclnDdln bizInstructorClclnDdln = bizInstructorClclnDdlnRepository.findOneByBizInstrClclnDdlnYrAndBizInstrClclnDdlnMm(today.getYear(), today.getMonthValue());

        if (bizInstructorClclnDdln != null && !bizInstructorClclnDdln.equals(null)) {
            BizInstructorClclnDdlnApiResponseVO result = BizInstructorClclnDdlnApiResponseVO.builder().build();
            BeanUtils.copyProperties(bizInstructorClclnDdln, result);

            return result;
        } else return null;
    }


    /**
     정산 마감일 정보 생성
     */
    public BizInstructorClclnDdlnApiResponseVO findOneByYearAndMonth() {
        LocalDate today = LocalDate.now();
        BizInstructorClclnDdln bizInstructorClclnDdln = bizInstructorClclnDdlnRepository.selectJPQLByNearDate();
        if (bizInstructorClclnDdln != null && !bizInstructorClclnDdln.equals(null)) {
            BizInstructorClclnDdlnApiResponseVO result = BizInstructorClclnDdlnApiResponseVO.builder().build();
            BeanUtils.copyProperties(bizInstructorClclnDdln, result);

            return result;
        } else return null;
    }

}
