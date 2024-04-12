package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.LmsDataFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 파일 업로드 Repository
 */
public interface LmsDataFileRepository extends JpaRepository<LmsDataFile, Long>, QueryByExampleExecutor<LmsDataFile>{
}