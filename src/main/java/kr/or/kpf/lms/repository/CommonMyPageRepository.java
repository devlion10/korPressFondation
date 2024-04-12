package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.framework.model.CSPageImpl;

import java.time.LocalDate;
import java.util.Date;

/**
 * 나의 페이지 공통 Repository
 */
public interface CommonMyPageRepository {

    /**
     * 공통 리스트 조회
     *
     * @param requestObject
     * @return
     */
    <T extends CSViewVOSupport> CSPageImpl<?> findEntityList(T requestObject);

    /**
     * 수업지도안 코드 생성
     */
    String generateClassGuideCode(String prefixCode);
    
    /**
     * 수업지도안 교과 코드 생성
     */
    String generateClassSubjectCode(String prefixCode);
}
