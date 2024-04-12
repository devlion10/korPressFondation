package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizAplyDtlFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 자유형 사업 신청서 파일 Repository
 */
public interface BizAplyDtlFileRepository extends JpaRepository<BizAplyDtlFile, Long>, QueryByExampleExecutor<BizAplyDtlFile> { }