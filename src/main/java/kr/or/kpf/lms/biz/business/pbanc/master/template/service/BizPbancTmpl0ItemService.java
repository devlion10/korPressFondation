package kr.or.kpf.lms.biz.business.pbanc.master.template.service;

import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.BizPbancTmpl0ItemApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response.BizPbancTmpl0ItemApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.BizPbancTmpl0ItemRepository;
import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizPbancTmpl0ItemService extends CSServiceSupport {
    private final BizPbancTmpl0ItemRepository bizPbancTmpl0ItemRepository;

    /**
     사업 공고 템플릿 0 정보 생성
     */
    public BizPbancTmpl0ItemApiResponseVO createBizPbancTmpl0Item(BizPbancTmpl0ItemApiRequestVO requestObject) {
        BizPbancTmpl0Item entity = BizPbancTmpl0Item.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizPbancTmpl0ItemApiResponseVO result = BizPbancTmpl0ItemApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl0ItemRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     사업 공고 템플릿 0 정보 업데이트
     */
    public BizPbancTmpl0ItemApiResponseVO updateBizPbancTmpl0Item(BizPbancTmpl0ItemApiRequestVO requestObject) {
        return bizPbancTmpl0ItemRepository.findOne(Example.of(BizPbancTmpl0Item.builder()
                        .bizPbancTmpl0ItemNo(requestObject.getBizPbancTmpl0ItemNo())
                        .build()))
                .map(bizPbancTmpl0Item -> {
                    copyNonNullObject(requestObject, bizPbancTmpl0Item);

                    BizPbancTmpl0ItemApiResponseVO result = BizPbancTmpl0ItemApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl0ItemRepository.saveAndFlush(bizPbancTmpl0Item), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }


    /**
     사업 공고 템플릿 0 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl0Item(BizPbancTmpl0ItemApiRequestVO requestObject) {
        bizPbancTmpl0ItemRepository.delete(bizPbancTmpl0ItemRepository.findOne(Example.of(BizPbancTmpl0Item.builder()
                        .bizPbancTmpl0ItemNo(requestObject.getBizPbancTmpl0ItemNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }



}