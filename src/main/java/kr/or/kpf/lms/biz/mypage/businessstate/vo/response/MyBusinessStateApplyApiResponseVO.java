package kr.or.kpf.lms.biz.mypage.businessstate.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;

/**
 * 마이페이지 - 사업 참여 현황 - 신청 사업 관련 응답 객체
 */
@Schema(name="MyBusinessStateApplyApiResponseVO", description="마이페이지 - 사업 참여 현황 - 신청 사업 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBusinessStateApplyApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 일련번호", example="1")
    private String bizPbancNo;

    @Schema(description="사업 구분", example="1")
    private Integer bizPbancCtgr;

    @Schema(description="사업명", example="1")
    private String bizPbancNm;
    @Schema(description="사업년도", example="1")
    private Integer bizPbancYr;

    @Schema(description="사업 상태", example="1")
    private Integer bizPbancStts;

    @Schema(description="사업 접수기간 - 시작일", example="1")
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRcptBgng;

    @Schema(description="사업 접수기간 - 종료일", example="1")
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRcptEnd;

    @Schema(description="사용자 명", example="홍길동")
    private String userName;

    @Schema(description="사용자 기관 코드", example="")
    private String organizationCode;

    @Convert(converter= DateToStringConverter.class)
    private String createDateTime;

    private String registUserId;

    @Convert(converter=DateToStringConverter.class)
    private String updateDateTime;

    private String modifyUserId;

    @Schema(description="사업 공고 대상 신청 공고", example="1")
    private BizOrganizationAply orgAplyEntity;
}