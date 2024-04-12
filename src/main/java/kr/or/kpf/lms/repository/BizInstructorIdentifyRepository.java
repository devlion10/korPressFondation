package kr.or.kpf.lms.repository;

import kr.or.kpf.lms.repository.entity.BizInstructorIdentify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * 강의확인서 Repository
 */
public interface BizInstructorIdentifyRepository extends JpaRepository<BizInstructorIdentify, String>, QueryByExampleExecutor<BizInstructorIdentify>{

    @Override
    Optional<BizInstructorIdentify> findById(String bizInstIdentifyNo);

    @Override
    boolean existsById(String s);

    public static final String insertData =
            "INSERT INTO BIZ_INSTR_IDNTY (BIZ_INSTR_IDNTY_NO, BIZ_ORG_APLY_NO, BIZ_INSTR_IDNTY_YM, BIZ_INSTR_IDNTY_STTS, BIZ_INSTR_IDNTY_TIME, BIZ_INSTR_IDNTY_AMT)" +
                    "VALUES (:bizInstrIdntyNo, :bizOrgAplyNo, :bizInstrIdntyYm, :bizInstrIdntyStts, :bizInstrIdntyTime, :bizInstrIdntyAmt)";
    @Transactional
    @Modifying
    @Query(value = insertData, nativeQuery = true)
    public Optional<BizInstructorIdentify> insertData(@Param("bizInstrIdntyNo")String bizInstrIdntyNo, @Param("bizOrgAplyNo")String bizOrgAplyNo,
                                                      @Param("bizInstrIdntyYm")String bizInstrIdntyYm, @Param("bizInstrIdntyStts")Integer bizInstrIdntyStts,
                                                      @Param("bizInstrIdntyTime")String bizInstrIdntyTime, @Param("bizInstrIdntyAmt")Integer bizInstrIdntyAmt);
}