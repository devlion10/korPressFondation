package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 1:1 문의 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MY_QNA")
@Access(value = AccessType.FIELD)
public class MyQna extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 문의 타입(1: 공모/자격문의, 2: 교육문의, 3: 사이트 이용문의) */
    @Column(name="QNA_TYPE", nullable=false)
    private String qnaType;

    /** 문의 제목 */
    @Column(name="REQ_TITLE", nullable=false)
    private String reqTitle;

    /** 문의 내용 */
    @Column(name="REQ_CONTENTS", nullable=false)
    private String reqContents;

    /** 답변 제목 */
    @Column(name="RES_TITLE")
    private String resTitle;

    /** 답변 내용 */
    @Column(name="RES_CONTENTS")
    private String resContents;

    /** 문의 첨부 파일 경로 */
    @Column(name="REQ_ATCH_FILE_PATH")
    private String reqAttachFilePath;

    /** 문의 첨부 파일 사이즈 */
    @Column(name="REQ_FILE_SIZE")
    private Long reqFileSize;

    /** 문의 첨부 파일 원본명 */
    @Column(name="REQ_FILE_ORGN")
    private String reqFileOrigin;

    /** 답변 첨부 파일 경로 */
    @Column(name="RES_ATCH_FILE_PATH")
    private String resAttachFilePath;

    /** 답변 첨부 파일 원본명 */
    @Column(name="RES_FILE_ORGN")
    private String resFileOrigin;

    /** 답변 첨부 파일 사이즈 */
    @Column(name="RES_FILE_SIZE")
    private Long resFileSize;

    /** 문의 상태(1: 미확인, 2: 처리중, 3: 답변 완료) */
    @Column(name="QNA_STATE", nullable=false)
    private String qnaState;
}
