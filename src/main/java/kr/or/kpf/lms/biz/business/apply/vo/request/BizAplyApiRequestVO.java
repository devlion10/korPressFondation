package kr.or.kpf.lms.biz.business.apply.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.apply.controller.BizAplyApiController;
import kr.or.kpf.lms.repository.entity.BizAplyDtl;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigInteger;
import java.util.List;

/**
 사업 공고 신청 - 언론인 - 언론인 요청 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizAplyApiRequestVO {

    @Schema(description="사업 공고 신청 - 언론인 일련 번호", required = true, example="1")
    private BigInteger sequenceNo;

    @Schema(description="사업 공고 일련 번호", required = true, example="PAC000001")
    @NotEmpty(groups={BizAplyApiController.CreateBizAply.class}, message="사업 공고 일련 번호는 필수 입니다.")
    private String bizPbancNo;

    @Schema(description="사업 공고 신청 - 언론인/기본형 타입(journalist: 언론인(개인), general: 기본형(단체))", required = true, example="PAC000001")
    @NotEmpty(groups={BizAplyApiController.CreateBizAply.class}, message="사업 공고 신청 - 언론인/기본형 타입은 필수 입니다.")
    private String bizAplyType;

    @Schema(description="사업 공고 신청 - 언론인/기본형 첨부파일 경로", example="/Public/a/b/c.txt")
    private String bizAplyFile;

    @Schema(description="사업 공고 신청 - 언론인/기본형 첨부파일 사이즈", example="10")
    private Long bizAplyFileSize;

    @Schema(description="사업 공고 신청 - 언론인/기본형 신청자 아이디", example="10")
    private String bizAplyUserID;


    @Schema(description="사업신청 기관이름", example="10")
    private String orgName;

    @Schema(description="기관코드", example="10")
    private String orgCd;

    /** 사업 공고 신청 담당자 이름 */
    @Schema(description="사업 공고 신청 담당자 이름", example="10")
    private String bizAplyUserNm;

    /** 사업 공고 신청 담당자 부서 */
    @Schema(description="사업 공고 신청 담당자 부서", example="10")
    private String bizAplyUserDptm;

    /** 사업 공고 신청 담당자 직위 */
    @Schema(description="사업 공고 신청 담당자 직위", example="10")
    private String bizAplyUserJbgd;

    /** 사업 공고 신청 담당자 이메일 */
    @Schema(description="사업 공고 신청 담당자 이메일", example="10")
    private String bizAplyUserEml;

    /** 사업 공고 신청 담당자 번호 */
    @Schema(description="사업 공고 신청 담당자 번호", example="10")
    private String bizAplyUserTelno;

    /** 신청서 상태(1: 신청, 3: 반려, 5: 가승인, 7: 승인, 9: 종료) */
    @Schema(description="신청서 상태", example="10")
    private Integer bizAplyStts;

    private List<BizAplyDtl> bizAplyDtl;
}
