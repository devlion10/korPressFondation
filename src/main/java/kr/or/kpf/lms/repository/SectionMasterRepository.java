package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.SectionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 절 마스터 테이블 Repository
 */
public interface SectionMasterRepository extends JpaRepository<SectionMaster, Long>, QueryByExampleExecutor<SectionMaster> {}
