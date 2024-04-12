package kr.or.kpf.lms.repository.entity;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 일반 교육 강의 강사 목록 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "LECTURE_LECTURER")
@Access(value = AccessType.FIELD)
public class LectureLecturer implements Serializable {
    /** 일련 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private Long sequenceNo;

    /** 강의 코드 */
    @Column(name="LECTURE_CD", nullable = false)
    private String lectureCode;

    /** 교육 강사 */
    @Column(name="LECTURER", nullable = false)
    private String lecturer;

    /** 강사 사례비 */
    @Column(name="LECTURER_GRTT")
    private Integer lecturerGratuity;

    @ManyToOne(fetch=FetchType.EAGER)
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name="LECTURER", referencedColumnName="INSTR_SN", insertable=false, updatable=false)
    InstructorInfo lecturerInfo;
}
