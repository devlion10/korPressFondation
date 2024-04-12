package kr.or.kpf.lms.biz.mypage.instructorstate.vo.request;

import io.swagger.annotations.ApiModelProperty;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import kr.or.kpf.lms.repository.entity.BizInstructorClclnDdln;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;

/**
 마이페이지 - 완료된 사업공고 객체
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class MyInstructorStateViewRequestVO extends CSViewVOSupport {

    /** 검색어 범위 */
    @ApiModelProperty(name="containTextType", value="검색어 범위", required=false, dataType="string", position=10, example = "")
    private String containTextType;

    /** 검색에 포함할 단어 */
    @ApiModelProperty(name="containText", value="검색에 포함할 단어", required=false, dataType="string", position=20, example = "")
    private String containText;

    /** 사업 신청 지역 */
    private String bizOrgAplyRgn;

    /** 강사 id */
    private String bizInstrAplyInstrId;

    private String bizInstrAplyNo;

    private String notBizInstrAplyNo;

    private String bizInstrIdntyNo;

    private String searchYm;

    private Integer bizInstrIdntyStts;

    private Boolean isWrite;

    private String year;

    private String month;

    /** 강사 모집 공고 신청 강사 정보 상태 0: 임시저장, 1: 접수미승인, 2: 승인, 3: 반려 */
    private Integer bizInstrAplyStts;

    /** 정산 마감일 객체 */
    private BizInstructorClclnDdln standClclnDdlnNearDate;

}
