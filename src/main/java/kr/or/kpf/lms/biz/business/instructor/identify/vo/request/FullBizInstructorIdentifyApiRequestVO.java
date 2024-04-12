package kr.or.kpf.lms.biz.business.instructor.identify.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request.BizInstructorIdentifyDtlApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.CreateBizInstructorIdentify;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.UpdateBizInstructorIdentify;
import kr.or.kpf.lms.repository.entity.BizInstructorIdentifyDtl;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 강의확인서 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FullBizInstructorIdentifyApiRequestVO {
    @Schema(description="강의확인서 일련번호", required = false, example="1")
    private String bizInstrIdntyNo;

    @Schema(description="기관 신청 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorIdentify.class, UpdateBizInstructorIdentify.class}, message="기관 신청 일련번호은 필수 입니다.")
    private String bizOrgAplyNo;

    @Schema(description="강의확인서 강의시간표 -> 날짜 조회 방식 변경", required = true, example="202212")
    @NotNull(groups={CreateBizInstructorIdentify.class, UpdateBizInstructorIdentify.class}, message="강의확인서 강의시간표은 필수 입니다.")
    private String bizInstrIdntyYm;

    @Schema(description="강의확인서 상태(0: 미승인, 1: 승인(지출 접수), 2: 반려, 3: 지출 완료)", required = true, example="1")
    @NotNull(groups={CreateBizInstructorIdentify.class, UpdateBizInstructorIdentify.class}, message="강의확인서 상태는 필수 입니다.")
    private Integer bizInstrIdntyStts;

    @Schema(description="강의확인서 수업지도안", required = false, example="0")
    private String bizInstrIdntyLsnFile;

    @Schema(description="강의확인서 수업자료안", required = false, example="0")
    private String bizInstrIdntyAtchFile;

    @Schema(description="강의확인서 강의 시간", required = false, example="0")
    private String bizInstrIdntyTime;

    @Schema(description="강의확인서 승인 일시", required = false, example="0")
    private String bizInstrIdntyAprvDt;

    @Schema(description="강의확인서 최종 금액", required = false, example="0")
    private Integer bizInstrIdntyAmt;

    @Schema(description="강의확인서 세부 내용", required = false, example="0")
    private List<BizInstructorIdentifyDtlApiRequestVO> bizInstructorIdentifyDtlApiRequestVOS;
}
