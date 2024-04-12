package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.EvaluateQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * 강의평가 연결 질문 록록 테이블 Repository
 */
public interface EvaluateQuestionRepository extends JpaRepository<EvaluateQuestion, Long>, QueryByExampleExecutor<EvaluateQuestion> {}
