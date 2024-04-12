package kr.or.kpf.lms.biz.business.instructor.identify.service;

import kr.or.kpf.lms.biz.business.instructor.aply.vo.request.BizInstructorAplyViewRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.request.BizInstructorIdentifyDtlApiRequestVO;
import kr.or.kpf.lms.biz.business.instructor.identify.dtl.vo.response.BizInstructorIdentifyDtlApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.request.*;
import kr.or.kpf.lms.biz.business.instructor.identify.vo.response.BizInstructorIdentifyApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.BizInstructorIdentifyDtlRepository;
import kr.or.kpf.lms.repository.BizInstructorIdentifyRepository;
import kr.or.kpf.lms.repository.BizOrganizationAplyDtlRepository;
import kr.or.kpf.lms.repository.CommonBusinessRepository;
import kr.or.kpf.lms.repository.entity.BizInstructorIdentify;
import kr.or.kpf.lms.repository.entity.BizInstructorIdentifyDtl;
import kr.or.kpf.lms.repository.entity.BizOrganizationAplyDtl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BizInstructorIdentifyService extends CSServiceSupport {
    private static final String PREFIX_INSTR_IDENTIFY = "BII";
    private static final String PREFIX_INSTR_IDENTIFY_DTL = "BIID";

    private static final String ATCH_TAG = "_ATCH";
    private static final String LSN_TAG = "_LSN";
    private final CommonBusinessRepository commonBusinessRepository;

    private final BizInstructorIdentifyRepository bizInstructorIdentifyRepository;
    private final BizInstructorIdentifyDtlRepository bizInstructorIdentifyDtlRepository;
    private final BizOrganizationAplyDtlRepository bizOrganizationAplyDtlRepository;

    /**
     강의확인서 정보 생성
     */
    public BizInstructorIdentifyApiResponseVO createBizInstructorIdentify(BizInstructorIdentifyApiRequestVO requestObject) {
        BizInstructorIdentify entity = BizInstructorIdentify.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizInstructorIdentifyApiResponseVO result = BizInstructorIdentifyApiResponseVO.builder().build();
        String code = commonBusinessRepository.generateCode(PREFIX_INSTR_IDENTIFY);
        if (!bizInstructorIdentifyRepository.existsById(code)) {
            entity.setBizInstrIdntyNo(code);
            BeanUtils.copyProperties(bizInstructorIdentifyRepository.saveAndFlush(entity), result);
        }
        return result;
    }

    // 강의확인서, 내용 전체 생성
    public BizInstructorIdentifyApiResponseVO createFullBizInstructorIdentify(FullBizInstructorIdentifyApiRequestVO requestObject) {
        BizInstructorIdentify entity = BizInstructorIdentify.builder().build();
        BeanUtils.copyProperties(requestObject, entity);
        BizInstructorIdentifyApiResponseVO result = BizInstructorIdentifyApiResponseVO.builder().build();
        String code = commonBusinessRepository.generateCode(PREFIX_INSTR_IDENTIFY);
        if (!bizInstructorIdentifyRepository.existsById(code)) {
            entity.setBizInstrIdntyNo(code);
            BeanUtils.copyProperties(bizInstructorIdentifyRepository.saveAndFlush(entity), result);

            try {
                List<BizInstructorIdentifyDtlApiRequestVO> bizInstructorIdentifyDtlApiRequestVOS = requestObject.getBizInstructorIdentifyDtlApiRequestVOS();
                for(BizInstructorIdentifyDtlApiRequestVO requestVO : bizInstructorIdentifyDtlApiRequestVOS){
                    BizInstructorIdentifyDtl dtlEntity = BizInstructorIdentifyDtl.builder().build();
                    BeanUtils.copyProperties(requestVO, dtlEntity);
                    dtlEntity.setBizInstrIdntyNo(code);
                    dtlEntity.setBizInstrIdntyDtlNo(commonBusinessRepository.generateCode(PREFIX_INSTR_IDENTIFY_DTL));

                    BizOrganizationAplyDtl bizOrganizationAplyDtl = bizOrganizationAplyDtlRepository.findOne(Example.of(BizOrganizationAplyDtl.builder()
                            .bizOrgAplyDtlNo(requestVO.getBizOrgAplyDtlNo())
                            .build())).get();

                    if (requestObject.getBizOrgAplyNo().equals(bizOrganizationAplyDtl.getBizOrgAplyNo())) {
                        bizInstructorIdentifyDtlRepository.saveAndFlush(dtlEntity);
                    }
                }
            } catch (Exception e) {
                BizInstructorIdentifyApiRequestVO requestVO = new BizInstructorIdentifyApiRequestVO();
                BeanUtils.copyProperties(requestObject, requestVO);
                deleteBizInstructorIdentify(requestVO);
                result.setBizInstrIdntyNo("error");
                logger.error("{}- {}", e.getClass().getCanonicalName(), e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     강의확인서 정보 업데이트
     */
    public BizInstructorIdentifyApiResponseVO updateBizInstructorIdentify(BizInstructorIdentifyApiRequestVO requestObject) {
        return bizInstructorIdentifyRepository.findById(requestObject.getBizInstrIdntyNo())
                .map(bizInstructorIdentify -> {
                    copyNonNullObject(requestObject, bizInstructorIdentify);

                    if (requestObject.getBizInstrIdntyStts() == 2) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Calendar calendar = Calendar.getInstance();
                        Date date = calendar.getTime();
                        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
                        bizInstructorIdentify.setBizInstrIdntyAprvDt(sdf.format(date));
                    }

                    BizInstructorIdentifyApiResponseVO result = BizInstructorIdentifyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstructorIdentifyRepository.saveAndFlush(bizInstructorIdentify), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강의확인서 미존재"));
    }

    /**
     강의확인서 정보 삭제
     */
    public CSResponseVOSupport deleteBizInstructorIdentify(BizInstructorIdentifyApiRequestVO requestObject) {
        if (requestObject.getBizInstrIdntyStts() != 0 || requestObject.getBizInstrIdntyStts() != 1) {
            bizInstructorIdentifyDtlRepository.deleteAll(bizInstructorIdentifyDtlRepository.findAll(Example.of(BizInstructorIdentifyDtl.builder()
                    .bizInstrIdntyNo(requestObject.getBizInstrIdntyNo())
                    .build())));

            bizInstructorIdentifyRepository.delete(bizInstructorIdentifyRepository.findById(requestObject.getBizInstrIdntyNo())
                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3607, "삭제된 강의확인서 입니다.")));
            return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
        } else {
            return CSResponseVOSupport.of(KPF_RESULT.ERROR3637);
        }
    }

    /**
     강의확인서 리스트
     */
    public <T> Page<T> getList(BizInstructorIdentifyViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 강의확인서 관련 파일 업로드
     *
     * @param bizInstrIdntyNo
     * @param attachFile
     * @param fileType
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String bizInstrIdntyNo, MultipartFile attachFile, String fileType) {
        BizInstructorIdentify bizInstructorIdentify = bizInstructorIdentifyRepository.findOne(Example.of(BizInstructorIdentify.builder()
                        .bizInstrIdntyNo(bizInstrIdntyNo)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1061, "강의확인서 정보 미존재"));

        String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();
        Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getInstrFolder()).toString());

        try {
            /** 파일 저장 및 파일 경로 셋팅 */
            Files.createDirectories(directoryPath);
            if(fileType.equals("atch")){
                Optional.ofNullable(attachFile)
                        .ifPresent(file -> {
                            String idntyLsnFilePath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getInstrFolder())
                                    .append("/")
                                    .append(bizInstrIdntyNo + ATCH_TAG + imageSequence + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".")).toString();
                            try {
                                file.transferTo(new File(idntyLsnFilePath));
                            } catch (IOException e) {
                                throw new KPFException(KPF_RESULT.ERROR1061, "파일 업로드 실패");
                            }
                            bizInstructorIdentify.setBizInstrIdntyAtchFile(idntyLsnFilePath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizInstructorIdentify.setBizInstrIdntyAtchFileSize(file.getSize());
                            bizInstructorIdentify.setBizInstrIdntyAtchFileOrgn(file.getOriginalFilename());
                        });
            }else if(fileType.equals("lsn")){
                Optional.ofNullable(attachFile)
                        .ifPresent(file -> {
                            String idntyAtchFilePath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getInstrFolder())
                                    .append("/")
                                    .append(bizInstrIdntyNo + LSN_TAG + imageSequence + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), ".")).toString();
                            try {
                                file.transferTo(new File(idntyAtchFilePath));
                            } catch (IOException e) {
                                throw new KPFException(KPF_RESULT.ERROR1061, "파일 업로드 실패");
                            }
                            bizInstructorIdentify.setBizInstrIdntyLsnFile(idntyAtchFilePath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            bizInstructorIdentify.setBizInstrIdntyLsnFileSize(file.getSize());
                            bizInstructorIdentify.setBizInstrIdntyLsnFileOrgn(file.getOriginalFilename());
                        });
            }

        } catch (IOException e1) {
            throw new KPFException(KPF_RESULT.ERROR9005, "파일 경로 확인 또는, 생성 실패");
        }
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     강의확인서 상세
     */
    public <T> T getBizInstructorIdentify(BizInstructorIdentifyViewRequestVO requestObject) {
        return (T) commonBusinessRepository.findEntity(requestObject);
    }

    /**
     포미 강의확인서 리스트
     */
    public <T> Page<T> getBizInstructorIdentifyList(FormeBizlecinfoApiRequestVO requestObject, String userId) {
        requestObject.setBlciUserId(userId);
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    public  <T> Page<T>  getBizInstructorIdentify(String blciId, String userId) {
        FormeBizlecinfoDetailApiRequestVO requestObject = FormeBizlecinfoDetailApiRequestVO.builder().blciId(Long.valueOf(blciId)).build();
        requestObject.setBlciUserId(userId);
        requestObject.setPageable(Pageable.ofSize(1));
        return (Page<T>) Optional.ofNullable(commonBusinessRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }
    /**
     * 강의확인서 관련 파일 삭제
     *
     * @param instuctorIdentifyNo
     * @param fileType
     * @return
     */
    public BizInstructorIdentifyApiResponseVO fileUploadDelete(String instuctorIdentifyNo, String fileType) {

        return bizInstructorIdentifyRepository.findById(instuctorIdentifyNo)
                .map(bizInstructorIdentify -> {
                    if(fileType.equals("atch")){
                        bizInstructorIdentify.setBizInstrIdntyAtchFile(null);
                    }else if (fileType.equals("lsn")){
                        bizInstructorIdentify.setBizInstrIdntyLsnFile(null);
                    }
                    BizInstructorIdentifyApiResponseVO result = BizInstructorIdentifyApiResponseVO.builder().build();
                    BeanUtils.copyProperties(bizInstructorIdentifyRepository.saveAndFlush(bizInstructorIdentify), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3605, "해당 강의확인서 미존재"));
    }

}
