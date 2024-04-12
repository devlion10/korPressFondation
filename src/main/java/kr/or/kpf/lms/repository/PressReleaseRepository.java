package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.PressRelease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 행사소개(보도자료) 관련 Repository
 */
public interface PressReleaseRepository extends JpaRepository<PressRelease, Long>, QueryByExampleExecutor<PressRelease> {}
