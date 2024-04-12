package kr.or.kpf.lms.biz.mypage.bizapply.service;

import kr.or.kpf.lms.biz.mypage.bizapply.vo.request.MyBizApplyViewRequestVO;
import kr.or.kpf.lms.biz.mypage.businessstate.vo.request.MyBusinessStateSurveyViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyBizApplyService extends CSServiceSupport {
    private final CommonMyPageRepository commonMyPageRepository;

    public <T> Page<T> getApplyList(MyBizApplyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }
}
