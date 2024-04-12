package kr.or.kpf.lms.biz.education.schedule.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSViewVOSupport;
import lombok.*;

@Schema(name="ScheduleViewRequestVO", description="교육 일정 View 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleViewRequestVO extends CSViewVOSupport {

    /** 검색 연월 (YYYY-MM) */
    private String yearAndMonth;
}
