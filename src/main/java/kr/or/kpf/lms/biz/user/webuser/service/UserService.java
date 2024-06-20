package kr.or.kpf.lms.biz.user.webuser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kr.or.kpf.lms.biz.user.webuser.vo.NiceBody;
import kr.or.kpf.lms.biz.user.webuser.vo.NiceHeader;
import kr.or.kpf.lms.biz.user.webuser.vo.request.*;
import kr.or.kpf.lms.biz.user.webuser.vo.response.NiceResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.OrganizationApiResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.UserApiResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.UserViewResponseVO;
import kr.or.kpf.lms.common.client.CSWebClient;
import kr.or.kpf.lms.common.encrypt.SecurityUtil;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSServiceSupport;
import kr.or.kpf.lms.common.support.Code;
import kr.or.kpf.lms.common.support.util.CustomUtil;
import kr.or.kpf.lms.config.security.vo.KoreaPressFoundationUserDetails;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSPageImpl;
import kr.or.kpf.lms.repository.CommonUserRepository;
import kr.or.kpf.lms.repository.LmsUserRepository;
import kr.or.kpf.lms.repository.OrganizationInfoRepository;
import kr.or.kpf.lms.repository.SelfAuthenticationHistoryRepository;
import kr.or.kpf.lms.repository.entity.BizInstructor;
import kr.or.kpf.lms.repository.entity.LmsUser;
import kr.or.kpf.lms.repository.entity.OrganizationInfo;
import kr.or.kpf.lms.repository.entity.SelfAuthenticationHistory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 유저 정보 API 관련 Controller
 */
@Service
@RequiredArgsConstructor
public class UserService extends CSServiceSupport {

    private static final String PREFIX_ORGANIZATION = "ORG";

    private static final String PROOF_EMPLOYMENT_IMG_TAG = "_PROOF_EMPLOYMENT";
    private static final String ORGANIZATION_IMG_TAG = "_ORGANIZATION";
    private static final String BIZREG_IMG_TAG = "_BIZREG";

    private static final String TOKEN_URI = "/digital/niceid/api/v1.0/common/crypto/token";

    private final CSWebClient client;

    private final CommonUserRepository commonUserRepository;
    private final LmsUserRepository lmsUserRepository;
    private final OrganizationInfoRepository organizationInfoRepository;
    private final SelfAuthenticationHistoryRepository selfAuthenticationHistoryRepository;

