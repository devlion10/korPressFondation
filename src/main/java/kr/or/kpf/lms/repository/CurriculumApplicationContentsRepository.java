package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumApplicationContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육 과정 신청 콘텐츠 목록 테이블 Repository
 */
public interface CurriculumApplicationContentsRepository extends JpaRepository<CurriculumApplicationContents, Long>, QueryByExampleExecutor<CurriculumApplicationContents> {}
