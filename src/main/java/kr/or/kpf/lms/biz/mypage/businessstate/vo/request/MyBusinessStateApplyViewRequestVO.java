package kr.or.kpf.lms.biz.mypage.businessstate.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import javax.persistence.Convert;
import java.util.Date;

/**
 마이페이지 - 완료된 사업공고 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyBusinessStateApplyViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 기관 코드 */
    private String organizationCode;

    /** 사업 공고 사업 년도 */
    private Integer bizPbancYr;

    /** 사업 공고 사업 상태 */
    private Integer bizPbancStts;

    /** 사업 공고 접수기간 시작일 */
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRcptBgng;

    /** 사업 공고 접수기간 종료일 */
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRcptEnd;

    /** 사업 공고 신청 일련번호 */
    private String bizOrgAplyNo;

    /** 사업 공고 신청 상태 0: 임시저장, 1: 신청, 2: 승인, 3: 반려, 7: 종료, 9: 가승인 */
    private Integer bizOrgAplyStts;

    /** 사업 공고 신청 교육기간 시작일 */
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizOrgAplyLsnPlanBgng;

    /** 작성자 id */
    private String userId;
}
