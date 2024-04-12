package kr.or.kpf.lms.biz.business.instructor.question.service;

import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.response.BizInstructorQuestionApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstrQstnRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorQuestion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorQuestionService extends CSServiceSupport {
    private static final String PREFIX_INSTR_QUESTION = "BIQ";

    private final CommonBusinessRepository commonBusinessRepository;
    private final CommonMyPageRepository commonMyPageRepository;

    private final BizInstrQstnRepository bizInstrQstnRepository;

    /**
     강사 지원 문의 정보 생성
     */
    public BizInstructorQuestionApiResponseVO createBizInstructorQuestion(BizInstructorQuestionApiRequestVO requestObject) {
        BizInstructorQuestion entity = BizInstructorQuestion.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrQstnNo(commonBusinessRepository.generateCode(PREFIX_INSTR_QUESTION));
        BizInstructorQuestionApiResponseVO result = BizInstructorQuestionApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizInstrQstnRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     강사 지원 문의 정보 업데이트
     */
    public BizInstructorQuestionApiResponseVO updateBizInstructorQuestion(BizInstructorQuestionApiRequestVO requestObject) {
        return bizInstrQstnRepository.findById(requestObject.getBizInstrQstnNo())
                .map(bizInstructorQuestion -> {
                    copyNonNullObject(requestObject, bizInstructorQuestion);

                    BizInstructorQuestionApiResponseVO result = BizInstructorQuestionApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstrQstnRepository.saveAndFlush(bizInstructorQuestion), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 지원 문의 미존재"));
    }

    /**
     강사 지원 문의 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorQuestion(BizInstructorQuestionApiRequestVO requestObject) {
        bizInstrQstnRepository.delete(bizInstrQstnRepository.findById(requestObject.getBizInstrQstnNo())
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강사 지원 문의 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     강사 지원 문의 리스트
     */
    public <T> Page<T> getBizInstructorQuestionList(BizInstructorQuestionViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 지원 문의 상세
     */
    public <T> T getBizInstructorQuestion(BizInstructorQuestionViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

}
