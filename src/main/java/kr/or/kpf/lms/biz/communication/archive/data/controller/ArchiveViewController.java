package kr.or.kpf.lms.biz.communication.archive.data.controller;

import io.swagger.v3.oas.annotations.Parameter;
import kr.or.kpf.lms.biz.communication.archive.data.service.ArchiveService;
import kr.or.kpf.lms.biz.communication.archive.data.vo.request.ArchiveViewRequestVO;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * 참여 / 소통 > 교육 후기방 View 관련 Controller
 */
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/communication/archive")
public class ArchiveViewController extends CSViewControllerSupport {

    private static final String COMMUNICATION = "views/community/";

    private final ArchiveService archiveService;

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 조회
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/etc-data"})
    public String getEtcDataList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("materialCategory", Code.MTRL_CTGR.ETC.enumCode);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "MTRL_CTGR", "MTRL_TYPE"));
        return new StringBuilder(COMMUNICATION).append("etc").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/etc-data/view/{sequenceNo}"})
    public String getEtcDataView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                 @Parameter(description = "자료실 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("etcView").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 조회
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/edu-data"})
    public String getEduDataList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("materialCategory", Code.MTRL_CTGR.A.enumCode);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "MTRL_CTGR", "MTRL_TYPE"));
        return new StringBuilder(COMMUNICATION).append("eduFile").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/edu-data/view/{sequenceNo}"})
    public String getEduDataView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                @Parameter(description = "자료실 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("eduFileView").toString();
    }

    /** 연구/간행물 */
    @GetMapping(path={"/publicing"})
    public String getPublication(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        return new StringBuilder(COMMUNICATION).append("publicing").toString();
    }

    /** 미디어리터러시 */
    @GetMapping(path={"/publication"})
    public String getResearchBook(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("materialCategory", Code.MTRL_CTGR.E.enumCode);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList());
        return new StringBuilder(COMMUNICATION).append("publication").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/publication/view/{sequenceNo}"})
    public String getResearchBookView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                                      @Parameter(description = "자료실 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("publicationView").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/research-data"})
    public String getResearchData(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("materialCategory", Code.MTRL_CTGR.B.enumCode);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "MTRL_CTGR", "MTRL_TYPE"));
        return new StringBuilder(COMMUNICATION).append("researchFile").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/research-data/view/{sequenceNo}"})
    public String getResearchDataView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "자료실 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("researchFileView").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/media"})
    public String getMediaList(HttpServletRequest request, @PageableDefault(size = 8) Pageable pageable, Model model) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("materialCategory", Code.MTRL_CTGR.C.enumCode);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "MTRL_CTGR", "MTRL_TYPE"));
        return new StringBuilder(COMMUNICATION).append("media").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/media/view/{sequenceNo}"})
    public String getMediaView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                          @Parameter(description = "자료실 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("mediaView").toString();
    }

    /** 사업결과물 선택 */
    @GetMapping(path={"/result"})
    public String getResult(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        return new StringBuilder(COMMUNICATION).append("results").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping(path={"/results"})
    public String getResultList(HttpServletRequest request, @PageableDefault Pageable pageable, Model model) {
        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("materialCategory", Code.MTRL_CTGR.D.enumCode);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)), Arrays.asList("TEAM_CTGR", "MTRL_CTGR", "MTRL_TYPE"));
        return new StringBuilder(COMMUNICATION).append("resultsList").toString();
    }

    /**
     * 참여 / 소통 > 자료실 > 0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시 상세 조회
     *
     * @param request
     * @param pageable
     * @param model
     * @param sequenceNo
     * @return
     */
    @GetMapping(path={"/results/view/{sequenceNo}"})
    public String getResultView(HttpServletRequest request, @PageableDefault Pageable pageable, Model model,
                               @Parameter(description = "자료실 시리얼 번호") @PathVariable(value = "sequenceNo", required = true) BigInteger sequenceNo) {

        CSSearchMap requestParam = CSSearchMap.of(request);
        requestParam.put("sequenceNo", sequenceNo);
        modelSetting(model, Optional.ofNullable(requestParam)
                .map(searchMap -> archiveService.getList((ArchiveViewRequestVO) params(ArchiveViewRequestVO.class, searchMap, pageable)))
                .orElse(CSPageImpl.of(new ArrayList<>(), pageable, 0)));
        return new StringBuilder(COMMUNICATION).append("resultsView").toString();
    }

}
