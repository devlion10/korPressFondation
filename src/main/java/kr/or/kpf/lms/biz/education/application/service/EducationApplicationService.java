package kr.or.kpf.lms.biz.education.application.service;

import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationApiRequestVO;
import kr.or.kpf.lms.biz.education.application.vo.request.EducationApplicationViewRequestVO;
import kr.or.kpf.lms.biz.education.application.vo.response.EducationApplicationApiResponseVO;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.*;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 교육신청 관련 Service
 */
@Service
@RequiredArgsConstructor
public class EducationApplicationService extends CSServiceSupport {
    private static final String APPLICATION_IMG_TAG = "_APPLICATION";
    private final AsyncService asyncService;

    private final CommonEducationRepository commonEducationRepository;
    private final EducationPlanRepository educationPlanRepository;
    private final CurriculumQuestionRepository curriculumQuestionRepository;
    private final CurriculumApplicationMasterRepository curriculumApplicationMasterRepository;
    private final CurriculumApplicationLectureRepository curriculumApplicationLectureRepository;

    /**
     * 교육 과정 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getList(EducationApplicationViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonEducationRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 교육 과정 조회(연계 교육 과정 포함)
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getIncludeCollaborationEducationList(EducationApplicationViewRequestVO requestObject) {
        Page<Object> originalResult = getList(requestObject);
        List<Object> educationPlanList = originalResult.getContent().stream()
                .filter(educationPlan -> educationPlan instanceof EducationPlan)
                .map(educationPlan -> {
                    if (((EducationPlan) educationPlan).getCurriculumMaster().getCurriculumCollaborationList().size() > 0) {
                        ((EducationPlan) educationPlan).getCurriculumMaster().getCurriculumCollaborationList().forEach(curriculumCollaboration -> {
                            curriculumCollaboration.setEducationPlanList(educationPlanRepository
                                    .findByApplyBeginDateTimeLessThanEqualAndApplyEndDateTimeGreaterThanEqualAndCurriculumCodeAndIsUsable(
                                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                                            , new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
                                            , curriculumCollaboration.getReferenceCurriculumCode()
                                            , true).stream().peek(data -> {
                                                data.setCurriculumMaster(null);
                                    }).collect(Collectors.toList()));
                        });
                        return educationPlan;
                    }
                    return educationPlan;
                }).collect(Collectors.toList());
        return (Page<T>) CSPageImpl.of(educationPlanList, originalResult.getPageable(), originalResult.getTotalElements());
    }

    /**
     * 교육 신청
     *
     * @param educationApplicationApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public EducationApplicationApiResponseVO createEducationApplication(EducationApplicationApiRequestVO educationApplicationApiRequestVO, MultipartFile applyFile) {
        /** Spring Session에서 유저 정보 획득 */
        String userId = authenticationInfo().getUserId();
        CurriculumApplicationMaster entity = CurriculumApplicationMaster.builder().build();

        EducationPlan educationPlan = educationPlanRepository.findOne(Example.of(EducationPlan.builder()
                        .educationPlanCode(educationApplicationApiRequestVO.getEducationPlanCode())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3064, "존재하지 않는 교육 과정 일정"));
        /** 신청 인원이 허용 인원보다 크거나 같을 경우 */
        if((educationApplicationApiRequestVO.getSetEducationType()).equals("1")){ // 화상강의
            if (educationPlan.getNumberOfPeople() != null && educationPlan.getNumberOfPeople() > 0
                    && educationPlan.getNumberOfPeople() <= curriculumApplicationMasterRepository.findAll(Example.of(CurriculumApplicationMaster.builder()
                    .adminApprovalState(Code.ADM_APL_STATE.APPROVAL.enumCode)
                    .adminApprovalState(Code.ADM_APL_STATE.WAIT.enumCode)
                    .setEducationType(educationApplicationApiRequestVO.getSetEducationType())
                    .educationPlanCode(educationPlan.getEducationPlanCode())
                    .build())).size()) {
                throw new RuntimeException("정원을 초과하였습니다.");
            }
        }else if((educationApplicationApiRequestVO.getSetEducationType()).equals("2")){ //집합강의
            if (educationPlan.getNumberOfPeopleParallel() != null && educationPlan.getNumberOfPeopleParallel() > 0
                    && educationPlan.getNumberOfPeopleParallel() <= curriculumApplicationMasterRepository.findAll(Example.of(CurriculumApplicationMaster.builder()
                    .adminApprovalState(Code.ADM_APL_STATE.APPROVAL.enumCode)
                    .adminApprovalState(Code.ADM_APL_STATE.WAIT.enumCode)
                    .setEducationType(educationApplicationApiRequestVO.getSetEducationType())
                    .educationPlanCode(educationPlan.getEducationPlanCode())
                    .build())).size()) {
                throw new RuntimeException("정원을 초과하였습니다.");
            }
        }else{
            if (educationPlan.getNumberOfPeople() != null && educationPlan.getNumberOfPeople() > 0
                    && educationPlan.getNumberOfPeople() <= curriculumApplicationMasterRepository.findAll(Example.of(CurriculumApplicationMaster.builder()
                    .adminApprovalState(Code.ADM_APL_STATE.APPROVAL.enumCode)
                    .educationPlanCode(educationPlan.getEducationPlanCode())
                    .build())).size()) {
                throw new RuntimeException("정원을 초과하였습니다.");
            }
        }

