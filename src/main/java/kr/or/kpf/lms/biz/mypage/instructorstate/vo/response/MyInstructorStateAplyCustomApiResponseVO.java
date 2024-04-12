package kr.or.kpf.lms.biz.mypage.instructorstate.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.repository.entity.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 강사 모집 공고 신청 관련 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyInstructorStateAplyCustomApiResponseVO {
    @Schema(description="강사 모집 신청 일련번호", required = false, example="1")
    private String bizInstrAplyNo;

    @Schema(description="강사 모집 일련번호", required = true, example="ISR0000001")
    private String bizInstrNo;

    @Schema(description="사업 공고 신청 일련번호", required = true, example="ISR0000001")
    private String bizOrgAplyNo;

    @Schema(description="강사 모집 공고 신청 강사명", required = false, example="홍길동")
    private String bizInstrAplyInstrNm;

    @Schema(description="강사 모집 공고 신청 아이디", required = true, example="abc")
    private String bizInstrAplyInstrId;

    @Schema(description="강사 모집 공고 신청 강사 지원 조건 - 순위", required = false, example="1")
    private Integer bizInstrAplyCndtOrdr;

    @Schema(description="강사 모집 공고 신청 강사 지원 조건 - 거리", required = false, example="42.195")
    private float bizInstrAplyCndtDist;

    @Schema(description="강사 모집 공고 신청 강사 배정 점수(선정 점수)", required = false, example="10")
    private Integer bizInstrAplySlctnScr;

    @Schema(description="강사 모집 공고 신청 강사 배정 조건(선정 조건)", required = false, example="5")
    private Integer bizInstrAplySlctnCndt;

    @Schema(description="강사 모집 공고 신청 강사 지정 우선순위", required = false, example="3")
    private Integer bizInstrAplyProtOrdr;

    @Schema(description="강사 모집 공고 신청 강사 코멘트", required = false, example="memo")
    private String bizInstrAplyCmnt;

    @Schema(description="강사 모집 공고 신청 강사 정보 상태 0: 임시저장, 1: 접수, 2: 미승인, 3: 승인", required = true, example="1")
    private Integer bizInstrAplyStts;

    @Schema(description="등록자 유저 ID", required = true, example="1")
    private String registUserId;

    @Transient
    private BizInstructor bizInstructor;

    @Transient
    private BizOrganizationAply bizOrganizationAply;

    @Transient
    private BizPbancMaster bizPbancMaster;

    @Transient
    private BizInstructorDist bizInstructorDist;

    @Transient
    private BizInstructorPbanc bizInstructorPbanc;

    @Transient
    private String bizInstructorQnaStts;

}
