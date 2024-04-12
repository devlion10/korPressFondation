package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizAply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.math.BigInteger;

/**
 * 사업 공고 신청 - 언론인 Repository
 */
public interface BizAplyRepository extends JpaRepository<BizAply, BigInteger>, QueryByExampleExecutor<BizAply> {
}