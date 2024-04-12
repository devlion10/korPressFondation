package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 상호평가 Repository
 */
public interface BizSurveyRepository extends JpaRepository<BizSurvey, String>, QueryByExampleExecutor<BizSurvey>{}