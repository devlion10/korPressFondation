package kr.or.kpf.lms.biz.search.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

@Schema(name="SearchViewRequestVO", description="검색 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchViewRequestVO extends CSViewVOSupport {

    /** 검색어 */
    @Schema(description="검색어")
    private String searchText;

}
