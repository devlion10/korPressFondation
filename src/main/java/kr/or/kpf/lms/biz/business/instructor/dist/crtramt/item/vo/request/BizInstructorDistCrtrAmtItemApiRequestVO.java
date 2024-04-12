package kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.CreateBizInstructorDistCrtrAmtItem;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.UpdateBizInstructorDistCrtrAmtItem;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.CreateBizInstructorDistCrtrAmt;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.UpdateBizInstructorDistCrtrAmt;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 이동거리 기준단가 항목 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorDistCrtrAmtItemApiRequestVO {
    @Schema(description="이동거리 기준단가 항목 일련번호", required = true, example="1")
    private String bizInstrDistCrtrAmtItemNo;

    @Schema(description="이동거리 기준단가 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructorDistCrtrAmtItem.class, UpdateBizInstructorDistCrtrAmtItem.class}, message="사업 공고 일련번호은 필수 입니다.")
    private String bizInstrDistCrtrAmtNo;

    @Schema(description="이동거리 기준단가 항목 최대값", required = true, example="1")
    @NotNull(groups={CreateBizInstructorDistCrtrAmtItem.class, UpdateBizInstructorDistCrtrAmtItem.class}, message="강사 일련번호은 필수 입니다.")
    private Integer bizInstrDistCrtrAmtItemUp;

    @Schema(description="이동거리 기준단가 항목 최소값", required = true, example="1")
    @NotNull(groups={CreateBizInstructorDistCrtrAmtItem.class, UpdateBizInstructorDistCrtrAmtItem.class}, message="기관 코드은 필수 입니다.")
    private Integer bizInstrDistCrtrAmtItemLowr;

    @Schema(description="이동거리 기준단가 항목 배율", required = true, example="1")
    @NotNull(groups={CreateBizInstructorDistCrtrAmtItem.class, UpdateBizInstructorDistCrtrAmtItem.class}, message="기관 코드은 필수 입니다.")
    private Double bizInstrDistCrtrAmtItemRate;

    @Schema(description="이동거리 기준단가 가격", required = true, example="1")
    @NotNull(groups={CreateBizInstructorDistCrtrAmtItem.class, UpdateBizInstructorDistCrtrAmtItem.class}, message="기관 코드은 필수 입니다.")
    private Integer bizInstrDistCrtrAmtItemValue;


}
