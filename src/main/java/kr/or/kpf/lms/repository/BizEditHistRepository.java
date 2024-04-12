package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizEditHist;
import kr.or.kpf.lms.repository.entity.BizInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 사업 관련 변경 이력 관리 Repository
 */
public interface BizEditHistRepository extends JpaRepository<BizEditHist, String>, QueryByExampleExecutor<BizEditHist>{}