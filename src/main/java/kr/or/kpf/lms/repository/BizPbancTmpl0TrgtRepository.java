package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Trgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.transaction.Transactional;

/**
 * 사업 공고 템플릿 0 Trgt Repository
 */
public interface BizPbancTmpl0TrgtRepository extends JpaRepository<BizPbancTmpl0Trgt, String>, QueryByExampleExecutor<BizPbancTmpl0Trgt>{
    @Transactional
    void deleteAllByBizPbancTmpl0No(String bizPbancTmpl0No);

}