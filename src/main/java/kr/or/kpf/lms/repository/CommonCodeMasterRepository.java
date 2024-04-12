package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CommonCodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 공통 코드 Repository
 */
public interface CommonCodeMasterRepository extends JpaRepository<CommonCodeMaster, Long>, QueryByExampleExecutor<CommonCodeMaster>{}