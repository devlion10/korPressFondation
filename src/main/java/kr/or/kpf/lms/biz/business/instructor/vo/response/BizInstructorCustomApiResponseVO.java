package kr.or.kpf.lms.biz.business.instructor.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.instructor.vo.CreateBizInstructor;
import kr.or.kpf.lms.biz.business.instructor.vo.UpdateBizInstructor;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.BizOrganizationAply;
import kr.or.kpf.lms.repository.entity.BizOrganizationAplyDtl;
import kr.or.kpf.lms.repository.entity.OrganizationAuthorityHistory;
import kr.or.kpf.lms.repository.entity.OrganizationInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 사업 공고 관련 응답 객체
 */
@Schema(name="BizPbancRsltApiResponseVO", description="사업공모 결과 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizInstructorCustomApiResponseVO extends CSResponseVOSupport {

    /** 일련 번호 */
    private Long sequenceNo;

    /** 강사 모집 일련번호 */
    private String bizInstrNo;

    /** 사업 공고 일련번호 */
    private String bizPbancNo;

    /** 강사 모집 기간 - 시작일 */
    private String bizInstrRcptBgng;

    /** 강사 모집 기간 - 종료일 */
    private String bizInstrRcptEnd;

    /** 강사 모집 상태 (0: 마감, 1: 모집, 2: 추가모집) */
    private Integer bizInstrPbancStts;

    /** 사업명 */
    private String bizPbancNm;

    /** 강사 모집 공고명 */
    private String bizInstrNm;

    /** 강사 모집 내용  */
    private String bizInstrCn;

    /** 최대 모집 참여 기관 */
    private Integer bizInstrMaxInst;

    /** 첨부파일 */
    private String bizInstrFile;

    /** 첨부파일 설명 */
    private String bizInstrFileDscr;

    /** 첨부파일 사이즈 */
    private Long bizInstrFileSize;

    /** 첨부파일 원본명 */
    private String bizInstrFileOrigin;

    /** 강사 모집 상태 */
    private Integer bizInstrStts;


    /** 사업 공고 신청 일련번호 */
    private String bizOrgAplyNo;

    /** 기관 일련번호  */
    private String orgCd;

    /** 사업공고 신청 기관 대표자명 */
    private String bizOrgAplyRprsvNm;

    /** 사업공고 신청 총 시간 */
    private double bizOrgAplyTime;

    /** 사업공고 신청 최초 총 시간 */
    private double bizOrgAplyTimeFrst;

    /** 사업 공고 신청 지역 */
    private String bizOrgAplyRgn;

    /** 사업 공고 신청 담당자 아이디 */
    private String bizOrgAplyPic;

    /** 사업 공고 신청 담당자 이름 */
    private String bizOrgAplyPicNm;

    /** 사업 공고 신청 담당자 직위 */
    private String bizOrgAplyPicJbgd;

    /** 사업 공고 신청 담당자 이메일 */
    private String bizOrgAplyPicEml;

    /** 사업 공고 신청 담당자 번호 */
    private String bizOrgAplyPicTelno;

    /** 사업 공고 신청 담당자 핸드폰번호 */
    private String bizOrgAplyPicMpno;

    /** 사업 공고 신청 접수 상태 0: 임시저장, 1: 신청, 2: 승인, 3: 반려, 7: 종료, 9: 가승인 */
    private Integer bizOrgAplyStts;

    /** 사업 공고 신청 접수 상태 변경 사유 */
    private String bizOrgAplySttsCmnt;

    /** 사업 공고 신청 변경 가능 여부 */
    private Integer bizOrgAplyChgYn;

    /** 운영 구분 */
    private String bizOrgAplyOperCtgr;

    /** 수업 형태(운영 방법) */
    private String bizOrgAplyOperMeth;

    /** 수업 계획 - 교육기간 - 시작일 */
    private String bizOrgAplyLsnPlanBgng;

    /** 수업 계획 - 교육기간 - 종료일 */
    private String bizOrgAplyLsnPlanEnd;

    /** 수업 대상 */
    private String bizOrgAplyLsnPlanTrgt;

    /** 회차당 교육 인원 */
    private Integer bizOrgAplyLsnPlanNope;

    /** 설명1(교육의 방향성 및 신청한 이유) */
    private String bizOrgAplyLsnPlanDscr1;

    /** 설명2(미디어교육 운영 현황) */
    private String bizOrgAplyLsnPlanDscr2;

    /** 설명3(향후 활용계획 및 수업을 통한 기대효과) */
    private String bizOrgAplyLsnPlanDscr3;

    /** 건의사항 - 희망강사 */
    private String bizOrgAplyLsnPlanEtcInstr;

    /** 기타 의견 */
    private String bizOrgAplyLsnPlanEtc;

    /** 신문 신청 여부(없음/0: N, 1: Y) */
    private Integer bizOrgAplyPeprYn;

    /** 사업 공고 신청 첨부파일 */
    private String bizOrgAplyFile;

    /** 등록 일시(강사 모집) */
    @Convert(converter= DateToStringConverter.class)
    private String createDateTime;

    /** 등록자 유저 ID(사업 신청) */
    private String registUserId;

    /** 신규 공지사항 여부 */
    @Schema(description="신규 여부")
    @Transient
    @Builder.Default
    private Boolean isNew = false;

    @Transient
    private OrganizationAuthorityHistory organization;

    private OrganizationInfo organizationInfo;

    @Transient
    private List<BizOrganizationAplyDtl> bizOrganizationAplyDtls;

    @Transient
    private String loginUserApplied;

    /** 지원배정방식 0승인, 1선착순, 9없음  */
    private Integer bizPbancInstrSlctnMeth;

    @Transient
    private BizOrganizationAply bizOrganizationAply;

}