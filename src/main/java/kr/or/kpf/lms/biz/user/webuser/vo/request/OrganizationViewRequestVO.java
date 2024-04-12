package kr.or.kpf.lms.biz.user.webuser.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 * 기관 정보 관련 요청 객체
 */
@Schema(name="OrganizationViewRequestVO", description="기관 정보 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationViewRequestVO extends CSViewVOSupport {
    /** 기관 코드 */
    @Schema(description="기관 코드")
    private String organizationCode;
    /** 기관 명 */
    @Schema(description="기관 명")
    private String organizationName;
    /** 기관 타입 */
    @Schema(description="기관 타입")
    private String organizationType;
}
