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

@Schema(name="UserApiRequestVO", description="유저 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude={"password"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserApiRequestVO {

    @Schema(description="로그인 아이디", required = true, example="")
    @NotEmpty(groups={UserApiController.CreateUser.class, UserApiController.WithdrawalUser.class}, message="로그인 아이디는 필수 입니다.")
    private String userId;

    @Schema(description="비밀번호", required = true, example="")
    @NotEmpty(groups={UserApiController.CreateUser.class, ChangePassword.class, UserApiController.DeleteUser.class, UserApiController.WithdrawalUser.class}, message="비밀번호는 필수 입니다.")
    @Null(groups={UserApiController.UpdateUser.class}, message="비밀번호 변경 기능을 이용해 주세요.")
    private String password;

    @Schema(description="사용자 명", required = true, example="")
    @NotEmpty(groups={UserApiController.CreateUser.class}, message="사용자 명은 필수 입니다.")
    private String userName;

    @Schema(description="사용자 생년월일", required = true, example="20220910")
    @NotEmpty(groups={UserApiController.CreateUser.class}, message="사용자 생년월일는 필수 입니다.")
    private String birthDay;

    @Schema(description="권한 그룹", required = true, example="GENERAL")
    @NotEmpty(groups={UserApiController.CreateUser.class}, message="권한 그룹는 필수 입니다.")
    private String roleGroup;

    @Schema(description="사용자 전화번호", example="01012345678")
    private String phone;

    @Schema(description="SMS 수신 동의 여부", required = true, example="true")
    @NotNull(groups={UserApiController.CreateUser.class}, message="SMS 수신 동의 여부는 필수 입니다.")
    private Boolean isSmsAgree;

    @Schema(description="사용자 이메일 주소", example="")
    private String email;

    @Schema(description="EMAIL 수신 동의 여부", required = true, example="true")
    @NotNull(groups={UserApiController.CreateUser.class}, message="EMAIL 수신 동의 여부는 필수 입니다.")
    private Boolean isEmailAgree;

    @Schema(description="성별 코드", example="1")
    private String gender;

    @Schema(description="인증 DN 값", example="")
    private String certValue;

    @Schema(description="DI 값", example="")
    private String di;

    @Schema(description="CI 값", example="")
    private String ci;

    @Schema(description="비밀번호 변경 일자", example="20220101")
    private String passwordChangeDate;

    @Schema(description="탈퇴 일자", example="")
    private String withDrawDate;

    @Schema(description="잠금 수", example="0")
    private Integer lockCount;

    @Schema(description="잠금 일시", example="")
    private LocalDateTime lockDateTime;

    @Schema(description="잠금 여부", example="false")
    private Boolean isLock;

    @Schema(description="회원 상태 (1: 정상, 2: 휴면, 3: 잠금, 4: 탈퇴)")
    private String state;

    @Schema(description="기관 코드")
    private String organizationCode;

    @Schema(description="매체 코드", example="")
    private String mediaCode;

    @Schema(description="소속 부서", example="")
    private String department;

    @Schema(description="직급", example="")
    private String rank;

    @Schema(description="직책", example="")
    private String position;

    @Schema(description="재직 증명서 및 첨부 파일", example="")
    private String attachFilePath;

    @Schema(description="보호자 성명", example="")
    private String parentName;

    @Schema(description="보호자 생년월일", example="")
    private String parentBirthDay;

    @Schema(description="보호자 연락처", example="")
    private String nokPhone;

    @Schema(description="보호자 성별 (1: 남성, 2: 여성)", example="")
    private String parentGender;

    @Schema(description="개인 나이스 번호(교원)", example="")
    private String personalNeisNo;

    @Schema(description = "마이그레이션 플래그")
    @Builder.Default
    private String migrationFlag = "N";

    @Schema(description="회원승인여부", example="")
    @Builder.Default
    private String approFlag = "Y";

}
