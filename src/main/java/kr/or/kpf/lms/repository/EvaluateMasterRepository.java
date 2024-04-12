package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EvaluateMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강의 평가 Repository
 */
public interface EvaluateMasterRepository extends JpaRepository<EvaluateMaster, Long>, QueryByExampleExecutor<EvaluateMaster> {}
