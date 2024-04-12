package kr.or.kpf.lms.biz.business.organization.rpt.service;

import kr.or.kpf.lms.biz.business.organization.rpt.vo.request.BizOrganizationRsltRptApiRequestVO;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.request.BizOrganizationRsltRptViewRequestVO;
import kr.or.kpf.lms.biz.business.organization.rpt.vo.response.BizOrganizationRsltRptApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizOrganizationRsltRptRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizOrganizationRsltRpt;
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

@Service
@RequiredArgsConstructor
public class BizOrganizationRsltRptService extends CSServiceSupport {

    private static final String PREFIX_ORG_RSLT_RPT = "BORR";
    private static final String ORGANIZATION_RPT_FILE_TAG = "ORGANIZATION_RPT";

    private final CommonBusinessRepository commonBusinessRepository;
    private final BizOrganizationRsltRptRepository bizOrganizationRsltRptRepository;

    /**
     결과보고서 정보 생성
     */
    public BizOrganizationRsltRptApiResponseVO createBizOrganizationRsltRpt(BizOrganizationRsltRptApiRequestVO requestObject) {
        BizOrganizationRsltRpt entity = BizOrganizationRsltRpt.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizOrgRsltRptNo(commonBusinessRepository.generateCode(PREFIX_ORG_RSLT_RPT));
        BizOrganizationRsltRptApiResponseVO result = BizOrganizationRsltRptApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizOrganizationRsltRptRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     결과보고서 정보 업데이트
     */
    public BizOrganizationRsltRptApiResponseVO updateBizOrganizationRsltRpt(BizOrganizationRsltRptApiRequestVO requestObject) {
        return bizOrganizationRsltRptRepository.findOne(Example.of(BizOrganizationRsltRpt.builder()
                        .bizOrgRsltRptNo(requestObject.getBizOrgRsltRptNo())
                        .build()))
                .map(bizOrganizationRsltRpt -> {
                    copyNonNullObject(requestObject, bizOrganizationRsltRpt);

                    BizOrganizationRsltRptApiResponseVO result = BizOrganizationRsltRptApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizOrganizationRsltRptRepository.saveAndFlush(bizOrganizationRsltRpt), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "해당 결과보고서 미존재"));
    }

    /**
     결과보고서 정보 삭제
     */
    public CSResponseVOSupport deleteBizOrganizationRsltRpt(BizOrganizationRsltRptApiRequestVO requestObject) {
        bizOrganizationRsltRptRepository.delete(bizOrganizationRsltRptRepository.findOne(Example.of(BizOrganizationRsltRpt.builder()
                        .bizOrgRsltRptNo(requestObject.getBizOrgRsltRptNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3547, "삭제된 결과보고서 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     결과보고서 리스트
     */
    public <T> Page<T> getList(BizOrganizationRsltRptViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     결과보고서 상세
     */
    public <T> T getBizOrganizationRsltRpt(BizOrganizationRsltRptViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     * 결과보고서 첨부파일 업로드
     *
     * @param bizOrgRsltRptNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String bizOrgRsltRptNo, MultipartFile attachFile) {
        /** 결과보고서 이력 확인 */
        BizOrganizationRsltRpt bizOrganizationRsltRpt = bizOrganizationRsltRptRepository.findOne(Example.of(BizOrganizationRsltRpt.builder()
                        .bizOrgRsltRptNo(bizOrgRsltRptNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3645, "대상 결과보고서 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizOrganizationRsltRptFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizOrganizationRsltRptFolder())
                                    .append("/")
                                    .append(ORGANIZATION_RPT_FILE_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            bizOrganizationRsltRpt.setBizOrgRsltRptFile(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizOrganizationRsltRpt.setBizOrgRsltRptFileSize(file.getSize());
                            bizOrganizationRsltRpt.setBizOrgRsltRptFileOrigin(file.getOriginalFilename());
                        } catch (IOException e) {
                            throw new KPFException(KPF_RESULT.ERROR9005, "파일 업로드 실패");
                        }
                    } catch (IOException e2) {
                        throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
                    }
                });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}