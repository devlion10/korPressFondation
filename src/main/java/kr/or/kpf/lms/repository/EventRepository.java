package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 이벤트 테이블 Repository
 */
public interface EventRepository extends JpaRepository<EventInfo, Long>, QueryByExampleExecutor<EventInfo> {}
