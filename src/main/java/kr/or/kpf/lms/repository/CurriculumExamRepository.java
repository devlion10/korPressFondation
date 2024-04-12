package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 연계 시험 목록 테이블 Repository
 */
public interface CurriculumExamRepository extends JpaRepository<CurriculumExam, Long>, QueryByExampleExecutor<CurriculumExam> {}
