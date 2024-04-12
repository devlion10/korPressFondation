package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EduPlaceAply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 교육장 신청 관련 Repository
 */
public interface EduPlaceAplyRepository extends JpaRepository<EduPlaceAply, Long>, QueryByExampleExecutor<EduPlaceAply> {}
