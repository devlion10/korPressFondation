package kr.or.kpf.lms.biz.business.instructor.dist.crtramt.service;

import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.request.BizInstructorDistCrtrAmtApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.request.BizInstructorDistCrtrAmtViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.response.BizInstructorDistCrtrAmtApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstructorDistCrtrAmtRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorDistCrtrAmt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorDistCrtrAmtService extends CSServiceSupport {
    private static final String PREFIX_INSTR_DIST_CRTRAMT = "BIDA";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorDistCrtrAmtRepository bizPbancInstructorDistCrtrAmtRepository;

    /**
     이동거리 기준단가 정보 생성
     */
    public BizInstructorDistCrtrAmtApiResponseVO createBizInstructorDistCrtrAmt(BizInstructorDistCrtrAmtApiRequestVO requestObject) {
        BizInstructorDistCrtrAmt entity = BizInstructorDistCrtrAmt.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrDistCrtrAmtNo(commonBusinessRepository.generateCode(PREFIX_INSTR_DIST_CRTRAMT));
        BizInstructorDistCrtrAmtApiResponseVO result = BizInstructorDistCrtrAmtApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancInstructorDistCrtrAmtRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     이동거리 기준단가 정보 업데이트
     */
    public BizInstructorDistCrtrAmtApiResponseVO updateBizInstructorDistCrtrAmt(BizInstructorDistCrtrAmtApiRequestVO requestObject) {
        return bizPbancInstructorDistCrtrAmtRepository.findOne(Example.of(BizInstructorDistCrtrAmt.builder()
                        .bizInstrDistCrtrAmtNo(requestObject.getBizInstrDistCrtrAmtNo())
                        .build()))
                .map(bizPbancInstructorDistCrtrAmt -> {
                    copyNonNullObject(requestObject, bizPbancInstructorDistCrtrAmt);

                    BizInstructorDistCrtrAmtApiResponseVO result = BizInstructorDistCrtrAmtApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancInstructorDistCrtrAmtRepository.saveAndFlush(bizPbancInstructorDistCrtrAmt), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 모집 미존재"));
    }

    /**
     이동거리 기준단가 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorDistCrtrAmt(BizInstructorDistCrtrAmtApiRequestVO requestObject) {
        bizPbancInstructorDistCrtrAmtRepository.delete(bizPbancInstructorDistCrtrAmtRepository.findOne(Example.of(BizInstructorDistCrtrAmt.builder()
                        .bizInstrDistCrtrAmtNo(requestObject.getBizInstrDistCrtrAmtNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강사 모집 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     이동거리 기준단가 리스트
     */
    public <T> Page<T> getBizInstructorDistCrtrAmtList(BizInstructorDistCrtrAmtViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     이동거리 기준단가 상세
     */
    public <T> T getBizInstructorDistCrtrAmt(BizInstructorDistCrtrAmtViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

}
