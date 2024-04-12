package kr.or.kpf.lms.biz.business.instructor.aply.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.CreateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.UpdateBizInstructorAply;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 강사 모집 공고 신청 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorAplyApiRequestVO {
    @Schema(description="강사 모집 신청 일련번호", required = false, example="1")
    private String bizInstrAplyNo;

    @Schema(description="강사 모집 일련번호", required = true, example="ISR0000001")
    @NotEmpty(groups={CreateBizInstructorAply.class, UpdateBizInstructorAply.class}, message="강사 모집 공고 신청 내용은 필수 입니다.")
    private String bizInstrNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="BOA0000001")
    @NotEmpty(groups={CreateBizInstructorAply.class, UpdateBizInstructorAply.class}, message="강사 모집 공고 신청 내용은 필수 입니다.")
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

    @Schema(description="강사 모집 공고 신청 강사 지정 우선순위", required = false, example="3")
    private Integer bizInstrAplyProtOrdr;

    @Schema(description="강사 모집 공고 신청 강사 코멘트", required = false, example="memo")
    private String bizInstrAplyCmnt;

    @Schema(description="강사 모집 공고 신청 강사 정보 상태 0: 임시저장, 1: 접수미승인, 2: 승인, 3: 반려 ", required = true, example="1")
    @NotNull(groups={CreateBizInstructorAply.class, UpdateBizInstructorAply.class}, message="강사 모집 공고 신청 상태는 필수 입니다.")
    private Integer bizInstrAplyStts;

    @JsonIgnore
    private String LoginUserId;

    @JsonIgnore
    @Transient
    private String save;

}