        /** 회원 ID 추가 셋팅 */
        entity.setUserId(userId);
        entity.setIsAccommodation(educationApplicationApiRequestVO.getIsAccommodation());
        entity.setEducationPlanCode(educationPlan.getEducationPlanCode());
        entity.setCurriculumCode(educationPlan.getCurriculumCode());
        EducationApplicationApiResponseVO result = EducationApplicationApiResponseVO.builder().build();

        curriculumApplicationMasterRepository.findOne(Example.of(entity)).ifPresentOrElse(curriculumApplicationMaster -> {
            if(Code.ADM_APL_STATE.CANCEL.enumCode.equals(curriculumApplicationMaster.getAdminApprovalState())) {
                /** 사용자 취소 건의 경우에는 승인 대기 상태로 변경하여 재신청 */
                curriculumApplicationMaster.setAdminApprovalState(Code.ADM_APL_STATE.WAIT.enumCode);
                BeanUtils.copyProperties(curriculumApplicationMasterRepository.saveAndFlush(curriculumApplicationMaster), result);
            } else {
                throw new KPFException(KPF_RESULT.ERROR3023, "이미 등록되어 있는 교육 과정 신청 내역 존재");
            }
        }, () -> {
            /** 사용자 지정 교육 유형 */
            entity.setSetEducationType(educationApplicationApiRequestVO.getSetEducationType());
            /** 수료 기본 처리 중 상태 */
            entity.setIsComplete("W");
            /** 관리자 승인 대기 중 */
            entity.setAdminApprovalState(Code.ADM_APL_STATE.WAIT.enumCode);
            /** 신청 심사 중 */
            entity.setEducationState(Code.EDU_STATE.WAIT.enumCode);

            if(!Code.APLY_APPR_TYPE.ADMIN.equals(Code.APLY_APPR_TYPE.enumOfCode(educationPlan.getCurriculumMaster().getApplyApprovalType()))) { /** 관리자 승인이 필요하지 않은 교육 과정일 경우 */
                /** 신청 즉시 승인 처리 */
                entity.setAdminApprovalState(Code.ADM_APL_STATE.APPROVAL.enumCode);
                /** 교육 진행 중 처리 */
                entity.setEducationState(Code.EDU_STATE.PROCEEDING.enumCode);
                /** 교육 상시 여부에 따른 교육 기간 정의 */
                if (Code.OPER_TYPE.CARDINAL.equals(Code.OPER_TYPE.enumOfCode(educationPlan.getOperationType()))) {
                    /** 교육 시작 일시 (교육 계획에 지정된 교육 시작일자로 지정) */
                    entity.setOperationBeginDateTime(educationPlan.getOperationBeginDateTime());
                    /** 교육 종료 일시 (교육 계획에 지정된 교육 종료일자로 지정) */
                    entity.setOperationEndDateTime(educationPlan.getOperationEndDateTime());
                } else if (Code.OPER_TYPE.ALWAYS.equals(Code.OPER_TYPE.enumOfCode(educationPlan.getOperationType()))) {
                    /** 교육 시작 일시 (신청일 기준으로 시작일자 지정) */
                    entity.setOperationBeginDateTime(new DateToStringConverter().convertToEntityAttribute(new Date()));
                    /** 교육 종료 일시 (신청일 기준으로 교육 유효기간으로 종료일자 지정) */
                    entity.setOperationEndDateTime(new DateToStringConverter().convertToEntityAttribute(new DateTime().plusDays(educationPlan.getAlwaysEducationTerm()).toDate()));
                }
                /** 교육 점수 '0'으로 초기화 */
                entity.setProgressRate(0.0); entity.setProgressScore(0); entity.setExamScore(0); entity.setAssignmentScore(0);
            }

            CurriculumApplicationMaster curriculumApplicationMaster = CurriculumApplicationMaster.builder().build();
            BeanUtils.copyProperties(curriculumApplicationMasterRepository.saveAndFlush(entity), curriculumApplicationMaster);
            BeanUtils.copyProperties(curriculumApplicationMaster, result);

            /** 교육 신청 관련 하위 데이터들 생성 (강의 목록, 시험 등...) */
            CompletableFuture<CurriculumApplicationMaster> generateResult = asyncService.generateContentsAndExam(educationPlan, curriculumApplicationMaster);
            generateResult.thenAccept(it -> {
                logger.debug("교육 신청 일련 번호 {} 교육 신청 하위 데이터 생성 완료", it.getApplicationNo());
            });
        });
        return result;
    }

    /**
     * 교육 신청 취소
     *
     * @param applicationNo
     * @return
     */
    public CSResponseVOSupport cancelEducationApplication(String applicationNo) {
        curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                        .applicationNo(applicationNo)
                        .userId(authenticationInfo().getUserId())
                        .adminApprovalState(Code.ADM_APL_STATE.WAIT.enumCode)
                        .build()))
                .ifPresentOrElse(curriculumApplicationMaster -> {
                    curriculumApplicationMaster.setAdminApprovalState(Code.ADM_APL_STATE.CANCEL.enumCode);
                    curriculumApplicationMasterRepository.saveAndFlush(curriculumApplicationMaster);

                    CurriculumApplicationLecture curriculumApplicationLecture = CurriculumApplicationLecture.builder()
                            .applicationNo(applicationNo)
                            .curriculumCode(curriculumApplicationMaster.getCurriculumCode())
                            .userId(authenticationInfo().getUserId())
                            .build();
                    curriculumApplicationLectureRepository.deleteAll(curriculumApplicationLectureRepository.findAll(Example.of(curriculumApplicationLecture)));
                }, () -> {
                    throw new KPFException(KPF_RESULT.ERROR3025, "교육 신청 취소 가능한 교육 과정이 아닙니다.");
                });
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 난이도 별 시험 문제 추출
     *
     * @param curriculumCode
     * @param level
     * @param count
     * @return
     */
    public List<CurriculumQuestion> difficultyLevelExamList(String curriculumCode, Code.QUE_LEVEL level, Integer count) {
        return curriculumQuestionRepository.findAll(Example.of(CurriculumQuestion.builder()
                        .curriculumCode(curriculumCode)
                        .build())).parallelStream()
                .filter(mappingInfo -> level.equals(Code.QUE_LEVEL.enumOfCode(mappingInfo.getExamQuestionMaster().getQuestionLevel())))
                .distinct()
                .limit(count)
                .collect(Collectors.toList());
    }

    public CSResponseVOSupport fileUpload(String curriculumCode, MultipartFile applicationFile) {
        /** 교육 신청 이력 확인 */
        CurriculumApplicationMaster curriculumApplicationMaster = curriculumApplicationMasterRepository.findOne(Example.of(CurriculumApplicationMaster.builder()
                        .curriculumCode(curriculumCode)
                        .userId(authenticationInfo().getUserId())
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3025, "대상 교육 과정 신청 이력 없음."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(applicationFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getApplicationFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String applicationFilePath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getApplicationFolder())
                                    .append("/")
                                    .append(authenticationInfo().getUserId() + APPLICATION_IMG_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(applicationFilePath));
                            curriculumApplicationMaster.setApplicationFilePath(applicationFilePath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                        } catch (IOException e) {
                            throw new KPFException(KPF_RESULT.ERROR9004, "파일 업로드 실패");
                        }
                    } catch (IOException e2) {
                        throw new KPFException(KPF_RESULT.ERROR9004, "파일 경로 확인 또는, 생성 실패");
                    }
                });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
