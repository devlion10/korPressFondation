package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateHMSToStringConverter;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Convert;

/**
 * 정산 마감일 관련 응답 객체
 */
@Schema(name="BizInstructorClclnDdlnApiResponseVO", description="정산 마감일 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorClclnDdlnApiResponseVO extends CSResponseVOSupport {

    @Schema(description="정산 마감일 일련번호", example="1")
    private String bizInstrClclnDdlnNo;

    @Schema(description="정산 마감일 해당 연월 - 연", example="2022")
    private Integer bizInstrClclnDdlnYr;

    @Schema(description="정산 마감일 해당 연월 - 월", example="12")
    private Integer bizInstrClclnDdlnMm;

    @Schema(description="정산 마감일 연월일", example="1")
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizInstrClclnDdlnValue;

    @Schema(description="정산 마감일 시각", example="11:00")
    @Convert(converter= DateHMSToStringConverter.class)
    private String bizInstrClclnDdlnTm;
    
    @Schema(description="등록 일시", example="")
    @Convert(converter= DateToStringConverter.class)
    private String createDateTime;
    
    @Schema(description="등록자 유저 ID", example="")
    private String registUserId;

    @Schema(description="수정 일시", example="")
    @Convert(converter= DateToStringConverter.class)
    private String updateDateTime;

    @Schema(description="수정자 유저 ID", example="")
    private String modifyUserId;
}