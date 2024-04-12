package kr.or.kpf.lms.biz.user.authority.service;

import com.google.gson.Gson;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.item.vo.response.BizInstructorDistCrtrAmtItemApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.dist.crtramt.vo.response.BizInstructorDistCrtrAmtApiResponseVO;
import kr.or.kpf.lms.biz.business.instructor.dist.vo.request.BizInstructorDistApiRequestVO;
import kr.or.kpf.lms.biz.user.authority.vo.request.IndividualAuthorityApiRequestVO;
import kr.or.kpf.lms.biz.user.authority.vo.request.OrganizationAuthorityApiRequestVO;
import kr.or.kpf.lms.biz.user.authority.vo.response.IndividualAuthorityApiResponseVO;
import kr.or.kpf.lms.biz.user.authority.vo.response.OrganizationAuthorityApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.repository.IndividualAuthorityHistoryRepository;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.OrganizationAuthorityHistoryRepository;
import kr.or.kpf.lms.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 회원 가입 > 회원정보 입력(권한 신청) 관련 Service
 */
@Service
@RequiredArgsConstructor
public class AuthorityService extends CSServiceSupport {

    /** 사업 참여 권한 신청 (기관/학교) */
    private final OrganizationAuthorityHistoryRepository organizationAuthorityHistoryRepository;
    /** 사업 참여 권한 신청 (개인) */
    private final IndividualAuthorityHistoryRepository individualAuthorityHistoryRepository;
    private final LmsUserRepository lmsUserRepository;

    private static final String PICTURE_IMG_TAG = "_PICTURE";
    private static final String SIGN_IMG_TAG = "_SIGN";

