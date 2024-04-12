package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfCurriculumApplicationLecture;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 일반 교육 과정 출석 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CRCL_APLY_LEC")
@Access(value = AccessType.FIELD)
@IdClass(value = KeyOfCurriculumApplicationLecture.class)
public class CurriculumApplicationLecture extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO")
    private BigInteger sequenceNo;

    /** 교육 신청 일련 번호 */
    @Id
    @Column(name="EDU_APLY_SN")
    private String applicationNo;

    /** 과정 코드 */
    @Id
    @Column(name="CRCL_CD")
    private String curriculumCode;

    /** 강의 코드 */
    @Id
    @Column(name="LECTURE_CD", nullable = false)
    private String lectureCode;

    /** 참석 여부 */
    @Column(name="ATTEND_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isAttend;

    /** 로그인 아이디 */
    @Id
    @Column(name = "LGN_ID")
    private String userId;

    /** 강의 정보 */
    @ManyToOne
    @JoinColumn(name="LECTURE_CD", referencedColumnName="LECTURE_CD", updatable = false, insertable = false)
    private LectureMaster lectureMaster;
}
