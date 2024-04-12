package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_APLY_DTL_FILE")
@Access(value = AccessType.FIELD)
public class BizAplyDtlFile extends CSEntitySupport implements Serializable {
    /** 파일 일련번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="FILE_SN")
    private Long fileSn;

    /** 첨부 파일 일련번호 */
    @Column(name="ATCH_FILE_SN")
    private String atchFileSn;

    /** 파일 저장 경로 */
    @Column(name="FILE_STRG_PATH")
    private String filePath;

    /** 저장 파일 명 */
    @Column(name="STRG_FILE_NM")
    private String fileName;

    /** 원본 파일 명 */
    @Column(name="ORGN_FILE_NM")
    private String originalFileName;

    /** 파일 확장자 명 */
    @Column(name="FILE_EXTN_NM")
    private String fileExtension;

    /** 파일 크기 */
    @Column(name="ATCH_FILE_SZ")
    private BigInteger fileSize;

}
