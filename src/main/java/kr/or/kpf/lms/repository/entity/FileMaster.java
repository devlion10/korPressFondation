package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 파일 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "FILE_MASTER")
@Access(value = AccessType.FIELD)
public class FileMaster extends CSEntitySupport implements Serializable {

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

    /** 파일 내용 */
    @Column(name="FILE_CN")
    private String fileComments;

    /** 파일 크기 */
    @Column(name="ATCH_FILE_SZ")
    private BigInteger fileSize;

    /** 파일 전송 키 값 */
    @Column(name="FILE_TRSM_KEY_VALUE")
    private String fileKeyValue;
}
