package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * 페이지 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DOCUMENTS")
@Access(value = AccessType.FIELD)
public class Documents extends CSEntitySupport implements Serializable {

    /** 일련 번호 */
    @Id
    @Schema(description="일련 번호")
    @Column(name="SEQ_NO", nullable = false)
    private BigInteger sequenceNo;

    /** 문서(개인정보 , 이용 약관 및 기타) 유형 (0: 일반, 1: 이용 약관, 2: 개인정보 처리 방침, 3: 개인정보 동의서) */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 유형 (0: 일반, 1: 이용 약관, 2: 개인정보 처리 방침, 3: 개인정보 동의서)")
    @Column(name="DCMNT_TYPE", nullable=false)
    private String documentType;

    /** 문서(개인정보 , 이용 약관 및 기타) 내용 */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 내용")
    @Column(name="DCMNT_CN", nullable=false)
    private String documentContent;

    /** 문서(개인정보 , 이용 약관 및 기타) 변경 내용 */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 변경 내용")
    @Column(name="DCMNT_CHG", nullable=true)
    private String documentChange;

    /** 문서(개인정보 , 이용 약관 및 기타) 상태 */
    @Schema(description="문서(개인정보 , 이용 약관 및 기타) 상태 (0: 미사용, 1: 사용)")
    @Column(name="DCMNT_STTS", nullable=false)
    private String documentStatus;
}
