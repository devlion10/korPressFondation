package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.InstructorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강사 정보 테이블 Repository
 */
public interface InstructorInfoRepository extends JpaRepository<InstructorInfo, Long>, QueryByExampleExecutor<InstructorInfo>{}