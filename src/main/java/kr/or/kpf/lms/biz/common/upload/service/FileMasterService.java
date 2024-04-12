package kr.or.kpf.lms.biz.common.upload.service;

import kr.or.kpf.lms.biz.common.upload.vo.request.FileMasterApiRequestVO;
import kr.or.kpf.lms.biz.common.upload.vo.request.FileMasterViewRequestVO;
import kr.or.kpf.lms.biz.common.upload.vo.response.FileMasterApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonFileRepository;
import kr.or.kpf.lms.repository.FileMasterRepository;
import kr.or.kpf.lms.repository.entity.FileMaster;
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
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileMasterService extends CSServiceSupport {

    private final CommonFileRepository commonFileRepository;
    private final FileMasterRepository fileMasterRepository;


    /**
     * 첨부파일 업로드
     *
     * @param attachFileSn
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public FileMasterApiResponseVO fileUpload(String attachType, String attachFileSn, MultipartFile attachFile) {

        /** 파일 저장 및 파일 경로 셋팅 */
        FileMasterApiResponseVO result = FileMasterApiResponseVO.builder().build();
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getUploadFolder()).append("/" + attachType.toLowerCase()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getUploadFolder())
                                    .append("/"+attachType.toLowerCase()+"/")
                                    .append(attachType + imageSequence + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".")).toString();

                            file.transferTo(new File(attachFilepath));

                            FileMaster fileMaster = FileMaster.builder().build();
                            fileMaster.setAtchFileSn(attachFileSn);
                            fileMaster.setFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            fileMaster.setFileName(attachType + imageSequence);
                            fileMaster.setOriginalFileName(file.getOriginalFilename());
                            fileMaster.setFileExtension(StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
                            fileMaster.setFileSize(BigInteger.valueOf(file.getSize()));
                            BeanUtils.copyProperties(fileMasterRepository.saveAndFlush(fileMaster), result);

                        } catch (IOException e) {
                            throw new KPFException(KPF_RESULT.ERROR9005, "파일 업로드 실패");
                        }
                    } catch (IOException e2) {
                        throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
                    }
                });

        return result;
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

    /**
     파일 업로드 정보 생성
     */
    public FileMasterApiResponseVO createFileMaster(FileMasterApiRequestVO requestObject) {
        FileMaster entity = FileMaster.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        FileMasterApiResponseVO result = FileMasterApiResponseVO.builder().build();
        BeanUtils.copyProperties(fileMasterRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     파일 업로드 업데이트
     */
    public FileMasterApiResponseVO updateFileMaster(FileMasterApiRequestVO requestObject) {
        return fileMasterRepository.findOne(Example.of(FileMaster.builder()
                        .fileSn(requestObject.getFileSn())
                        .build()))
                .map(fileMaster -> {
                    copyNonNullObject(requestObject, fileMaster);

                    FileMasterApiResponseVO result = FileMasterApiResponseVO.builder().build();
                    BeanUtils.copyProperties(fileMasterRepository.saveAndFlush(fileMaster), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3505, "해당 파일 업로드 미존재"));
    }

    /**
     파일 업로드 삭제
     */
    public CSResponseVOSupport deleteFileMaster(FileMasterApiRequestVO requestObject) {
        fileMasterRepository.delete(fileMasterRepository.findOne(Example.of(FileMaster.builder()
                        .fileSn(requestObject.getFileSn())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3507, "삭제된 파일 업로드 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     파일 업로드 리스트
     */
    public <T> Page<T> getFileMasterList(FileMasterViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonFileRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     파일 업로드 상세 보기
     */
    public <T> T getFileMaster(FileMasterViewRequestVO requestObject) {
        return (T) commonFileRepository.findEntity(requestObject);
    }

    /**
     파일 업로드 리스트 (by AtchFileSn) 보기
     */
    public <T> List<FileMaster> getFileMasterListByAtchFileSn(FileMasterViewRequestVO requestObject) {
        return commonFileRepository.findEntityByAtchFileSn(requestObject);
    }
}