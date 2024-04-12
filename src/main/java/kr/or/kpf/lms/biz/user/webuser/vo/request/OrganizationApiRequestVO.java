package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.webuser.vo.CreateOrganizationInfo;
import kr.or.kpf.lms.biz.user.webuser.vo.UpdateOrganizationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(name="OrganizationApiRequestVO", description="기관 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationApiRequestVO {

    @Schema(description="기관 코드", example="ORG000001")
    @NotEmpty(groups={UpdateOrganizationInfo.class}, message="기관 코드는 필수 입니다.")
    private String organizationCode;

    @Schema(description="기관 명", example="")
    @NotEmpty(groups={CreateOrganizationInfo.class}, message="기관 명은 필수 입니다.")
    private String organizationName;

    @Schema(description="기관 서브 기관 명", example="")
    private String orgnizationSubName;

    @Schema(description="기관 사업자 등록번호", example="12300456789")
    private String bizLicenseNumber;

    @Schema(description="기관 타입(1: 매체사, 2: 기관, 3: 학교)", example="3")
    @NotEmpty(groups={CreateOrganizationInfo.class}, message="기관 타입(1: 매체사, 2: 기관, 3: 학교)")
    private String organizationType;

    @Schema(description="기관 우편번호", example="")
    private String organizationZipCode;

    @Schema(description="기관 주소1", example="")
    private String organizationAddress1;

    @Schema(description="기관 주소2", example="")
    private String organizationAddress2;

    @Schema(description="기관 연락처", example="0311234567")
    private String organizationTelNumber;

    @Schema(description="기관 팩스 번호", example="0278974561")
    private String organizationFaxNumber;

    @Schema(description="홈페이지 주소", example="www.naver.com")
    private String organizationHomepage;

    @Schema(description="사용 여부", example="Y")
    private String isUsable;
}
