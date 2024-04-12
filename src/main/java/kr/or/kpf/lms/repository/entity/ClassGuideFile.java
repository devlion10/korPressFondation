package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 수업 지도안 파일 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CLASS_GUIDE_FILE")
@Access(value = AccessType.FIELD)
public class ClassGuideFile extends CSEntitySupport implements Serializable {

    /** 일련번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO")
    private BigInteger sequenceNo;

    /** 수업 지도안 코드 */
    @Column(name="GUI_CD", nullable=false)
    private String classGuideCode;

    /** 수업 지도안 파일 타입 (TEACH: 수업지도안/길라잡이, ACTIVITY: 활동지, ANSWER: 예시답안, NIE: 10분 NIE) */
    @Column(name="FILE_TYPE", nullable=false)
    private String fileType;

    /** 조회수 */
    @Column(name="VIEW_CNT")
    private BigInteger viewCount;

    /** 파일 경로 */
    @Column(name="FILE_PATH")
    private String filePath;

    /** 파일 크기 */
    @Column(name="FILE_SIZE")
    private Long fileSize;

    /** 저장 파일 명 */
    @Column(name="FILE_STRG_NM")
    private String fileName;

    /** 원본 파일 명 */
    @Column(name="FILE_ORGN_NM")
    private String originalFileName;

    /** 파일 확장자 명 */
    @Column(name="FILE_EXTN_NM")
    private String fileExtension;
}
