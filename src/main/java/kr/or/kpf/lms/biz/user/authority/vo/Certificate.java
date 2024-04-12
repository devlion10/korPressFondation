package kr.or.kpf.lms.biz.user.authority.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 자격증 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Schema(description="취득 일자", required = true, example="")
    private String date;

    @Schema(description="자격증", required = true, example="")
    private String certificateName;

    @Schema(description="자격번호", required = true, example="")
    private String certificateNo;

    @Schema(description="발급 기관", required = true, example="")
    private String organization;
}
