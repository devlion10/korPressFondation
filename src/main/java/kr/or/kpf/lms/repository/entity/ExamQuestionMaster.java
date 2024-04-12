package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 시험 문제 마스터 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EXAM_QUES_MASTER")
@Access(value = AccessType.FIELD)
public class ExamQuestionMaster extends CSEntitySupport implements Serializable {

    /** 시험 문제 일련 번호 */
    @Id
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 문제 유형 타입 (1: 단일선택, 2: 다중선택, 3: 단답형, 4: 서술형) */
    @Column(name="QUES_TYPE")
    private String questionType;

    /** 문제 제목 */
    @Column(name="QUES_TITLE")
    private String questionTitle;

    /** 등급 번호 */
    @Column(name="GRD_NO")
    private String questionLevel;

    /** 문항 수 */
    @Column(name="QITEM_CNT")
    private Integer questionItemCount;

    /** 사용 여부 */
    @Column(name="USE_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isUsable;

    /** 문제 내용 */
    @Column(name="QUES_CN")
    private String questionContents;

    /** 문제 파일 경로 */
    @Column(name="QUES_FILE_PATH")
    private String questionFilePath;

    /** 문제 파일 크기 */
    @Column(name="QUES_FILE_SIZE")
    private Long questionFileSize;

    /** 문제 파일 원본명 */
    @Column(name="QUES_FILE_ORGN")
    private String questionFileOrigin;

    /** 정답 설명 */
    @Column(name="COAN_DSCR")
    private String correctAnswerDescription;

    /** 시험 문제 문항 리스트 */
    @OneToMany
    @JoinColumn(name="QUES_SN", insertable=false, updatable=false)
    @OrderBy("questionSortNo ASC")
    private List<ExamQuestionItem> questionItemList;
}
