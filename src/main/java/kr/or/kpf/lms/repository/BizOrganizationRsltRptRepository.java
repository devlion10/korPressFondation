package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizOrganizationRsltRpt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 결과보고서 Repository
 */
public interface BizOrganizationRsltRptRepository extends JpaRepository<BizOrganizationRsltRpt, String>, QueryByExampleExecutor<BizOrganizationRsltRpt>{}