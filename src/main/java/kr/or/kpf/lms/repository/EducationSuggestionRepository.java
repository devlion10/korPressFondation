package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EducationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 주제 제안 Repository
 */
public interface EducationSuggestionRepository extends JpaRepository<EducationSuggestion, Long>, QueryByExampleExecutor<EducationSuggestion> {}
