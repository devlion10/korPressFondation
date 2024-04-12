package kr.or.kpf.lms.biz.business.survey.service;

import kr.or.kpf.lms.biz.business.survey.vo.request.BizSurveyApiRequestVO;
import kr.or.kpf.lms.biz.business.survey.vo.request.BizSurveyViewRequestVO;
import kr.or.kpf.lms.biz.business.survey.vo.response.BizSurveyApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizSurveyRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizSurvey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizSurveyService extends CSServiceSupport {

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizSurveyRepository bizSurveyRepository;

    /**
     상호평가 정보 생성
     */
    public BizSurveyApiResponseVO createBizSurvey(BizSurveyApiRequestVO requestObject) {
        BizSurvey entity = BizSurvey.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizSurveyApiResponseVO result = BizSurveyApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizSurveyRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     상호평가 정보 업데이트
     */
    public BizSurveyApiResponseVO updateBizSurvey(BizSurveyApiRequestVO requestObject) {
        return bizSurveyRepository.findOne(Example.of(BizSurvey.builder()
                        .bizSurveyNo(requestObject.getBizSurveyNo())
                        .build()))
                .map(bizSurvey -> {
                    copyNonNullObject(requestObject, bizSurvey);

                    BizSurveyApiResponseVO result = BizSurveyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizSurveyRepository.saveAndFlush(bizSurvey), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3585, "해당 사업 공고 미존재"));
    }

    /**
     상호평가 정보 삭제
     */
    public CSResponseVOSupport deleteBizSurvey(BizSurveyApiRequestVO requestObject) {
        bizSurveyRepository.delete(bizSurveyRepository.findOne(Example.of(BizSurvey.builder()
                        .bizSurveyNo(requestObject.getBizSurveyNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3587, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     상호평가 리스트
     */
    public <T> Page<T> getBizSurveyList(BizSurveyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     상호평가 상세
     */
    public <T> T getBizSurvey(BizSurveyViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }
}
