package kr.or.kpf.lms.biz.business.pbanc.master.template.service;

import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.*;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response.*;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizPbancTmplService extends CSServiceSupport {

    private static final String PREFIX_PBANC_TMP0 = "P0T";
    private static final String PREFIX_PBANC_TMP0I = "P0TI";
    private static final String PREFIX_PBANC_TMP1 = "P1T";
    private static final String PREFIX_PBANC_TMP2 = "P2T";
    private static final String PREFIX_PBANC_TMP3 = "P3T";
    private static final String PREFIX_PBANC_TMP4 = "P4T";

    private final CommonBusinessRepository commonBusinessRepository;
    private final BizPbancTmpl0Repository bizPbancTmpl0Repository;
    private final BizPbancTmpl1Repository bizPbancTmpl1Repository;
    private final BizPbancTmpl2Repository bizPbancTmpl2Repository;
    private final BizPbancTmpl3Repository bizPbancTmpl3Repository;
    private final BizPbancTmpl4Repository bizPbancTmpl4Repository;

    private final BizPbancTmpl0TrgtRepository bizPbancTmpl0TrgtRepository;
    private final BizPbancTmpl0ItemRepository bizPbancTmpl0ItemRepository;
    private final BizPbancTmpl1TrgtRepository bizPbancTmpl1TrgtRepository;

    /**
     사업 공고 템플릿 0 정보 생성
     */
    public BizPbancTmpl0ApiResponseVO createBizPbancTmpl0(BizPbancTmpl0ApiRequestVO requestObject) {
        BizPbancTmpl0 entity = BizPbancTmpl0.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancTmpl0No(commonBusinessRepository.generateCode(PREFIX_PBANC_TMP0));
        BizPbancTmpl0ApiResponseVO result = BizPbancTmpl0ApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl0Repository.saveAndFlush(entity), result);

        List<BizPbancTmpl0Item> bizPbancTmpl0Items = new ArrayList<>();
        if (result != null && requestObject.getBizPbancTmpl0Items() != null && requestObject.getBizPbancTmpl0Items().size() > 0){
            for (BizPbancTmpl0ItemApiRequestVO item : requestObject.getBizPbancTmpl0Items()){
                item.setBizPbancTmpl0No(result.getBizPbancTmpl0No());

                BizPbancTmpl0Item entityItem = BizPbancTmpl0Item.builder().build();
                BeanUtils.copyProperties(item, entityItem);
                entityItem.setBizPbancTmpl0ItemNo(commonBusinessRepository.generateCode(PREFIX_PBANC_TMP0I));
                bizPbancTmpl0ItemRepository.saveAndFlush(entityItem);
                bizPbancTmpl0Items.add(entityItem);

            }
            result.setBizPbancTmpl0Items(bizPbancTmpl0Items);
        }

        if (result != null && requestObject.getBizPbancTmpl0Trgts() != null) {
            List<BizPbancTmpl0Trgt> bizPbancTmpl0Trgts = new ArrayList<>();
            for (Integer target : requestObject.getBizPbancTmpl0Trgts()){
                bizPbancTmpl0Trgts.add(BizPbancTmpl0Trgt.builder()
                                .bizPbancTmpl0No(result.getBizPbancTmpl0No())
                                .bizPbancTmpl0TrgtCd(target)
                        .build());
            }
            List<BizPbancTmpl0Trgt> savedBizPbancTmpl0Trgts = bizPbancTmpl0TrgtRepository.saveAllAndFlush(bizPbancTmpl0Trgts);
            result.setBizPbancTmpl0Trgts(savedBizPbancTmpl0Trgts);
        }

        return result;
    }

    /**
     사업 공고 템플릿 1 정보 생성
     */
    public BizPbancTmpl1ApiResponseVO createBizPbancTmpl1(BizPbancTmpl1ApiRequestVO requestObject) {
        BizPbancTmpl1 entity = BizPbancTmpl1.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancTmpl1No(commonBusinessRepository.generateCode(PREFIX_PBANC_TMP1));
        BizPbancTmpl1ApiResponseVO result = BizPbancTmpl1ApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl1Repository.saveAndFlush(entity), result);

        if (result != null && requestObject.getBizPbancTmpl1Trgts() != null) {
            List<BizPbancTmpl1Trgt> bizPbancTmpl1Trgts = new ArrayList<>();
            for (Integer target : requestObject.getBizPbancTmpl1Trgts()){
                bizPbancTmpl1Trgts.add(BizPbancTmpl1Trgt.builder()
                        .bizPbancTmpl1No(result.getBizPbancTmpl1No())
                        .bizPbancTmpl1TrgtCd(target)
                        .build());
            }
            List<BizPbancTmpl1Trgt> savedBizPbancTmpl1Trgts = bizPbancTmpl1TrgtRepository.saveAllAndFlush(bizPbancTmpl1Trgts);
            result.setBizPbancTmpl1Trgts(savedBizPbancTmpl1Trgts);
        }

        return result;
    }

    /**
     사업 공고 템플릿 2 정보 생성
     */
    public BizPbancTmpl2ApiResponseVO createBizPbancTmpl2(BizPbancTmpl2ApiRequestVO requestObject) {
        BizPbancTmpl2 entity = BizPbancTmpl2.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancTmpl2No(commonBusinessRepository.generateCode(PREFIX_PBANC_TMP2));
        BizPbancTmpl2ApiResponseVO result = BizPbancTmpl2ApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl2Repository.saveAndFlush(entity), result);

        return result;
    }

    /**
     사업 공고 템플릿 3 정보 생성
     */
    public BizPbancTmpl3ApiResponseVO createBizPbancTmpl3(BizPbancTmpl3ApiRequestVO requestObject) {
        BizPbancTmpl3 entity = BizPbancTmpl3.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancTmpl3No(commonBusinessRepository.generateCode(PREFIX_PBANC_TMP3));
        BizPbancTmpl3ApiResponseVO result = BizPbancTmpl3ApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl3Repository.saveAndFlush(entity), result);

        return result;
    }

    /**
     사업 공고 템플릿 4 정보 생성
     */
    public BizPbancTmpl4ApiResponseVO createBizPbancTmpl4(BizPbancTmpl4ApiRequestVO requestObject) {
        BizPbancTmpl4 entity = BizPbancTmpl4.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancTmpl4No(commonBusinessRepository.generateCode(PREFIX_PBANC_TMP4));
        BizPbancTmpl4ApiResponseVO result = BizPbancTmpl4ApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancTmpl4Repository.saveAndFlush(entity), result);

        return result;
    }

    /**
     사업 공고 템플릿 0 정보 업데이트
     */
    public BizPbancTmpl0ApiResponseVO updateBizPbancTmpl0(BizPbancTmpl0ApiRequestVO requestObject) {
        return bizPbancTmpl0Repository.findOne(Example.of(BizPbancTmpl0.builder()
                        .bizPbancTmpl0No(requestObject.getBizPbancTmpl0No())
                        .build()))
                .map(bizPbancTmpl0 -> {
                    copyNonNullObject(requestObject, bizPbancTmpl0);

                    BizPbancTmpl0ApiResponseVO result = BizPbancTmpl0ApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl0Repository.saveAndFlush(bizPbancTmpl0), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }

    /**
     사업 공고 템플릿 1 정보 업데이트
     */
    public BizPbancTmpl1ApiResponseVO updateBizPbancTmpl1(BizPbancTmpl1ApiRequestVO requestObject) {
        return bizPbancTmpl1Repository.findOne(Example.of(BizPbancTmpl1.builder()
                        .bizPbancTmpl1No(requestObject.getBizPbancTmpl1No())
                        .build()))
                .map(bizPbancTmpl1 -> {
                    copyNonNullObject(requestObject, bizPbancTmpl1);

                    BizPbancTmpl1ApiResponseVO result = BizPbancTmpl1ApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl1Repository.saveAndFlush(bizPbancTmpl1), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }

    /**
     사업 공고 템플릿 2 정보 업데이트
     */
    public BizPbancTmpl2ApiResponseVO updateBizPbancTmpl2(BizPbancTmpl2ApiRequestVO requestObject) {
        return bizPbancTmpl2Repository.findOne(Example.of(BizPbancTmpl2.builder()
                        .bizPbancTmpl2No(requestObject.getBizPbancTmpl2No())
                        .build()))
                .map(bizPbancTmpl2 -> {
                    copyNonNullObject(requestObject, bizPbancTmpl2);

                    BizPbancTmpl2ApiResponseVO result = BizPbancTmpl2ApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl2Repository.saveAndFlush(bizPbancTmpl2), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }

    /**
     사업 공고 템플릿 3 정보 업데이트
     */
    public BizPbancTmpl3ApiResponseVO updateBizPbancTmpl3(BizPbancTmpl3ApiRequestVO requestObject) {
        return bizPbancTmpl3Repository.findOne(Example.of(BizPbancTmpl3.builder()
                        .bizPbancTmpl3No(requestObject.getBizPbancTmpl3No())
                        .build()))
                .map(bizPbancTmpl3 -> {
                    copyNonNullObject(requestObject, bizPbancTmpl3);

                    BizPbancTmpl3ApiResponseVO result = BizPbancTmpl3ApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl3Repository.saveAndFlush(bizPbancTmpl3), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }

    /**
     사업 공고 템플릿 4 정보 업데이트
     */
    public BizPbancTmpl4ApiResponseVO updateBizPbancTmpl4(BizPbancTmpl4ApiRequestVO requestObject) {
        return bizPbancTmpl4Repository.findOne(Example.of(BizPbancTmpl4.builder()
                        .bizPbancTmpl4No(requestObject.getBizPbancTmpl4No())
                        .build()))
                .map(bizPbancTmpl4 -> {
                    copyNonNullObject(requestObject, bizPbancTmpl4);

                    BizPbancTmpl4ApiResponseVO result = BizPbancTmpl4ApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancTmpl4Repository.saveAndFlush(bizPbancTmpl4), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }


    /**
     사업 공고 템플릿 0 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl0(BizPbancTmpl0ApiRequestVO requestObject) {
        bizPbancTmpl0Repository.delete(bizPbancTmpl0Repository.findOne(Example.of(BizPbancTmpl0.builder()
                        .bizPbancTmpl0No(requestObject.getBizPbancTmpl0No())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));

        bizPbancTmpl0ItemRepository.deleteAllByBizPbancTmpl0No(requestObject.getBizPbancTmpl0No());
        bizPbancTmpl0TrgtRepository.deleteAllByBizPbancTmpl0No(requestObject.getBizPbancTmpl0No());

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 템플릿 1 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl1(BizPbancTmpl1ApiRequestVO requestObject) {
        bizPbancTmpl1Repository.delete(bizPbancTmpl1Repository.findOne(Example.of(BizPbancTmpl1.builder()
                        .bizPbancTmpl1No(requestObject.getBizPbancTmpl1No())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));

        bizPbancTmpl1TrgtRepository.deleteAllByBizPbancTmpl1No(requestObject.getBizPbancTmpl1No());

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 템플릿 2 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl2(BizPbancTmpl2ApiRequestVO requestObject) {
        bizPbancTmpl2Repository.delete(bizPbancTmpl2Repository.findOne(Example.of(BizPbancTmpl2.builder()
                        .bizPbancTmpl2No(requestObject.getBizPbancTmpl2No())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 템플릿 3 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl3(BizPbancTmpl3ApiRequestVO requestObject) {
        bizPbancTmpl3Repository.delete(bizPbancTmpl3Repository.findOne(Example.of(BizPbancTmpl3.builder()
                        .bizPbancTmpl3No(requestObject.getBizPbancTmpl3No())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 템플릿 4 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancTmpl4(BizPbancTmpl4ApiRequestVO requestObject) {
        bizPbancTmpl4Repository.delete(bizPbancTmpl4Repository.findOne(Example.of(BizPbancTmpl4.builder()
                        .bizPbancTmpl4No(requestObject.getBizPbancTmpl4No())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

}