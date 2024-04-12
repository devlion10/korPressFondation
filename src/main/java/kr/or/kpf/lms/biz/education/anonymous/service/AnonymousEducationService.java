package kr.or.kpf.lms.biz.education.anonymous.service;

import kr.or.kpf.lms.biz.education.anonymous.vo.request.AnonymousEducationViewRequestVO;
import kr.or.kpf.lms.biz.education.anonymous.vo.response.AnonymousEducationViewResponseVO;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonEducationRepository;
import kr.or.kpf.lms.repository.ContentsChapterRepository;
import kr.or.kpf.lms.repository.CurriculumMasterRepository;
import kr.or.kpf.lms.repository.entity.ContentsChapter;
import kr.or.kpf.lms.repository.entity.CurriculumMaster;
import kr.or.kpf.lms.repository.entity.LmsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 비로그인 교육 관련 Service
 */
@Service
@RequiredArgsConstructor
public class AnonymousEducationService extends CSServiceSupport {

    private final CommonEducationRepository commonEducationRepository;

    private final ContentsChapterRepository contentsChapterRepository;
    private final CurriculumMasterRepository curriculumMasterRepository;

    /**
     * 전체 챕터 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(AnonymousEducationViewRequestVO requestObject) {
        requestObject.setContentsCode(contentsChapterRepository.findOne(Example.of(ContentsChapter.builder()
                        .chapterCode(requestObject.getChapterCode())
                .build())).orElseThrow(() -> new RuntimeException("연결된 콘텐츠 정보가 존재하지 않습니다.")).getContentsCode());
        requestObject.setChapterCode(null);
        return (Page<T>) Optional.ofNullable(commonEducationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 비회원 교육 조회수 업데이트
     *
     * @param curriculumCode
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport updateViewCount(String curriculumCode) {
        CurriculumMaster curriculumMaster = curriculumMasterRepository.findOne(Example.of(CurriculumMaster.builder().curriculumCode(curriculumCode).build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR7104, "존재하지 않는 자료실"));

        /** 조회수 + 1 */
        curriculumMaster.setViewCount(curriculumMaster.getViewCount().add(BigInteger.ONE));
        curriculumMasterRepository.saveAndFlush(curriculumMaster);

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
