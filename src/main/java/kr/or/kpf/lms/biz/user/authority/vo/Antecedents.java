package kr.or.kpf.lms.biz.user.authority.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 주요 이력 객체 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Antecedents {

    @Schema(description="연도", required = true, example="")
    private String year;

    @Schema(description="기관", required = true, example="")
    private String organization;

    @Schema(description="내용", required = true, example="")
    private String comments;
}
