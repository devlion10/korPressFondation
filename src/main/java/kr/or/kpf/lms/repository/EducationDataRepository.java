package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.LmsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 자료실 테이블 Repository
 */
public interface EducationDataRepository extends JpaRepository<LmsData, Long>, QueryByExampleExecutor<LmsData> {}
