package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorClclnDdln;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * 강사모집 Repository
 */
public interface BizInstructorClclnDdlnRepository extends JpaRepository<BizInstructorClclnDdln, String>, QueryByExampleExecutor<BizInstructorClclnDdln>{
    @Query(value = "select * from BIZ_INSTR_CLCLN_DDLN where BIZ_INSTR_CLCLN_DDLN_VALUE >= date_format(now(), '%Y-%m-%d') order by BIZ_INSTR_CLCLN_DDLN_MM ASC Limit 1 ", nativeQuery = true)
    public BizInstructorClclnDdln selectJPQLByNearDate();

    public BizInstructorClclnDdln findOneByBizInstrClclnDdlnYrAndBizInstrClclnDdlnMm(Integer year, Integer month);

}