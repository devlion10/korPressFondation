package kr.or.kpf.lms.biz.mypage.businessstate.service;

import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.BizInstructorIdentifyViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.response.BizOrganizationAplyCustomApiResponseVO;
import kr.or.kpf.lms.biz.mypage.businessstate.vo.request.*;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MyBusinessStateService extends CSServiceSupport {

    private final CommonMyPageRepository commonMyPageRepository;

    /**
     사업 공고 - 신청 리스트
     */
    public <T> Page<T> getApplyList(MyBusinessStateApplyViewRequestVO requestObject) {
        requestObject.setBizOrgAplyStts(0);
        List<BizOrganizationAplyCustomApiResponseVO> saveList = (List<BizOrganizationAplyCustomApiResponseVO>) commonMyPageRepository.findEntityList(requestObject).getContent()
                .stream().collect(Collectors.toList());

        requestObject.setBizOrgAplyStts(1);
        List<BizOrganizationAplyCustomApiResponseVO> submitList = (List<BizOrganizationAplyCustomApiResponseVO>) commonMyPageRepository.findEntityList(requestObject).getContent()
                .stream().collect(Collectors.toList());

        requestObject.setBizOrgAplyStts(9);
        List<BizOrganizationAplyCustomApiResponseVO> apprvList = (List<BizOrganizationAplyCustomApiResponseVO>) commonMyPageRepository.findEntityList(requestObject).getContent()
                .stream().collect(Collectors.toList());

        requestObject.setBizOrgAplyStts(3);
        List<BizOrganizationAplyCustomApiResponseVO> rejectList = (List<BizOrganizationAplyCustomApiResponseVO>) commonMyPageRepository.findEntityList(requestObject).getContent()
                .stream().collect(Collectors.toList());

        int totalAllCount = saveList.size() +  submitList.size() + apprvList.size() + rejectList.size();

        List<BizOrganizationAplyCustomApiResponseVO> applyList = List.of(saveList, submitList, apprvList, rejectList)
                .stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(BizOrganizationAplyCustomApiResponseVO::getCreateDateTime).reversed())
                .skip(requestObject.getPageable().getOffset())
                .limit(requestObject.getPageable().getPageSize())
                .collect(Collectors.toList());

        return (Page<T>) Optional.ofNullable(applyList)
                .map(list -> CSPageImpl.of(list, requestObject.getPageable(), totalAllCount))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 - 진행 중 리스트
     */
    public <T> Page<T> getList(MyBusinessStateViewRequestVO requestObject) {
        requestObject.setBizOrgAplyStts(2);
        List<BizOrganizationAplyCustomApiResponseVO> apprvList = (List<BizOrganizationAplyCustomApiResponseVO>) commonMyPageRepository.findEntityList(requestObject).getContent()
                .stream().collect(Collectors.toList());

        requestObject.setBizOrgAplyStts(3);
        List<BizOrganizationAplyCustomApiResponseVO> rejectList = (List<BizOrganizationAplyCustomApiResponseVO>) commonMyPageRepository.findEntityList(requestObject).getContent()
                .stream().collect(Collectors.toList());
        int totalAllCount = apprvList.size() +  rejectList.size();

        List<BizOrganizationAplyCustomApiResponseVO> ingList = List.of(apprvList, rejectList)
                .stream()
                .flatMap(List::stream)
                .sorted(Comparator.comparing(BizOrganizationAplyCustomApiResponseVO::getCreateDateTime).reversed())
                .skip(requestObject.getPageable().getOffset())
                .limit(requestObject.getPageable().getPageSize())
                .collect(Collectors.toList());

        return (Page<T>) Optional.ofNullable(ingList)
                .map(list -> CSPageImpl.of(list, requestObject.getPageable(), totalAllCount))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 - 진행 중 리스트 - 상호평가
     */
    public <T> Page<T> getSurveyList(MyBusinessStateSurveyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 - 완료 리스트
     */
    public <T> Page<T> getCompleteList(MyBusinessStateCompleteViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 - 강의확인서
     */
    public <T> Page<T> getConfirmList(BizInstructorIdentifyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

}