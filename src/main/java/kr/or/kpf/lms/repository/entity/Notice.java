package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 공지사항 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "NOTICE")
@Access(value = AccessType.FIELD)
public class Notice extends CSEntitySupport implements Serializable {

    /** 공지사항 일련번호 */
    @Id
    @Schema(description="공지사항 일련번호")
    @Column(name="NOTICE_SN", nullable = false)
    private String noticeSerialNo;

    /** 공지사항 타입 */
    @Schema(description="공지사항 타입")
    @Column(name="NOTICE_TYPE", nullable=false)
    private String noticeType;

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

    /** 조회수 */
    @Schema(description="조회수")
    @Column(name="VIEW_CNT")
    private BigInteger viewCount;

    /** 상위 노출 여부 */
    @Schema(description="상위 노출 여부")
    @Column(name="TOP_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isTop;

    /** 신규 공지사항 여부 */
    @Schema(description="신규 공지사항 여부")
    @Transient
    @Builder.Default
    private Boolean isNew = false;

    @Transient
    private List<FileMaster> fileMasters;

    @Transient
    private String adminName;
}
