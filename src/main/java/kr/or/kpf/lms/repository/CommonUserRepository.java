package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;

import java.util.List;

/**
 * 유저 공통 Repository
 */
public interface CommonUserRepository {
    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);

    /**
     * 전체 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> List<?> allEntityList(T requestObject);

    /** 코드값 생성 */
    String generateCode(String prefixCode);

    /** 단일 객체 조회 */
    <T> Object findEntity(T requestObject);
}