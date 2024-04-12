package kr.or.kpf.lms.biz.business.pbanc.master.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.*;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.CreateBizPbanc;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.UpdateBizPbanc;
import kr.or.kpf.lms.common.converter.DateYMDToStringConverter;
import kr.or.kpf.lms.repository.entity.*;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(name="BizPbancApiRequestVO", description="사업 공고 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizPbancApiRequestVO {
    @Schema(description="사업 공고 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 공고 일련번호는 필수 입니다.")
    private String bizPbancNo;

    @Schema(description="사업 공고 템플릿 유형", required = true, example="0")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 공고 템플릿 유형은 필수 입니다.")
    private Integer bizPbancType;

    @Schema(description="사업 구분", required = true, example="0")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 구분은 필수 입니다.")
    private Integer bizPbancCtgr;

    @Schema(description="기본형 View - 1: 사회미디어, 2: 학교미디어 (Main View Type)")
    private Integer bizPbancCtgrSub;

    @Schema(description="사업명", required = true, example="미디어교육 평생교실")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업명은 필수 입니다.")
    private String bizPbancNm;

    @Schema(description="최대 시간", required = true, example="1")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="최대 시간은 필수 입니다.")
    private Integer bizPbancMaxTm;

    @Schema(description="사업년도", required = true, example="2022")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업년도는 필수 입니다.")
    private Integer bizPbancYr;

    @Schema(description="최대 사업참여 기관", required = true, example="3")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="최대 사업참여 기관은 필수 입니다.")
    private Integer bizPbancMaxInst;

    @Schema(description="사업 선정 방식", required = true, example="승인")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 선정 방식은 필수 입니다.")
    private Integer bizPbancSlctnMeth;

    @Schema(description="강사 배정 방식", required = true, example="0")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="강사 배정 방식은 필수 입니다.")
    private Integer bizPbancInstrSlctnMeth;

    @Schema(description="사업 상태", required = true, example="개설")
    @NotNull(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 상태는 필수 입니다.")
    private Integer bizPbancStts;

    @Schema(description="사업 접수기간 - 시작일", required = true, example="2022-09-29")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 접수기간은 필수 입니다.")
    private String bizPbancRcptBgng;

    @Schema(description="사업 접수기간 - 종료일", required = true, example="2022-09-29")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 접수기간은 필수 입니다.")
    private String bizPbancRcptEnd;

    @Schema(description="사업 지원기간 - 시작일", required = true, example="2022-09-30")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 지원기간은 필수 입니다.")
    private String bizPbancSprtBgng;

    @Schema(description="사업 지원기간 - 종료일", required = true, example="2022-09-30")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 지원기간은 필수 입니다.")
    private String bizPbancSprtEnd;

    @Schema(description="사업공고 결과발표일(없을 시 미정)", required = false, example="2022-09-30")
    @Convert(converter= DateYMDToStringConverter.class)
    private String bizPbancRsltYmd;

    @Schema(description="사업 내용", required = true, example="사업 내용 입니다.")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="사업 내용은 필수 입니다.")
    private String bizPbancCn;

    @Schema(description="사업공고 신문 신청 가능 여부(없음/0: N, 1: Y)", required = false, example="사업공고 신문 신청 가능 여부 입니다.")
    private Integer bizPbancPeprYn;

    @Schema(description="사업 공고 담당자 연락처")
    @NotEmpty(groups={CreateBizPbanc.class, UpdateBizPbanc.class}, message="담당자 연락처는 필수 입니다.")
    private String bizPbancPicTel;

    @Schema(description="상위 노출 여부")
    private String isTop;

    private BizPbancTmpl0ApiRequestVO bizPbancTmpl0ApiRequestVO;
    private BizPbancTmpl1ApiRequestVO bizPbancTmpl1ApiRequestVO;
    private BizPbancTmpl2ApiRequestVO bizPbancTmpl2ApiRequestVO;
    private BizPbancTmpl3ApiRequestVO bizPbancTmpl3ApiRequestVO;
    private BizPbancTmpl4ApiRequestVO bizPbancTmpl4ApiRequestVO;

}
