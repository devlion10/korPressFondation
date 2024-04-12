package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ExamApplicationQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 신청 시험 문항 목록 테이블 Repository
 */
public interface ExamApplicationQuestionRepository extends JpaRepository<ExamApplicationQuestion, Long>, QueryByExampleExecutor<ExamApplicationQuestion> {}
