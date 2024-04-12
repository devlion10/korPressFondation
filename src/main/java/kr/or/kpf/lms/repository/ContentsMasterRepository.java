package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ContentsMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 콘텐츠 마스터 Repository
 */
public interface ContentsMasterRepository extends JpaRepository<ContentsMaster, Long>, QueryByExampleExecutor<ContentsMaster>{}