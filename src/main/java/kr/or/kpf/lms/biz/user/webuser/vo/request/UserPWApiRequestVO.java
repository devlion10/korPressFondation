package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.webuser.controller.UserApiController;
import kr.or.kpf.lms.biz.user.webuser.vo.ChangePassword;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Schema(name="UserPWApiRequestVO", description="유저 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude={"password"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPWApiRequestVO {

    @Schema(description="로그인 아이디", required = true, example="")
    @NotEmpty(groups={UserApiController.CreateUser.class}, message="로그인 아이디는 필수 입니다.")
    private String userId;

    @Schema(description="비밀번호", required = true, example="")
    @NotEmpty(groups={UserApiController.CreateUser.class, ChangePassword.class, UserApiController.DeleteUser.class}, message="비밀번호는 필수 입니다.")
    @Null(groups={UserApiController.UpdateUser.class}, message="비밀번호 변경 기능을 이용해 주세요.")
    private String password;
}
