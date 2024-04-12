package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ChapterApplicationSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 챕터 마스터 테이블 Repository
 */
public interface ChapterApplicationSectionRepository extends JpaRepository<ChapterApplicationSection, Long>, QueryByExampleExecutor<ChapterApplicationSection> {}