    /**
     * 사업 참여 권한 신청(기관/학교) 생성
     *
     * @param organizationAuthorityApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public OrganizationAuthorityApiResponseVO createOrganizationBusinessAuthority(OrganizationAuthorityApiRequestVO organizationAuthorityApiRequestVO) {
        OrganizationAuthorityHistory entity = OrganizationAuthorityHistory.builder().build();
        copyNonNullObject(organizationAuthorityApiRequestVO, entity);

        List<OrganizationAuthorityHistory> organizationAuthorityHistories = organizationAuthorityHistoryRepository.findAll(Example.of(OrganizationAuthorityHistory.builder()
                        .userId(organizationAuthorityApiRequestVO.getUserId())
                        .build())).stream().sorted(Comparator.comparing(OrganizationAuthorityHistory::getCreateDateTime).reversed()).limit(1)
                .collect(Collectors.toList());
        
        if (!organizationAuthorityHistories.isEmpty() && organizationAuthorityHistories.size() > 0) {
            if (organizationAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.APPLY.enumCode)) { /** 아직 승인되지 않고 신청 상태인 사업 권한 신청 이력이 존재할 경우 */
                throw new KPFException(KPF_RESULT.ERROR1062, "이미 등록되어 있는 사업 권한 신청 내역 존재");
            } else if (organizationAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.APPROVAL.enumCode)) { /** 승인 상태인 사업 권한 신청 이력이 존재할 경우 */
                OrganizationAuthorityApiResponseVO result = OrganizationAuthorityApiResponseVO.builder().build();
                /** 유효한 사업 참여 권한인지 확인 및 셋팅 */
                entity.setBizAuthority(Code.BIZ_AUTH.enumOfCode(organizationAuthorityApiRequestVO.getBizAuthority()).enumCode);
                /** 사업 참여 권한 해제 신청 상태로 생성 */
                entity.setBizAuthorityApprovalState(Code.BIZ_AUTH_STATE.CANCEL_APPLY.enumCode);
                BeanUtils.copyProperties(organizationAuthorityHistoryRepository.saveAndFlush(entity), result);

                return result;
            } else if (organizationAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.CANCEL_APPLY.enumCode)) {
                throw new KPFException(KPF_RESULT.ERROR1062, "이미 등록되어 있는 사업 권한 신청 해제 내역 존재");
            } else {
                OrganizationAuthorityApiResponseVO result = OrganizationAuthorityApiResponseVO.builder().build();
                /** 유효한 사업 참여 권한인지 확인 및 셋팅 */
                entity.setBizAuthority(Code.BIZ_AUTH.enumOfCode(organizationAuthorityApiRequestVO.getBizAuthority()).enumCode);
                /** 사업 참여 권한 신청 상태로 생성 */
                entity.setBizAuthorityApprovalState(Code.BIZ_AUTH_STATE.APPLY.enumCode);
                BeanUtils.copyProperties(organizationAuthorityHistoryRepository.saveAndFlush(entity), result);

                return result;
            }
        } else {
            OrganizationAuthorityApiResponseVO result = OrganizationAuthorityApiResponseVO.builder().build();
            /** 유효한 사업 참여 권한인지 확인 및 셋팅 */
            entity.setBizAuthority(Code.BIZ_AUTH.enumOfCode(organizationAuthorityApiRequestVO.getBizAuthority()).enumCode);
            // 사업 참여 권한 승인 상태로 생성
            entity.setBizAuthorityApprovalState(Code.BIZ_AUTH_STATE.APPROVAL.enumCode);
            BeanUtils.copyProperties(organizationAuthorityHistoryRepository.saveAndFlush(entity), result);

            LmsUser lmsUser = lmsUserRepository.findOne(Example.of(LmsUser.builder()
                    .userId(organizationAuthorityApiRequestVO.getUserId())
                    .build())).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR0001, "유효하지 않은 회원입니다."));
            if(lmsUser != null){
                lmsUser.setBusinessAuthority(Code.BIZ_AUTH.enumOfCode(organizationAuthorityApiRequestVO.getBizAuthority()).enumCode);
                lmsUser.setOrganizationCode(organizationAuthorityApiRequestVO.getOrganizationCode());
                lmsUserRepository.saveAndFlush(lmsUser);
            }
            return result;
        }
    }

    /**
     * 사업 참여 권한 신청(개인) 생성
     *
     * @param individualAuthorityApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public IndividualAuthorityApiResponseVO createIndividualBusinessAuthority(IndividualAuthorityApiRequestVO individualAuthorityApiRequestVO) {
        IndividualAuthorityHistory entity = IndividualAuthorityHistory.builder().build();
        BeanUtils.copyProperties(individualAuthorityApiRequestVO, entity);

        List<IndividualAuthorityHistory> individualAuthorityHistories = individualAuthorityHistoryRepository.findAll(Example.of(IndividualAuthorityHistory.builder()
                .userId(individualAuthorityApiRequestVO.getUserId())
                .build())).stream().sorted(Comparator.comparing(IndividualAuthorityHistory::getCreateDateTime).reversed()).limit(1)
                .collect(Collectors.toList());

        if (!individualAuthorityHistories.isEmpty() && individualAuthorityHistories.size() > 0) {
            if (individualAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.APPLY.enumCode)) { /** 아직 승인되지 않고 신청 상태인 사업 권한 신청 이력이 존재할 경우 */
                throw new KPFException(KPF_RESULT.ERROR1062, "이미 등록되어 있는 사업 권한 신청 내역 존재");
            } else if (individualAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.APPROVAL.enumCode)) { /** 승인 상태인 사업 권한 신청 이력이 존재할 경우 */
                IndividualAuthorityApiResponseVO result = IndividualAuthorityApiResponseVO.builder().build();

                entity.setRegion(new Gson().toJson(individualAuthorityApiRequestVO.getRegion()));
                entity.setAntecedents(new Gson().toJson(individualAuthorityApiRequestVO.getAntecedents()));
                entity.setCertificate(new Gson().toJson(individualAuthorityApiRequestVO.getCertificate()));
                /** 유효한 사업 참여 권한인지 확인 및 셋팅 */
                entity.setBizAuthority(Code.BIZ_AUTH.enumOfCode(individualAuthorityApiRequestVO.getBizAuthority()).enumCode);
                /** 사업 참여 권한 해제 신청 상태로 생성 */
                entity.setBizAuthorityApprovalState(Code.BIZ_AUTH_STATE.CANCEL_APPLY.enumCode);

                BeanUtils.copyProperties(individualAuthorityHistoryRepository.saveAndFlush(entity), result);

                result.setRegion(individualAuthorityApiRequestVO.getRegion());
                result.setAntecedents(individualAuthorityApiRequestVO.getAntecedents());
                result.setCertificate(individualAuthorityApiRequestVO.getCertificate());

                return result;
            } else if (individualAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.CANCEL_APPLY.enumCode)) {
                throw new KPFException(KPF_RESULT.ERROR1062, "이미 등록되어 있는 사업 권한 신청 해제 내역 존재");
            } else {
                IndividualAuthorityApiResponseVO result = IndividualAuthorityApiResponseVO.builder().build();

                entity.setRegion(new Gson().toJson(individualAuthorityApiRequestVO.getRegion()));
                entity.setAntecedents(new Gson().toJson(individualAuthorityApiRequestVO.getAntecedents()));
                entity.setCertificate(new Gson().toJson(individualAuthorityApiRequestVO.getCertificate()));
                /** 유효한 사업 참여 권한인지 확인 및 셋팅 */
                entity.setBizAuthority(Code.BIZ_AUTH.enumOfCode(individualAuthorityApiRequestVO.getBizAuthority()).enumCode);
                /** 사업 참여 권한 신청 상태로 생성 */
                entity.setBizAuthorityApprovalState(Code.BIZ_AUTH_STATE.APPLY.enumCode);

                BeanUtils.copyProperties(individualAuthorityHistoryRepository.saveAndFlush(entity), result);

                result.setRegion(individualAuthorityApiRequestVO.getRegion());
                result.setAntecedents(individualAuthorityApiRequestVO.getAntecedents());
                result.setCertificate(individualAuthorityApiRequestVO.getCertificate());

                return result;
            }
        } else {
            IndividualAuthorityApiResponseVO result = IndividualAuthorityApiResponseVO.builder().build();

            entity.setRegion(new Gson().toJson(individualAuthorityApiRequestVO.getRegion()));
            entity.setAntecedents(new Gson().toJson(individualAuthorityApiRequestVO.getAntecedents()));
            entity.setCertificate(new Gson().toJson(individualAuthorityApiRequestVO.getCertificate()));
            /** 유효한 사업 참여 권한인지 확인 및 셋팅 */
            entity.setBizAuthority(Code.BIZ_AUTH.enumOfCode(individualAuthorityApiRequestVO.getBizAuthority()).enumCode);
            /** 사업 참여 권한 신청 상태로 생성 */
            entity.setBizAuthorityApprovalState(Code.BIZ_AUTH_STATE.APPLY.enumCode);

            BeanUtils.copyProperties(individualAuthorityHistoryRepository.saveAndFlush(entity), result);

            result.setRegion(individualAuthorityApiRequestVO.getRegion());
            result.setAntecedents(individualAuthorityApiRequestVO.getAntecedents());
            result.setCertificate(individualAuthorityApiRequestVO.getCertificate());

            return result;
        }
    }

    /**
     * 사업 참여 권한 신청(기관/학교) 업데이트
     *
     * @param organizationApplicationApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public OrganizationAuthorityApiResponseVO updateOrganizationBusinessAuthority(OrganizationAuthorityApiRequestVO organizationApplicationApiRequestVO) {
        return organizationAuthorityHistoryRepository.findOne(Example.of(OrganizationAuthorityHistory.builder()
                        .userId(authenticationInfo().getUserId())
                        .bizLicenseNumber(organizationApplicationApiRequestVO.getBizLicenseNumber())
                        .build()))
                .map(organizationAuthorityHistory -> {
                    copyNonNullObject(organizationApplicationApiRequestVO, organizationAuthorityHistory);

                    OrganizationAuthorityApiResponseVO result = OrganizationAuthorityApiResponseVO.builder().build();
                    BeanUtils.copyProperties(organizationAuthorityHistoryRepository.saveAndFlush(organizationAuthorityHistory), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1063, "해당 사업 권한 신청 내역 미존재"));
    }



    /**
     * 사업 참여 권한 신청(개인) 업데이트
     *
     * @param individualAuthorityApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public IndividualAuthorityApiResponseVO updateIndividualBusinessAuthority(IndividualAuthorityApiRequestVO individualAuthorityApiRequestVO) {
        return individualAuthorityHistoryRepository.findOne(Example.of(IndividualAuthorityHistory.builder()
                        .userId(individualAuthorityApiRequestVO.getUserId())
                        .build()))
                .map(individualAuthorityHistory -> {
                    copyNonNullObject(individualAuthorityApiRequestVO, individualAuthorityHistory);

                    Optional.ofNullable(individualAuthorityApiRequestVO.getRegion()).filter(data -> data.size() > 0)
                        .ifPresent(data -> individualAuthorityHistory.setRegion(new Gson().toJson(data)));

                    Optional.ofNullable(individualAuthorityApiRequestVO.getAntecedents()).filter(data -> data.size() > 0)
                        .ifPresent(data -> individualAuthorityHistory.setAntecedents(new Gson().toJson(data)));

                    Optional.ofNullable(individualAuthorityApiRequestVO.getCertificate()).filter(data -> data.size() > 0)
                        .ifPresent(data -> individualAuthorityHistory.setCertificate(new Gson().toJson(data)));

                    IndividualAuthorityApiResponseVO result = IndividualAuthorityApiResponseVO.builder().build();
                    BeanUtils.copyProperties(individualAuthorityHistoryRepository.saveAndFlush(individualAuthorityHistory), result);

                    result.setRegion(individualAuthorityApiRequestVO.getRegion());
                    result.setAntecedents(individualAuthorityApiRequestVO.getAntecedents());
                    result.setCertificate(individualAuthorityApiRequestVO.getCertificate());

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1063, "해당 사업 권한 신청 내역 미존재"));
    }

    /**
     * 사업 참여 권한 신청(개인) 조회
     *
     * @param requestObject
     * @return
     */
    public IndividualAuthorityHistory getIndividualBusinessAuthority(IndividualAuthorityApiRequestVO requestObject) {
        List<IndividualAuthorityHistory> individualAuthorityHistories = individualAuthorityHistoryRepository.findAll(Example.of(IndividualAuthorityHistory.builder()
                        .userId(requestObject.getUserId())
                        .build())).stream()
                .sorted(Comparator.comparing(IndividualAuthorityHistory::getCreateDateTime).reversed())
                .collect(Collectors.toList());

        if (individualAuthorityHistories.size() > 0 && !individualAuthorityHistories.isEmpty()) {
            if (!individualAuthorityHistories.get(0).getBizAuthorityApprovalState().equals(Code.BIZ_AUTH_STATE.CANCEL_APPROVAL.enumCode))
                return individualAuthorityHistories.get(0);
            else return null;
        } else return null;
    }

    /**
     * 사업 참여 권한 관련 파일 업로드
     *
     * @param userId
     * @param pictureFile
     * @param signFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String userId, MultipartFile pictureFile, MultipartFile signFile) {
        /** 사업 참여 권한 신청 확인 */
        IndividualAuthorityHistory individualAuthorityHistory = individualAuthorityHistoryRepository.findOne(Example.of(IndividualAuthorityHistory.builder()
                                                    .userId(userId)
                                                    .bizAuthorityApprovalState(Code.BIZ_AUTH_STATE.APPLY.enumCode)
                                                    .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1061, "수정 가능한 사업 참여 권한 신청 내역 미존재"));

        String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

        Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getUserFolder()).toString());

        try {
            /** 파일 저장 및 파일 경로 셋팅 */
            Files.createDirectories(directoryPath);
            Optional.ofNullable(pictureFile)
                .ifPresent(file -> {
                    String pictureFilePath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                            .append(appConfig.getUploadFile().getUserFolder())
                            .append("/")
                            .append(userId + PICTURE_IMG_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                    try {
                        file.transferTo(new File(pictureFilePath));
                    } catch (IOException e) {
                        throw new KPFException(KPF_RESULT.ERROR1061, "파일 업로드 실패");
                    }
                    individualAuthorityHistory.setPictureFilePath(pictureFilePath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                    individualAuthorityHistory.setPictureFileSize(file.getSize());
                });

            Optional.ofNullable(signFile)
                .ifPresent(file -> {
                    String signFilePath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                            .append(appConfig.getUploadFile().getUserFolder())
                            .append("/")
                            .append(userId + SIGN_IMG_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                    try {
                        file.transferTo(new File(signFilePath));
                    } catch (IOException e) {
                        throw new KPFException(KPF_RESULT.ERROR1061, "파일 업로드 실패");
                    }
                    individualAuthorityHistory.setSignFilePath(signFilePath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                    individualAuthorityHistory.setSignFileSize(file.getSize());
                });
        } catch (IOException e1) {
            throw new KPFException(KPF_RESULT.ERROR9004, "파일 경로 확인 또는, 생성 실패");
        }
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }
}
