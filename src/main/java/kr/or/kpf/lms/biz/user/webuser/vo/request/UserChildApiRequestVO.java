package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.webuser.controller.UserApiController;
import kr.or.kpf.lms.biz.user.webuser.vo.ChangePassword;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Schema(name="UserChildApiRequestVO", description="자녀 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserChildApiRequestVO {

    @Schema(description="사용자 명", required = true, example="")
    private String name;

    @Schema(description="사용자 생년월일", required = true, example="20220910")
    private String birth;

    @Schema(description="보호자 전화번호", required = true, example="01012345678")
    private String phone;

}
