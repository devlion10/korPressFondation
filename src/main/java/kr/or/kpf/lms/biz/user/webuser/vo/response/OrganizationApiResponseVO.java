package kr.or.kpf.lms.biz.user.webuser.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Schema(name="OrganizationApiResponseVO", description="소속 기관 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationApiResponseVO extends CSResponseVOSupport {
    /** 소속 기관 코드 */
    @Schema(description = "소속 기관 코드")
    private String organizationCode;

    /** 소속 기관 명 */
    @Schema(description = "소속 기관 명")
    private String organizationName;

    /** 소속 기관 대표 명 */
    @Schema(description = "소속 기관 대표 명")
    private String organizationRepresentativeName;

    /** 소속 기관 서브 기관 명 */
    @Schema(description = "소속 기관 서브 기관 명")
    private String organizationSubName;

    /** 소속 기관 사업자 등록번호 */
    @Schema(description = "소속 기관 사업자 등록번호")
    private String bizLicenseNumber;

    /** 기관 타입 (1: 매체사, 2: 기관, 3: 학교) */
    @Schema(description = "기관 타입 (1: 매체사, 2: 기관, 3: 학교)")
    private String organizationType;

    /** 소속 기관 우편번호 */
    @Schema(description = "소속 기관 우편번호")
    private String organizationZipCode;

    /** 소속 기관 주소1 */
    @Schema(description = "소속 기관 주소1")
    private String organizationAddress1;

    /** 소속 기관 주소2 */
    @Schema(description = "소속 기관 주소2")
    private String organizationAddress2;

    /** 소속 기관 연락처 */
    @Schema(description = "소속 기관 연락처")
    private String organizationTelNumber;

    /** 소속 기관 팩스 번호 */
    @Schema(description = "소속 기관 팩스 번호")
    private String organizationFaxNumber;

    /** 소속 기관 홈페이지 주소 */
    @Schema(description="홈페이지 주소", example="www.naver.com")
    private String organizationHomepage;
}