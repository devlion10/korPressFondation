package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
import kr.or.kpf.lms.biz.servicecenter.myqna.vo.request.MyQnaViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.entity.EducationPlan;

import java.util.List;

/**
 * 교육 신청 공통 Repository
 */
public interface CommonEducationRepository {
    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);

    /**
     * 교육신청 일련번호 생성
     */
    String generateApplicationNo(String prefixCode);
}