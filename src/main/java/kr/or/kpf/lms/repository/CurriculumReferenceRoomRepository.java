package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumReferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 자료실 Repository
 */
public interface CurriculumReferenceRoomRepository extends JpaRepository<CurriculumReferenceRoom, Long>, QueryByExampleExecutor<CurriculumReferenceRoom> {}
