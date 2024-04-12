package kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.CreateBizInstructorIdentifyDtl;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.UpdateBizInstructorIdentifyDtl;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.CreateBizInstructorIdentify;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.UpdateBizInstructorIdentify;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 강의확인서 강의시간표 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorIdentifyDtlApiRequestVO {
    @Schema(description="강의확인서 강의시간표 일련번호", required = false, example="1")
    private String bizInstrIdntyDtlNo;

    @Schema(description="강의확인서 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorIdentifyDtl.class, UpdateBizInstructorIdentifyDtl.class}, message="기관 신청 일련번호은 필수 입니다.")
    private String bizInstrIdntyNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorIdentifyDtl.class, UpdateBizInstructorIdentifyDtl.class}, message="강의확인서 강의시간표은 필수 입니다.")
    private String bizOrgAplyDtlNo;

    @Schema(description="강의확인서 강의시간표 인원수", required = true, example="1")
    @NotNull(groups={CreateBizInstructorIdentifyDtl.class, UpdateBizInstructorIdentifyDtl.class}, message="강의확인서 강의시간표 인원수는 필수 입니다.")
    private Integer bizInstrIdntyDtlNope;

    @Schema(description="강의확인서 강의시간표 주제", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorIdentifyDtl.class, UpdateBizInstructorIdentifyDtl.class}, message="강의확인서 강의시간표 주제는 필수 입니다.")
    private String bizInstrIdntyDtlTpic;

    @Schema(description="강의확인서 강의시간표 내용", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorIdentifyDtl.class, UpdateBizInstructorIdentifyDtl.class}, message="강의확인서 강의시간표 내용은 필수 입니다.")
    private String bizInstrIdntyDtlCn;

    @Schema(description="화상강의 포함 유무", required = false, example="0")
    private Integer vdoLctYn;

    @Schema(description="강사료 거리반영 계산수식", required = false, example="1")
    private String bizInstrIdntyFormula;

    @Schema(description="강사료 강의일 중복", required = false, example="1")
    private String bizInstrIdntyEtc;


}
