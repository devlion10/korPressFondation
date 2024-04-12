package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 시험 질문 목록 Repository
 */
public interface CurriculumQuestionRepository extends JpaRepository<CurriculumQuestion, Long>, QueryByExampleExecutor<CurriculumQuestion>{}