package kr.or.kpf.lms.biz.mypage.instructorstate.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.repository.entity.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 마이페이지 - 완료된 사업공고 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyInstructorStateCompleteCustomApiResponseVO extends CSViewVOSupport {

    @Schema(description="강사 배정 일련번호", required = false, example="1")
    private String bizInstrAsgnmNo;

    @Schema(description="강사 모집 일련번호", required = true, example="ISR0000001")
    private String bizInstrNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="ISR0000001")
    private String bizOrgAplyNo;

    @Schema(description="사업 공고 신청 수업 계획서 일련번호", required = false, example="홍길동")
    private String bizOrgAplyDtlNo;

    @Schema(description="강사 모집 공고 신청 정보 일련번호", required = true, example="abc")
    private String bizInstrAplyNo;

    @Transient
    private BizOrganizationAply bizOrganizationAply;

    @Transient
    private BizInstructorPbanc bizInstructorPbanc;

    @Transient
    private BizInstructorAply bizInstructorAply;

    @Transient
    private List<BizInstructorIdentify> bizInstructorIdentifies;

    @Transient
    private BizSurvey bizSurvey;

    @Transient
    private List<BizSurveyAns> bizSurveyAns;

    @Transient
    private LmsUser lmsUser;

    @Transient
    private InstructorInfo instructorInfo;
}
