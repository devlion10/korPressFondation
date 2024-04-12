package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 공고 템플릿 2 Repository
 */
public interface BizPbancTmpl2Repository extends JpaRepository<BizPbancTmpl2, String>, QueryByExampleExecutor<BizPbancTmpl2>{}