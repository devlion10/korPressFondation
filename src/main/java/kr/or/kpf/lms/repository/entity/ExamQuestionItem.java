package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.converter.BooleanConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import kr.or.kpf.lms.repository.entity.key.KeyOfQuestionItem;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 시험 문제 문항 목록 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EXAM_QUES_ITEM")
@IdClass(KeyOfQuestionItem.class)
@Access(value = AccessType.FIELD)
public class ExamQuestionItem extends CSEntitySupport implements Serializable {

    /** 시험 문제 일련 번호 */
    @Id
    @Column(name="QUES_SN", nullable = false)
    private String questionSerialNo;

    /** 시험 문제 문항 일련 번호 */
    @Id
    @Column(name="ITEM_SN", nullable = false)
    private String questionItemSerialNo;

    /** 시험 문제 문항 순서 */
    @Column(name="ITEM_SORT_NO", nullable = false)
    private Integer questionSortNo;

    /** 시험 문제 문항 */
    @Column(name="ITEM_VALUE", nullable = false)
    private String questionItemValue;

    /** 시험 문제 문항 파일 경로 */
    @Column(name="ITEM_FILE_PATH")
    private String questionItemFilePath;

    /** 시험 문제 문항 파일 크기 */
    @Column(name="ITEM_FILE_SIZE")
    private Long questionItemFileSize;

    /** 시험 문제 문항 파일 원본명 */
    @Column(name="ITEM_FILE_ORGN")
    private String questionItemFileOrigin;

    /** 정답 여부 */
    @Column(name="COR_ANS_YN")
    @Convert(converter = BooleanConverter.class)
    private Boolean isCorrectAnswer;
}
