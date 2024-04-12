package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.MyQna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 1:1 문의 Repository
 */
public interface MyQnaRepository extends JpaRepository<MyQna, Long>, QueryByExampleExecutor<MyQna> {}
