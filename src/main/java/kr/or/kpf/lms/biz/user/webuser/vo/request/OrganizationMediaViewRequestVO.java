package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="OrganizationMediaViewRequestVO", description="매체 정보 관리 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrganizationMediaViewRequestVO extends CSViewVOSupport {

    /** 매체사코드 */
    private String organizationCode;

    /** 매체사명 */
    private String mediaName;

    /** 매체 대분류 */
    private String mediaClsName1;

    /** 매체 중분류 */
    private String mediaClsName2;

    /** 지역코드 */
    private String mediaArea;

    /** 사용 여부 */
    private Boolean isUsable;
}
