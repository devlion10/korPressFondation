package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 교육장 사용 신청 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EDU_PLACE_APLY")
@Access(value = AccessType.FIELD)
public class EduPlaceAply extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목")
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 내용 */
    @Schema(description="내용")
    @Column(name="CONTENTS", nullable=false)
    private String contents;

    /** 첨부 파일 경로 */
    @Schema(description="첨부 파일 경로")
    @Column(name="ATCH_FILE_PATH")
    private String attachFilePath;

    /** 신청자 */
    @Schema(description="신청자")
    @Column(name="APLY_USER_NM", nullable=false)
    private String aplyUserNm;

    /** 신청자 연락처 */
    @Schema(description="신청자 연락처")
    @Column(name="APLY_PHONE", nullable=false)
    private String aplyPhone;

    /** 신청자 이메일 주소 */
    @Schema(description="신청자 이메일 주소")
    @Column(name="APLY_EML_ADDR")
    private String aplyEmlAddr;

    /** 신규 여부 */
    @Schema(description="신규 여부")
    @Transient
    @Builder.Default
    private Boolean isNew = false;
}