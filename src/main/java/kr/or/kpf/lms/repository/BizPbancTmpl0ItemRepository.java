package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizPbancTmpl0Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import javax.transaction.Transactional;

/**
 * 사업 공고 템플릿 0 Item Repository
 */
public interface BizPbancTmpl0ItemRepository extends JpaRepository<BizPbancTmpl0Item, String>, QueryByExampleExecutor<BizPbancTmpl0Item>{
    @Transactional
    void deleteAllByBizPbancTmpl0No(String bizPbancTmpl0No);
}