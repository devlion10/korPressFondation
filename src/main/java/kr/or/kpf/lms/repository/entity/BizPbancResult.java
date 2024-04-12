package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 사업 공고 결과공고 테이블 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_PBANC_RSLT")
@Access(value = AccessType.FIELD)
public class BizPbancResult extends CSEntitySupport implements Serializable {
    /** 사업 공고 결과공고 일련번호 */
    @Id
    @Column(name = "BIZ_PBANC_RSLT_NO", nullable = false)
    private String bizPbancRsltNo;

    /** 사업 공고 일련번호 */
    @Column(name = "BIZ_PBANC_NO", nullable = false)
    private String bizPbancNo;

    /** 선정 결과 내용 */
    @Column(name = "BIZ_PBANC_RSLT_CN", nullable = false)
    private String bizPbancRsltCn;

    /** 공지 여부 */
    @Column(name = "BIZ_PBANC_NTC_YN", nullable = false)
    private Integer bizPbancNtcYn;

    /** 결과공고 첨부파일 */
    @Column(name = "BIZ_PBANC_RSLT_FILE", nullable = true)
    private String bizPbancRsltFile;

    /** 결과공고 첨부파일 사이즈 */
    @Column(name = "BIZ_PBANC_RSLT_FILE_SIZE", nullable = true)
    private Long bizPbancRsltFileSize;

    /** 결과공고 첨부파일 원본명 */
    @Column(name = "BIZ_PBANC_RSLT_FILE_ORGN", nullable = true)
    private String bizPbancRsltFileOrigin;
}