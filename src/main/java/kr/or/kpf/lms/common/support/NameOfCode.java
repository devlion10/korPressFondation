package kr.or.kpf.lms.common.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NameOfCode {
    /** 코드명 */
    @Schema(description="코드명", example="")
    private String codeName;

    /** 코드값 */
    @Schema(description="코드값", example="")
    private String code;

    /** 하위 코드 */
    @Schema(description="하위 코드", example="")
    List<NameOfCode> subCode;
}
