package kr.or.kpf.lms.repository.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSEntitySupport;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Homepage Banner 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "HOME_BANNER")
@Access(value = AccessType.FIELD)
public class HomeBanner extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @Column(name="BANNER_SN", nullable = false)
    private String bannerSn;

    /** 제목 */
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 시작 일시 */
    @Column(name="BANNER_START_YMD")
    private LocalDateTime bannerStartYmd;

    /** 종료 일시 */
    @Column(name="BANNER_END_YMD")
    private LocalDateTime bannerEndYmd;

    /** 배너 링크 */
    @Column(name="BANNER_LINK", nullable=false)
    private String bannerLink;

    /** 배너 이미지 경로 */
    @Column(name="BANNER_IMAGE_PATH", nullable=false)
    private String bannerImagePath;

    /** 배너 상태 0:미사용, 1:사용 */
    @Column(name="BANNER_STTS", nullable=false)
    private int bannerStts;

}
