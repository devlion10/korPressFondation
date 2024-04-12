package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 자료실 테이블 Entity
 */
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "EVENT_INFO")
@Access(value = AccessType.FIELD)
public class EventInfo extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "SEQ_NO")
    private BigInteger sequenceNo;

    /** 타입(1: 이벤트, 2: 설문) */
    @Column(name = "TYPE", nullable = true)
    private String type;

    /** 제목 */
    @Column(name = "TITLE", nullable = false)
    private String title;

    /** 내용 */
    @Column(name = "CONTENTS", nullable = false)
    private String contents;

    /** 썸네일 파일 경로 */
    @Column(name = "THUMB_FILE_PATH", nullable = true)
    private String thumbFilePath;

    /** 시작 일자 */
    @Column(name = "START_DT", nullable = true)
    private String startDT;

    /** 종료 일자 */
    @Column(name = "END_DT", nullable = true)
    private String endDT;

    /** 상태 (0:종료, 1:진행) */
    @Column(name = "STATUS", nullable = true)
    private String status;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="RGTR_LGN_ID", referencedColumnName = "LGN_ID", insertable=false, updatable=false)
    private AdminUser adminUser;
}