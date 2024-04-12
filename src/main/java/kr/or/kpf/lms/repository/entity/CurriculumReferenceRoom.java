package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 자료실 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_REFER_MASTER")
@Access(value = AccessType.FIELD)
public class CurriculumReferenceRoom extends CSEntitySupport implements Serializable {
    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 과정 코드 */
    @Column(name="CRCL_CD", nullable = false)
    private String curriculumCode;

    /** 자료실 제목 */
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 자료실 내용 */
    @Column(name="CONTENTS", nullable=false)
    private String contents;

    /** 파일 경로 */
    @Column(name="FILE_PATH")
    private String filePath;

    /** 파일 사이즈 */
    @Column(name="FILE_SIZE")
    private Long fileSize;

    /** 파일 본명 */
    @Column(name="FILE_ORGN_NM")
    private String fileOriginName;

    @NotFound(action= NotFoundAction.IGNORE)
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RGTR_LGN_ID", referencedColumnName = "LGN_ID", insertable=false, updatable=false)
    private AdminUser adminUser;
}
