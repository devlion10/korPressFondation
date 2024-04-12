package kr.or.kpf.lms.biz.business.instructor.question.answer.service;

import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.request.BizInstructorQuestionAnswerApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.request.BizInstructorQuestionAnswerViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.answer.vo.response.BizInstructorQuestionAnswerApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstrQstnAnsRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorQuestionAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorQuestionAnswerService extends CSServiceSupport {
    private static final String PREFIX_INSTR_QUESTION_ANS = "BIQA";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstrQstnAnsRepository bizInstrQstnAnsRepository;

    /**
     강사 지원 문의 답변 정보 생성
     */
    public BizInstructorQuestionAnswerApiResponseVO createBizInstructorQuestionAnswer(BizInstructorQuestionAnswerApiRequestVO requestObject) {
        BizInstructorQuestionAnswer entity = BizInstructorQuestionAnswer.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrQstnAnsNo(commonBusinessRepository.generateCode(PREFIX_INSTR_QUESTION_ANS));
        BizInstructorQuestionAnswerApiResponseVO result = BizInstructorQuestionAnswerApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizInstrQstnAnsRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     강사 지원 문의 답변 정보 업데이트
     */
    public BizInstructorQuestionAnswerApiResponseVO updateBizInstructorQuestionAnswer(BizInstructorQuestionAnswerApiRequestVO requestObject) {
        return bizInstrQstnAnsRepository.findById(requestObject.getBizInstrQstnAnsNo())
                .map(bizInstructorQuestionAnswer -> {
                    copyNonNullObject(requestObject, bizInstructorQuestionAnswer);

                    BizInstructorQuestionAnswerApiResponseVO result = BizInstructorQuestionAnswerApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstrQstnAnsRepository.saveAndFlush(bizInstructorQuestionAnswer), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 지원 문의 미존재"));
    }

    /**
     강사 지원 문의 답변 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorQuestionAnswer(BizInstructorQuestionAnswerApiRequestVO requestObject) {
        bizInstrQstnAnsRepository.delete(bizInstrQstnAnsRepository.findById(requestObject.getBizInstrQstnAnsNo())
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강사 지원 문의 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     강사 지원 문의 답변 리스트
     */
    public <T> Page<T> getBizInstructorQuestionAnswerList(BizInstructorQuestionAnswerViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 지원 문의 답변 상세
     */
    public <T> T getBizInstructorQuestionAnswer(BizInstructorQuestionAnswerViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

}
