package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강사모집 Repository
 */
public interface BizInstructorRepository extends JpaRepository<BizInstructor, String>, QueryByExampleExecutor<BizInstructor>{}