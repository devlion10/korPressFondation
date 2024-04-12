package kr.or.kpf.lms.biz.mypage.businessstate.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import java.util.Date;

/**
 마이페이지 - 진행중 사업공고 - 상호평가 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyBusinessStateSurveyViewRequestVO extends CSViewVOSupport {

    /** 사업 공고 신청 일련번호 */
    private String bizOrgAplyNo;

}
