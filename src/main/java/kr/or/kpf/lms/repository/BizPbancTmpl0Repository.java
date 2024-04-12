package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl0;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 공고 템플릿 0 Repository
 */
public interface BizPbancTmpl0Repository extends JpaRepository<BizPbancTmpl0, String>, QueryByExampleExecutor<BizPbancTmpl0>{}