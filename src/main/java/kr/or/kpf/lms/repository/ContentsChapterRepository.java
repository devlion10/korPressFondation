package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ContentsChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 콘텐츠 챕터 목록 테이블 Repository
 */
public interface ContentsChapterRepository extends JpaRepository<ContentsChapter, Long>, QueryByExampleExecutor<ContentsChapter> {}
