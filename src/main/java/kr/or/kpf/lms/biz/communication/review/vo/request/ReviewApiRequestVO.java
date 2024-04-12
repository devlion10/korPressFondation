package kr.or.kpf.lms.biz.communication.review.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.review.controller.ReviewApiController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="ReviewApiRequestVO", description="교육 후기 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewApiRequestVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호", required=true, example="")
    @NotNull(groups={ReviewApiController.UpdateReview.class}, message="시퀀스 번호는 필수 입니다.")
    private BigInteger sequenceNo;

    /** 제목 */
    @Schema(description="제목", required=true, example="")
    @NotEmpty(groups={ReviewApiController.CreateReview.class}, message="제목은 필수 입니다.")
    private String title;

    /** 내용 */
    @Schema(description="내용", required=true, example="")
    @NotEmpty(groups={ReviewApiController.CreateReview.class}, message="내용은 필수 입니다.")
    private String contents;
}
