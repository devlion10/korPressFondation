package kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 강의확인서 강의시간표 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorIdentifyDtlViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 강의확인서 - 강의시간표 일련번호 */
    private String bizInstrIdntyDtlNo;

    /** 강의확인서 일련번호 */
    private String bizInstrIdntyNo;

    /** 화상강의 포함 유무 */
    private Integer vdoLctYn;
}
