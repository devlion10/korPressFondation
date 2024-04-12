package kr.or.kpf.lms.biz.user.authority.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.authority.controller.AuthorityApiController;
import kr.or.kpf.lms.biz.user.webuser.vo.CreateOrganizationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(name="OrganizationAuthorityApiRequestVO", description="사업 참여 권한 신청 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationAuthorityApiRequestVO {

    @Schema(description="로그인 아이디", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateOrganizationAuthority.class, AuthorityApiController.UpdateOrganizationAuthority.class}, message="로그인 아이디는 필수 입니다.")
    private String userId;

    @Schema(description="기관 코드", example="ORG000001")
    @NotEmpty(groups={AuthorityApiController.CreateOrganizationAuthority.class, AuthorityApiController.UpdateOrganizationAuthority.class}, message="기관 코드는 필수 입니다.")
    private String organizationCode;

    @Schema(description="기관 명", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateOrganizationAuthority.class}, message="기관 명은 필수 입니다.")
    private String organizationName;

    @Schema(description="기관 서브 기관 명", example="")
    private String orgnizationSubName;

    @Schema(description="사업 참여 권한", required = true, example="SCHOOL")
    @NotEmpty(groups={AuthorityApiController.CreateOrganizationAuthority.class}, message="사업 참여 권한은 필수 입니다.")
    private String bizAuthority;

    @Schema(description="기관 사업자 등록번호", required = false, example="12300486789")
    private String bizLicenseNumber;

    @Schema(description="기관 대표자", required = false, example="")
    private String representative;

    @Schema(description="기관 우편번호", required = false, example="12356")
    private String organizationZipCode;

    @Schema(description="기관 주소1", required = false, example="")
    private String organizationAddress1;

    @Schema(description="기관 주소2", example="")
    private String organizationAddress2;

    @Schema(description="기관 홈페이지", example="")
    private String homepage;

    @Schema(description="기관 연락처", example="")
    private String organizationTelNumber;

    @Schema(description="기관 팩스 번호", example="")
    private String organizationFaxNumber;

    @Schema(description="학습 수", example="")
    private Integer learningCount;

    @Schema(description="학생 수", example="")
    private Integer numberStudents;

    @Schema(description="담당자 부서", example="")
    private String department;

    @Schema(description="담당자 직급", example="")
    private String rank;

    @Schema(description="직책", example="")
    private String position;

    @Schema(description="담당 콘텐츠/과목", example="")
    private String charge;

    @Schema(description="담당자 연락처", example="")
    private String chargeTelNumber;
}
