package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 교육 후기 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EDU_REVIEW")
@Access(value = AccessType.FIELD)
public class EducationReview extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 구분(팀) */
    @Column(name="CTGR", nullable=false)
    private String category;

    /** 유형 */
    @Column(name="TYPE", nullable=false)
    private String type;

    /** 제목 */
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 내용 */
    @Column(name="CONTENTS", nullable=false)
    private String contents;

    /** 조회수 */
    @Schema(description="조회수")
    @Column(name="VIEW_CNT")
    private BigInteger viewCount;

    /** 게시자 */
    @Transient
    private AdminUser adminUser;
}
