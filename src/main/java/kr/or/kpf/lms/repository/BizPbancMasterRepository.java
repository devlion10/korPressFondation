package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 공고 Repository
 */
public interface BizPbancMasterRepository extends JpaRepository<BizPbancMaster, String>, QueryByExampleExecutor<BizPbancMaster>{}