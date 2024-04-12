package kr.or.kpf.lms.biz.business.instructor.dist.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.CreateBizInstructorDist;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.UpdateBizInstructorDist;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 강사 거리 증빙 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorDistApiRequestVO {
    @Schema(description="거리 증빙 일련번호", required = true, example="1")
    private String bizInstrDistNo;

    @Schema(description="강사 모집 공고 신청 정보 일련 번호", required = true, example="일반")
    @NotEmpty(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="강사 모집 공고 신청 정보 일련 번호는 필수 입니다.")
    private String bizInstrAplyNo;

    @Schema(description="사업 공고 신청 정보 일련 번호", required = true, example="일반")
    @NotEmpty(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="사업 공고 신청 정보 일련 번호는 필수 입니다.")
    private String bizOrgAplyNo;

    @Schema(description="거리 증빙 출발지명", required = true, example="경기")
    @NotEmpty(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="거리 증빙 출발지명은 필수 입니다.")
    private String bizDistBgngNm;

    @Schema(description="거리 증빙 출발지 주소", required = true, example="2022-12-01")
    @NotEmpty(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="거리 증빙 출발지 주소는 필수 입니다.")
    private String bizDistBgngAddr;

    @Schema(description="거리 증빙 도착지명", required = true, example="2022-12-01")
    @NotEmpty(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="거리 증빙 도착지명은 필수 입니다.")
    private String bizDistEndNm;

    @Schema(description="거리 증빙 도착지 주소", required = true, example="3")
    @NotEmpty(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="거리 증빙 도착지 주소은 필수 입니다.")
    private String bizDistEndAddr;

    @Schema(description="거리 증빙 상태", required = true, example="0")
    @NotNull(groups={CreateBizInstructorDist.class, UpdateBizInstructorDist.class}, message="거리 증빙 상태는 필수 입니다.")
    private Integer bizDistStts;

    @Schema(description="거리 증빙 거리", required = false, example="1")
    private Double bizDistValue;

    @Schema(description="거리 증빙 금액", required = false, example="1")
    private Integer bizDistAmt;

    @Schema(description="거리 증빙 지도 첨부 여부", required = false, example="1")
    private Integer bizDistMapYn;

    @Schema(description="거리 증빙 지도 첨부파일", required = false, example="1")
    private String bizDistMapFile;

    @Schema(description="거리 증빙 지도 첨부파일 사이즈", required = false, example="1")
    private Long bizDistMapFileSize;

}
