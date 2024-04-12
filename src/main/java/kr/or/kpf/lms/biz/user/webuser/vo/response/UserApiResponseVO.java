package kr.or.kpf.lms.biz.user.webuser.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.OrganizationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Schema(name="UserApiResponseVO", description="유저 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserApiResponseVO extends CSResponseVOSupport {

    @Schema(description="로그인 아이디")
    private String userId;

    @Schema(description="비밀번호")
    private String password;

    @Schema(description="사용자 명")
    private String userName;

    @Schema(description="사용자 생년월일")
    private String birthDay;

    @Schema(description="권한 그룹")
    private String roleGroup;

    @Schema(description="사용자 전화번호")
    private String phone;

    @Schema(description="SMS 수신 동의 여부")
    private Boolean isSmsAgree;

    @Schema(description="사용자 이메일 주소")
    private String email;

    @Schema(description="EMAIL 수신 동의 여부")
    private Boolean isEmailAgree;

    @Schema(description="성별 코드")
    private String gender;

    @Schema(description="인증 DN 값")
    private String certValue;

    @Schema(description="DI 값")
    private String di;

    @Schema(description="CI 값")
    private String ci;

    @Schema(description="비밀번호 변경 일자")
    private String passwordChangeDate;

    @Schema(description="탈퇴 일자")
    private String withDrawDate;

    @Schema(description="잠금 수")
    private Integer lockCount;

    @Schema(description="잠금 일시")
    private LocalDateTime lockDateTime;

    @Schema(description="잠금 여부")
    private Boolean isLock;

    @Schema(description="회원 상태 (1: 정상, 2: 휴면, 3: 잠금, 4: 탈퇴)")
    private String state;

    @Schema(description="기관 코드")
    private String organizationCode;

    @Schema(description="소속 부서")
    private String department;

    @Schema(description="직급")
    private String rank;

    @Schema(description="직책")
    private String position;

    @Schema(description="재직 증명서 및 첨부 파일")
    private Integer fileSerialNumber;

    @Schema(description="보호자 성명")
    private String parentName;

    @Schema(description="보호자 생년월일")
    private String parentBirthDay;

    @Schema(description="보호자 성별 (1: 남성, 2: 여성)")
    private String parentGender;

    @Schema(description="개인 나이스 번호(교원)", example="")
    private String personalNeisNo;

    @Schema(description = "소속 기관")
    private OrganizationInfo organizationInfo;
}
