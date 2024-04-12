package kr.or.kpf.lms.biz.business.survey.ans.service;

import kr.or.kpf.lms.biz.business.survey.ans.vo.request.BizSurveyAnsApiRequestVO;
import kr.or.kpf.lms.biz.business.survey.ans.vo.request.BizSurveyAnsViewRequestVO;
import kr.or.kpf.lms.biz.business.survey.ans.vo.response.BizSurveyAnsApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizSurveyAnsRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizSurveyAns;
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
public class BizSurveyAnsService extends CSServiceSupport {

    private static final String PREFIX_SURVEYANS = "BSA";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizSurveyAnsRepository bizSurveyAnsRepository;

    /**
     상호평가 응답 정보 생성
     */
    public BizSurveyAnsApiResponseVO createBizSurveyAns(BizSurveyAnsApiRequestVO requestObject) {
        BizSurveyAns entity = BizSurveyAns.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizSurveyAnsNo(commonBusinessRepository.generateCode(PREFIX_SURVEYANS));
        BizSurveyAnsApiResponseVO result = BizSurveyAnsApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizSurveyAnsRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     상호평가 응답 정보 복수 생성
     */
    public List<BizSurveyAnsApiResponseVO> createBizSurveyAnsAll(List<BizSurveyAnsApiRequestVO> requestObjects) {

        List<BizSurveyAnsApiResponseVO> resultList = new ArrayList<>();
        for(BizSurveyAnsApiRequestVO requestObject:requestObjects){

            BizSurveyAns entity = BizSurveyAns.builder().build();
            BeanUtils.copyProperties(requestObject, entity);
            entity.setBizSurveyAnsNo(commonBusinessRepository.generateCode(PREFIX_SURVEYANS));
            BizSurveyAnsApiResponseVO result = BizSurveyAnsApiResponseVO.builder().build();
            BeanUtils.copyProperties(bizSurveyAnsRepository.saveAndFlush(entity), result);
            resultList.add(result);
        }

        return resultList;
    }
    /**
     상호평가 응답 정보 업데이트
     */
    public BizSurveyAnsApiResponseVO updateBizSurveyAns(BizSurveyAnsApiRequestVO requestObject) {
        return bizSurveyAnsRepository.findOne(Example.of(BizSurveyAns.builder()
                        .bizSurveyAnsNo(requestObject.getBizSurveyAnsNo())
                        .build()))
                .map(bizSurveyAns -> {
                    copyNonNullObject(requestObject, bizSurveyAns);

                    BizSurveyAnsApiResponseVO result = BizSurveyAnsApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizSurveyAnsRepository.saveAndFlush(bizSurveyAns), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3665, "해당 사업 공고 미존재"));
    }

    /**
     상호평가 응답 정보 삭제
     */
    public CSResponseVOSupport deleteBizSurveyAns(BizSurveyAnsApiRequestVO requestObject) {
        bizSurveyAnsRepository.delete(bizSurveyAnsRepository.findOne(Example.of(BizSurveyAns.builder()
                        .bizSurveyAnsNo(requestObject.getBizSurveyAnsNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3667, "삭제된 사업 공고 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     상호평가 응답 리스트
     */
    public <T> Page<T> getBizSurveyAnsList(BizSurveyAnsViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     상호평가 응답 상세
     */
    public <T> T getBizSurveyAns(BizSurveyAnsViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }
}
