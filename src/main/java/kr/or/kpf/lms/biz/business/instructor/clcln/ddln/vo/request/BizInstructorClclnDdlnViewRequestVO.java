package kr.or.kpf.lms.biz.business.instructor.clcln.ddln.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 정산 마감일 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorClclnDdlnViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 정산 마감일 일련번호 */
    private String bizInstrClclnDdlnNo;

    /** 정산 마감일 해당 연월 - 연 */
    private Integer bizInstrClclnDdlnYr;

    /** 정산 마감일 해당 연월 - 월 */
    private Integer bizInstrClclnDdlnMm;
}
