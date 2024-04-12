package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EvaluateQuestionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강의 평가 질문 문할 테이블 Repository
 */
public interface EvaluateQuestionItemRepository extends JpaRepository<EvaluateQuestionItem, Long>, QueryByExampleExecutor<EvaluateQuestionItem> {}
