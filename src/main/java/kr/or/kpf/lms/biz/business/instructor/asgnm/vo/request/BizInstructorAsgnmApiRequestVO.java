package kr.or.kpf.lms.biz.business.instructor.asgnm.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.CreateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.aply.vo.UpdateBizInstructorAply;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.CreateBizInstructorAsgnm;
import kr.or.kpf.lms.biz.business.instructor.asgnm.vo.UpdateBizInstructorAsgnm;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 강사 모집 공고 강사 배정 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorAsgnmApiRequestVO {
    @Schema(description="강사 모집 공고 강사 배정 정보 일련번호", required = false, example="1")
    private String bizInstrAsgnmNo;

    @Schema(description="강사 모집 공고 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorAsgnm.class, UpdateBizInstructorAsgnm.class}, message="강사 모집 공고 일련번호은 필수 입니다.")
    private String bizInstrNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorAsgnm.class, UpdateBizInstructorAsgnm.class}, message="강사 모집 공고 신청 내용은 필수 입니다.")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 신청 수업 계획서 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorAsgnm.class, UpdateBizInstructorAsgnm.class}, message="강사 모집 공고 신청 내용은 필수 입니다.")
    private String bizOrgAplyDtlNo;

    @Schema(description="강사 모집 공고 신청 정보 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorAsgnm.class, UpdateBizInstructorAsgnm.class}, message="강사 모집 공고 신청 내용은 필수 입니다.")
    private String bizInstrAplyNo;



}
