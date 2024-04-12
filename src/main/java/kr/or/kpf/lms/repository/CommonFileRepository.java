package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.entity.FileMaster;

import java.util.List;

/**
 * 파일 업로드 공통 Repository
 */
public interface CommonFileRepository {
    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);

    <T> Object findEntity(T requestObject);

    <T> List<FileMaster> findEntityByAtchFileSn(T requestObject);

    /** 코드값 생성 */
    String generateCode(String prefixCode);

}
