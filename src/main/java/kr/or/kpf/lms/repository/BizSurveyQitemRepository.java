package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizSurveyQitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 상호평가 Repository
 */
public interface BizSurveyQitemRepository extends JpaRepository<BizSurveyQitem, String>, QueryByExampleExecutor<BizSurveyQitem>{}