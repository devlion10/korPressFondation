package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.LectureLecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 일반 교육 강의 강사 목록 Repository
 */
public interface LectureLecturerRepository extends JpaRepository<LectureLecturer, Long>, QueryByExampleExecutor<LectureLecturer>{}