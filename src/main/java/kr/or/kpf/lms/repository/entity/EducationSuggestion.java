package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 교육 주제 제안 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EDU_SUB_SGST")
@Access(value = AccessType.FIELD)
public class EducationSuggestion extends CSEntitySupport implements Serializable {
    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 교육 주제 제안 타입(1: 언론인, 2: 시민) */
    @Column(name="SGST_TYPE", nullable=false)
    private String suggestionType;

    /** 제안 내용 */
    @Column(name="CONTENTS", nullable=false)
    private String contents;

    /** 비밀글 여부 */
    @Column(name="SECURITY_YN", nullable=false)
    @Convert(converter = BooleanConverter.class)
    private Boolean isSecurity;

    /** 댓글 */
    @Column(name="COMMENT")
    private String comment;

    /** 댓글 작성자 유저 ID */
    @Column(name="COMMENT_LGN_ID")
    private String commentUserId;

    /** 비밀댓글 여부 */
    @Column(name="COMMENT_SECURITY_YN", nullable=false)
    @Convert(converter = BooleanConverter.class)
    private Boolean commentSecurity;

    /** 제안자명 */
    @Transient
    private String suggestUser;

    /** 답변자명 */
    @Transient
    private String commentUser;
}
