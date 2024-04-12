package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl3;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 공고 템플릿 3 Repository
 */
public interface BizPbancTmpl3Repository extends JpaRepository<BizPbancTmpl3, String>, QueryByExampleExecutor<BizPbancTmpl3>{}