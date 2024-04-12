package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumCollaboration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 연계 교육 과정 목록 테이블 Repository
 */
public interface CurriculumCollaborationRepositroy extends JpaRepository<CurriculumCollaboration, Long>, QueryByExampleExecutor<CurriculumCollaboration> {}
