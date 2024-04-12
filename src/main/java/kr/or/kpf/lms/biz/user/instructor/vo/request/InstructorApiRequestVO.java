package kr.or.kpf.lms.biz.user.instructor.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.user.instructor.controller.InstructorApiController;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.NotNull;

@Schema(name="InstructorApiRequestVO", description="강사 관리 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorApiRequestVO {

    @Schema(description="강사 정보 일련 번호", required = true, example="")
    @NotNull(groups={InstructorApiController.UpdateInstructor.class}, message="강사 정보 일련 번호는 필수 입니다.")
    private Long instrSerialNo;

    @Schema(description="웹 회원 아이디", required = false, example="")
    private String userId;

    @Schema(description="강사 유형", required = true, example="")
    private String instrCategory;

    @Schema(description="강사 명", required = true, example="")
    private String instrNm;

    @Schema(description="강사 생년월일", required = true, example="")
    @Convert(converter= DateYMDToStringConverter.class)
    private String instrBrdt;

    @Schema(description="성별 코드", required = false, example="")
    private String gender;

    @Schema(description="강사 연락처(핸드폰 번호)", required = true, example="")
    private String instrTel;

    @Schema(description="강사 이메일", required = true, example="")
    private String instrEml;

    @Schema(description="강사 상태 (0: 미사용, 1: 사용)", required = true, example="")
    private Integer instrStts;

    @Schema(description="강사 사진 파일 주소", required = true, example="")
    private String instrPctr;

    @Schema(description="강사 사진 파일 크기", required = true, example="")
    private Long instrPctrSize;

    @Schema(description="강사 서명/도장 파일 주소", required = true, example="")
    private String instrSgntr;

    @Schema(description="강사 서명/도장 파일 크기", required = true, example="")
    private Long instrSgntrSize;

    @Schema(description="강사 자택 우편번호", required = true, example="")
    private String instrZipCd;

    @Schema(description="강사 자택 주소1", required = true, example="")
    private String instrAddr1;

    @Schema(description="강사 자택 주소2", required = false, example="")
    private String instrAddr2;

    @Schema(description="강사 강의가능 지역1 (0: 선택 없음)", required = true, example="")
    private String instrLctRgn1;

    @Schema(description="강사 강의가능 지역2 (0: 선택 없음)", required = true, example="")
    private String instrLctRgn2;

    @Schema(description="강사 최종학력 연도", required = true, example="")
    private Integer instrAcbgYr;

    @Schema(description="강사 최종학력 학교명", required = true, example="")
    private String instrAcbgSchlNm;

    @Schema(description="강사 최종학력 전공", required = true, example="")
    private String instrAcbgMjr;

    @Schema(description="강사 최종학력 학위", required = true, example="")
    private String instrAcbgDgr;

    @Schema(description="강사 강의 주요 내용", required = true, example="")
    private String instrMainCn;

    @Schema(description="강사 강의분야 대분류", required = true, example="")
    private String instrRelmFrst;

    @Schema(description="강사 강의분야 소분류", required = true, example="")
    private String instrRelmLast;

    @Schema(description="강사 은행", required = true, example="")
    private String instrBank;

    @Schema(description="강사 계좌번호", required = true, example="")
    private String instrActno;

    @Schema(description="강사 소속명", required = false, example="")
    private String orgName;

    @Schema(description="강사 부서/직급", required = false, example="")
    private String department;
}
