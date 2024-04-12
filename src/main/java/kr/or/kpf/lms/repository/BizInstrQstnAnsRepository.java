package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강사 지원 문의 답변 Repository
 */
public interface BizInstrQstnAnsRepository extends JpaRepository<BizInstructorQuestionAnswer, String>, QueryByExampleExecutor<BizInstructorQuestionAnswer>{ }