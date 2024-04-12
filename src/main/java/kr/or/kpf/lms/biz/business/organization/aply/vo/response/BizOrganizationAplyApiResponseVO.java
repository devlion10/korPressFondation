package kr.or.kpf.lms.biz.business.organization.aply.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사업 공고 신청 응답 객체
 */
@Schema(name="BizOrganizationAplyApiResponseVO", description="사업 공고 신청 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizOrganizationAplyApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 신청 일련번호", example="1")
    private String bizOrgAplyNo;
}