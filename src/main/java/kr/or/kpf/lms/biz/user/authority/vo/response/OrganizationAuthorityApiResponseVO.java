package kr.or.kpf.lms.biz.user.authority.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.authority.controller.AuthorityApiController;
import kr.or.kpf.lms.biz.user.webuser.vo.CreateOrganizationInfo;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Schema(name="OrganizationAuthorityApiResponseVO", description="사업 참여 권한 신청(기관/학교) API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAuthorityApiResponseVO extends CSResponseVOSupport {

    @Schema(title="로그인 아이디", description="로그인 아이디", example="")
    private String userId;

    @Schema(description="기관 코드", example="ORG000001")
    private String organizationCode;

    @Schema(description="소속 기관 명", example="")
    private String organizationName;

    @Schema(description="소속 기관 서브 기관 명", example="")
    private String orgnizationSubName;

    @Schema(description="사업 참여 권한", example="SCHOOL")
    private String bizAuthority;

    @Schema(description="소속 기관 사업자 등록번호", example="12300486789")
    private String bizLicenseNumber;

    @Schema(description="권한 승인 상태", example="1")
    private String bizAuthorityApprovalState;

    @Schema(description="기관 대표자", example="")
    private String representative;

    @Schema(description="소속 기관 우편번호", example="123456")
    private String organizationZipCode;

    @Schema(description="소속 기관 주소1", example="")
    private String organizationAddress1;

    @Schema(description="소속 기관 주소2", example="")
    private String organizationAddress2;

    @Schema(description="기관 홈페이지", example="")
    private String homepage;

    @Schema(description="소속 기관 연락처", example="")
    private String organizationTelNumber;

    @Schema(description="소속 기관 팩스 번호", example="")
    private String organizationFaxNumber;

    @Schema(description="학습 수", example="")
    private Integer learningCount;

    @Schema(description="학생 수", example="")
    private Integer numberStudents;

    @Schema(description="소속 부서", example="")
    private String department;

    @Schema(description="직급", example="")
    private String rank;

    @Schema(description="직책", example="")
    private String position;

    @Schema(description="담당 콘텐츠/과목", example="")
    private String charge;

    @Schema(description="담당자 연락처", example="")
    private String chargeTelNumber;
}
