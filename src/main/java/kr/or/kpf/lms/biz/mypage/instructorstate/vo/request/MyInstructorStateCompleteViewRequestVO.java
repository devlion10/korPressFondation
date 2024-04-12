package kr.or.kpf.lms.biz.mypage.instructorstate.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import java.util.Date;

/**
 강사 참여 현황 - 강의 현황 - 완료 사업 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyInstructorStateCompleteViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 공고 사업 년도 */
    private Integer bizPbancYr;

    /** 사업 신청 지역 */
    private String bizOrgAplyRgn;

    /** 사용자 id */
    private String userId;

    /** 강의확인서 상태 */
    private String bizInstrIdntyStts;

    /** 강사 배정 목록 */
    private String[] bizInstructorAsgnmNos;
}
