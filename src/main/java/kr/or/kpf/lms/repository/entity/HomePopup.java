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
 * Homepage Popup 테이블 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "HOME_POPUP")
@Access(value = AccessType.FIELD)
public class HomePopup extends CSEntitySupport implements Serializable {

    /** 시퀀스 번호 */
    @Id
    @Column(name="POPUP_SN", nullable = false)
    private String popupSn;

    /** 제목 */
    @Column(name="TITLE", nullable=false)
    private String title;

    /** 내용 */
    @Column(name="CONTENTS", nullable=false)
    private String contents;

    /** B:로그인전, A:로그인후 */
    @Column(name="POPUP_VIEW_TYPE", nullable=false)
    private String popupViewType;

    /** 시작 일시 */
    @Column(name="POPUP_START_YMD")
    private LocalDateTime popupStartYmd;

    /** 종료 일시 */
    @Column(name="POPUP_END_YMD")
    private LocalDateTime popupEndYmd;

    /** 창 사이즈 V */
    @Column(name="WINDOW_SIZE_V", nullable=false)
    private Integer windowSizeV;

    /** 창 사이즈 H */
    @Column(name="WINDOW_SIZE_H", nullable=false)
    private Integer windowSizeH;

    /** 창 위치 위부터 */
    @Column(name="WINDOW_TOP", nullable=false)
    private Integer windowTop;

    /** 창 위치 왼쪽부터 */
    @Column(name="WINDOW_LEFT", nullable=false)
    private Integer windowLeft;

    /** 팝업 링크 */
    @Column(name="POPUP_LINK", nullable=false)
    private String popupLink;

    /** 팝업 이미지 경로 */
    @Column(name="POPUP_IMAGE_PATH", nullable=false)
    private String popupImagePath;

    /** 팝업 상태 0:미사용, 1:사용 */
    @Column(name="POPUP_STTS", nullable=false)
    private int popupStts;

}
