package kr.or.kpf.lms.biz.business.apply.service;

import kr.or.kpf.lms.biz.business.apply.vo.request.BizAplyApiRequestVO;
import kr.or.kpf.lms.biz.business.apply.vo.request.BizAplyDtlFileApiRequestVO;
import kr.or.kpf.lms.biz.business.apply.vo.request.BizAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.apply.vo.response.BizAplyApiResponseVO;
import kr.or.kpf.lms.biz.business.apply.vo.response.BizAplyDtlFileApiResponseVO;
import kr.or.kpf.lms.biz.common.upload.service.FileMasterService;
import kr.or.kpf.lms.biz.common.upload.vo.response.FileMasterApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizAplyDtlFileRepository;
import kr.or.kpf.lms.repository.BizAplyDtlRepository;
import kr.or.kpf.lms.repository.BizAplyRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BizAplyService extends CSServiceSupport {
    private static final String BIZ_APLY_FILE_TAG = "_BIZ_APLY";
    private final BizAplyRepository bizAplyRepository;
    private final BizAplyDtlRepository bizAplyDtlRepository;
    private final BizAplyDtlFileRepository bizAplyDtlFileRepository;

    /**
     사업 공고 신청 - 언론인/기본형 정보 생성
     */
    public BizAplyApiResponseVO createBizAply(BizAplyApiRequestVO requestObject) {
        BizAply entity = BizAply.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizAplyApiResponseVO result = BizAplyApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizAplyRepository.saveAndFlush(entity), result);
        return result;
    }
    /**
     사업 공고 신청 - 자유형 정보 생성
     */
    public BizAplyApiResponseVO createBizAply(BizAplyApiRequestVO requestObject, List<MultipartFile> file) {
        Integer fileCount = 0;
        BizAply entity = BizAply.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizAplyApiResponseVO result = BizAplyApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizAplyRepository.saveAndFlush(entity), result);
        for(BizAplyDtl dtl : requestObject.getBizAplyDtl()){
            BizAplyDtl bizAplyDtl = BizAplyDtl.builder().build();
            if(dtl.getFileSn() != null && dtl.getFileSn() == 0 && dtl.getBizAplyDtlCont().equals("1")){
                bizAplyDtl.setBizAplyDtlCont(file.get(fileCount).getOriginalFilename());
                BizAplyDtlFileApiResponseVO fileResponse = dtlFileUpload(result.getSequenceNo().toString(), file.get(fileCount));
                fileCount +=1;
                bizAplyDtl.setFileSn(fileResponse.getFileSn());
                bizAplyDtl.setBizPbancTmpl5No(dtl.getBizPbancTmpl5No());
            }else{
                bizAplyDtl.setBizAplyDtlCont(dtl.getBizAplyDtlCont());
                bizAplyDtl.setBizPbancTmpl5No(dtl.getBizPbancTmpl5No());
            }
            bizAplyDtl.setSequenceNo(result.getSequenceNo());
            bizAplyDtlRepository.saveAndFlush(bizAplyDtl);
        }
        return result;
    }


    /**
     사업 공고 신청 - 자유형 정보 업데이트
     */
    public BizAplyApiResponseVO updateBizAply(BizAplyApiRequestVO requestObject, List<MultipartFile> file) {
        Integer fileCount = 0;
        BizAply entity = BizAply.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizAplyApiResponseVO result = BizAplyApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizAplyRepository.saveAndFlush(entity), result);

        for(BizAplyDtl dtl : requestObject.getBizAplyDtl()){
            BizAplyDtl bizAplyDtl = BizAplyDtl.builder().build();
            if(dtl.getFileSn() != null && dtl.getFileSn() == 0){
                if (dtl.getBizAplyDtlCont().equals("0")) {
                    // 첨부된 파일 없으면 기존 파일 정보 지우기
                    BizAplyDtl aplyDtl = bizAplyDtlRepository.findOne(Example.of(BizAplyDtl.builder()
                            .sequenceNo(requestObject.getSequenceNo())
                            .bizPbancTmpl5No(dtl.getBizPbancTmpl5No())
                            .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "사업 신청 내용 조회 실패"));
                    if (!dtl.getFileSn().equals(aplyDtl.getFileSn())) {
                        bizAplyDtlFileRepository.delete(bizAplyDtlFileRepository.findOne(Example.of(BizAplyDtlFile.builder()
                                .fileSn(aplyDtl.getFileSn())
                                .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3543, "사업 신청 내용 수정 실패")));
                    }
                } else {
                    bizAplyDtl.setBizAplyDtlCont(file.get(fileCount).getOriginalFilename());
                    BizAplyDtlFileApiResponseVO fileResponse = this.dtlFileUpload(result.getSequenceNo().toString(), file.get(fileCount));
                    fileCount += 1;
                    bizAplyDtl.setFileSn(fileResponse.getFileSn());
                    bizAplyDtl.setBizPbancTmpl5No(dtl.getBizPbancTmpl5No());
                }
            }else{
                bizAplyDtl.setFileSn(dtl.getFileSn());
                bizAplyDtl.setBizAplyDtlCont(dtl.getBizAplyDtlCont());
                bizAplyDtl.setBizPbancTmpl5No(dtl.getBizPbancTmpl5No());
            }
            bizAplyDtl.setSequenceNo(result.getSequenceNo());
            bizAplyDtlRepository.saveAndFlush(bizAplyDtl);
        }
        return result;
    }

    /**
     * 사업 공고 신청 - 언론인/기본형/자유형 결과보고서 업로드
     *
     * @param sequenceNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(BigInteger sequenceNo, MultipartFile attachFile) {
        /** 사업 공고 신청 - 언론인/기본형/자유형 이력 확인 */
        BizAply bizAply = bizAplyRepository.findOne(Example.of(BizAply.builder()
                        .sequenceNo(sequenceNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "대상 사업 공고 신청 - 언론인/기본형 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizAplyFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizAplyFolder())
                                    .append("/")
                                    .append(authenticationInfo().getUserId() + BIZ_APLY_FILE_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            bizAply.setBizAplyFile(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizAply.setBizAplyFileSize(file.getSize());
                            bizAply.setBizAplyFileOrigin(file.getOriginalFilename());
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
     * 신청서 내용 첨부파일 업로드
     *
     * @param attachFileSn
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public BizAplyDtlFileApiResponseVO dtlFileUpload(String attachFileSn, MultipartFile attachFile) {

        /** 파일 저장 및 파일 경로 셋팅 */
        BizAplyDtlFileApiResponseVO result = BizAplyDtlFileApiResponseVO.builder().build();
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getUploadFolder()).append("/aply").toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getUploadFolder())
                                    .append("/aply/")
                                    .append(imageSequence + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".")).toString();

                            file.transferTo(new File(attachFilepath));
                            BizAplyDtlFile bizAplyDtlFile = BizAplyDtlFile.builder().build();
                            bizAplyDtlFile.setAtchFileSn(attachFileSn);
                            bizAplyDtlFile.setFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizAplyDtlFile.setFileName(imageSequence);
                            bizAplyDtlFile.setOriginalFileName(file.getOriginalFilename());
                            bizAplyDtlFile.setFileExtension(StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
                            bizAplyDtlFile.setFileSize(BigInteger.valueOf(file.getSize()));
                            BeanUtils.copyProperties(bizAplyDtlFileRepository.saveAndFlush(bizAplyDtlFile), result);

                        } catch (IOException e) {
                            throw new KPFException(KPF_RESULT.ERROR9005, "파일 업로드 실패");
                        }
                    } catch (IOException e2) {
                        throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
                    }
                });

        return result;
    }
}