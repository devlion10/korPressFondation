package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizSurveyQitem;
import kr.or.kpf.lms.repository.entity.BizSurveyQitemItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 상호평가 Repository
 */
public interface BizSurveyQitemItemRepository extends JpaRepository<BizSurveyQitemItem, String>, QueryByExampleExecutor<BizSurveyQitemItem>{}