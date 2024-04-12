package kr.or.kpf.lms.biz.business.instructor.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.vo.CreateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.UpdateBizInstructor;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 강사 모집 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorApiRequestVO {
    @Schema(description="강사 모집 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizInstructor.class, UpdateBizInstructor.class}, message="강사 모집 일련번호는 필수 입니다.")
    private String bizInstrNo;

    @Schema(description="강사 모집 공고명", required = true, example="강사 모집 공고1")
    @NotEmpty(groups={CreateBizInstructor.class, UpdateBizInstructor.class}, message="강사 모집 공고명은 필수 입니다.")
    private String bizInstrNm;

    @Schema(description="강사 모집 내용", required = true, example="강사 모집 내용 내용")
    @NotEmpty(groups={CreateBizInstructor.class, UpdateBizInstructor.class}, message="강사 모집 내용은 필수 입니다.")
    private String bizInstrCn;

    @Schema(description="최대 모집 참여 기관", required = true, example="3")
    @NotNull(groups={CreateBizInstructor.class, UpdateBizInstructor.class}, message="최대 모집 참여 기관은 필수 입니다.")
    private Integer bizInstrMaxInst;

    @Schema(description="강의확인서 리스트", required = false, example="1")
    private String bizInstrIdntyNo;

    @Schema(description="첨부파일", required = false, example="1")
    private String bizInstrFile;

    @Schema(description="첨부파일 설명", required = false, example="1")
    private String bizInstrFileDscr;

    @Schema(description="첨부파일 사이즈", required = false, example="1")
    private Long bizInstrFileSize;

    @Schema(description="강사 모집 상태", required = true, example="1")
    @NotNull(groups={CreateBizInstructor.class, UpdateBizInstructor.class}, message="강사 모집 상태는 필수 입니다.")
    private Integer bizInstrStts;

    @Schema(description="강사 모집 공고의 사업 공고 리스트", required = false, example="[PAC0000003]")
    private List<String> bizInstrPbancs;

}
