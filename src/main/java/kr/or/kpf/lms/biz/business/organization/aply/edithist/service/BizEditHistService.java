package kr.or.kpf.lms.biz.business.organization.aply.edithist.service;

import kr.or.kpf.lms.biz.business.organization.aply.edithist.vo.request.BizEditHistViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizEditHistRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizEditHist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizEditHistService extends CSServiceSupport {

    private static final String PREFIX_PBANC_EDIT_HIST = "BEH";

    private final BizEditHistRepository bizEditHistRepository;
    private final CommonBusinessRepository commonBusinessRepository;

    /**
     사업 관련 변경 이력 관리 생성
     */
    public void addBizEditHist(String bizEditHistTrgtNo, int bizEditHistType, String bizEditHistBfr, String bizEditHistAftr, int bizEditHistStts) {
        BizEditHist entity = BizEditHist.builder()
                .bizEditHistNo(commonBusinessRepository.generateCode(PREFIX_PBANC_EDIT_HIST))
                .bizEditHistTrgtNo(bizEditHistTrgtNo)
                .bizEditHistType(bizEditHistType)
                .bizEditHistBfr(bizEditHistBfr)
                .bizEditHistAftr(bizEditHistAftr)
                .bizEditHistStts(bizEditHistStts)
                .build();
        bizEditHistRepository.saveAndFlush(entity);

    }

    public void editBizEditHistStts(String bizEditHistTrgtNo, int status){
        BizEditHistViewRequestVO requestVO = BizEditHistViewRequestVO.builder()
                .bizEditHistTrgtNo(bizEditHistTrgtNo)
                .bizEditHistStts(status)
                .build();

        commonBusinessRepository.updateSttsEntityList(requestVO);

    }

    /**
     사업 관련 변경 이력 관리 리스트
     */
    public <T> Page<T> getBizEditHistList(BizEditHistViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

}