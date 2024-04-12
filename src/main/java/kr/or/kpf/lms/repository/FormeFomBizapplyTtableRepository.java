package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.FormeFomBizapplyTtable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FormeFomBizapplyTtableRepository extends JpaRepository<FormeFomBizapplyTtable, Long>, QueryByExampleExecutor<FormeFomBizapplyTtable> {
}
