package kr.or.kpf.lms.biz.user.adminuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="AdminUserViewRequestVO", description="어드민 계정 관리 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AdminUserViewRequestVO  extends CSViewVOSupport {
    /** 계정명 */
    private String userName;
    /** 아이디 */
    private String userId;
}
