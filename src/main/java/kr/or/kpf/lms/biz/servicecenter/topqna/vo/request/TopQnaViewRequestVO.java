package kr.or.kpf.lms.biz.servicecenter.topqna.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 자주묻는 질문 관련 요청 객체
 */
@Schema(name="TopQnaViewRequestVO", description="자주묻는 질문 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class TopQnaViewRequestVO extends CSViewVOSupport {
    /** 검색어 범위 */
    @Schema(description="검색어 범위")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @Schema(description="검색에 포함할 단어")
    private String containText;
}
