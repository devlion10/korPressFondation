package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Trgt;
import kr.or.kpf.lms.repository.entity.BizPbancTmpl1Trgt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.transaction.Transactional;

/**
 * 사업 공고 템플릿 1 Trgt Repository
 */
public interface BizPbancTmpl1TrgtRepository extends JpaRepository<BizPbancTmpl1Trgt, String>, QueryByExampleExecutor<BizPbancTmpl1Trgt>{
    @Transactional
    void deleteAllByBizPbancTmpl1No(String bizPbancTmpl1No);
}