package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.ClassGuide;
import kr.or.kpf.lms.repository.entity.ClassGuideSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 자료실 - 수업지도안 테이블 Repository
 */
public interface ClassGuideSubjectRepository extends JpaRepository<ClassGuideSubject, Long>, QueryByExampleExecutor<ClassGuideSubject> {}
