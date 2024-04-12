package kr.or.kpf.lms.biz.business.apply.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * 사업 공고 신청 - 언론인 응답 객체
 */
@Schema(name="BizAplyApiResponseVO", description="사업 공고 신청 - 언론인 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizAplyApiResponseVO extends CSResponseVOSupport {

    @Schema(description="일련 번호", example="1")
    private BigInteger sequenceNo;
}