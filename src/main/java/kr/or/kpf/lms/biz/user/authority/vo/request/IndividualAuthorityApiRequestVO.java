package kr.or.kpf.lms.biz.user.authority.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.authority.controller.AuthorityApiController;
import kr.or.kpf.lms.biz.user.authority.vo.Antecedents;
import kr.or.kpf.lms.biz.user.authority.vo.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Schema(name="IndividualAuthorityApiRequestVO", description="사업 참여 권한 신청(개인) API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndividualAuthorityApiRequestVO {

    @Schema(description="로그인 아이디", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="로그인 아이디는 필수 입니다.")
    private String userId;

    @Schema(description="사업 참여 권한", required = true, example="INSTR")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="사업 참여 권한은 필수 입니다.")
    private String bizAuthority;

    /** 집 우편번호 */
    @Schema(description="집 우편번호", required = true, example="")
    private String userZipCode;

    /** 집 주소 */
    @Schema(description="집 주소", required = true, example="")
    private String userAddress1;

    /** 집 주소 상세 */
    @Schema(description="집 주소 상세", required = true, example="")
    private String userAddress2;

    @JsonIgnore
    @Schema(description="사진파일", hidden = true)
    private MultipartFile pictureFile;

    @JsonIgnore
    @Schema(description="서명/도장 파일", hidden = true)
    private MultipartFile signFile;

    @Schema(description="강의 가능 지역", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="강의 가능 지역은 필수 입니다.")
    private List<String> region;

    @Schema(description="졸업년도", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="졸업년도은 필수 입니다.")
    private String graduationYear;

    @Schema(description="학교명", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="학교명은 필수 입니다.")
    private String graduationSchool;

    @Schema(description="전공", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="전공은 필수 입니다.")
    private String major;

    @Schema(description="학위", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="학위은 필수 입니다.")
    private String degree;

    @Schema(description="강의 주요 내용", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="강의 주요 내용은 필수 입니다.")
    private String comments;

    @Schema(description="주요 이력", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="주요 이력은 필수 입니다.")
    private List<Antecedents> antecedents;

    @Schema(description="자격증", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="자격증은 필수 입니다.")
    private List<Certificate> certificate;

    @Schema(description="은행", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="은행은 필수 입니다.")
    private String bank;

    @Schema(description="계좌번호", required = true, example="")
    @NotEmpty(groups={AuthorityApiController.CreateIndividualAuthority.class}, message="계좌번호은 필수 입니다.")
    private String accountNo;

    @Schema(description="사업 참여 권한 신청 상태", required = false, example="")
    private String bizAuthorityApprovalState;
}
