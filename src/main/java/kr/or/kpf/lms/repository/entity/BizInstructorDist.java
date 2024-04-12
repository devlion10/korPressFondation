package kr.or.kpf.lms.repository.entity;

import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 거리 증빙 Entity
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BIZ_INSTR_DIST")
@Access(value = AccessType.FIELD)
public class BizInstructorDist extends CSEntitySupport {
    /** 거리 증빙 일련 번호 */
    @Id
    @Column(name = "BIZ_INSTR_DIST_NO", nullable = false)
    private String bizInstrDistNo;

    /** 강사 모집 공고 신청 정보 일련 번호 */
    @Column(name = "BIZ_INSTR_APLY_NO", nullable = false)
    private String bizInstrAplyNo;

    /** 사업 공고 신청 일련 번호 */
    @Column(name = "BIZ_ORG_APLY_NO", nullable = false)
    private String bizOrgAplyNo;

    /** 거리 증빙 출발지명  */
    @Column(name = "BIZ_DIST_BGNG_NM", nullable = false)
    private String bizDistBgngNm;

    /** 거리 증빙 출발지 주소  */
    @Column(name = "BIZ_DIST_BGNG_ADDR", nullable = false)
    private String bizDistBgngAddr;

    /** 거리 증빙 도착지명  */
    @Column(name = "BIZ_DIST_END_NM", nullable = false)
    private String bizDistEndNm;

    /** 거리 증빙 도착지 주소 */
    @Column(name = "BIZ_DIST_END_ADDR", nullable = false)
    private String bizDistEndAddr;

    /** 거리 증빙 상태  */
    @Column(name = "BIZ_DIST_STTS", nullable = false)
    private Integer bizDistStts;

    /** 거리 증빙 거리 */
    @Column(name = "BIZ_DIST_VALUE", nullable = true)
    private Double bizDistValue;

    /** 거리 증빙 금액 */
    @Column(name = "BIZ_DIST_AMT", nullable = true)
    private Integer bizDistAmt;

    /** 거리 증빙 지도 첨부 여부 */
    @Column(name = "BIZ_DIST_MAP_YN", nullable = true)
    private Integer bizDistMapYn;

    /** 거리 증빙 지도 첨부파일 */
    @Column(name = "BIZ_DIST_MAP_FILE", nullable = true)
    private String bizDistMapFile;

    /** 거리 증빙 지도 첨부파일 사이즈 */
    @Column(name = "BIZ_DIST_MAP_FILE_SIZE", nullable = true)
    private Long bizDistMapFileSize;

}
