package kr.or.kpf.lms.biz.common.code.service;

import kr.or.kpf.lms.biz.common.code.vo.request.CommonCodeViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.ResponseSummary;
import kr.or.kpf.lms.repository.CommonCodeMasterRepository;
import kr.or.kpf.lms.repository.CommonRepository;
import kr.or.kpf.lms.repository.entity.CommonCodeMaster;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 공통 Service
 */
@Service
@RequiredArgsConstructor
public class CommonCodeService extends CSServiceSupport {

    private final CommonRepository commonRepository;
    private final CommonCodeMasterRepository commonCodeMasterRepository;

    /**
     *
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(CommonCodeViewRequestVO requestObject) {
        AtomicInteger count = new AtomicInteger();
        return (Page<T>) Optional.ofNullable(Optional.ofNullable(commonRepository.findTopCommonCode(requestObject))
                        .map(results -> {
                            count.set(results.size());
                            return results;
                        }).orElse(new ArrayList<>()))
                .filter(results -> !ListUtils.isEmpty(results))
                .map(results -> {
                    results.stream().forEach(sub -> sub.setSubCommonCodeMaster(commonCodeMasterRepository.findAll(Example.of(CommonCodeMaster.builder()
                            .upIndividualCode(sub.getIndividualCode())
                            .codeDepth(1)
                            .build()))));
                    ResponseSummary summary = ResponseSummary.builder()
                                                .count(results.size())
                                                .offset(requestObject.getPageNum())
                                                .limit(requestObject.getPageSize())
                                                .build();
                    Pageable pageableToApply = summary.ensureValidPageable(requestObject.getPageable());
                    return CSPageImpl.of(results, pageableToApply, count.get());
                }).orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

}