    public UserViewResponseVO getUserInfo(String userId) {
        UserViewResponseVO result = UserViewResponseVO.builder().build();
        BeanUtils.copyProperties(lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(userId).build()))
                                    .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1004, "유효한 회원정보 미존재")), result);
        return result;
    }

    public <T> T getUserBizInfo(UserBizViewRequestVO requestObject) {
        return (T) commonUserRepository.findEntity(requestObject);
    }

    /**
     * 이용 약관 동의 처리
     *  
     * @param joinTermsApiRequestVO
     * @return
     */
    public CSResponseVOSupport joinTerms(JoinTermsApiRequestVO joinTermsApiRequestVO) {
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * User Pass 인증
     *
     * @param request
     * @param type
     * @return
     */
    public Map<String, String> selfAuthentication(HttpServletRequest request, String type) {
        String sessionId = request.getSession().getId();
        logger.info("본인인증 요청: {}", sessionId);

        NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();

        String sSiteCode = appConfig.getSelfAuthentication().getSiteCode();			// NICE로부터 부여받은 사이트 코드
        String sSitePassword = appConfig.getSelfAuthentication().getSitePassword();		// NICE로부터 부여받은 사이트 패스워드

        String sRequestNumber = UUID.randomUUID().toString();        	// 요청 번호, 이는 성공/실패후에 같은 값으로 되돌려주게 되므로

        // 업체에서 적절하게 변경하여 쓰거나, 아래와 같이 생성한다.
        sRequestNumber = niceCheck.getRequestNO(sSiteCode);


        String sAuthType = "";      	// 없으면 기본 선택화면, M(휴대폰), X(인증서공통), U(공동인증서), F(금융인증서), S(PASS인증서), C(신용카드)
        String customize 	= "";		//없으면 기본 웹페이지 / Mobile : 모바일페이지

        String sReturnUrl = "";
        String youth = request.getParameter("youth");
        if (youth != null && youth.equals("youth")) {
            sReturnUrl = appConfig.getDomain(request)+(type.equals("join") ? appConfig.getSelfAuthentication().getReturnUrl3() : appConfig.getSelfAuthentication().getReturnUrl4());      // 성공시 이동될 URL
        } else sReturnUrl = appConfig.getDomain(request)+(type.equals("join") ? appConfig.getSelfAuthentication().getReturnUrl() : appConfig.getSelfAuthentication().getReturnUrl2());      // 성공시 이동될 URL
        // CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
        //리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~

        String sErrorUrl = appConfig.getDomain(request)+appConfig.getSelfAuthentication().getFailureUrl();          // 실패시 이동될 URL

        // 입력될 plain 데이타를 만든다.
        String sPlainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
                "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;

        String sMessage = "";
        String sEncData = "";

        int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
        if( iReturn == 0 ) { sEncData = niceCheck.getCipherData(); }
        else if( iReturn == -1) { sMessage = "암호화 시스템 에러입니다."; }
        else if( iReturn == -2) { sMessage = "암호화 처리오류입니다."; }
        else if( iReturn == -3) { sMessage = "암호화 데이터 오류입니다."; }
        else if( iReturn == -9) { sMessage = "입력 데이터 오류입니다."; }
        else { sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn; }

        /** 인증 요청 내역 저장 */
        selfAuthenticationHistoryRepository.saveAndFlush(SelfAuthenticationHistory.builder()
                .sessionId(sessionId)
                .requstEncryptData(sEncData)
                .build());

        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("userId", request.getParameter("userId"));
        resultMap.put("encryptData", sEncData);
        return resultMap;
    }

    /**
     * 인증 토큰 획득
     *
     * @param niceBody
     * @return
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    public NiceResponseVO token(NiceBody niceBody) {
        try {
            return new ObjectMapper().readValue(Optional.ofNullable(niceBody)
                    .map(body -> {
                        String apiURL = appConfig.getSelfAuthentication().getUrl() + TOKEN_URI;
                        NiceRequestVO niceRequest = NiceRequestVO.builder()
                                .header(NiceHeader.builder().build())
                                .body(body)
                                .build();
                        logger.info("본인인증(NICE) 토큰 정보 요청: {}", niceRequest.toString());

                        RestTemplate restTemplate = client.settingWebProtocol();

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                        headers.set("Authorization", this.getAuthorizationHeader());
                        headers.set("ProductID", appConfig.getSelfAuthentication().getProductCode());
                        //1. 통신 객체 생성
                        HttpEntity<NiceRequestVO> request = new HttpEntity<>(niceRequest, headers);
                        //2. 토큰 요청
                        ResponseEntity<String> response = restTemplate.exchange(apiURL, HttpMethod.POST, request, String.class);
                        logger.info("본인인증(NICE) 토큰 정보 응답: {}", response);

                        return response.getBody();
                    }).orElse(null), NiceResponseVO.class);
        } catch(JsonProcessingException e1) {
            logger.error("{}- {}", e1.getClass().getCanonicalName(), e1.getMessage(), e1);
            throw new KPFException(KPF_RESULT.ERROR9003, "Json 파싱 중 오류가 발생하였습니다.");
        } catch(Exception e2) {
            logger.error("{}- {}", e2.getClass().getCanonicalName(), e2.getMessage(), e2);
            throw new KPFException(KPF_RESULT.ERROR9999, "예상하지 못한 오류가 발생하였습니다.");
        }
    }

    /**
     * 본인인증 요청 데이터 Json 포맷팅
     *
     * @param transactionNo
     * @param siteCode
     * @return
     */
    public String requestFormJson(String transactionNo, String siteCode) {
        JsonObject reqJSON = new JsonObject();
        reqJSON.addProperty("requestno", transactionNo);
        reqJSON.addProperty("sitecode", siteCode);
        reqJSON.addProperty("returnurl", appConfig.getSelfAuthentication().getReturnUrl());
        reqJSON.addProperty("authtype", "M");
        reqJSON.addProperty("mobileco", "K");
        reqJSON.addProperty("methodtype", "POST");
        reqJSON.addProperty("popupyn", "Y");
        return new Gson().toJson(reqJSON);
    }

    /**
     * 본인인증 요청 헤더 생성
     *
     * @return 본인인증 요청 헤더값
     */
    private String getAuthorizationHeader() {
        return new StringBuilder("bearer ")
                .append(Base64.getEncoder()
                        .encodeToString(new StringBuilder(appConfig.getSelfAuthentication().getClientToken())
                                .append(":").append(CustomUtil.getTimeStamp()).append(":")
                                .append(appConfig.getSelfAuthentication().getClientId())
                                .toString()
                                .getBytes()))
                .toString();
    }

    /**
     * 본인인증 결과 처리
     *
     * @param request
     * @param type join/find
     * @return
     */
    public Map<String,String> selfAuthenticationResult(HttpServletRequest request, String type) {
        String sessionId = request.getSession().getId();

        NiceID.Check.CPClient niceCheck = new NiceID.Check.CPClient();

        String sEncodeData = requestReplace(request.getParameter("EncodeData"), "encodeData");

        String sSiteCode = appConfig.getSelfAuthentication().getSiteCode();            // NICE로부터 부여받은 사이트 코드
        String sSitePassword = appConfig.getSelfAuthentication().getSitePassword();        // NICE로부터 부여받은 사이트 패스워드

        String sCipherTime = "";            // 복호화한 시간
        String sRequestNumber = "";            // 요청 번호
        String sResponseNumber = "";        // 인증 고유번호
        String sAuthType = "";                // 인증 수단
        String sName = "";                    // 성명
        String sDupInfo = "";                // 중복가입 확인값 (DI_64 byte)
        String sConnInfo = "";                // 연계정보 확인값 (CI_88 byte)
        String sBirthDate = "";                // 생년월일(YYYYMMDD)
        String sGender = "";                // 성별
        String sNationalInfo = "";            // 내/외국인정보 (개발가이드 참조)
        String sMobileNo = "";                // 휴대폰번호
        String sMobileCo = "";                // 통신사
        String sMessage = "";
        String sPlainData = "";

        int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

        if (iReturn == 0) {
            sPlainData = niceCheck.getPlainData();
            sCipherTime = niceCheck.getCipherDateTime();
            // 데이타를 추출합니다.
            java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);

            sRequestNumber = (String) mapresult.get("REQ_SEQ");
            sResponseNumber = (String) mapresult.get("RES_SEQ");
            sAuthType = (String) mapresult.get("AUTH_TYPE");
            sName = (String) mapresult.get("NAME");
            sBirthDate = (String) mapresult.get("BIRTHDATE");
            sGender = (String) mapresult.get("GENDER");
            sNationalInfo = (String) mapresult.get("NATIONALINFO");
            sDupInfo = (String) mapresult.get("DI");
            sConnInfo = (String) mapresult.get("CI");
            sMobileNo = (String) mapresult.get("MOBILE_NO");

        } else if (iReturn == -1) {
            sMessage = "복호화 시스템 오류입니다.";
        } else if (iReturn == -4) {
            sMessage = "복호화 처리 오류입니다.";
        } else if (iReturn == -5) {
            sMessage = "복호화 해쉬 오류입니다.";
        } else if (iReturn == -6) {
            sMessage = "복호화 데이터 오류입니다.";
        } else if (iReturn == -9) {
            sMessage = "입력 데이터 오류입니다.";
        } else if (iReturn == -12) {
            sMessage = "사이트 패스워드 오류입니다.";
        } else {
            sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
        }

        /** 인증 실패시 Exception 처리 */
        if (iReturn != 0) {
            throw new KPFException(KPF_RESULT.ERROR1009, sMessage);
        }

        /** 인증 요청 내역 조회 */
        SelfAuthenticationHistory authenticationHistory = selfAuthenticationHistoryRepository.findAll(Example.of(SelfAuthenticationHistory.builder()
                        .sessionId(sessionId)
                        .build())).stream().sorted(Comparator.comparing(SelfAuthenticationHistory::getCreateDateTime).reversed()).findFirst()
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1008, "본인인증 요청 정보가 없습니다. 다시 시도해 주세요."));
        authenticationHistory.setResponseEncryptData(sEncodeData);
        /** 인증 결과 업데이트 */
        selfAuthenticationHistoryRepository.saveAndFlush(authenticationHistory);

        /** 회원 여부 확인 by DI */
        Map<String,String> result = new HashMap<>();
        String migrationYN = "N";

        if (request.getParameter("youth") != null) {
            List<LmsUser> users = lmsUserRepository.findAll(Example.of(LmsUser.builder().nokPhone(sMobileNo).state(Code.STATE.USE.enumCode).build()));
            // 해당 휴대폰번호로 부모 정보가 있을때
            if( users != null ){
                // 회원가입 진입일 시
                if (type.equals("join")) {
                    result.put("youth", request.getParameter("youth"));
                } else if (type.equals("find")) { // 아이디 또는 비밀번호 찾기 일시
                    String userIds = "";
                    String userNames = "";
                    for (LmsUser user : users) {
                        userIds = new StringBuilder(userIds).append(user.getUserId()).append(',').toString();
                        userNames = new StringBuilder(userNames).append(user.getUserName()).append(',').toString();
                    }
                    result.put("userIds", userIds);
                    result.put("userNames", userNames);
                    result.put("youth", request.getParameter("youth"));
                }else{
                    new KPFException(KPF_RESULT.ERROR1002, "정상적인 요청이 아닙니다.");
                }
            }else{
                if(type.equals("find")){
                    new KPFException(KPF_RESULT.ERROR1002, "회원정보가 존재하지 않습니다.");
                }
            }
        } else {
            LmsUser user = lmsUserRepository.findAll(Example.of(LmsUser.builder()
                    .phone(sMobileNo)
                    .state(Code.STATE.USE.enumCode).build()))
                    .stream().filter(data -> data != null && data.getNokDI() == null).sorted(Comparator.comparing(LmsUser::getCreateDateTime).reversed()).findFirst().orElse(null);
            // 해당 휴대폰번호로 회원 정보가 있을때
            if( user != null ){
                // 회원가입 진입일 시
                if (type.equals("join")) {
                    if( "Y".equals(user.getMigrationFlag()) ){
                        migrationYN = "Y";
                        result.put("userId", user.getUserId());
                    }else{
                        throw new KPFException(KPF_RESULT.ERROR1002, "이미 존재하는 회원정보 입니다.");
                    }
                } else if (type.equals("find")) { // 아이디 또는 비밀번호 찾기 일시
                    result.put("userId", user.getUserId());
                }else{
                    new KPFException(KPF_RESULT.ERROR1002, "정상적인 요청이 아닙니다.");
                }
            }else{
                if(type.equals("find")){
                    new KPFException(KPF_RESULT.ERROR1002, "회원정보가 존재하지 않습니다.");
                }
            }
        }

        /** 결과값 셋팅 */
        result.put("migrationYN", migrationYN);
        result.put("userName", sName);
        result.put("userPhone", sMobileNo);
        result.put("birthDate", sBirthDate);
        result.put("gender", sGender.equals("0") ? "2" : sGender);
        return result;
    }

    public String requestReplace(String paramValue, String gubun) {

        String result = "";

        if (paramValue != null) {
            paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            paramValue = paramValue.replaceAll("\\*", "");
            paramValue = paramValue.replaceAll("\\?", "");
            paramValue = paramValue.replaceAll("\\[", "");
            paramValue = paramValue.replaceAll("\\{", "");
            paramValue = paramValue.replaceAll("\\(", "");
            paramValue = paramValue.replaceAll("\\)", "");
            paramValue = paramValue.replaceAll("\\^", "");
            paramValue = paramValue.replaceAll("\\$", "");
            paramValue = paramValue.replaceAll("'", "");
            paramValue = paramValue.replaceAll("@", "");
            paramValue = paramValue.replaceAll("%", "");
            paramValue = paramValue.replaceAll(";", "");
            paramValue = paramValue.replaceAll(":", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll("#", "");
            paramValue = paramValue.replaceAll("--", "");
            paramValue = paramValue.replaceAll("-", "");
            paramValue = paramValue.replaceAll(",", "");
            if (gubun != "encodeData") {
                paramValue = paramValue.replaceAll("\\+", "");
                paramValue = paramValue.replaceAll("/", "");
                paramValue = paramValue.replaceAll("=", "");
            }
            result = paramValue;
        }
        return result;
    }

    /**
     * 본인인증 이력 획득
     *
     * @param request
     * @return
     */
    public Map<String, String> selfAuthenticationHistoryBySessionId(HttpServletRequest request) {
        String sessionId = request.getSession().getId();

        /** 인증 요청 내역 조회 */
        SelfAuthenticationHistory authenticationHistory = selfAuthenticationHistoryRepository.findAll(Example.of(SelfAuthenticationHistory.builder()
                        .sessionId(sessionId)
                        .build())).stream().sorted(Comparator.comparing(SelfAuthenticationHistory::getCreateDateTime).reversed()).findFirst()
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1008, "본인인증 이력 미존재"));

        NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
        String sEncodeData = requestReplace(authenticationHistory.getResponseEncryptData(), "encodeData");
        String sSiteCode = appConfig.getSelfAuthentication().getSiteCode();			// NICE로부터 부여받은 사이트 코드
        String sSitePassword = appConfig.getSelfAuthentication().getSitePassword();		// NICE로부터 부여받은 사이트 패스워드

        niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);
        // 데이타를 추출합니다.
        java.util.HashMap mapresult = niceCheck.fnParse(niceCheck.getPlainData());

        /** 결과값 셋팅 */
        Map<String,String> result = new HashMap<>();
        result.put("userName", (String)mapresult.get("NAME"));
        result.put("userPhone", (String)mapresult.get("MOBILE_NO"));
        result.put("di", (String)mapresult.get("DI"));
        return result;
    }

    /**
     * 유저 체크
     *
     * @param userId
     * @return
     */
    public CSResponseVOSupport check(String userId) {
        lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(userId).build()))
                .ifPresent(data -> {
                    throw new KPFException(KPF_RESULT.ERROR1002, "존재하는 회원 정보");
                });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 자녀 체크
     *
     * @param userChildApiRequestVO
     * @return
     */
    public CSResponseVOSupport childCheck(UserChildApiRequestVO userChildApiRequestVO) {
        String name = "";
        try {
            name = URLDecoder.decode(userChildApiRequestVO.getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new KPFException(KPF_RESULT.ERROR9006, "파일명 URL 인코드 실패");
        }
        lmsUserRepository.findOne(Example.of(LmsUser.builder()
                        .userName(name)
                        .birthDay(userChildApiRequestVO.getBirth())
                        .nokPhone(userChildApiRequestVO.getPhone())
                        .build()))
                .ifPresent(data -> {
                    throw new KPFException(KPF_RESULT.ERROR1002, "존재하는 회원 정보");
                });

        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 회원 정보 생성
     *
     * @param userApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public UserApiResponseVO createUserInfo(HttpServletRequest request, UserApiRequestVO userApiRequestVO) {
        LmsUser entity = LmsUser.builder().build();
        BeanUtils.copyProperties(userApiRequestVO, entity);

        Map<String, String> selfAuthenticationHistory = selfAuthenticationHistoryBySessionId(request);

        if (userApiRequestVO.getNokPhone() != null) {
            entity.setNokDI(String.valueOf(selfAuthenticationHistory.get("di")));
        } else {
            if (lmsUserRepository.findOne(Example.of(LmsUser.builder()
                    .di(String.valueOf(selfAuthenticationHistory.get("di")))
                    .build())).isPresent()) {
                throw new KPFException(KPF_RESULT.ERROR1002, "이미 회원가입된 DI 값 존재.");
            }
            entity.setDi(String.valueOf(selfAuthenticationHistory.get("di")));
        }

        UserApiResponseVO result = UserApiResponseVO.builder().build();
        /** 비밀번호 암호화 */
        entity.setPassword(SecurityUtil.hashPassword(String.valueOf(userApiRequestVO.getPassword()), userApiRequestVO.getUserId()));
        entity.setState(Code.USER_STATE.GENERAL.enumCode);
        /** 잠금 수 초기화 */
        entity.setLockCount(0);

        /** 언론인의 경우 승인 상태 N으로 */
        if (userApiRequestVO.getRoleGroup().equals(Code.WEB_USER_ROLE.JOURNALIST.enumCode)) {
            entity.setApproFlag(Code.JOURNALIST_STTS.N.enumCode);
        }
        /** 튜터 기본 승인 상태 N */
        entity.setTutorYn("N");

        /** 비밀번호 변경 날짜 이력 */
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 포맷 적용
        String formatedNow = now.format(formatter);
        entity.setPasswordChangeDate(formatedNow);

        BeanUtils.copyProperties(lmsUserRepository.saveAndFlush(entity), result);
        return result;
    }

    /**
     * 회원 정보 업데이트
     *
     * @param userApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public UserApiResponseVO updateUserInfo(HttpServletRequest request, UserApiRequestVO userApiRequestVO) {
        return lmsUserRepository.findOne(Example.of(LmsUser.builder()
                                                        .userId(userApiRequestVO.getUserId())
                                                        .build()))
                .map(userMaster -> {
                    copyNonNullObject(userApiRequestVO, userMaster);

                    if("C".equals(userApiRequestVO.getMigrationFlag())) {
                        Map<String, String> selfAuthenticationHistory = selfAuthenticationHistoryBySessionId(request);
                        if (lmsUserRepository.findOne(Example.of(LmsUser.builder()
                                .di(String.valueOf(selfAuthenticationHistory.get("di")))
                                .build())).isPresent()) {
                            throw new KPFException(KPF_RESULT.ERROR1002, "이미 회원가입된 DI 값 존재.");
                        }
                        userMaster.setDi(String.valueOf(selfAuthenticationHistory.get("di")));
                        userMaster.setMigrationFlag(userApiRequestVO.getMigrationFlag());
                        /** 비밀번호 변경 날짜 이력 */
                        LocalDate now = LocalDate.now();
                        // 포맷 정의
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        // 포맷 적용
                        String formatedNow = now.format(formatter);
                        userMaster.setPasswordChangeDate(formatedNow);
                    }

                    UserApiResponseVO result = UserApiResponseVO.builder().build();
                    BeanUtils.copyProperties(lmsUserRepository.saveAndFlush(userMaster), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 회원 정보 미존재"));
    }

    /**
     * 회원 정보 삭제(회원 탈퇴)
     *
     * @param userApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport deleteUserInfo(UserApiRequestVO userApiRequestVO) {
        lmsUserRepository.delete(lmsUserRepository.findOne(Example.of(LmsUser.builder()
                                                                                .userId(userApiRequestVO.getUserId())
                                                                                .password(userApiRequestVO.getUserId())
                                                                                .build()))
                                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1004, "탈퇴 대상 회원 조회 실패")));
        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
    }

    /**
     * 회원 탈퇴
     *
     * @param userApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public UserApiResponseVO withdrawalUser(UserApiRequestVO userApiRequestVO) {
        return lmsUserRepository.findOne(Example.of(LmsUser.builder()
                        .userId(userApiRequestVO.getUserId())
                        .build()))
                .map(userMaster -> {
                    SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date now = new Date();
                    String nowStr = str.format(now);
                    userMaster.setWithDrawDate(nowStr);
                    userMaster.setState(Code.USER_STATE.WITHDRAWAL.enumCode);
                    userMaster.setDi(null);
                    userMaster.setEmail(null);

                    String nm = userMaster.getUserName();
                    String phon = userMaster.getPhone();
                    String birth = userMaster.getBirthDay();
                    StringBuffer sbNm = new StringBuffer(nm);
                    StringBuffer sbPhon = new StringBuffer(phon);
                    StringBuffer sbBirth = new StringBuffer(birth);
                    String reverseNm = sbNm.reverse().toString();
                    String reversePhon = sbPhon.reverse().toString();
                    String reverseBirth = sbBirth.reverse().toString();
                    userMaster.setUserName(reverseNm);
                    userMaster.setPhone(reversePhon);
                    userMaster.setBirthDay(reverseBirth);

                    UserApiResponseVO result = UserApiResponseVO.builder().build();
                    BeanUtils.copyProperties(lmsUserRepository.saveAndFlush(userMaster), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 회원 정보 미존재"));
    }

    /**
     * 비밀번호 확인
     *
     * @param userPWApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport checkPassword(UserPWApiRequestVO userPWApiRequestVO) {
        String check = SecurityUtil.hashPassword(userPWApiRequestVO.getPassword(), userPWApiRequestVO.getUserId());
        if (lmsUserRepository.findOne(Example.of(LmsUser.builder().userId(userPWApiRequestVO.getUserId()).password(check).build())).isPresent()) {
            return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
        } else {
            return CSResponseVOSupport.of(KPF_RESULT.ERROR1004);
        }
    }

    /**
     * 비밀번호 변경 (비밀번호 분실 시...)
     *
     * @param userApiRequestVO
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport changePassword(HttpServletRequest request, UserApiRequestVO userApiRequestVO, Authentication authentication) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") != null) {
            String userId = (String) session.getAttribute("userId");
            if (lmsUserRepository.findOne(Example.of(LmsUser.builder()
                    .userId(userId)
                    .build())).isPresent()) {
                return lmsUserRepository.findOne(Example.of(LmsUser.builder()
                                .userId(userId)
                                .build()))
                        .map(userMaster -> {
                            if (userId.equals(userApiRequestVO.getUserId())) {
                                /** 비밀번호 암호화 - 재가입 */
                                userMaster.setPassword(SecurityUtil.hashPassword(String.valueOf(userApiRequestVO.getPassword()), userId));
                            } else {
                                return CSResponseVOSupport.of(KPF_RESULT.ERROR9997);
                            }

                            /** 비밀번호 변경 날짜 이력 */
                            LocalDate now = LocalDate.now();
                            // 포맷 정의
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                            // 포맷 적용
                            String formatedNow = now.format(formatter);
                            userMaster.setPasswordChangeDate(formatedNow);

                            userMaster.setIsLock(false);
                            userMaster.setLockCount(0);
                            lmsUserRepository.saveAndFlush(userMaster);

                            return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
                        }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 회원 정보 미존재"));
            } else {
                return CSResponseVOSupport.of(KPF_RESULT.ERROR1004);
            }
        } else if(authentication != null) {
            String userId = ((KoreaPressFoundationUserDetails) authentication.getPrincipal()).getUserId();
            return lmsUserRepository.findOne(Example.of(LmsUser.builder()
                            .userId(userId)
                            .build()))
                    .map(userMaster -> {
                        if (userId.equals(userApiRequestVO.getUserId())) {
                            /** 비밀번호 암호화 - 개인정보 관리로 변경 */
                            userMaster.setPassword(SecurityUtil.hashPassword(String.valueOf(userApiRequestVO.getPassword()), userId));
                        } else {
                            return CSResponseVOSupport.of(KPF_RESULT.ERROR9997);
                        }

                        /** 비밀번호 변경 날짜 이력 */
                        LocalDate now = LocalDate.now();
                        // 포맷 정의
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        // 포맷 적용
                        String formatedNow = now.format(formatter);
                        userMaster.setPasswordChangeDate(formatedNow);

                        userMaster.setIsLock(false);
                        userMaster.setLockCount(0);
                        lmsUserRepository.saveAndFlush(userMaster);

                        return CSResponseVOSupport.of(KPF_RESULT.SUCCESS);
                    }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "해당 회원 정보 미존재"));
        } else {
            return CSResponseVOSupport.of(KPF_RESULT.ERROR1004);
        }
    }

    /**
     * 재직증명서 업로드
     *
     * @param userId
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport fileUpload(String userId, MultipartFile attachFile) {
        /** 회원 확인 */

        LmsUser lmsUser = lmsUserRepository.findOne(Example.of(LmsUser.builder()
                        .userId(userId)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1004, "해당 회원 미존재."));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getUserFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getUserFolder())
                                    .append("/")
                                    .append(userId + PROOF_EMPLOYMENT_IMG_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            lmsUser.setAttachFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            lmsUser.setFileSize(file.getSize());
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
     * 매체사(법인명) 등록 시 사업자등록증 업로드
     *
     * @param organizationCode
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport bnsFileUpload(String organizationCode, MultipartFile attachFile) {
        /** 기관 정보 확인 */
        OrganizationInfo organizationInfo = organizationInfoRepository.findOne(Example.of(OrganizationInfo.builder()
                        .organizationCode(organizationCode)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1033, "해당 기관 정보 미존재"));

        logger.info("__bnsFileUpload");

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getBizRegFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getBizRegFolder())
                                    .append("/")
                                    .append(organizationCode + BIZREG_IMG_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            organizationInfo.setAttachFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            organizationInfo.setFileSize(file.getSize());
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
     * 기관 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getOrganizationInfo(OrganizationViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 기관 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getOrganizationMediaInfo(OrganizationMediaViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

    /**
     * 소속 기관 정보 생성
     *
     * @param organizationApiRequestVO
     * @return
     */
    public OrganizationApiResponseVO createOrganizationInfo(OrganizationApiRequestVO organizationApiRequestVO, MultipartFile attachFile) {
        OrganizationInfo entity = OrganizationInfo.builder().build();
        BeanUtils.copyProperties(organizationApiRequestVO, entity);
        if (organizationInfoRepository.findOne(Example.of(OrganizationInfo.builder()
                                                            .bizLicenseNumber(organizationApiRequestVO.getBizLicenseNumber())
                                                            .build())).isPresent()) {
            throw new KPFException(KPF_RESULT.ERROR1032, "이미 등록되어 있는 사업자 등록번호 존재");
        } else {
            OrganizationApiResponseVO result = OrganizationApiResponseVO.builder().build();
            BeanUtils.copyProperties(organizationApiRequestVO, entity);
            entity.setOrganizationCode(commonUserRepository.generateCode(PREFIX_ORGANIZATION));
            BeanUtils.copyProperties(organizationInfoRepository.saveAndFlush(entity), result);


            CSResponseVOSupport bnsFileUploadRes=new CSResponseVOSupport();
            bnsFileUploadRes=bnsFileUpload(entity.getOrganizationCode(),attachFile);


            return result;
        }
    }

    /**
     * 소속 기관(학교) 정보 생성
     *
     * @param organizationApiRequestVO
     * @return
     */
    public OrganizationApiResponseVO createSchoolInfo(OrganizationApiRequestVO organizationApiRequestVO) {
        OrganizationInfo entity = OrganizationInfo.builder().build();
        BeanUtils.copyProperties(organizationApiRequestVO, entity);
        if (organizationInfoRepository.findOne(Example.of(OrganizationInfo.builder()
                .organizationName(organizationApiRequestVO.getOrganizationName())
                .build())).isPresent()) {
            throw new KPFException(KPF_RESULT.ERROR1032, "이미 등록되어 있는 학교명 존재");
        } else {
            OrganizationApiResponseVO result = OrganizationApiResponseVO.builder().build();
            BeanUtils.copyProperties(organizationApiRequestVO, entity);
            entity.setOrganizationCode(commonUserRepository.generateCode(PREFIX_ORGANIZATION));
            BeanUtils.copyProperties(organizationInfoRepository.saveAndFlush(entity), result);

            return result;
        }
    }

    /**
     * 소속 기관 정보 업데이트
     *
     * @param organizationApiRequestVO
     * @return
     */
    public OrganizationApiResponseVO updateOrganizationInfo(OrganizationApiRequestVO organizationApiRequestVO) {
        return organizationInfoRepository.findOne(Example.of(OrganizationInfo.builder()
                                                                .organizationCode(organizationApiRequestVO.getOrganizationCode())
                                                                .build()))
                .map(organizationInfo -> {
                    copyNonNullObject(organizationApiRequestVO, organizationInfo);

                    OrganizationApiResponseVO result = OrganizationApiResponseVO.builder().build();
                    BeanUtils.copyProperties(organizationInfoRepository.saveAndFlush(organizationInfo), result);

                    return result;
                }).orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1033, "해당 기관 정보 미존재"));
    }

    /**
     * 기관 정보 관련 첨부파일 업로드
     *
     * @param organizationCode
     * @param attachFile
     * @return
     */
    @Transactional(rollbackOn = {Exception.class})
    public CSResponseVOSupport organizationFileUpload(String organizationCode, MultipartFile attachFile) {
        /** 기관 정보 확인 */
        OrganizationInfo organizationInfo = organizationInfoRepository.findOne(Example.of(OrganizationInfo.builder()
                        .organizationCode(organizationCode)
                        .build()))
                .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1033, "해당 기관 정보 미존재"));

        /** 파일 저장 및 파일 경로 셋팅 */
        Optional.ofNullable(attachFile)
                .ifPresent(file -> {
                    Path directoryPath = Paths.get(new StringBuilder(appConfig.getUploadFile().getUploadContextPath()).append(appConfig.getUploadFile().getMyQnaFolder()).toString());
                    try {
                        Files.createDirectories(directoryPath);
                        try {
                            String imageSequence = new StringBuilder("_").append(new SimpleDateFormat("yyMMddHHmmss").format(new Date())).toString();

                            String attachFilepath = new StringBuilder(appConfig.getUploadFile().getUploadContextPath())
                                    .append(appConfig.getUploadFile().getMyQnaFolder())
                                    .append("/")
                                    .append(organizationCode + ORGANIZATION_IMG_TAG + imageSequence + "." + StringUtils.substringAfter(file.getOriginalFilename(), ".")).toString();
                            file.transferTo(new File(attachFilepath));
                            organizationInfo.setAttachFilePath(attachFilepath.replace(appConfig.getUploadFile().getUploadContextPath(), ""));
                            organizationInfo.setFileSize(file.getSize());
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
     * 매체 정보 조회
     *
     * @param requestObject
     * @return
     * @param <T>
     */
    public <T> Page<T> getMediaInfo(OrganizationMediaViewRequestVO requestObject) {
        return (Page<T>) Optional.ofNullable(commonUserRepository.findEntityList(requestObject))
                .orElse(CSPageImpl.of(new ArrayList<>(), requestObject.getPageable(), 0));
    }

}

