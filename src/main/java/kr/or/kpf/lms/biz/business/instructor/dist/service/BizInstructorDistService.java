package kr.or.kpf.lms.biz.business.instructor.dist.service;

import kr.or.kpf.lms.biz.business.instructor.dist.vo.request.BizInstructorDistApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.request.BizInstructorDistViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.response.BizInstructorDistApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstructorDistRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorDist;
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
public class BizInstructorDistService extends CSServiceSupport {
    private static final String INSTR_DIST_FILE_TAG = "INSTR_DIST";
    private static final String PREFIX_INSTR_DIST = "BID";

    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorDistRepository bizPbancInstructorDistRepository;

    /**
     거리 증빙 정보 생성
     */
    public BizInstructorDistApiResponseVO createBizInstructorDist(BizInstructorDistApiRequestVO requestObject) {
        BizInstructorDist entity = BizInstructorDist.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        entity.setBizInstrDistNo(commonBusinessRepository.generateCode(PREFIX_INSTR_DIST));
        BizInstructorDistApiResponseVO result = BizInstructorDistApiResponseVO.builder().build();
        BeanUtils.copyProperties(bizPbancInstructorDistRepository.saveAndFlush(entity), result);

        return result;
    }

    /**
     거리 증빙 정보 업데이트
     */
    public BizInstructorDistApiResponseVO updateBizInstructorDist(BizInstructorDistApiRequestVO requestObject) {
        return bizPbancInstructorDistRepository.findOne(Example.of(BizInstructorDist.builder()
                        .bizInstrDistNo(requestObject.getBizInstrDistNo())
                        .build()))
                .map(bizPbancInstructorDist -> {
                    copyNonNullObject(requestObject, bizPbancInstructorDist);

                    BizInstructorDistApiResponseVO result = BizInstructorDistApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizPbancInstructorDistRepository.saveAndFlush(bizPbancInstructorDist), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강사 모집 미존재"));
    }

    /**
     거리 증빙 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorDist(BizInstructorDistApiRequestVO requestObject) {
        bizPbancInstructorDistRepository.delete(bizPbancInstructorDistRepository.findOne(Example.of(BizInstructorDist.builder()
                        .bizInstrDistNo(requestObject.getBizInstrDistNo())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강사 모집 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     거리 증빙 리스트
     */
    public <T> Page<T> getBizInstructorDistList(BizInstructorDistViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     거리 증빙 상세
     */
    public <T> T getBizInstructorDist(BizInstructorDistViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     * 거리 증빙 결과 업로드
     *
     * @param bizInstrDistNo
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String bizInstrDistNo, MultipartFile attachFile) {
        /** 공지사항 이력 확인 */
        BizInstructorDist bizInstructorDist = bizPbancInstructorDistRepository.findOne(Example.of(BizInstructorDist.builder()
                        .bizInstrDistNo(bizInstrDistNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3545, "대상 거리 증빙 신청 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizInstrDistFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizInstrDistFolder())
                                    .append("/")
                                    .append(INSTR_DIST_FILE_TAG + imageSequence + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            bizInstructorDist.setBizDistMapFile(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizInstructorDist.setBizDistMapFileSize(file.getSize());
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
