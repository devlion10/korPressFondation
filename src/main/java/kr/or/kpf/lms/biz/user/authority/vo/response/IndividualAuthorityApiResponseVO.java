package kr.or.kpf.lms.biz.user.authority.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.authority.vo.Antecedents;
import kr.or.kpf.lms.biz.user.authority.vo.Certificate;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import java.util.List;

@Schema(name="IndividualAuthorityApiResponseVO", description="사업 참여 권한 신청(개인) API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class IndividualAuthorityApiResponseVO extends CSResponseVOSupport {

    @Schema(description="로그인 아이디", example="")
    private String userId;

    @Schema(description="사업 참여 권한", example="INSTR")
    private String businessAuthority;

    @Schema(description="권한 승인 상태", example="1")
    private String businessAuthorityApprovalState;

    @Schema(description="강의 가능 지역", example="")
    private List<String> region;

    @Schema(description="졸업년도", example="")
    private String graduationYear;

    @Schema(description="학교명", example="")
    private String graduationSchool;

    @Schema(description="전공", example="")
    private String major;

    @Schema(description="학위", example="")
    private String degree;

    @Schema(description="강의 주요 내용", example="")
    private String comments;

    @Schema(description="주요 이력", example="")
    private List<Antecedents> antecedents;

    @Schema(description="자격증", example="")
    private List<Certificate> certificate;

    @Schema(description="은행", example="")
    private String bank;

    @Schema(description="계좌번호", example="")
    private String accountNo;
}
