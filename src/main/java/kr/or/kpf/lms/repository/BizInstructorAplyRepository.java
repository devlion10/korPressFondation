package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorAply;
import kr.or.kpf.lms.repository.entity.BizOrganizationAply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

/**
 * 강사모집 Repository
 */
public interface BizInstructorAplyRepository extends JpaRepository<BizInstructorAply, String>, QueryByExampleExecutor<BizInstructorAply>{

    @Override
    Optional<BizInstructorAply> findById(String bizInstAplyNo);

    BizInstructorAply findByBizInstrNoAndBizOrgAplyNoAndBizInstrAplyInstrId(String bizInstrNo, String bizOrgAplyNo, String bizInstrAplyInstrId);
}