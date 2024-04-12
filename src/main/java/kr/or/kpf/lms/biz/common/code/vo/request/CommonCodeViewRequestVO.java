package kr.or.kpf.lms.biz.common.code.vo.request;

import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 공통 코드 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CommonCodeViewRequestVO extends CSViewVOSupport {

    /** 상위 코드 */
    private String upIndividualCode;

    /** 상위 코드 명 */
    private String codeName;
}
