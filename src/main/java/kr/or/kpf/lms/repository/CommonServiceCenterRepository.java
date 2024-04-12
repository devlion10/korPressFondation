package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;

/**
 * 고객센터 공통 Repository
 */
public interface CommonServiceCenterRepository {

    /**
     * 공통 리스트 조회
     *  
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);
}
