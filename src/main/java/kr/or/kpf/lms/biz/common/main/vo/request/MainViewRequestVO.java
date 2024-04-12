package kr.or.kpf.lms.biz.common.main.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(name="MainViewRequestVO", description="메인 통합 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MainViewRequestVO extends CSViewVOSupport {
    /** 조회 기준 */
    private String inquiryCriteria;
    /** 교육 타입 */
    private String educationType;
    /** 교육 대상 */
    private List<String> educationTarget;
    /** 사업 공고 구분 */
    private Integer bizPbancCtgr;
    /** 사업 공고 구분 ( View Type - 1: 사회미디어, 2: 학교미디어 ) */
    private Integer bizPbancCtgrSub;
    /** 사업 공고 템플릿 유형 */
    private List<Integer> bizPbancType;
    /** 사업 공고 상태 필터링 */
    private Integer bizPbancStts;
    /** 캐러셀 타입 */
    private String carouselType;
    /** 로그인 여부 */
    private String loginYn;
}
