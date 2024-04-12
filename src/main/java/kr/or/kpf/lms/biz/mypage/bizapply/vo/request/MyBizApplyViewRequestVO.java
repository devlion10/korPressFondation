package kr.or.kpf.lms.biz.mypage.bizapply.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import java.math.BigInteger;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyBizApplyViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;


    /** 신청자 아이디 */
    private String userId;

    /** 사업신청 연도 */
    private Integer year;

    /** 사업신청 유형 */
    private String bizAplyType;

    /** 사업신청 상태 */
    private Integer bizAplyStts;

    /** 사업신청서 번호 */
    private BigInteger sequenceNo;

}
