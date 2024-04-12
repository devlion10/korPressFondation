package kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.service;

import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request.BizInstructorDistCrtrAmtItemApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request.BizInstructorDistCrtrAmtItemViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.response.BizInstructorDistCrtrAmtItemApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstructorDistCrtrAmtItemRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorDistCrtrAmtItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorDistCrtrAmtItemService extends CSServiceSupport {
    private static final String PREFIX_INSTR_DIST_CRTRAMT_ITEM = "BIAI";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorDistCrtrAmtItemRepository bizPbancInstructorDistCrtrAmtItemRepository;

    /**
     이동거리 기준단가 항목 정보 생성
     */
    public BizInstructorDistCrtrAmtItemApiResponseVO createBizInstructorDistCrtrAmtItem(BizInstructorDistCrtrAmtItemApiRequestVO requestObject) {
        BizInstructorDistCrtrAmtItem entity = BizInstructorDistCrtrAmtItem.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrDistCrtrAmtItemNo(commonBusinessRepository.generateCode(PREFIX_INSTR_DIST_CRTRAMT_ITEM));
        BizInstructorDistCrtrAmtItemApiResponseVO result = BizInstructorDistCrtrAmtItemApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancInstructorDistCrtrAmtItemRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     이동거리 기준단가 항목 정보 업데이트
     */
    public BizInstructorDistCrtrAmtItemApiResponseVO updateBizInstructorDistCrtrAmtItem(BizInstructorDistCrtrAmtItemApiRequestVO requestObject) {
        return bizPbancInstructorDistCrtrAmtItemRepository.findOne(Example.of(BizInstructorDistCrtrAmtItem.builder()
                        .bizInstrDistCrtrAmtItemNo(requestObject.getBizInstrDistCrtrAmtItemNo())
                        .build()))
                .map(bizPbancInstructorDistCrtrAmtItem -> {
                    copyNonNullObject(requestObject, bizPbancInstructorDistCrtrAmtItem);

                    BizInstructorDistCrtrAmtItemApiResponseVO result = BizInstructorDistCrtrAmtItemApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancInstructorDistCrtrAmtItemRepository.saveAndFlush(bizPbancInstructorDistCrtrAmtItem), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 모집 미존재"));
    }

    /**
     이동거리 기준단가 항목 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorDistCrtrAmtItem(BizInstructorDistCrtrAmtItemApiRequestVO requestObject) {
        bizPbancInstructorDistCrtrAmtItemRepository.delete(bizPbancInstructorDistCrtrAmtItemRepository.findOne(Example.of(BizInstructorDistCrtrAmtItem.builder()
                        .bizInstrDistCrtrAmtItemNo(requestObject.getBizInstrDistCrtrAmtItemNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강사 모집 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     이동거리 기준단가 항목 리스트
     */
    public <T> Page<T> getBizInstructorDistCrtrAmtItemList(BizInstructorDistCrtrAmtItemViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     이동거리 기준단가 항목 상세
     */
    public <T> T getBizInstructorDistCrtrAmtItem(BizInstructorDistCrtrAmtItemViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

}
