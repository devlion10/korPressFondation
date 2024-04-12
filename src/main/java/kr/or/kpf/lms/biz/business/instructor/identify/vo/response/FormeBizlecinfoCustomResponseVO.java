package kr.or.kpf.lms.biz.business.instructor.identify.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name="FormeBizlecinfoCustomResponseVO", description="강의확인서 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormeBizlecinfoCustomResponseVO extends CSResponseVOSupport {
    // 사업명
    private String bninTitle;

    // 기관이름
    private String bainInstNm;

    private String blciUserId;

    private Long blciId;

    private Long bninId;

    private Long bainId;

    private String bainEduEdate;

    private  String bainEduSdate;

    // 지역
    private String blciAreaNm;

    // 시간
    private Integer eduTime;

    // 연도
    private String bainYear;

    private String blciYymm;
}
