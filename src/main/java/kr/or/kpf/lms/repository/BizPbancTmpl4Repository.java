package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl4;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 공고 템플릿 4 Repository
 */
public interface BizPbancTmpl4Repository extends JpaRepository<BizPbancTmpl4, String>, QueryByExampleExecutor<BizPbancTmpl4>{}