package kr.or.kpf.lms.biz.communication.event.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.event.vo.CreateEvent;
import kr.or.kpf.lms.biz.communication.event.vo.UpdateEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="EventApiRequestVO", description="이벤트 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventApiRequestVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호", required=true, example="")
    @NotNull(groups={CreateEvent.class, UpdateEvent.class}, message="시퀀스 번호는 필수 입니다.")
    private BigInteger sequenceNo;

    /** 타입(1: 이벤트, 2: 설문) */
    @Schema(description="타입(1: 이벤트, 2: 설문)", required=false, example="")
    private String type;

    /** 제목 */
    @Schema(description="제목", required=true, example="")
    @NotEmpty(groups={CreateEvent.class, UpdateEvent.class}, message="제목은 필수 입니다.")
    private String title;

    /** 내용 */
    @Schema(description="내용", required=true, example="")
    @NotEmpty(groups={CreateEvent.class, UpdateEvent.class}, message="내용은 필수 입니다.")
    private String contents;

    /** 썸네일 파일 경로 */
    @Schema(description="썸네일 파일 경로", required=false, example="")
    private String thumbFilePath;

    /** 시작 일자 */
    @Schema(description="시작 일자", required=false, example="")
    private String startDT;

    /** 종료 일자 */
    @Schema(description="종료 일자", required=false, example="")
    private String endDT;

    /** 상태 (0:종료, 1:진행) */
    @Schema(description="상태 (0:종료, 1:진행)", required=false, example="")
    private String status;
}
