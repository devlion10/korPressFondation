package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 공고 Repository
 */
public interface BizPbancResultRepository extends JpaRepository<BizPbancResult, String>, QueryByExampleExecutor<BizPbancResult>{}