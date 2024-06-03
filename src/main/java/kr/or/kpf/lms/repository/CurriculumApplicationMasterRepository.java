package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.CurriculumApplicationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 교육 과정 신청 이력 테이블 Repository
 */
public interface CurriculumApplicationMasterRepository extends JpaRepository<CurriculumApplicationMaster, Long>, QueryByExampleExecutor<CurriculumApplicationMaster> {

    List<CurriculumApplicationMaster> findByEducationPlanCodeAndAdminApprovalStateIn(String educationPlanCode, List<Object> objects);

    List<CurriculumApplicationMaster> findByEducationPlanCodeAndAdminApprovalStateInAndSetEducationType(String educationPlanCode, List<Object> objects, String setEducationType);
}
