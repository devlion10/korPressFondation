package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 문서(개인정보 , 이용 약관 및 기타) Repository
 */
public interface DocumentsRepository extends JpaRepository<Documents, Long>, QueryByExampleExecutor<Documents> {}
