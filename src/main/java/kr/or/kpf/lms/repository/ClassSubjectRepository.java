package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 수업지도안 교과 테이블 Repository
 */
public interface ClassSubjectRepository extends JpaRepository<ClassSubject, String>, QueryByExampleExecutor<ClassSubject> {}
