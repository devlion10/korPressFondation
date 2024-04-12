package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.FileMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * 파일 업로드 Repository
 */
public interface FileMasterRepository extends JpaRepository<FileMaster, Long>, QueryByExampleExecutor<FileMaster>{

    List<FileMaster> findAllByAtchFileSn(String atchFileSn);
}