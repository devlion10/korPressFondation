package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;

/**
 * 연수원 소개 공통 Repository
 */
public interface CommonEducationCenterRepository {
    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);

    /** 코드값 생성 */
    String generateCode(String prefixCode);
}