package kr.or.kpf.lms.biz.business.pbanc.master.template.service;

import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.BizPbancTmpl0TrgtApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response.BizPbancTmpl0TrgtApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.BizPbancTmpl0TrgtRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Trgt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BizPbancTmpl0TrgtService extends CSServiceSupport {
    private final BizPbancTmpl0TrgtRepository bizPbancTmpl0TrgtRepository;

    /**
     사업 공고 템플릿 0 정보 생성
     */
    public BizPbancTmpl0TrgtApiResponseVO createBizPbancTmpl0Trgt(BizPbancTmpl0TrgtApiRequestVO requestObject) {
        BizPbancTmpl0Trgt entity = BizPbancTmpl0Trgt.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizPbancTmpl0TrgtApiResponseVO result = BizPbancTmpl0TrgtApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl0TrgtRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     사업 공고 템플릿 0 정보 업데이트
     */
    public BizPbancTmpl0TrgtApiResponseVO updateBizPbancTmpl0Trgt(BizPbancTmpl0TrgtApiRequestVO requestObject) {
        return bizPbancTmpl0TrgtRepository.findOne(Example.of(BizPbancTmpl0Trgt.builder()
                        .bizPbancTmpl0TrgtNo(requestObject.getBizPbancTmpl0TrgtNo())
                        .build()))
                .map(bizPbancTmpl0Trgt -> {
                    copyNonNullObject(requestObject, bizPbancTmpl0Trgt);

                    BizPbancTmpl0TrgtApiResponseVO result = BizPbancTmpl0TrgtApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl0TrgtRepository.saveAndFlush(bizPbancTmpl0Trgt), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }


    /**
     사업 공고 템플릿 0 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl0Trgt(BizPbancTmpl0TrgtApiRequestVO requestObject) {
        bizPbancTmpl0TrgtRepository.delete(bizPbancTmpl0TrgtRepository.findOne(Example.of(BizPbancTmpl0Trgt.builder()
                        .bizPbancTmpl0TrgtNo(requestObject.getBizPbancTmpl0TrgtNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }



}