package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ChapterMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 챕터 마스터 테이블 Repository
 */
public interface ChapterMasterRepository extends JpaRepository<ChapterMaster, Long>, QueryByExampleExecutor<ChapterMaster> {}
