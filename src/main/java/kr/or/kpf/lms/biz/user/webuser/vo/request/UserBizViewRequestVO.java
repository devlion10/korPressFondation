package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="UserBizViewRequestVO", description="유저 VIEW 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBizViewRequestVO extends CSViewVOSupport {

    @Schema(description="로그인 아이디")
    private String userId;

    @Schema(description="기관 코드")
    private String organizationCode;

    @Schema(description="사업 권한 상태")
    private String bizAuthorityApprovalState;
}
