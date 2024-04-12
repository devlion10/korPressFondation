package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumApplicationExam;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * 교육 과정 신청 시험 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_APLY_EXAM")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfCurriculumApplicationExam.class)
public class CurriculumApplicationExam extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 교육 신청 일련 번호 */
    @Id
    @Column(name="EDU_APLY_SN", nullable=false)
    private String applicationNo;

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD", nullable=false)
    private String curriculumCode;

    /** 시험 일련 번호 */
    @Id
    @Column(name="EXAM_SN", nullable = false)
    private String examSerialNo;

    /** 로그인 아이디 */
    @Id
    @Column(name="LGN_ID", nullable=false)
    private String userId;

    /** 신청 콘텐츠 정렬 번호 */
    @Column(name="EXAM_SORT_NO", nullable=false)
    private Integer sortNo;

    /** 시험 시작 시간 */
    @Column(name="EXAM_BGNG_TM")
    @Convert(converter= DateToStringConverter.class)
    private String beginDateTime;

    /** 시험 점수 */
    @Column(name="EXAM_SCR")
    private Integer examScore;

    /** 완료 여부 */
    @Column(name="CMPTN_YN", nullable=false)
    @Convert(converter= BooleanConverter.class)
    private Boolean isComplete;

    /** 완료 일시 */
    @Column(name="CMPTN_DT")
    @Convert(converter= DateToStringConverter.class)
    private String completeDateTime;

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "EDU_APLY_SN", referencedColumnName="EDU_APLY_SN", insertable = false, updatable = false),
            @JoinColumn(name = "CRCL_CD", referencedColumnName="CRCL_CD", insertable = false, updatable = false),
            @JoinColumn(name = "EXAM_SN", referencedColumnName="EXAM_SN", insertable = false, updatable = false),
            @JoinColumn(name = "LGN_ID", referencedColumnName="LGN_ID", insertable = false, updatable = false)
    })
    private List<ExamApplicationQuestion> examApplicationQuestionList;
}
