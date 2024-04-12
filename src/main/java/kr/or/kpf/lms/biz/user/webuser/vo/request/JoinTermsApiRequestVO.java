package kr.or.kpf.lms.biz.user.webuser.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.webuser.controller.UserApiController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(name="JoinTermsApiRequestVO", description="약관동의 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JoinTermsApiRequestVO {

    @Schema(description="홈페이지 이용 약관", example="")
    @NotNull(groups={UserApiController.JoinTerms.class}, message="홈페이지 이용 약관은 필수 입니다.")
    private Boolean websiteTermsOfUse;

    @Schema(description="개인정보 수집, 이용, 제공 동의", example="")
    @NotNull(groups={UserApiController.JoinTerms.class}, message="개인정보 수집, 이용, 제공 동의는 필수 입니다.")
    private Boolean collectionOfPersonalInformation;

    @Schema(description="고유 식별 정보 처리 동의", example="")
    @NotNull(groups={UserApiController.JoinTerms.class}, message="고유 식별 정보 처리 동의는 필수 입니다.")
    private Boolean uniquelyIdentifiableInformation;

    @Schema(description="개인정보 제3자 제공", example="")
    @NotNull(groups={UserApiController.JoinTerms.class}, message="개인정보 제3자 제공은 필수 입니다.")
    private Boolean provisionOfPersonalInformation;
}
