package kr.or.kpf.lms.biz.mypage.businessstate.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.List;

/**
 * 사업참여현황 진행중 사업 상호평가 응답 객체
 */
@Schema(name="MyBusinessStateSurveyApiResponseVO", description="사업 공고 신청 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBusinessStateSurveyApiResponseVO extends CSResponseVOSupport {

    @Schema(description="사업 공고 신청 일련번호", example="1")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 일련번호", example="1")
    private String bizPbancNo;

    @Schema(description="사업 공고명", example="1")
    private String bizPbancNm;

    @Schema(description="사업 공고 신청 교육 시작일", example="1")
    private String bizOrgAplyLsnPlanBgng;

    @Schema(description="사업 공고 신청 교육 종료일", example="1")
    private String bizOrgAplyLsnPlanEnd;

    @Transient
    private List<BizInstructorAsgnm> bizInstructorAsgnms;

}