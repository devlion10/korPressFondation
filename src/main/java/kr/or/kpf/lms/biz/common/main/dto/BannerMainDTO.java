package kr.or.kpf.lms.biz.common.main.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class BannerMainDTO {
    /** 배너 코드 */
    private String bannerSn;
    /** 제목 */
    private String title;
    /** 배너 링크 */
    private String bannerLink;
    /** 썸네일 파일 경로 */
    private String bannerImagePath;
    /** 상태 (0:비활성, 1:활성) */
    private int bannerStts;
}
