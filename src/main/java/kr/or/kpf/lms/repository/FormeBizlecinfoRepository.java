package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.FormeBizlecinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface FormeBizlecinfoRepository extends JpaRepository<FormeBizlecinfo, Long>, QueryByExampleExecutor<FormeBizlecinfo> {
}
