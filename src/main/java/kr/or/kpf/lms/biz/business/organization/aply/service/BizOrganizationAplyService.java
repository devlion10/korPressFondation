package kr.or.kpf.lms.biz.business.organization.aply.service;

import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.dtl.service.BizOrganizationAplyDtlService;
import kr.or.kpf.lms.biz.business.organization.aply.edithist.vo.request.BizEditHistViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.request.BizOrganizationAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.aply.vo.response.BizOrganizationAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.organization.aply.edithist.service.BizEditHistService;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizOrganizationAplyRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizOrganizationAply;
import kr.or.kpf.lms.repository.entity.LmsUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BizOrganizationAplyService extends CSServiceSupport {

    private static final String ORGANIZATION_APLY_FILE_TAG = "ORGANIZATION_APLY";

    private static final String PREFIX_ORGAPLY = "BOA";

    private final CommonBusinessRepository commonBusinessRepository;
    private final BizOrganizationAplyRepository bizOrganizationAplyRepository;
    private final BizEditHistService bizEditHistService;
    private final BizOrganizationAplyDtlService bizOrganizationAplyDtlService;

    /**
     사업 공고 신청 정보 생성
     */
    public BizOrganizationAplyApiResponseVO createBizOrganizationAply(BizOrganizationAplyApiRequestVO requestObject) {

        BizOrganizationAply bizOrganizationAply = bizOrganizationAplyRepository.findByBizPbancNoAndOrgCd(requestObject.getBizPbancNo(), requestObject.getOrgCd());
        if (bizOrganizationAply != null) {
            return null;
        } else {
            BizOrganizationAply entity = BizOrganizationAply.builder().build();
            BeanUtils.copyProperties(requestObject, entity);
            entity.setOrgCd(requestObject.getOrgCd());
            entity.setBizPbancNo(requestObject.getBizPbancNo());
            String bizOrgAplyNo = commonBusinessRepository.generateCode(PREFIX_ORGAPLY);
            entity.setBizOrgAplyNo(bizOrgAplyNo);

            requestObject.getBizOrganizationAplyDtlApiRequestVOS().forEach(bizOrganizationAplyDtlApiRequestVO -> {
                bizOrganizationAplyDtlApiRequestVO.setBizOrgAplyNo(bizOrgAplyNo);
            });
            bizOrganizationAplyDtlService.createBizOrganizationAplyDtls(requestObject.getBizOrganizationAplyDtlApiRequestVOS());

            BizOrganizationAplyApiResponseVO result = BizOrganizationAplyApiResponseVO.builder().build();
            BeanUtils.copyProperties(bizOrganizationAplyRepository.saveAndFlush(entity), result);
            return result;
        }
    }

    /**
     사업 공고 신청 정보 업데이트
     */
    @Transactional
    public BizOrganizationAplyApiResponseVO updateBizOrganizationAply(BizOrganizationAplyApiRequestVO requestObject) {
        return bizOrganizationAplyRepository.findById(requestObject.getBizOrgAplyNo())
                .map(bizOrganizationAply -> {
                    if (bizOrganizationAply.getBizOrgAplyStts() == 2) {
                        if (bizOrganizationAply.getBizOrgAplyChgYn() == 1) {
                            requestObject.setBizOrgAplyStts(2);
                        } else return null;
                    } else if (bizOrganizationAply.getBizOrgAplyStts() == 9) {
                        if (bizOrganizationAply.getBizOrgAplyChgYn() == 1) {
                            requestObject.setBizOrgAplyStts(9);
                        } else return null;
                    }
                    copyNonNullObject(requestObject, bizOrganizationAply);
                    BizOrganizationAplyApiResponseVO result = BizOrganizationAplyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizOrganizationAplyRepository.saveAndFlush(bizOrganizationAply), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "해당 사업 공고 신청이 없거나 변경이 불가합니다."));
    }

    /**
     사업 공고 신청 정보 삭제
     */
    public CSResponseVOSupport deleteBizOrganizationAply(BizOrganizationAplyApiRequestVO requestObject) {
        bizOrganizationAplyRepository.delete(bizOrganizationAplyRepository.findOne(Example.of(BizOrganizationAply.builder()
                        .bizOrgAplyNo(requestObject.getBizOrgAplyNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3547, "삭제된 사업 공고 신청 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 신청 리스트
     */
    public <T> Page<T> getList(BizOrganizationAplyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     사업 공고 신청 상세
     */
    public <T> T getBizOrganizationAply(BizOrganizationAplyViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     * 사업 공고 신청 신청서 업로드
     *
     * @param bizOrgAplyNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String bizOrgAplyNo, MultipartFile attachFile) {
        /** 사업 공고 신청 이력 확인 */
        BizOrganizationAply bizOrganizationAply = bizOrganizationAplyRepository.findOne(Example.of(BizOrganizationAply.builder()
                        .bizOrgAplyNo(bizOrgAplyNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "대상 사업 공고 신청 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizOrganizationAplyFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizOrganizationAplyFolder())
                                    .append("/")
                                    .append(ORGANIZATION_APLY_FILE_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            bizOrganizationAply.setBizOrgAplyFile(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                        } catch (IOException e) {
                            throw new KPFException(KPF_RESULT.ERROR9005, "파일 업로드 실패");
                        }
                    } catch (IOException e2) {
                        throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
                    }
                });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 첨부파일 다운로드
     *
     * @param attachFilePath
     * @return
     */
    public byte[] fileDownload(String attachFilePath) {
        try {
            return FileUtils.readFileToByteArray(new File(new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                    .append(attachFilePath).toString()));
        } catch (IOException e) {
            throw new KPFException(KPF_RESULT.ERROR9006, "파일 미존재");
        }
    }
}