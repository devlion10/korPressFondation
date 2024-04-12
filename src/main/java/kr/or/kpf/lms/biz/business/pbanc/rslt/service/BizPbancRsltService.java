package kr.or.kpf.lms.biz.business.pbanc.rslt.service;

import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.request.BizPbancRsltViewRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.rslt.vo.response.BizPbancRsltApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizPbancResultRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizPbancResult;
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
public class BizPbancRsltService extends CSServiceSupport {

    private static final String PBANC_RSLT_FILE_TAG = "PBANC_RSLT";
    private static final String PREFIX_PBANC_RSLT = "PACR";

    private final BizPbancResultRepository bizPbancResultRepository;
    private final CommonBusinessRepository commonBusinessRepository;

    /**
     사업 공고 결과 정보 생성
     */
    public BizPbancRsltApiResponseVO createBizPbancRslt(BizPbancRsltApiRequestVO requestObject) {
        BizPbancResult entity = BizPbancResult.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizPbancRsltNo(commonBusinessRepository.generateCode(PREFIX_PBANC_RSLT));
        BizPbancRsltApiResponseVO result = BizPbancRsltApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancResultRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     사업 공고 결과 정보 업데이트
     */
    public BizPbancRsltApiResponseVO updateBizPbancRslt(BizPbancRsltApiRequestVO requestObject) {
        return bizPbancResultRepository.findOne(Example.of(BizPbancResult.builder()
                        .bizPbancRsltNo(requestObject.getBizPbancRsltNo())
                        .build()))
                .map(bizPbancRsltNo -> {
                    copyNonNullObject(requestObject, bizPbancRsltNo);

                    BizPbancRsltApiResponseVO result = BizPbancRsltApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancResultRepository.saveAndFlush(bizPbancRsltNo), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "해당 사업 공고 결과 미존재"));
    }

    /**
     사업 공고 결과 정보 삭제
     */
    public CSResponseVOSupport deleteBizPbancRslt(BizPbancRsltApiRequestVO requestObject) {
        bizPbancResultRepository.delete(bizPbancResultRepository.findOne(Example.of(BizPbancResult.builder()
                        .bizPbancRsltNo(requestObject.getBizPbancRsltNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3547, "삭제된 사업 공고 결과 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     사업 공고 결과 리스트
     */
    public <T> Page<T> getBizPbancRsltList(BizPbancRsltViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 모집 신청 상세
     */
    public <T> T getBizPbancRslt(BizPbancRsltViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     * 사업 공고 결과 업로드
     *
     * @param bizPbancRsltNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String bizPbancRsltNo, MultipartFile attachFile) {
        /** 공지사항 이력 확인 */
        BizPbancResult bizPbancResult = bizPbancResultRepository.findOne(Example.of(BizPbancResult.builder()
                        .bizPbancRsltNo(bizPbancRsltNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "대상 사업 공고 신청 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizPbancResultFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizPbancResultFolder())
                                    .append("/")
                                    .append(PBANC_RSLT_FILE_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            bizPbancResult.setBizPbancRsltFile(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
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