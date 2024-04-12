package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 일반 교육 강의 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "LECTURE_MASTER")
@Access(value = AccessType.FIELD)
public class LectureMaster extends CSEntitySupport implements Serializable {
    /** 강의 코드 */
    @Id
    @Column(name="LECTURE_CD", nullable = false)
    private String lectureCode;

    /** 교육 계획 코드 */
    @Column(name="PLAN_CD", nullable = false)
    private String educationPlanCode;

    /** 교육 강사 */
    @Column(name="LECTURER", nullable = false)
    private String lecturer;

    /** 강사 장소 */
    @Column(name="LECTURER_PLACE", nullable = false)
    private String lecturerPlace;

    /** 강의 명 */
    @Column(name="LECTURE_TITLE", nullable = false)
    private String lectureTitle;

    /** 강의 내용 */
    @Column(name="LECTURE_CN", nullable = false)
    private String lectureDetail;

    /** 출석 QR 파일 경로 */
    @Column(name="QR_PATH")
    private String attendQRCodePath;

    /** 교육 시작 일시 */
    @Column(name="OPER_BGNG_DT")
    @Convert(converter= DateToStringConverter.class)
    private String operationBeginDateTime;

    /** 교육 종료 일시 */
    @Column(name="OPER_END_DT")
    @Convert(converter=DateToStringConverter.class)
    private String operationEndDateTime;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name="LECTURER", referencedColumnName="INSTR_SN", insertable=false, updatable=false)
    InstructorInfo lecturerInfo;

    @OneToMany(fetch=FetchType.EAGER)
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name="LECTURE_CD", insertable=false, updatable=false)
    List<LectureLecturer> lectureLecturerList;
}
