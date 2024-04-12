package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorPbanc;
import kr.or.kpf.lms.repository.entity.BizOrganizationAply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강사모집 공고의 대상 사업 공고 Repository
 */
public interface BizOrganizationAplyRepository extends JpaRepository<BizOrganizationAply, String>, QueryByExampleExecutor<BizOrganizationAply>{

    public BizOrganizationAply findByBizOrgAplyNo(String bizOrgNo);

    BizOrganizationAply findByBizPbancNoAndOrgCd(String bizPbanc, String OrgCd);

}