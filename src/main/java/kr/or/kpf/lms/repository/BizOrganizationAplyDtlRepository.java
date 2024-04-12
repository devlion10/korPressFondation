package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizOrganizationAply;
import kr.or.kpf.lms.repository.entity.BizOrganizationAplyDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

/**
 * 사업 공고 신청 - 수업계획서 Repository
 */
public interface BizOrganizationAplyDtlRepository extends JpaRepository<BizOrganizationAplyDtl, String>, QueryByExampleExecutor<BizOrganizationAplyDtl>{
    Optional<BizOrganizationAplyDtl> findBizOrganizationAplyDtlByBizOrgAplyDtlNo(String bizOrgAplyDtlNo);
}