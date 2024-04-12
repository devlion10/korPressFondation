package kr.or.kpf.lms.biz.communication.suggestion.service;

import kr.or.kpf.lms.biz.communication.suggestion.vo.request.SuggestionApiRequestVO;
import kr.or.kpf.lms.biz.communication.suggestion.vo.request.SuggestionViewRequestVO;
import kr.or.kpf.lms.biz.communication.suggestion.vo.response.SuggestionApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonCommunicationRepository;
import kr.or.kpf.lms.repository.EducationSuggestionRepository;
import kr.or.kpf.lms.repository.entity.EducationSuggestion;
import kr.or.kpf.lms.repository.entity.MyQna;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 교육 주제 제안 관련 Service
 */
@Service
@RequiredArgsConstructor
public class SuggestionService extends CSServiceSupport {

    /** 참여 / 소통 공통 Repository */
    private final CommonCommunicationRepository commonCommunicationRepository;
    private final EducationSuggestionRepository educationSuggestionRepository;

    /**
     * 참여 / 소통 > 교육 주제 제안 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getSuggestionList(SuggestionViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonCommunicationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 참여 / 소통 > 교육 주제 제안 등록
     *
     * @param suggestionApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public SuggestionApiResponseVO createSuggestion(SuggestionApiRequestVO suggestionApiRequestVO) {
        EducationSuggestion entity = EducationSuggestion.builder().build();
        copyNonNullObject(suggestionApiRequestVO, entity);

        SuggestionApiResponseVO result = SuggestionApiResponseVO.builder().build();
        BeanUtils.copyProperties(educationSuggestionRepository.saveAndFlush(entity), result);
        return result;
    }

    /**
     * 참여 / 소통 > 교육 주제 제안 업데이트
     *
     * @param suggestionApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public SuggestionApiResponseVO updateSuggestion(SuggestionApiRequestVO suggestionApiRequestVO) {
        return educationSuggestionRepository.findOne(Example.of(EducationSuggestion.builder().sequenceNo(suggestionApiRequestVO.getSequenceNo()).build()))
                .map(suggestion -> {
                    if(suggestion.getCommentUser() != null) {
                        throw new KPFException(KPF_RESULT.ERROR7082, "답변이 달린 교육 주제 제안");
                    }
                    copyNonNullObject(suggestionApiRequestVO, suggestion);

                    SuggestionApiResponseVO result = SuggestionApiResponseVO.builder().build();
                    BeanUtils.copyProperties(educationSuggestionRepository.saveAndFlush(suggestion), result);

                    return result;
                })
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7084, "해당 교육 주제 제안 미존재"));
    }

    /**
     * 교육 주제 제안 삭제
     */
    public CSResponseVOSupport deleteSuggestion(BigInteger sequenceNo) {
        educationSuggestionRepository.delete(Optional.ofNullable(educationSuggestionRepository.findOne(Example.of(EducationSuggestion.builder()
                                                                            .sequenceNo(sequenceNo)
                                                                            .build()))
                                                                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7084, "존재하지 않는 교육 주제 제안")))
                                                .filter(data -> data.getRegistUserId().equals(authenticationInfo().getUserId()))
                                                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7083, "삭제 요청자와 데이터 작성자가 상이")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
