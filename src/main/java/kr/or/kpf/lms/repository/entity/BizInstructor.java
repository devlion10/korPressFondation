package kr.or.kpf.lms.repository.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.vo.CreateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.UpdateBizInstructor;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 강사 모집 테이블 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR")
@Access(value = AccessType.FIELD)
public class BizInstructor extends CSEntitySupport implements Serializable {
    /** 강사 모집 일련번호 */
    @Id
    @Column(name = "BIZ_INSTR_NO", nullable = false)
    private String bizInstrNo;

    /** 강사 모집 공고명 */
    @Column(name = "BIZ_INSTR_NM", nullable = false)
    private String bizInstrNm;

    /** 강사 모집 내용  */
    @Column(name = "BIZ_INSTR_CN", nullable = false)
    private String bizInstrCn;

    /** 최대 모집 참여 기관 */
    @Column(name = "BIZ_INSTR_MAX_INST", nullable = false)
    private Integer bizInstrMaxInst;

    /** 첨부파일 */
    @Column(name = "BIZ_INSTR_FILE", nullable = true)
    private String bizInstrFile;

    /** 첨부파일 설명 */
    @Column(name = "BIZ_INSTR_FILE_DSCR", nullable = true)
    private String bizInstrFileDscr;

    /** 첨부파일 사이즈 */
    @Column(name = "BIZ_INSTR_FILE_SIZE", nullable = true)
    private Long bizInstrFileSize;

    /** 첨부파일 원본명 */
    @Column(name = "BIZ_INSTR_FILE_ORGN", nullable = true)
    private String bizInstrFileOrigin;

    /** 강사 모집 상태 */
    @Column(name = "BIZ_INSTR_STTS", nullable = false)
    private Integer bizInstrStts;

    @Transient
    private String bizInstrPeriod;

}