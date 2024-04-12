package kr.or.kpf.lms.biz.mypage.classguide.service;

import kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.service.ClassGuideSubjectService;
import kr.or.kpf.lms.biz.mypage.classguide.classguidesubject.vo.request.ClassGuideSubjectApiRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideApiRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.request.ClassGuideViewRequestVO;
import kr.or.kpf.lms.biz.mypage.classguide.vo.response.ClassGuideApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.ClassGuideFileRepository;
import kr.or.kpf.lms.repository.ClassGuideRepository;
import kr.or.kpf.lms.repository.ClassGuideSubjectRepository;
import kr.or.kpf.lms.repository.CommonMyPageRepository;
import kr.or.kpf.lms.repository.entity.*;
import kr.or.kpf.lms.repository.entity.ClassGuide;
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

/**
 * 수업지도안 관련 Service
 */
@Service
@RequiredArgsConstructor
public class ClassGuideService extends CSServiceSupport {

    private static final String PREFIX_GUIDE = "GUI";
    private static final String GUIDE_TAG = "_CLASS_GUIDE";

    private final CommonMyPageRepository commonMyPageRepository;
    private final ClassGuideRepository classGuideRepository;
    private final ClassGuideSubjectRepository classGuideSubjectRepository;
    private final ClassGuideFileRepository classGuideFileRepository;

    private final ClassGuideSubjectService classGuideSubjectService;

