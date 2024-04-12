package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.biz.mypage.instructorstate.vo.BizInstructorsVO;
import kr.or.kpf.lms.repository.entity.BizInstructorAsgnm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

/**
 * 강사모집 강사 배정 Repository
 */
public interface BizInstructorAsgnmRepository extends JpaRepository<BizInstructorAsgnm, String>, QueryByExampleExecutor<BizInstructorAsgnm>{
    @Override
    Optional<BizInstructorAsgnm> findById(String bizInstrAsgnmNo);

    @Query(value = "select A.BIZ_INSTR_ASGNM_NO, A.BIZ_INSTR_APLY_NO, C.BIZ_ORG_APLY_LSN_DTL_YMD, C.BIZ_ORG_APLY_LSN_DTL_RND " +
            "from BIZ_INSTR_ASGNM AS A " +
            "INNER JOIN BIZ_INSTR_APLY AS B ON B.BIZ_INSTR_APLY_NO = A.BIZ_INSTR_APLY_NO " +
            "INNER JOIN BIZ_ORG_APLY_DTL AS C ON C.BIZ_ORG_APLY_DTL_NO = A.BIZ_ORG_APLY_DTL_NO " +
            "INNER JOIN BIZ_ORG_APLY AS D ON D.BIZ_ORG_APLY_NO = C.BIZ_ORG_APLY_NO " +
            "INNER JOIN BIZ_PBANC_MASTER AS E ON E.BIZ_PBANC_NO = D.BIZ_PBANC_NO " +
            "where B.BIZ_INSTR_APLY_INSTR_ID = :instrId " +
            "order by C.BIZ_ORG_APLY_LSN_DTL_YMD DESC, C.BIZ_ORG_APLY_LSN_DTL_RND DESC", nativeQuery = true)
    List<BizInstructorsVO> bizInstructorAsgnms(String instrId);
}