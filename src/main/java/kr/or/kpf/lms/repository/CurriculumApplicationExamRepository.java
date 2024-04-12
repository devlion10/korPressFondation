package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumApplicationExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 신청 시험 목록 테이블 Repository
 */
public interface CurriculumApplicationExamRepository extends JpaRepository<CurriculumApplicationExam, Long>, QueryByExampleExecutor<CurriculumApplicationExam> {}
