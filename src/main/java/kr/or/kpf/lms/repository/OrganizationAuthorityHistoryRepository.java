package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.OrganizationAuthorityHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 참여 권한 신청 이력 (기관 및 학교) 테이블 Repository
 */
public interface OrganizationAuthorityHistoryRepository extends JpaRepository<OrganizationAuthorityHistory, Long>, QueryByExampleExecutor<OrganizationAuthorityHistory>{}