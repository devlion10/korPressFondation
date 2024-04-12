package kr.or.kpf.lms.biz.mypage.instructorstate.service;

import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.FormeBizlecinfoApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.question.vo.request.BizInstructorQuestionViewRequestVO;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.BizInstructorsVO;
import kr.or.kpf.lms.biz.mypage.instructorstate.vo.request.*;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstructorAsgnmRepository;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorAsgnm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyInstructorStateService extends CSServiceSupport {

    private final BizInstructorAsgnmRepository bizInstructorAsgnmRepository;
    private final CommonMyPageRepository commonMyPageRepository;

    /**
     강사 참여 신청 - 감사모집 - 지원 중 리스트
     */
    public <T> Page<T> getApplyList(MyInstructorStateApplyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 참여 신청 - 감사모집 - 지원 완료 리스트
     */
    public <T> Page<T> getApplyResultList(MyInstructorStateApplyResultViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 참여 신청 - 강의현황 - 진행 중 리스트
     */
    public <T> Page<T> getList(MyInstructorStateViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 참여 신청 - 강의현황 - 완료 리스트
     */
    public <T> Page<T> getCompleteList(MyInstructorStateCompleteViewRequestVO requestObject) {
        List<BizInstructorsVO> bizInstructorAsgnms = bizInstructorAsgnmRepository.bizInstructorAsgnms(requestObject.getUserId());
        Map<String, List<BizInstructorsVO>> groupBizInstructorAsgnms = bizInstructorAsgnms.stream()
                    .collect(Collectors.groupingBy(BizInstructorsVO::getBIZ_INSTR_APLY_NO));
        List<String> asgnmNos = new ArrayList<>();
        groupBizInstructorAsgnms.forEach((key, value) -> {
            asgnmNos.add(value.get(0).getBIZ_INSTR_ASGNM_NO());
        });
        int arrListSize = asgnmNos.size();
        String bizInstructorAsgnmNos[] = asgnmNos.toArray(new String[arrListSize]);
        requestObject.setBizInstructorAsgnmNos(bizInstructorAsgnmNos);

        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 참여 신청 - 강의현황 - 강의료 정산
     */
    public <T> Page<T> getCalculateList(MyInstructorStateCalculateViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     마이페이지 - 강사 지원 문의 리스트
     */
    public <T> Page<T> getBizInstructorQuestionList(BizInstructorQuestionViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 마이페이지 - 포미 강의 이력
     */
    public <T> Page<T> getBizInstructorIdentifyList(FormeBizlecinfoApiRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }
}