package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizAplyDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.math.BigInteger;

public interface BizAplyDtlRepository extends JpaRepository<BizAplyDtl, BigInteger>, QueryByExampleExecutor<BizAplyDtl> {
}
