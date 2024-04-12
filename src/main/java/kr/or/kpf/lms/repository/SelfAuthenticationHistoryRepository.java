package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.SelfAuthenticationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 본인 인증 내역 Entity Repository
 */
public interface SelfAuthenticationHistoryRepository extends JpaRepository<SelfAuthenticationHistory, Long>, QueryByExampleExecutor<SelfAuthenticationHistory>{}