package kr.or.kpf.lms.biz.business.instructor.aply.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.CreateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.UpdateBizInstructorAply;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.OrganizationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 강사 모집 신청 관련 응답 객체
 */
@Schema(name="BizInstructorAplyApiResponseVO", description="강사 모집 신청 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorAplyCustomApiResponseVO extends CSResponseVOSupport {

    @Schema(description="강사 모집 신청 일련번호", example="1")
    private String bizInstrAplyNo;

    @Schema(description="강사 모집 일련번호", required = true, example="ISR0000001")
    private String bizInstrNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="ISR0000001")
    private String bizOrgAplyNo;

    @Schema(description="강사 모집 공고 신청 강사명", required = false, example="홍길동")
    private String bizInstrAplyInstrNm;

    @Schema(description="강사 모집 공고 신청 아이디", required = true, example="abc")
    private String bizInstrAplyInstrId;

    @Schema(description="강사 모집 공고 신청 강사 지원 조건 - 순위", required = false, example="1")
    private Integer bizInstrAplyCndtOrdr;

    @Schema(description="강사 모집 공고 신청 강사 지원 조건 - 거리", required = false, example="42.195")
    private float bizInstrAplyCndtDist;

    @Schema(description="강사 모집 공고 신청 강사 배정 점수(선정 점수)", required = false, example="10")
    private Integer bizInstrAplySlctnScr;

    @Schema(description="강사 모집 공고 신청 강사 배정 조건(선정 조건)", required = false, example="5")
    private Integer bizInstrAplySlctnCndt;

    @Schema(description="강사 모집 공고 신청 강사 코멘트", required = false, example="memo")
    private String bizInstrAplyCmnt;

    @Schema(description="강사 모집 공고 신청 강사 정보 상태 0: 임시저장, 1: 접수미승인, 2: 승인, 3: 반려 ", required = true, example="1")
    private Integer bizInstrAplyStts;

    @Transient
    private OrganizationInfo organization;
}