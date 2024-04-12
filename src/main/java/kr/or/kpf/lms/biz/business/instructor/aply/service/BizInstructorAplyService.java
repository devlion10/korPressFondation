package kr.or.kpf.lms.biz.business.instructor.aply.service;

import io.swagger.models.auth.In;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.response.BizInstructorAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.service.BizInstructorService;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.response.BizOrganizationAplyApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorAplyService extends CSServiceSupport {
    private static final String PREFIX_INSTR_APLY = "BIA";
    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorAplyRepository bizInstructorAplyRepository;
    private final BizInstructorRepository bizInstructorRepository;
    private final BizPbancMasterRepository bizPbancMasterRepository;
    private final BizOrganizationAplyRepository bizOrganizationAplyRepository;

    public boolean vailedBizInstructorAply(BizInstructorAplyApiRequestVO requestVO, String type) {
        requestVO.setLoginUserId(authenticationInfo().getUserId());
        if (type.equals("save")) {
            BizOrganizationAply bizOrganizationAply = bizOrganizationAplyRepository.findById(requestVO.getBizOrgAplyNo())
                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "존재하지 않는 사업 공고 신청 정보입니다."));

            BizPbancMaster bizPbancMaster = bizPbancMasterRepository.findById(bizOrganizationAply.getBizPbancNo())
                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "존재하지 않는 사업 공고 정보입니다."));

            BizInstructor bizInstructor = bizInstructorRepository.findById(requestVO.getBizInstrNo())
                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "존재하지 않는 강사 공고 정보입니다."));

            if (bizPbancMaster.getBizPbancInstrSlctnMeth().equals(1) || bizPbancMaster.getBizPbancInstrSlctnMeth() == 1) {
                Long aplyCnt = commonBusinessRepository.countEntity(requestVO);

                if (aplyCnt + 1 <= bizInstructor.getBizInstrMaxInst()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        } else if (type.equals("submit")) {
            BizInstructor bizInstructor = bizInstructorRepository.findById(requestVO.getBizInstrNo())
                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "존재하지 않는 강사 공고 정보입니다."));
            Long aplyCnt = commonBusinessRepository.countEntity(requestVO);

            if (aplyCnt + 1 <= bizInstructor.getBizInstrMaxInst()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     강사 모집 신청 정보 생성
     */
    public BizInstructorAplyApiResponseVO createBizInstructorAply(BizInstructorAplyApiRequestVO requestObject) {

        BizInstructorAplyApiResponseVO result = BizInstructorAplyApiResponseVO.builder().build();
        BizInstructorAply check = bizInstructorAplyRepository.findByBizInstrNoAndBizOrgAplyNoAndBizInstrAplyInstrId(requestObject.getBizInstrNo(), requestObject.getBizOrgAplyNo(), requestObject.getBizInstrAplyInstrId());
        if(check == null){
            BizInstructorAply entity = BizInstructorAply.builder().build();
            BeanUtils.copyProperties(requestObject, entity);
            entity.setBizOrgAplyNo(requestObject.getBizOrgAplyNo());
            entity.setBizInstrNo(requestObject.getBizInstrNo());
            entity.setBizInstrAplyNo(commonBusinessRepository.generateCode(PREFIX_INSTR_APLY));
            BeanUtils.copyProperties(bizInstructorAplyRepository.saveAndFlush(entity), result);

            return result;
        } else {
            return null;
        }
    }

    /**
     강사 모집 신청 정보 생성 (복수)
     */
    public List<BizInstructorAplyApiResponseVO> createBizInstructorAplies(List<BizInstructorAplyApiRequestVO> requestObjects) {
        List<BizInstructorAplyApiResponseVO> bizInstructorAplyApiResponseVOList = new ArrayList<>();
        for (BizInstructorAplyApiRequestVO requestObject : requestObjects) {
            BizInstructorAply check = bizInstructorAplyRepository.findByBizInstrNoAndBizOrgAplyNoAndBizInstrAplyInstrId(requestObject.getBizInstrNo(), requestObject.getBizOrgAplyNo(), requestObject.getBizInstrAplyInstrId());
            if(check == null){
                if (vailedBizInstructorAply(requestObject, "save")) {
                    BizInstructorAply entity = BizInstructorAply.builder().build();
                    BeanUtils.copyProperties(requestObject, entity);
                    entity.setBizOrgAplyNo(requestObject.getBizOrgAplyNo());
                    entity.setBizInstrNo(requestObject.getBizInstrNo());
                    entity.setBizInstrAplyNo(commonBusinessRepository.generateCode(PREFIX_INSTR_APLY));
                    BizInstructorAplyApiResponseVO result = BizInstructorAplyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstructorAplyRepository.saveAndFlush(entity), result);
                    bizInstructorAplyApiResponseVOList.add(result);
                } else {
                    bizInstructorAplyApiResponseVOList.add(null);
                }
            }
        }
        return bizInstructorAplyApiResponseVOList;
    }

    /**
     강사 모집 신청 정보 업데이트
     */
    public BizInstructorAplyApiResponseVO updateBizInstructorAply(BizInstructorAplyApiRequestVO requestObject) {
        return bizInstructorAplyRepository.findById(requestObject.getBizInstrAplyNo())
                .map(bizInstructorAply -> {
                    copyNonNullObject(requestObject, bizInstructorAply);

                    BizInstructorAplyApiResponseVO result = BizInstructorAplyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstructorAplyRepository.saveAndFlush(bizInstructorAply), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 모집 미존재"));
    }

    /**
     강사 모집 신청 정보 업데이트 (복수)
     */
    public List<BizInstructorAplyApiResponseVO> updateBizInstructorAplies(List<BizInstructorAplyApiRequestVO> requestObjects) {
        List<BizInstructorAplyApiResponseVO> bizInstructorAplyApiResponseVOList = new ArrayList<>();
        for (BizInstructorAplyApiRequestVO requestObject : requestObjects) {
             if (vailedBizInstructorAply(requestObject, "submit")) {
                 bizInstructorAplyRepository.findById(requestObject.getBizInstrAplyNo())
                         .map(bizInstructorAply -> {
                             copyNonNullObject(requestObject, bizInstructorAply);

                             BizInstructorAplyApiResponseVO result = BizInstructorAplyApiResponseVO.builder().build();
                             BeanUtils.copyProperties(bizInstructorAplyRepository.saveAndFlush(bizInstructorAply), result);

                             return bizInstructorAplyApiResponseVOList.add(result);
                         }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 모집 미존재"));
             } else {
                 bizInstructorAplyApiResponseVOList.add(null);
             }
        }
        return bizInstructorAplyApiResponseVOList;
    }

    /**
     강사 모집 신청 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorAply(BizInstructorAplyApiRequestVO requestObject) {
        BizInstructorAply instructorAply = bizInstructorAplyRepository.findById(requestObject.getBizInstrAplyNo()).get();

        if (instructorAply != null) {
            if (instructorAply.getBizInstrAplyStts() == 0 || instructorAply.getBizInstrAplyStts() == 1) {
                bizInstructorAplyRepository.delete(bizInstructorAplyRepository.findById(requestObject.getBizInstrAplyNo())
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3625, "이미 삭제된 강사 신청 정보입니다.")));
                return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
            } else {
                return CSResponseVOSupport.of(KPF_RESULT.ERROR3627);
            }
        } else {
            return CSResponseVOSupport.of(KPF_RESULT.ERROR3625);
        }
    }

    /**
     강사 모집 신청 리스트
     */
    public <T> Page<T> getBizInstructorAplyList(BizInstructorAplyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 모집 신청 상세
     */
    public <T> T getBizInstructorAply(BizInstructorAplyViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

}
