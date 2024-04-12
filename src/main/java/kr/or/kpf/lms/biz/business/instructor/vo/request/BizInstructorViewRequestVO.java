package kr.or.kpf.lms.biz.business.instructor.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 강사 모집 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 강사 모집 - 공고 일련번호 */
    private Long sequenceNo;

    /** 기관 신청 일련번호 */
    private String bizOrgAplyNo;

    /** 기관 지역 */
    private String bizOrgAplyRgn;

    /** 강사 모집 일련 번호 */
    private String bizInstrNo;

    /** 강사 모집 상태 */
    private Integer bizInstrPbancStts;

    /** 강사 아이디 */
    private String userId;


    /** 리스트 표현 여부 */
    private String list;
}
