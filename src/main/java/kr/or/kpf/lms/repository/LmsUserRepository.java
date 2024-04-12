package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.LmsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 회원 Repository 
 */
public interface LmsUserRepository extends JpaRepository<LmsUser, Long>, QueryByExampleExecutor<LmsUser>{}