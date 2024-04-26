package kr.or.kpf.lms.biz.mypage.instructorstate.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 마이페이지 - 완료된 사업공고 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyInstructorStateApplyViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 강사 모집 신청 일련 번호 */
    private String bizInstrAplyNo;

    /** 강사 id */
    private String bizInstrAplyInstrId;

    /** 공고 마감상태 */
    private Integer bizInstrPbancStts;
}
