package kr.or.kpf.lms.biz.common.main.service;

import kr.or.kpf.lms.biz.common.main.dto.*;
import kr.or.kpf.lms.biz.common.main.vo.request.MainViewRequestVO;
import kr.or.kpf.lms.biz.common.main.vo.response.MainApiResponseVO;
import kr.or.kpf.lms.biz.servicecenter.notice.vo.request.NoticeViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.config.security.vo.RoleGroup;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 메인 통합 관련 Service
 */
@Service
@RequiredArgsConstructor
public class MainService extends CSServiceSupport {

    private final CommonEducationRepository commonEducationRepository;
    private final CommonServiceCenterRepository commonServiceCenterRepository;
    private final CommonBusinessRepository commonBusinessRepository;
    private final CommonCommunicationRepository commonCommunicationRepository;

    private final PressReleaseRepository pressReleaseRepository;
    private final EducationReviewRepository educationReviewRepository;
    private final EducationDataRepository educationDataRepository;
    private final EventRepository eventRepository;

    private final HomeBannerRepository homeBannerRepository;
    private final HomePopupRepository homePopupRepository;

    /**
     * 메인 통합
     *  
     * @param requestObject
     * @return
     */
    public MainApiResponseVO getMainSectionList(MainViewRequestVO requestObject) {
        String bizAuth = null;
        String roleGroup = RoleGroup.ANONYMOUS.getKey();
        try { bizAuth = authenticationInfo().getBusinessAuthority(); } catch (KPFException e) {/** 권한 획득 오류시 그대로 진행 */}
        try { roleGroup = authenticationInfo().getRoleGroup(); } catch (KPFException e) {/** 권한 획득 오류시 그대로 진행 */}

        /** 이러닝 교육 */
        requestObject.setEducationType(Code.EDU_TYPE.E_LEARNING.enumCode);
        List<EducationPlanMainDTO> eLearningList = (List<EducationPlanMainDTO>) commonEducationRepository.findEntityList(requestObject).getContent().stream()
                .limit(3)
                .collect(Collectors.toList());

        /** 화상 + 집합 교육 */
        requestObject.setEducationType("0");
        switch(requestObject.getInquiryCriteria()) {
            case "MISSION": default: /** 미션 */
                /** 사업 권한이 있는 경우 */
                if(Optional.ofNullable(bizAuth).isPresent()) {
                    requestObject.setEducationTarget(Arrays.stream(Code.EDU_TARGET.values())
                            .filter(data -> data != Code.EDU_TARGET.JOURNALIST)
                            .map(data -> data.enumCode)
                            .collect(Collectors.toList()));
                } else {
                    requestObject.setEducationTarget(null);
                }
                break;
            case "MEDIA":   /** 미디어 */
                requestObject.setEducationTarget(Arrays.stream(Code.EDU_TARGET.values())
                        .filter(data -> data != Code.EDU_TARGET.JOURNALIST)
                        .map(data -> data.enumCode)
                        .collect(Collectors.toList()));
                break;
            case "JOURNALIST":  /** 언론인 연수 */
                requestObject.setEducationTarget(Arrays.stream(Code.EDU_TARGET.values())
                        .filter(data -> data.equals(Code.EDU_TARGET.JOURNALIST))
                        .map(data -> data.enumCode)
                        .collect(Collectors.toList()));
                break;
        }

        List<EducationPlanMainDTO> lectureList = (List<EducationPlanMainDTO>) commonEducationRepository.findEntityList(requestObject).getContent().stream()
                .limit(3)
                .collect(Collectors.toList());

        /** 공지사항 */
        List<NoticeMainDTO> unionNoticeList = new ArrayList<>();
        NoticeViewRequestVO noticeViewRequestVO = NoticeViewRequestVO.builder().build();
        noticeViewRequestVO.setPageable(PageRequest.of(0, 20));

        noticeViewRequestVO.setNoticeType("0");
        List<NoticeMainDTO> noticeCommonList = commonServiceCenterRepository.findEntityList(noticeViewRequestVO).getContent()
                .stream().map(data -> {
                    NoticeMainDTO noticeMainDTO = NoticeMainDTO.builder().build();
                    BeanUtils.copyProperties(data, noticeMainDTO);
                    if ("0".equals(noticeMainDTO.getNoticeType())) noticeMainDTO.setNoticeTypeName("일반");
                    return noticeMainDTO;
                }).collect(Collectors.toList());
        unionNoticeList.addAll(noticeCommonList);

        noticeViewRequestVO.setNoticeType("1");
        List<NoticeMainDTO> noticeMediaList = commonServiceCenterRepository.findEntityList(noticeViewRequestVO).getContent()
                .stream().map(data -> {
                    NoticeMainDTO noticeMainDTO = NoticeMainDTO.builder().build();
                    BeanUtils.copyProperties(data, noticeMainDTO);
                    if ("1".equals(noticeMainDTO.getNoticeType())) noticeMainDTO.setNoticeTypeName("미디어교육");
                    return noticeMainDTO;
                }).collect(Collectors.toList());
        unionNoticeList.addAll(noticeMediaList);

        noticeViewRequestVO.setNoticeType("2");
        List<NoticeMainDTO> noticeJournalList = commonServiceCenterRepository.findEntityList(noticeViewRequestVO).getContent()
                .stream().map(data -> {
                    NoticeMainDTO noticeMainDTO = NoticeMainDTO.builder().build();
                    BeanUtils.copyProperties(data, noticeMainDTO);
                    if ("2".equals(noticeMainDTO.getNoticeType())) noticeMainDTO.setNoticeTypeName("언론인연수");
                    return noticeMainDTO;
                }).collect(Collectors.toList());
        unionNoticeList.addAll(noticeJournalList);


        /** Union 공모 사업 */
        List<BizMainDTO> bizMediaList = new ArrayList<>();
        List<BizMainDTO> unionBizList = new ArrayList<>();

        /** 공모 사업(사회 미디어) */
        requestObject.setBizPbancCtgr(0);
        requestObject.setBizPbancCtgrSub(1);
        List<BizMainDTO> socialMediaList =  commonBusinessRepository.findEntityList(requestObject).getContent()
                .stream().map(data -> {
                    BizMainDTO bizMainDTO = BizMainDTO.builder().build();
                    BeanUtils.copyProperties(data, bizMainDTO);
                    bizMainDTO.setCategoryName("사회 미디어");
                    return bizMainDTO;
                }).limit(4).collect(Collectors.toList());
        bizMediaList.addAll(socialMediaList);
        unionBizList.addAll(socialMediaList);

        /** 공모 사업(학교 미디어) */
        requestObject.setBizPbancCtgr(0);
        requestObject.setBizPbancCtgrSub(2);
        List<BizMainDTO> schoolMediaList =  commonBusinessRepository.findEntityList(requestObject).getContent()
                .stream().map(data -> {
                    BizMainDTO bizMainDTO = BizMainDTO.builder().build();
                    BeanUtils.copyProperties(data, bizMainDTO);
                    bizMainDTO.setCategoryName("학교 미디어");
                    return bizMainDTO;
                }).collect(Collectors.toList());
        bizMediaList.addAll(schoolMediaList);
        unionBizList.addAll(schoolMediaList);

        /** 공모 사업(언론인 연수) */
        requestObject.setBizPbancType(Arrays.asList(0,5));
        requestObject.setBizPbancCtgr(1);
        requestObject.setBizPbancCtgrSub(null);
        List<BizMainDTO> bizJournalList =  commonBusinessRepository.findEntityList(requestObject).getContent()
                .stream().map(data -> {
                    BizMainDTO bizMainDTO = BizMainDTO.builder().build();
                    BeanUtils.copyProperties(data, bizMainDTO);
                    bizMainDTO.setCategoryName("언론인 연수");
                    return bizMainDTO;
                }).collect(Collectors.toList());
        unionBizList.addAll(bizJournalList);

        /** 공모 사업(미디어 지원) */
        requestObject.setBizPbancType(Arrays.asList(5));
        requestObject.setBizPbancCtgr(2);
        List<BizMainDTO> supportList =  commonBusinessRepository.findEntityList(requestObject).getContent()
                .stream().map(data -> {
                    BizMainDTO bizMainDTO = BizMainDTO.builder().build();
                    BeanUtils.copyProperties(data, bizMainDTO);
                    bizMainDTO.setCategoryName("미디어 지원");
                    return bizMainDTO;
                }).limit(4).collect(Collectors.toList());
        unionBizList.addAll(supportList);

        switch(requestObject.getInquiryCriteria()) {
            case "MISSION": default: /** 미션 */
                return MainApiResponseVO.builder()
                        .lectureList(lectureList)
                        .eLearningList(eLearningList)
                        .allList(Stream.concat(unionBizList.stream(), unionNoticeList.stream())
                                        .map(data -> {
                                            AllMainDTO allMainDTO = AllMainDTO.builder().build();
                                            if (data instanceof BizMainDTO) {
                                                BeanUtils.copyProperties(data, allMainDTO);
                                                allMainDTO.setTitle(((BizMainDTO) data).getBizPbancNm());
                                                allMainDTO.setKeyValue(((BizMainDTO) data).getBizPbancNo());
                                            } else if (data instanceof NoticeMainDTO) {
                                                BeanUtils.copyProperties(data, allMainDTO);
                                                allMainDTO.setKeyValue(((NoticeMainDTO) data).getNoticeSerialNo());
                                            }
                                            return allMainDTO;
                                        }).collect(Collectors.toList()).stream()
                                .sorted(Comparator.comparing(AllMainDTO::getIsTop).thenComparing(AllMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .bizList(unionBizList.stream().sorted(Comparator.comparing(BizMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .noticeList(unionNoticeList.stream().sorted(Comparator.comparing(NoticeMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .build();
            case "MEDIA":   /** 미디어 */
                return MainApiResponseVO.builder()
                        .lectureList(lectureList)
                        .eLearningList(eLearningList)
                        .allList(Stream.concat(bizMediaList.stream(), noticeMediaList.stream())
                                .map(data -> {
                                    AllMainDTO allMainDTO = AllMainDTO.builder().build();
                                    if (data instanceof BizMainDTO) {
                                        BeanUtils.copyProperties(data, allMainDTO);
                                        allMainDTO.setTitle(((BizMainDTO) data).getBizPbancNm());
                                        allMainDTO.setKeyValue(((BizMainDTO) data).getBizPbancNo());
                                    } else if (data instanceof NoticeMainDTO) {
                                        BeanUtils.copyProperties(data, allMainDTO);
                                        allMainDTO.setKeyValue(((NoticeMainDTO) data).getNoticeSerialNo());
                                    }
                                    return allMainDTO;
                                }).collect(Collectors.toList()).stream()
                                .sorted(Comparator.comparing(AllMainDTO::getIsTop).thenComparing(AllMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .bizList(bizMediaList.stream().sorted(Comparator.comparing(BizMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .noticeList(noticeMediaList.stream().sorted(Comparator.comparing(NoticeMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .build();
            case "JOURNALIST":  /** 언론인 연수 */
                return MainApiResponseVO.builder()
                        .lectureList(lectureList)
                        .eLearningList(eLearningList)
                        .allList(Stream.concat(bizJournalList.stream(), noticeJournalList.stream())
                                .map(data -> {
                                    AllMainDTO allMainDTO = AllMainDTO.builder().build();
                                    if (data instanceof BizMainDTO) {
                                        BeanUtils.copyProperties(data, allMainDTO);
                                        allMainDTO.setTitle(((BizMainDTO) data).getBizPbancNm());
                                        allMainDTO.setKeyValue(((BizMainDTO) data).getBizPbancNo());
                                    } else if (data instanceof NoticeMainDTO) {
                                        BeanUtils.copyProperties(data, allMainDTO);
                                        allMainDTO.setKeyValue(((NoticeMainDTO) data).getNoticeSerialNo());
                                    }
                                    return allMainDTO;
                                }).collect(Collectors.toList()).stream()
                                .sorted(Comparator.comparing(AllMainDTO::getIsTop).thenComparing(AllMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .bizList(bizJournalList.stream().sorted(Comparator.comparing(BizMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .noticeList(noticeJournalList.stream().sorted(Comparator.comparing(NoticeMainDTO::getCreateDateTime).reversed())
                                .limit(4)
                                .collect(Collectors.toList()))
                        .build();
        }
    }

    /**
     * 메인 캘린더
     *  
     * @param requestObject
     * @return
     */
    public MainApiResponseVO getMainCalendar(MainViewRequestVO requestObject) {
        /** 교육 신청 일정 */
        requestObject.setEducationType("0");
        List<EducationPlanMainDTO> educationList = (List<EducationPlanMainDTO>) commonEducationRepository.findEntityList(requestObject).getContent().stream().collect(Collectors.toList());
        /** 공모 사업 */
        List<BizPbancMaster> bizList = (List<BizPbancMaster>) commonBusinessRepository.findEntityList(requestObject).getContent();

        return MainApiResponseVO.builder()
                .calendarList(Stream.concat(educationList.stream(), bizList.stream())
                        .map(data -> {
                            CalendarMainDTO calendarMainDTO = null;
                            if (data instanceof EducationPlanMainDTO) {
                                calendarMainDTO = CalendarMainDTO.builder()
                                        .keyValue(((EducationPlanMainDTO) data).getEducationPlanCode())
                                        .category("EDU")
                                        .type(((EducationPlanMainDTO) data).getEducationType())
                                        .title(((EducationPlanMainDTO) data).getCurriculumName())
                                        .applyBeginDateTime(((EducationPlanMainDTO) data).getApplyBeginDateTime())
                                        .applyEndDateTime(((EducationPlanMainDTO) data).getApplyEndDateTime())
                                        .contents(((EducationPlanMainDTO) data).getEducationContent())
                                        .build();
                            } else if (data instanceof BizPbancMaster) {
                                calendarMainDTO = CalendarMainDTO.builder()
                                        .keyValue(((BizPbancMaster) data).getBizPbancNo())
                                        .category("BIZ")
                                        .type(String.valueOf(((BizPbancMaster) data).getBizPbancType()))
                                        .title(((BizPbancMaster) data).getBizPbancNm())
                                        .applyBeginDateTime(((BizPbancMaster) data).getBizPbancRcptBgng())
                                        .applyEndDateTime(((BizPbancMaster) data).getBizPbancRcptEnd())
                                        .receiptBeginDate(((BizPbancMaster) data).getBizPbancSprtBgng())
                                        .receiptEndDate(((BizPbancMaster) data).getBizPbancSprtEnd())
                                        .contents(((BizPbancMaster) data).getBizPbancCn())
                                        .build();
                            }
                            return calendarMainDTO;
                        }).sorted(Comparator.comparing(CalendarMainDTO::getContents).reversed()).collect(Collectors.toList()))
                .build();
    }

    /**
     * 메인 참여/소통
     *
     * @param requestObject
     * @return
     */
    public MainApiResponseVO getMainCommunication(MainViewRequestVO requestObject) {

        /** 교육 후기 */
        List<EducationReview> reviewList = educationReviewRepository.findAll(Example.of(EducationReview.builder().build())).stream()
                .sorted(Comparator.comparing(EducationReview::getCreateDateTime).reversed()).limit(4)
                .collect(Collectors.toList());

        /** 자료실 - 수업지도안 */
        List<ClassGuide> classGuideList = (List<ClassGuide>) commonCommunicationRepository.findEntityList(requestObject).getContent();

        /** 자료실 - 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 */
        List<LmsData> lmsDataList = educationDataRepository.findAll(Example.of(LmsData.builder().build())).stream()
                .sorted(Comparator.comparing(LmsData::getCreateDateTime).reversed()).limit(4)
                .filter(lmsData -> !lmsData.getMaterialCategory().equals(Code.MTRL_CTGR.E.enumCode) && lmsData.getIsUse().equals(true)) // 미디어리터러시 메인 추가 임시 제한
                .collect(Collectors.toList());

        /** 이벤트/설문 */
        List<EventInfo> eventInfoList = eventRepository.findAll(Example.of(EventInfo.builder().build())).stream()
                .sorted(Comparator.comparing(EventInfo::getCreateDateTime).reversed()).limit(4)
                .collect(Collectors.toList());

        return MainApiResponseVO.builder()
                .communicationList(Stream.concat(Stream.concat(reviewList.stream(), classGuideList.stream())
                    .map(data -> {
                        if (data instanceof EducationReview) {
                            return CommunicationMainDTO.builder()
                                    .keyValue(((EducationReview) data).getSequenceNo().toString())
                                    .category("[교육후기방]")
                                    .title(((EducationReview) data).getTitle())
                                    .contents(((EducationReview) data).getContents())
                                    .createDateTime(((EducationReview) data).getCreateDateTime())
                                    .build();
                        } else if (data instanceof ClassGuide) {
                            return CommunicationMainDTO.builder()
                                    .keyValue(((ClassGuide) data).getClassGuideCode())
                                    .category("[자료실]")
                                    .type("9")
                                    .title(((ClassGuide) data).getTitle())
                                    .contents(((ClassGuide) data).getLearningGoal()) // 학습 목표
                                    .createDateTime(((ClassGuide) data).getCreateDateTime())
                                    .build();
                        } else {
                            return CommunicationMainDTO.builder().build();
                        }
                    }).collect(Collectors.toList()).stream().filter(data -> !StringUtils.isEmpty(data.getTitle())), Stream.concat(lmsDataList.stream(), eventInfoList.stream())
                    .map(data -> {
                        if (data instanceof LmsData) {
                            return CommunicationMainDTO.builder()
                                    .keyValue(((LmsData) data).getSequenceNo().toString())
                                    .category("[자료실]")
                                    .type(((LmsData) data).getMaterialCategory())
                                    .title(((LmsData) data).getTitle())
                                    .contents(((LmsData) data).getContents())
                                    .createDateTime(((LmsData) data).getCreateDateTime())
                                    .build();
                        } else if (data instanceof EventInfo) {
                            return CommunicationMainDTO.builder()
                                    .keyValue(((EventInfo) data).getSequenceNo().toString())
                                    .category("[이벤트/설문]")
                                    .title(((EventInfo) data).getTitle())
                                    .contents(((EventInfo) data).getContents())
                                    .createDateTime(((EventInfo) data).getCreateDateTime())
                                    .build();
                        } else {
                            return CommunicationMainDTO.builder().build();
                        }
                    }).collect(Collectors.toList()).stream().filter(data -> !StringUtils.isEmpty(data.getTitle())))
                .sorted(Comparator.comparing(CommunicationMainDTO::getCreateDateTime).reversed())
                .limit(4)
                .collect(Collectors.toList()))
            .build();
    }

    /**
     * 배너 / 팝업
     *
     * @param requestObject
     * @return
     */
    public MainApiResponseVO getMainDisplay(MainViewRequestVO requestObject) {
        LocalDateTime endNowDateTime = LocalDateTime.now().minusDays(1);
        List<HomeBanner> bannerList = homeBannerRepository.findAllByBannerStartYmdLessThanEqualAndBannerEndYmdGreaterThanEqualAndBannerStts(LocalDateTime.now(), endNowDateTime, 1).stream()
                .sorted(Comparator.comparing(HomeBanner::getBannerStartYmd).reversed())
                .collect(Collectors.toList());

        List<HomePopup> popupList = new ArrayList<>();
        if (requestObject.getLoginYn() != null && !requestObject.getLoginYn().equals(null) && !requestObject.getLoginYn().isEmpty()) {
            if (requestObject.getLoginYn().equals('Y')) {
                popupList = homePopupRepository.findAllByPopupStartYmdLessThanEqualAndPopupEndYmdGreaterThanAndPopupStts(LocalDateTime.now(), endNowDateTime, 1).stream()
                        .sorted(Comparator.comparing(HomePopup::getPopupStartYmd).reversed())
                        .collect(Collectors.toList());
            } else {
                popupList = homePopupRepository.findAllByPopupStartYmdLessThanEqualAndPopupEndYmdGreaterThanAndPopupViewTypeAndPopupStts(LocalDateTime.now(), endNowDateTime, "B", 1).stream()
                        .sorted(Comparator.comparing(HomePopup::getPopupStartYmd).reversed())
                        .collect(Collectors.toList());
            }
        } else {
            popupList = homePopupRepository.findAllByPopupStartYmdLessThanEqualAndPopupEndYmdGreaterThanAndPopupViewTypeAndPopupStts(LocalDateTime.now(), endNowDateTime, "B", 1).stream()
                    .sorted(Comparator.comparing(HomePopup::getPopupStartYmd).reversed())
                    .collect(Collectors.toList());
        }

        return MainApiResponseVO.builder()
                .bannerList(bannerList.stream()
                        .map(data -> BannerMainDTO.builder()
                                .bannerSn(data.getBannerSn())
                                .title(data.getTitle())
                                .bannerLink(data.getBannerLink())
                                .bannerImagePath(data.getBannerImagePath())
                                .bannerStts(data.getBannerStts())
                                .build())
                        .collect(Collectors.toList()))

                .popupList(popupList.stream()
                        .map(data -> PopupMainDTO.builder()
                                .popupSn(data.getPopupSn())
                                .title(data.getTitle())
                                .contents(data.getContents())
                                .popupViewType(data.getPopupViewType())
                                .windowSizeV(data.getWindowSizeV())
                                .windowSizeH(data.getWindowSizeH())
                                .windowTop(data.getWindowTop())
                                .windowLeft(data.getWindowLeft())
                                .popupLink(data.getPopupLink())
                                .popupImagePath(data.getPopupImagePath())
                                .popupStts(data.getPopupStts())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
