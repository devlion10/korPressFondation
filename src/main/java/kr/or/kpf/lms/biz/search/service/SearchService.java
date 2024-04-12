package kr.or.kpf.lms.biz.search.service;

import kr.or.kpf.lms.biz.business.pbanc.master.vo.request.BizPbancViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.vo.response.BizPbancCustomApiResponseVO;
import kr.or.kpf.lms.biz.communication.archive.data.vo.request.ArchiveViewRequestVO;
import kr.or.kpf.lms.biz.communication.archive.data.vo.response.ArchiveCustomApiResponseVO;
import kr.or.kpf.lms.biz.communication.archive.guide.vo.request.ArchiveClassGuideViewRequestVO;
import kr.or.kpf.lms.biz.communication.event.vo.request.EventViewRequestVO;
import kr.or.kpf.lms.biz.communication.review.vo.request.ReviewViewRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.request.PressViewRequestVO;
import kr.or.kpf.lms.biz.educenter.press.vo.response.PressCustomApiResponseVO;
import kr.or.kpf.lms.biz.search.vo.SearchResponseVO;
import kr.or.kpf.lms.biz.search.vo.SearchViewRequestVO;
import kr.or.kpf.lms.biz.servicecenter.notice.vo.request.NoticeViewRequestVO;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.CommonCommunicationRepository;
import kr.or.kpf.lms.repository.CommonEducationCenterRepository;
import kr.or.kpf.lms.repository.CommonServiceCenterRepository;
import kr.or.kpf.lms.repository.entity.ClassGuide;
import kr.or.kpf.lms.repository.entity.EducationReview;
import kr.or.kpf.lms.repository.entity.EventInfo;
import kr.or.kpf.lms.repository.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService extends CSServiceSupport {

    /** 참여 / 소통 공통 Repository */
    private final CommonBusinessRepository commonBusinessRepository;
    /** 참여 / 소통 공통 Repository - 교육주제안, 교육후기방, 자료실, 이벤트/설문 */
    private final CommonCommunicationRepository commonCommunicationRepository;
    /** 고객센터 공통 Repository - 공지사항 */
    private final CommonServiceCenterRepository commonServiceCenterRepository;
    /** 행사소개(보도자료) Repository */
    private final CommonEducationCenterRepository commonEducationCenterRepository;

    /**
     * 통합 검색
     *
     * @param
     * @return
     * @param
     */
    public <T> Page<T> getList(SearchViewRequestVO requestVO){
        // 검색어 인입

        List<SearchResponseVO> responseList = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 50, Sort.unsorted());

        // 공모/자격
        BizPbancViewRequestVO requestBizPbanc = BizPbancViewRequestVO.builder().build();
        requestBizPbanc.setContainTextType("1");
        requestBizPbanc.setContainText(requestVO.getSearchText());
        requestBizPbanc.setPageable(pageable);
        List<BizPbancCustomApiResponseVO> bizPbancList = (List<BizPbancCustomApiResponseVO>) commonBusinessRepository.findEntityList(requestBizPbanc).getContent();

        if (bizPbancList != null && bizPbancList.size() > 0) {
            for (BizPbancCustomApiResponseVO item : bizPbancList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("공모/자격");
                searchResponseVO.setMenuSubName("공모사업");
                searchResponseVO.setContentNo(item.getBizPbancNo());
                searchResponseVO.setTitle(item.getBizPbancNm());
                searchResponseVO.setContents(item.getBizPbancCn());
                searchResponseVO.setBizPbancRcptBgng(item.getBizPbancRcptBgng());
                searchResponseVO.setBizPbancRcptEnd(item.getBizPbancRcptEnd());
                searchResponseVO.setBizPbancSprtBgng(item.getBizPbancSprtBgng());
                searchResponseVO.setBizPbancSprtEnd(item.getBizPbancSprtEnd());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName(item.getUserName());
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        // 교육후기방
        ReviewViewRequestVO requestReview = ReviewViewRequestVO.builder().build();
        requestReview.setContainTextType("1");
        requestReview.setContainText(requestVO.getSearchText());
        requestReview.setPageable(pageable);

        List<EducationReview> educationReviewList = (List<EducationReview>) commonCommunicationRepository.findEntityList(requestReview).getContent();

        if (educationReviewList != null && educationReviewList.size() > 0) {
            for (EducationReview item : educationReviewList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("참여/소통");
                searchResponseVO.setMenuSubName("교육후기방");
                searchResponseVO.setContentNo(item.getSequenceNo().toString());
                searchResponseVO.setTitle(item.getTitle());
                searchResponseVO.setContents(item.getContents());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName(item.getAdminUser().getUserName());
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        // 자료실 (수업지도안)
        ArchiveClassGuideViewRequestVO requestArchiveClassGuide = ArchiveClassGuideViewRequestVO.builder().build();
        requestArchiveClassGuide.setContainTextType("1");
        requestArchiveClassGuide.setContainText(requestVO.getSearchText());
        requestArchiveClassGuide.setPageable(pageable);

        List<ClassGuide> archiveClassGuideList = (List<ClassGuide>) commonCommunicationRepository.findEntityList(requestArchiveClassGuide).getContent();
        if (archiveClassGuideList != null && archiveClassGuideList.size() > 0) {
            for (ClassGuide item : archiveClassGuideList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("참여/소통");
                searchResponseVO.setMenuSubName("자료실");
                searchResponseVO.setMenuSubSubName("수업지도안");

                searchResponseVO.setContentNo(item.getClassGuideCode());
                searchResponseVO.setTitle(item.getTitle());
                searchResponseVO.setContents(item.getLearningGoal());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName(item.getUserName());
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        // 자료실
        ArchiveViewRequestVO requestArchive = ArchiveViewRequestVO.builder().build();
        requestArchive.setContainTextType("1");
        requestArchive.setContainText(requestVO.getSearchText());
        requestArchive.setPageable(pageable);

        List<ArchiveCustomApiResponseVO> archiveList = (List<ArchiveCustomApiResponseVO>) commonCommunicationRepository.findEntityList(requestArchive).getContent();

        if (archiveList != null && archiveList.size() > 0) {
            for (ArchiveCustomApiResponseVO item : archiveList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("참여/소통");
                searchResponseVO.setMenuSubName("자료실");

                if (item.getMaterialCategory().equals(Code.MTRL_CTGR.ETC.enumCode)) {
                    searchResponseVO.setMenuSubSubName("기타 자료");
                }else if (item.getMaterialCategory().equals(Code.MTRL_CTGR.A.enumCode)) {
                    searchResponseVO.setMenuSubSubName("교육 자료");
                }else if (item.getMaterialCategory().equals(Code.MTRL_CTGR.B.enumCode)) {
                    searchResponseVO.setMenuSubSubName("연구/통계 자료");
                }else if (item.getMaterialCategory().equals(Code.MTRL_CTGR.C.enumCode)) {
                    searchResponseVO.setMenuSubSubName("영상 자료");
                }else if (item.getMaterialCategory().equals(Code.MTRL_CTGR.D.enumCode)) {
                    searchResponseVO.setMenuSubSubName("사업결과물");
                }

                searchResponseVO.setContentNo(item.getSequenceNo().toString());
                searchResponseVO.setTitle(item.getTitle());
                searchResponseVO.setContents(item.getContents());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName(item.getUserName());
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        // 이벤트/설문
        EventViewRequestVO requestEvent = EventViewRequestVO.builder().build();
        requestEvent.setContainTextType("1");
        requestEvent.setContainText(requestVO.getSearchText());
        requestEvent.setPageable(pageable);

        List<EventInfo> eventInfoList = (List<EventInfo>) commonCommunicationRepository.findEntityList(requestEvent).getContent();
        if (eventInfoList != null && eventInfoList.size() > 0) {
            for (EventInfo item : eventInfoList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("참여/소통");
                searchResponseVO.setMenuSubName("이벤트/설문");

                searchResponseVO.setContentNo(item.getSequenceNo().toString());
                searchResponseVO.setTitle(item.getTitle());
                searchResponseVO.setContents(item.getContents());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName(item.getAdminUser().getUserName());
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        // 고객센터 - 공지사항
        NoticeViewRequestVO requestNotice = NoticeViewRequestVO.builder().build();
        requestNotice.setContainTextType("1");
        requestNotice.setContainText(requestVO.getSearchText());
        requestNotice.setPageable(pageable);

        List<Notice> noticeList = (List<Notice>) commonServiceCenterRepository.findEntityList(requestNotice).getContent();
        if (noticeList != null && noticeList.size() > 0) {
            for (Notice item : noticeList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("고객센터");
                searchResponseVO.setMenuSubName("공지사항");

                searchResponseVO.setContentNo(item.getNoticeSerialNo());
                searchResponseVO.setTitle(item.getTitle());
                searchResponseVO.setContents(item.getContents());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName("");
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        // 행사소개(보도자료)
        PressViewRequestVO requestPress = PressViewRequestVO.builder().build();
        requestPress.setContainTextType("1");
        requestPress.setContainText(requestVO.getSearchText());
        requestPress.setPageable(pageable);

        List<PressCustomApiResponseVO> pressList = (List<PressCustomApiResponseVO>) commonEducationCenterRepository.findEntityList(requestPress).getContent();
        if (pressList != null && pressList.size() > 0) {
            for (PressCustomApiResponseVO item : pressList) {
                SearchResponseVO searchResponseVO = SearchResponseVO.builder().build();
                searchResponseVO.setMenuName("연수원소개");
                searchResponseVO.setMenuSubName("행사소개");

                searchResponseVO.setContentNo(item.getSequenceNo().toString());
                searchResponseVO.setTitle(item.getTitle());
                searchResponseVO.setContents(item.getContents());
                searchResponseVO.setRegisterId(item.getRegistUserId());
                searchResponseVO.setRegisterName(item.getUserName());
                searchResponseVO.setRegDt(item.getCreateDateTime());

                responseList.add(searchResponseVO);
            }
        }

        List<SearchResponseVO> resultList = responseList.stream()
                .skip(requestVO.getPageable().getOffset())
                .limit(requestVO.getPageable().getPageSize())
                .sorted(Comparator.comparing(SearchResponseVO::getRegDt).reversed()).collect(Collectors.toList());


        return (Page<T>) Optional.ofNullable(resultList)
                .map(list -> CSPageImpl.of(list, requestVO.getPageable(), responseList.size()))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestVO.getPageable(), 0));
    }

}