    /**
     * 수업지도안 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(ClassGuideViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonMyPageRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 수업지도안 생성
     *
     * @param classGuideApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public ClassGuideApiResponseVO createClassGuide(ClassGuideApiRequestVO classGuideApiRequestVO) {
        ClassGuide entity = ClassGuide.builder().build();
        copyNonNullObject(classGuideApiRequestVO, entity);

        ClassGuideApiResponseVO result = ClassGuideApiResponseVO.builder().build();
        entity.setClassGuideCode(commonMyPageRepository.generateClassGuideCode(PREFIX_GUIDE));
        entity.setViewCount(BigInteger.ZERO);
        BeanUtils.copyProperties(classGuideRepository.saveAndFlush(entity), result);

        String[] subjectCodes = null;
        if (classGuideApiRequestVO.getReferenceSubject() != null) {
            subjectCodes = classGuideApiRequestVO.getReferenceSubject().split(",");

            for (int i=0; i<subjectCodes.length; i++) {
                ClassGuideSubjectApiRequestVO requestObject = ClassGuideSubjectApiRequestVO.builder().build();
                requestObject.setClassGuideCode(result.getClassGuideCode());
                requestObject.setIndividualCode(subjectCodes[i]);
                classGuideSubjectService.createInfo(requestObject);
            }
        }

        return result;
    }

    /**
     * 수업지도안 업데이트
     *
     * @param requestVO
     * @return
     */
    public ClassGuideApiResponseVO updateInfo(ClassGuideApiRequestVO requestVO) {
        return classGuideRepository.findOne(Example.of(ClassGuide.builder().classGuideCode(requestVO.getClassGuideCode()).build()))
                .map(classGuide -> {
                    ClassGuideApiResponseVO result = ClassGuideApiResponseVO.builder().build();

                    if (classGuide.getReferenceSubject() != null && !classGuide.getReferenceSubject().isEmpty()) {
                        if (!classGuide.getReferenceSubject().equals(requestVO.getReferenceSubject())) {
                            classGuideSubjectRepository.deleteAll(classGuideSubjectRepository.findAll(Example.of(ClassGuideSubject.builder()
                                    .classGuideCode(requestVO.getClassGuideCode())
                                    .build())));
                            copyNonNullObject(requestVO, classGuide);

                            String[] subjectCodes = {};
                            if (requestVO.getReferenceSubject() != null && !requestVO.getReferenceSubject().isEmpty()) {
                                if (requestVO.getReferenceSubject().contains(",")) {
                                    subjectCodes = requestVO.getReferenceSubject().split(",");
                                } else {
                                    subjectCodes = new String[1];
                                    subjectCodes[0] = requestVO.getReferenceSubject();
                                }

                                for (int i=0; i<subjectCodes.length; i++) {
                                    ClassGuideSubjectApiRequestVO requestSubjectVO = ClassGuideSubjectApiRequestVO.builder().build();
                                    requestSubjectVO.setClassGuideCode(requestVO.getClassGuideCode());
                                    requestSubjectVO.setIndividualCode(subjectCodes[i]);
                                    classGuideSubjectService.createInfo(requestSubjectVO);
                                }
                            } else {
                                classGuide.setReferenceSubject(null);
                            }
                        } else {
                            copyNonNullObject(requestVO, classGuide);
                        }
                    } else {
                        copyNonNullObject(requestVO, classGuide);
                    }

                    BeanUtils.copyProperties(classGuideRepository.saveAndFlush(classGuide), result);
                    return result;
                })
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4005, "해당 수업지도안 미존재"));
    }

    /**
     수업지도안 정보 삭제
     */
    public CSResponseVOSupport deleteInfo(ClassGuideApiRequestVO requestObject) {
        classGuideRepository.delete(classGuideRepository.findById(requestObject.getClassGuideCode())
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4007, "이미 삭제된 수업지도안 입니다.")));

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 첨부파일 등록
     *
     * @param classGuideCode
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String classGuideCode, MultipartFile attachFile, String fileType) {
        /** 수업지도안 이력 확인 */
        ClassGuide classGuide = classGuideRepository.findOne(Example.of(ClassGuide.builder()
                        .classGuideCode(classGuideCode)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4005, "대상 수업지도안 이력 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getClassGuideFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getClassGuideFolder())
                                    .append("/")
                                    .append(authenticationInfo().getUserId() + GUIDE_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));

                            if (fileType.equals("1")) {   // 수업지도안/길라잡이
                                classGuide.setClassGuideFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                                classGuide.setClassGuideFileSize(file.getSize());
                            } else if (fileType.equals("2")) {    // 활동지
                                classGuide.setActivitiesFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                                classGuide.setActivitiesFileSize(file.getSize());
                            } else if (fileType.equals("3")) {    // 예시답안
                                classGuide.setExampleAnswerFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                                classGuide.setExampleAnswerFileSize(file.getSize());
                            } else if (fileType.equals("4")) {    // 10분 NIE
                                classGuide.setNieFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                                classGuide.setNieFileSize(file.getSize());
                            }

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
     * 첨부파일 다중 등록
     *
     * @param classGuideCode
     * @param attachFiles
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport multifileUpload(String classGuideCode, List<MultipartFile> attachFiles, String fileType) {
        /** 수업지도안 이력 확인 */
        ClassGuide classGuide = classGuideRepository.findOne(Example.of(ClassGuide.builder()
                        .classGuideCode(classGuideCode)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR4005, "대상 수업지도안 이력 없음."));

        if (classGuide != null && !classGuide.equals(null)) {
            for (MultipartFile multipartFile : attachFiles) {
                /** 파일 저장 및 파일 경로 셋팅 */
                Optional.ofNullable(multipartFile)
                        .ifPresent(file -> {
                            Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getClassGuideFolder()).toString());
                            try {
                                Files.createDirectories(directoryPath);
                                try {
                                    String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();
                                    String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                            .append(appConfig.getUploadFile().getClassGuideFolder())
                                            .append("/").toString();

                                    if (fileType.equals(Code.GUI_FILE_TYPE.TEACH.enumCode)) {   // 수업지도안/길라잡이
                                        attachFilepath = new StringBuilder(attachFilepath)
                                                .append(authenticationInfo().getUserId() + "_" + Code.GUI_FILE_TYPE.TEACH + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();

                                    } else if (fileType.equals(Code.GUI_FILE_TYPE.ACTIVITY.enumCode)) {    // 활동지
                                        attachFilepath = new StringBuilder(attachFilepath)
                                                .append(authenticationInfo().getUserId() + "_" + Code.GUI_FILE_TYPE.ACTIVITY + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();

                                    } else if (fileType.equals(Code.GUI_FILE_TYPE.ANSWER.enumCode)) {    // 예시답안
                                        attachFilepath = new StringBuilder(attachFilepath)
                                                .append(authenticationInfo().getUserId() + "_" + Code.GUI_FILE_TYPE.ANSWER + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();

                                    } else if (fileType.equals(Code.GUI_FILE_TYPE.NIE.enumCode)) {    // 10분 NIE
                                        attachFilepath = new StringBuilder(attachFilepath)
                                                .append(authenticationInfo().getUserId() + "_" + Code.GUI_FILE_TYPE.NIE + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                                    }

                                    file.transferTo(new File(attachFilepath));
                                    ClassGuideFile entity = ClassGuideFile.builder().build();
                                    entity.setClassGuideCode(classGuideCode);
                                    entity.setFileType(fileType);
                                    entity.setFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                                    if (fileType.equals(Code.GUI_FILE_TYPE.TEACH)) {   // 수업지도안/길라잡이
                                        entity.setFileName(file.getOriginalFilename() + "_" + Code.GUI_FILE_TYPE.TEACH + imageSequence);

                                    } else if (fileType.equals(Code.GUI_FILE_TYPE.ACTIVITY)) {    // 활동지
                                        entity.setFileName(file.getOriginalFilename() + "_" + Code.GUI_FILE_TYPE.ACTIVITY + imageSequence);

                                    } else if (fileType.equals(Code.GUI_FILE_TYPE.ANSWER)) {    // 예시답안
                                        entity.setFileName(file.getOriginalFilename() + "_" + Code.GUI_FILE_TYPE.ANSWER + imageSequence);

                                    } else if (fileType.equals(Code.GUI_FILE_TYPE.NIE)) {    // 10분 NIE
                                        entity.setFileName(file.getOriginalFilename() + "_" + Code.GUI_FILE_TYPE.NIE + imageSequence);
                                    }
                                    entity.setOriginalFileName(file.getOriginalFilename());
                                    entity.setFileExtension(StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
                                    entity.setFileSize(file.getSize());
                                    /** 조회수 '0' 셋팅 */
                                    entity.setViewCount(BigInteger.ZERO);
                                    classGuideFileRepository.save(entity);

                                } catch (IOException e) {
                                    throw new KPFException(KPF_RESULT.ERROR9005, "파일 업로드 실패");
                                }
                            } catch (IOException e2) {
                                throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
                            }
                        });
            }

            return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
        } else {
            return CSResponseVOSupport.of(KPF_RESULT.ERROR4005);
        }
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
     * 첨부파일 다운로드 및 카운트
     *
     * @param sequenceNo
     * @param attachFilePath
     * @return
     */
    public byte[] fileCountDownload(BigInteger sequenceNo, String attachFilePath) {
        ClassGuideFile entity = classGuideFileRepository.findOne(Example.of(ClassGuideFile.builder().sequenceNo(sequenceNo).build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9009, "존재하지 않는 파일입니다."));
        entity.setViewCount(entity.getViewCount().add(BigInteger.ONE));
        classGuideFileRepository.saveAndFlush(entity);

        try {
            return FileUtils.readFileToByteArray(new File(new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                    .append(attachFilePath).toString()));
        } catch (IOException e) {
            throw new KPFException(KPF_RESULT.ERROR9006, "파일 미존재");
        }
    }

    /**
     첨부파일 삭제
     */
    public CSResponseVOSupport deleteFile(BigInteger sequenceNo) {
        classGuideFileRepository.delete(classGuideFileRepository.findOne(Example.of(ClassGuideFile.builder()
                        .sequenceNo(sequenceNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9009, "이미 삭제된 수업지도안 파일 입니다.")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
