package kr.or.kpf.lms.biz.common.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class PopupMainDTO {

    /** 팝업 코드 */
    private String popupSn;

    /** 제목 */
    private String title;

    /** 내용 */
    private String contents;

    /** 팝업 타입 ( A : After, B : Before ) */
    private String popupViewType;

    /** 팝업 링크 */
    private String popupLink;

    /** 팝업 이미지 경로 */
    private String popupImagePath;

    /** 윈도우 세로 (pixel) */
    private Integer windowSizeV;

    /** 윈도우 가로 (pixel) */
    private Integer windowSizeH;

    /** 창 위치 위부터 */
    private Integer windowTop;

    /** 창 위치 왼쪽부터 */
    private Integer windowLeft;

    /** 상태(0:비활성, 1:활성) */
    private int popupStts;

}
