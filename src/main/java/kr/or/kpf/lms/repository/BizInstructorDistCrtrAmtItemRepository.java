package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorDistCrtrAmtItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강사모집 Repository
 */
public interface BizInstructorDistCrtrAmtItemRepository extends JpaRepository<BizInstructorDistCrtrAmtItem, String>, QueryByExampleExecutor<BizInstructorDistCrtrAmtItem>{}