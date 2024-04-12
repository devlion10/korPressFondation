package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.OrganizationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 기관 정보 테이블 Repository
 */
public interface OrganizationInfoRepository extends JpaRepository<OrganizationInfo, Long>, QueryByExampleExecutor<OrganizationInfo>{}