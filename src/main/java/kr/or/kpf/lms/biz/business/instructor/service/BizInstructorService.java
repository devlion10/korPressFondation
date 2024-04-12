package kr.or.kpf.lms.biz.business.instructor.service;

import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.request.BizInstructorViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.vo.response.BizInstructorApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.BizInstructor;
import kr.or.kpf.lms.repository.entity.BizInstructorPbanc;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BizInstructorService extends CSServiceSupport {
    private static final String INSTR_FILE_TAG = "INSTR";
    private static final String PREFIX_INSTR = "BIM";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorRepository bizPbancInstructorRepository;

    private final BizInstructorPbancRepository bizPbancInstructorPbancRepository;

    /**
     강사 모집 정보 생성
     */
    public BizInstructorApiResponseVO createBizInstructor(BizInstructorApiRequestVO requestObject) {
        BizInstructor entity = BizInstructor.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrNo(commonBusinessRepository.generateCode(PREFIX_INSTR));
        BizInstructorApiResponseVO result = BizInstructorApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancInstructorRepository.saveAndFlush(entity), result);

        // 강사 모집 공고가 등록되었다면, 해당하는 강사 모집 공고에 등록된 사업 공고 리스트 적재
        if (result != null && result.getBizInstrNo() != null && requestObject.getBizInstrPbancs() != null && requestObject.getBizInstrPbancs().size() > 0){

            List<BizInstructorPbanc> bizInstructorPbancs = new ArrayList<>();
            for(String bizPbancNo : requestObject.getBizInstrPbancs()){
                bizInstructorPbancs.add(
                        BizInstructorPbanc.builder()
                                .bizInstrNo(result.getBizInstrNo())
                                .bizPbancNo(bizPbancNo)
                                .build()
                );
            }
            bizPbancInstructorPbancRepository.saveAllAndFlush(bizInstructorPbancs);

        }

        return result;
    }

    /**
     강사 모집 정보 업데이트
     */
    public BizInstructorApiResponseVO updateBizInstructor(BizInstructorApiRequestVO requestObject) {
        return bizPbancInstructorRepository.findOne(Example.of(BizInstructor.builder()
                        .bizInstrNo(requestObject.getBizInstrNo())
                        .build()))
                .map(bizPbancInstructor -> {
                    copyNonNullObject(requestObject, bizPbancInstructor);

                    BizInstructorApiResponseVO result = BizInstructorApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancInstructorRepository.saveAndFlush(bizPbancInstructor), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 모집 미존재"));
    }

    /**
     강사 모집 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructor(BizInstructorApiRequestVO requestObject) {
        bizPbancInstructorRepository.delete(bizPbancInstructorRepository.findOne(Example.of(BizInstructor.builder()
                        .bizInstrNo(requestObject.getBizInstrNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강사 모집 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     강사 모집 리스트
     */
    public <T> Page<T> getBizInstructorList(BizInstructorViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     강사 모집 상세 - 가장 최근의, 사용하고 있는 강사 모집글을 불러오기
     */
    public <T> T getBizInstructor(BizInstructorViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     * 강사공고 결과 업로드
     *
     * @param bizInstrNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String bizInstrNo, MultipartFile attachFile) {
        /** 공지사항 이력 확인 */
        BizInstructor bizInstructor = bizPbancInstructorRepository.findOne(Example.of(BizInstructor.builder()
                        .bizInstrNo(bizInstrNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "대상 사업 공고 신청 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizInstrFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizInstrFolder())
                                    .append("/")
                                    .append(INSTR_FILE_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            bizInstructor.setBizInstrFile(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizInstructor.setBizInstrFileSize(file.getSize());
                            bizInstructor.setBizInstrFileOrigin(file.getOriginalFilename());
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
