package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;

import java.util.List;

/**
 * 사업 공고 공통 Repository
 */
public interface CommonBusinessRepository {
    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);

    <T> Object findEntity(T requestObject);

    <T> List<?> updateSttsEntityList(T requestObject);

    <T> Long countEntity(T requestObject);

    /** 코드값 생성 */
    String generateCode(String prefixCode);

    Integer generatePbancAutoIncrease(Integer bizPbancType, Integer bizPbancYr);

}
