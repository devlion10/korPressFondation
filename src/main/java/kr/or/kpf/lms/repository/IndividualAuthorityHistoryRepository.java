package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.IndividualAuthorityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 참여 권한 신청 이력 (개인) 테이블 Repository
 */
public interface IndividualAuthorityHistoryRepository extends JpaRepository<IndividualAuthorityHistory, Long>, QueryByExampleExecutor<IndividualAuthorityHistory> {}
