package kr.or.kpf.lms.biz.education.schedule.service;

import kr.or.kpf.lms.biz.education.schedule.vo.request.ScheduleViewRequestVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonEducationRepository;
import kr.or.kpf.lms.repository.EducationPlanRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 교육 일정 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ScheduleService extends CSApiControllerSupport {

    /** 교육 신청 공통 Repository */
    private final CommonEducationRepository commonEducationRepository;

    private final EducationPlanRepository educationPlanRepository;

    /**
     * 교육 신청 > 교육 일정 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getEducationSchedule(ScheduleViewRequestVO requestObject) {
        if(StringUtils.isEmpty(requestObject.getYearAndMonth())) {
            /** 월 첫번째 일자(1일) */
            String startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
            /** 월 마지막 일자 */
            String endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString();

            requestObject.setStartDate(startDate);
            requestObject.setEndDate(endDate);
        } else {
            /** 요청 연월로 해당 월 첫번째 일자 계산 */
            LocalDate date = LocalDate.of(Integer.parseInt(requestObject.getYearAndMonth().split("-")[0]), Integer.parseInt(requestObject.getYearAndMonth().split("-")[1]), 1);
            /** 월 첫번째 일자(1일) */
            String startDate = date.toString();
            /** 월 마지막 일자 */
            String endDate = date.with(TemporalAdjusters.lastDayOfMonth()).toString();

            requestObject.setStartDate(startDate);
            requestObject.setEndDate(endDate);
        }
        return (Page<T>) Optional.ofNullable(commonEducationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    public static void main(String[] args) {
        String a = "2022-09";
        LocalDate date = LocalDate.of(Integer.parseInt(a.split("-")[0]), Integer.parseInt(a.split("-")[1]), 1);

        String startDate1 = date.toString();
        String endDate1 = date.plusDays(date.lengthOfMonth() - 1).toString();

        System.out.println("startDate1: " + startDate1);
        System.out.println("endDate1: " + endDate1);

        String currentDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();

        System.out.println("startDate2: " + LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString());
        System.out.println("endDate2: " + LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString());
    }
}
