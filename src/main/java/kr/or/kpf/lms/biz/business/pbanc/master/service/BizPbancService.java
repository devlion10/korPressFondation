package kr.or.kpf.lms.biz.business.pbanc.master.service;

import kr.or.kpf.lms.biz.business.pbanc.master.template.service.BizPbancTmplService;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancCustomViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizPbancMasterRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.entity.BizPbancMaster;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizPbancService extends CSServiceSupport {

    private static final String PREFIX_PBANC = "PAC";

    private final CommonBusinessRepository commonBusinessRepository;
    private final BizPbancMasterRepository bizPbancMasterRepository;
    private final BizPbancTmplService bizPbancTmplService;
    private final LmsUserRepository lmsUserRepository;

    /**
     사업 공고 정보 생성
     */
    public BizPbancApiResponseVO createBizPbanc(BizPbancApiRequestVO requestObject) {
        BizPbancMaster entity = BizPbancMaster.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancNo(commonBusinessRepository.generateCode(PREFIX_PBANC));
        entity.setBizPbancRnd(commonBusinessRepository.generatePbancAutoIncrease(entity.getBizPbancType(), entity.getBizPbancYr()));
        BizPbancApiResponseVO result = BizPbancApiResponseVO.builder().build();

        // 미디어교육 구분일 때, 템플릿에 따른 ctgr sub 분기 처리 (1: 사회미디어, 2: 학교미디어)
        if (result.getBizPbancCtgr() == 0) {
            if (result.getBizPbancType() == 0){
                entity.setBizPbancCtgrSub(requestObject.getBizPbancCtgrSub());
            } else if (result.getBizPbancType() == 1){
                entity.setBizPbancCtgrSub(1);
            } else {
                entity.setBizPbancCtgrSub(2);
            }
        }

        BeanUtils.copyProperties(bizPbancMasterRepository.saveAndFlush(entity), result);

        if (result != null){
            if (result.getBizPbancType() == 1){
                // 미디어교육 평생교실
                requestObject.getBizPbancTmpl1ApiRequestVO().setBizPbancNo(result.getBizPbancNo());
                bizPbancTmplService.createBizPbancTmpl1(requestObject.getBizPbancTmpl1ApiRequestVO());
            } else if (result.getBizPbancType() == 2){
                // 미디어교육 운영학교
                requestObject.getBizPbancTmpl2ApiRequestVO().setBizPbancNo(result.getBizPbancNo());
                bizPbancTmplService.createBizPbancTmpl2(requestObject.getBizPbancTmpl2ApiRequestVO());
            } else if (result.getBizPbancType() == 3){
                // 자유학기제 미디어 교육지원
                requestObject.getBizPbancTmpl3ApiRequestVO().setBizPbancNo(result.getBizPbancNo());
                bizPbancTmplService.createBizPbancTmpl3(requestObject.getBizPbancTmpl3ApiRequestVO());
            } else if (result.getBizPbancType() == 4){
                //팩트체크 교실 공고
                requestObject.getBizPbancTmpl4ApiRequestVO().setBizPbancNo(result.getBizPbancNo());
                bizPbancTmplService.createBizPbancTmpl4(requestObject.getBizPbancTmpl4ApiRequestVO());
            } else if (result.getBizPbancType() == 0){
                // 기본형
                requestObject.getBizPbancTmpl0ApiRequestVO().setBizPbancNo(result.getBizPbancNo());
                bizPbancTmplService.createBizPbancTmpl0(requestObject.getBizPbancTmpl0ApiRequestVO());
            }
        }

        return result;
    }

    /**
     사업 공고 정보 업데이트
     */
    public BizPbancApiResponseVO updateBizPbanc(BizPbancApiRequestVO requestObject) {
        return bizPbancMasterRepository.findOne(Example.of(BizPbancMaster.builder()
                        .bizPbancNo(requestObject.getBizPbancNo())
                        .build()))
                .map(bizPbancMaster -> {
                    copyNonNullObject(requestObject, bizPbancMaster);

                    BizPbancApiResponseVO result = BizPbancApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancMasterRepository.saveAndFlush(bizPbancMaster), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 사업 공고 미존재"));
    }

    /**
     사업 공고 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbanc(BizPbancApiRequestVO requestObject) {
        bizPbancMasterRepository.delete(bizPbancMasterRepository.findOne(Example.of(BizPbancMaster.builder()
                        .bizPbancNo(requestObject.getBizPbancNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 리스트
     */
    public <T> Page<T> getBizPbancList(BizPbancViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 상세 보기
     */
    public <T> T getBizPbanc(BizPbancViewRequestVO requestObject) {

        if (requestObject != null){
            Optional<LmsUser> lmsUser = lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(authenticationInfo().getUserId()).build()));
            if (lmsUser != null) {
                String orgCd = lmsUser.get().getOrganizationCode();
                if (orgCd != null) {
                    requestObject.setLoginOrgCd(orgCd);
                }else {
                    requestObject.setLoginOrgCd("");
                }
            }
        }

        return (T) commonBusinessRepository.findEntity(requestObject);
    }
}