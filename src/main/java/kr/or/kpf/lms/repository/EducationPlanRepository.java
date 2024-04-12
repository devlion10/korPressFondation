package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EducationPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/**
 * 교육 계획 목록 Repository
 */
public interface EducationPlanRepository extends JpaRepository<EducationPlan, Long>, QueryByExampleExecutor<EducationPlan> {
    /** 연계 교육 과정 중 진행 중인 교육 일정 */
    List<EducationPlan> findByApplyBeginDateTimeLessThanEqualAndApplyEndDateTimeGreaterThanEqualAndCurriculumCodeAndIsUsable(String applyBeginDateTime, String applyEndDateTime, String curriculumCode, boolean isUsable);
}