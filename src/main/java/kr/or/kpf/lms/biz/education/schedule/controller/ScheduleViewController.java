package kr.or.kpf.lms.biz.education.schedule.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.education.schedule.service.ScheduleService;
import kr.or.kpf.lms.biz.education.schedule.vo.request.ScheduleViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.config.AppConfig;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 교육 일정 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/education/schedule")
public class ScheduleViewController extends CSViewControllerSupport {

    private static final String EDUCATION_SCHEDULE = "views/education/";

    private final ScheduleService scheduleService;

    /**
     * 교육 신청 > 교육 일정 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"", "/", "/page"})
    public String getList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        try {
            modelSetting(model, Optional.ofNullable(CSSearchMap.of(request))
                    .map(searchMap -> scheduleService.getEducationSchedule((ScheduleViewRequestVO) params(ScheduleViewRequestVO.class, searchMap, pageable)))
                    .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("PROVINCE_CD"));
            return new StringBuilder(EDUCATION_SCHEDULE).append("educationSchedule").toString();
        } catch (KPFException e1) {
            model.addAttribute("errorMessage", e1.getMessage());
            return "views/error/errorForm";
        } catch (Exception e2) {
            model.addAttribute("errorMessage", "알 수 없는 에러가 발생하였습니다.");
            return "views/error/errorForm";
        }
    }

    /**
     * 집합 / 화상 교육 - 출석 QR 코드 생성
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path = { "/lecture/qr-code/{educationPlanCode}/{lectureCode}" })
    public Object getLectureQRCode(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                   @Parameter(description = "교육 계획 코드") @PathVariable(value = "educationPlanCode", required = true) String educationPlanCode,
                                   @Parameter(description = "강의 코드") @PathVariable(value = "lectureCode", required = true) String lectureCode) {
        int width = 1024;
        int height = 1024;
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(appConfig.getDomain() + appConfig.getQrCodePath() + "/" + educationPlanCode + "/" + lectureCode, BarcodeFormat.QR_CODE, width, height);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(matrix, "JPEG", out);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
