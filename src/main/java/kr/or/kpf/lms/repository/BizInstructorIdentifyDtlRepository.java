package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorIdentifyDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

/**
 * 강의확인서 강의시간표 Repository
 */
public interface BizInstructorIdentifyDtlRepository extends JpaRepository<BizInstructorIdentifyDtl, String>, QueryByExampleExecutor<BizInstructorIdentifyDtl>{

    @Override
    Optional<BizInstructorIdentifyDtl> findById(String bizInstIdentifyDtlNo);
}