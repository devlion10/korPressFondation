package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumApplicationEvaluateComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 설문 및 평가 의견 테이블 Repository
 */
public interface CurriculumApplicationEvaluateCommentRepository extends JpaRepository<CurriculumApplicationEvaluateComment, Long>, QueryByExampleExecutor<CurriculumApplicationEvaluateComment> {}
