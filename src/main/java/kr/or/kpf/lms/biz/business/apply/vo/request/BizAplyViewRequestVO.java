package kr.or.kpf.lms.biz.business.apply.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

/**
 사업 공고 신청 - 언론인 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizAplyViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 템플릿 유형 */
    private Integer bizPbancType;

    /** 사업 공고 연도 */
    private Integer bizPbancYr;

    /** 사업 공고 차수 */
    private Integer bizPbancRnd;

    /** 사업 신청 타입 */
    private String bizAplyType;

    /** 사업 공고 신청 - 등록자명 / 기관명 */
    private String name;

}
