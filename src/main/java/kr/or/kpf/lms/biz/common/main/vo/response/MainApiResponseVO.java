package kr.or.kpf.lms.biz.common.main.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.common.main.dto.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(name="MainApiResponseVO", description="메인 통합 관련 응답 객체")
@Data
@Builder
@JsonPropertyOrder({"lectureList", "eLearningList", "elearningList", "allList", "bizList", "noticeList", "newsList", "calendarList", "communicationList"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MainApiResponseVO {
    /** 화상 + 집합 교육 */
    List<EducationPlanMainDTO> lectureList;
    /** 이러닝 교육 */
    List<EducationPlanMainDTO> eLearningList;
    /** 전체 */
    List<AllMainDTO> allList;
    /** 공모 사업 */
    List<BizMainDTO> bizList;
    /** 공지사항 */
    List<NoticeMainDTO> noticeList;
    /** 행사소개(보도자료) */
    List<NewsMainDTO> newsList;
    /** 캘린더 */
    List<CalendarMainDTO> calendarList;
    /** 참여/소통 */
    List<CommunicationMainDTO> communicationList;

    /** 배너 */
    List<BannerMainDTO> bannerList;

    /** 팝업 */
    List<PopupMainDTO> popupList;
}
