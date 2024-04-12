package kr.or.kpf.lms.biz.business.instructor.identify.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 강의확인서 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorIdentifyViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 신청서 담당자 id */
    private String bizOrgAplyPic;

    /** 기관 코드 */
    private String orgCd;

    /** 기관 신청 일련번호 */
    private String bizOrgAplyNo;

    /** 강의확인서 일련번호 */
    private String bizInstrIdntyNo;

    /** 강의확인서 상태(0: 미승인, 1: 승인(지출 접수), 2: 반려, 3: 지출 완료) */
    private Integer bizInstrIdntyStts;

    /** 연도 */
    private String year;

    /** 월 */
    private String month;

    /** 작성자 id */
    private String registUserId;

    /** 강사 id */
    private String bizInstrAplyInstrId;
    private String userId;
}
