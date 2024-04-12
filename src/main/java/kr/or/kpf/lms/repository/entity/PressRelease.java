package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 행사소개(보도자료) 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "PRESS_RELEASE")
@Access(value = AccessType.FIELD)
public class PressRelease extends CSEntitySupport implements Serializable {
    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 제목 */
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 내용 */
    @Column(name="CONTENTS", nullable=false)
    private String contents;

    /** 첨부 파일 경로 */
    @Column(name="ATCH_FILE_PATH")
    private String atchFilePath;

    /** 첨부 파일 크기 */
    @Column(name="ATCH_FILE_SIZE")
    private Long atchFileSize;

    /** 첨부 파일 원본명 */
    @Column(name="ATCH_FILE_ORGN")
    private String atchFileOrigin;

    /** 조회수 */
    @Column(name="VIEW_CNT", nullable=false)
    private BigInteger viewCount;

    /** 신규 여부 */
    @Transient
    @Builder.Default
    private Boolean isNew = false;
}