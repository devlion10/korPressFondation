package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 마스터 Repository
 */
public interface CurriculumMasterRepository extends JpaRepository<CurriculumMaster, Long>, QueryByExampleExecutor<CurriculumMaster>{}