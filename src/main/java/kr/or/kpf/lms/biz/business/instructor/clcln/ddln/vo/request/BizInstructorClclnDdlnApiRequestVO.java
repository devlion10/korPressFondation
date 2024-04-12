package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.CreateBizInstructorClclnDdln;
import kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.UpdateBizInstructorClclnDdln;
import kr.or.kpf.lms.common.converter.DateHMSToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 정산 마감일 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorClclnDdlnApiRequestVO {
    @Schema(description="정산 마감일 일련번호", required = false, example="1")
    private String bizInstrClclnDdlnNo;

    @Schema(description="정산 마감일 해당 연월 - 연", required = true, example="2022")
    @NotNull(groups={CreateBizInstructorClclnDdln.class, UpdateBizInstructorClclnDdln.class}, message="정산 마감일 해당 연월 - 연은 필수 입니다.")
    private Integer bizInstrClclnDdlnYr;

    @Schema(description="정산 마감일 해당 연월 - 월", required = true, example="12")
    @NotEmpty(groups={CreateBizInstructorClclnDdln.class, UpdateBizInstructorClclnDdln.class}, message="정산 마감일 해당 연월 - 월은 필수 입니다.")
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizInstrClclnDdlnMm;

    @Schema(description="정산 마감일 연월일", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorClclnDdln.class, UpdateBizInstructorClclnDdln.class}, message="정산 마감일 연월일은 필수 입니다.")
    @Convert(converter= DateHMSToStringConverter.class)
    private String bizInstrClclnDdlnValue;

    @Schema(description="정산 마감일 시각", required = true, example="11:00")
    @NotEmpty(groups={CreateBizInstructorClclnDdln.class, UpdateBizInstructorClclnDdln.class}, message="정산 마감일 시각은 필수 입니다.")
    private String bizInstrClclnDdlnTm;



}
