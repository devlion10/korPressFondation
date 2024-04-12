package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumApplicationEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 신청 평가(후기) 테이블 Repository
 */
public interface CurriculumApplicationEvaluateRepository extends JpaRepository<CurriculumApplicationEvaluate, Long>, QueryByExampleExecutor<CurriculumApplicationEvaluate> {}
