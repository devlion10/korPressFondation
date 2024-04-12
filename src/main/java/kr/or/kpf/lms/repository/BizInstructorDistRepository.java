package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorClclnDdln;
import kr.or.kpf.lms.repository.entity.BizInstructorDist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강사모집 Repository
 */
public interface BizInstructorDistRepository extends JpaRepository<BizInstructorDist, String>, QueryByExampleExecutor<BizInstructorDist>{}