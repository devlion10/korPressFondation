package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EducationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 후기 테이블 Repository
 */
public interface EducationReviewRepository extends JpaRepository<EducationReview, Long>, QueryByExampleExecutor<EducationReview> {}
