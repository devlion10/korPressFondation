package kr.or.kpf.lms.biz.user.webuser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.user.webuser.service.UserService;
import kr.or.kpf.lms.biz.user.webuser.vo.ChangePassword;
import kr.or.kpf.lms.biz.user.webuser.vo.CreateOrganizationInfo;
import kr.or.kpf.lms.biz.user.webuser.vo.UpdateOrganizationInfo;
import kr.or.kpf.lms.biz.user.webuser.vo.request.*;
import kr.or.kpf.lms.biz.user.webuser.vo.response.OrganizationApiResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.OrganizationViewResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.UserApiResponseVO;
import kr.or.kpf.lms.biz.user.webuser.vo.response.UserViewResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSApiControllerSupport;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import kr.or.kpf.lms.framework.model.CSSearchMap;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.session.Session;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;

/**
 * 유저 정보 API Controller
 */
@Tag(name = "User Management", description = "유저 정보 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
public class UserApiController extends CSApiControllerSupport {

    private final UserService userService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param userViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample1", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody UserViewRequestVO userViewRequestVO) {
        return null;
    }

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param organizationViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample2", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationViewResponseVO> swaggerUse2(HttpServletRequest request, HttpServletResponse response, @RequestBody OrganizationViewRequestVO organizationViewRequestVO) {
        return null;
    }

    /**
     * 이용 약관 동의
     *
     * @param request
     * @param response
     * @param joinTermsApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이용 약관 동의 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "이용 약관 동의", description = "이용 약관 동의에 대한 처리를 한다.")
    @PostMapping(value = "/join/terms", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> joinTerms(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {JoinTerms.class}) @NotNull @RequestBody JoinTermsApiRequestVO joinTermsApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.joinTerms(joinTermsApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1006, "이용 약관 처리 실패")));
    }

    public interface JoinTerms {}

    /**
     * 유저 체크
     *
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 체크 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "유저 체크", description = "유저를 체크 한다.")
    @GetMapping(value = "/check/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> check(HttpServletRequest request, HttpServletResponse response,
                                                     @Parameter(description = "회원 ID") @PathVariable(value = "userId", required = true) String userId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.check(userId))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1002, "이미 존재하는 회원정보")));
    }

    /**
     * 자녀 체크
     *
     * @param request
     * @param response
     * @param userChildApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자녀 체크 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "자녀 체크", description = "가입 자녀를 체크 한다.")
    @PostMapping(value = "/child_check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> childCheck(HttpServletRequest request, HttpServletResponse response,
                                                          @NotNull @RequestBody UserChildApiRequestVO userChildApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.childCheck(userChildApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1002, "이미 존재하는 회원정보")));
    }

    /**
     * 회원 정보 생성
     *
     * @param request
     * @param response
     * @param userApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 생성 성공", content = @Content(schema = @Schema(implementation = UserApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "회원 정보 생성", description = "회원 정보를 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserApiResponseVO> createUserInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {CreateUser.class}) @NotNull @RequestBody UserApiRequestVO userApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.createUserInfo(request, userApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1001, "회원 정보 생성 실패")));
    }

    public interface CreateUser {}

    /**
     * 회원 정보 업데이트
     *
     * @param request
     * @param response
     * @param userApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공", content = @Content(schema = @Schema(implementation = UserApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "회원 정보 업데이트", description = "회원 정보를 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserApiResponseVO> updateUserInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {UpdateUser.class}) @NotNull @RequestBody UserApiRequestVO userApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.updateUserInfo(request, userApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "회원 정보 수정 실패")));
    }

    public interface UpdateUser {}

    /**
     * 회원 탈퇴 신청
     *
     * @param request
     * @param response
     * @param userApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 신청 성공", content = @Content(schema = @Schema(implementation = UserApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "회원 탈퇴 신청", description = "회원 탈퇴 신청한다.")
    @PutMapping(value = "/withdrawal/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserApiResponseVO> withdrawalUserInfo(HttpServletRequest request, HttpServletResponse response,
                                                            @Validated(value = {WithdrawalUser.class}) @NotNull @RequestBody UserApiRequestVO userApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.withdrawalUser(userApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "회원 탈퇴 신청 실패")));
    }

    public interface WithdrawalUser {}

    /**
     * 회원 정보 삭제(회원 탈퇴)
     *
     * @param request
     * @param response
     * @param userApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 삭제(회원 탈퇴) 성공", content = @Content(schema = @Schema(implementation = UserApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "회원 정보 삭제(회원 탈퇴)", description = "회원 정보 삭제(회원 탈퇴) 한다.")
    @DeleteMapping(value = "/delete/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteUserInfo(HttpServletRequest request, HttpServletResponse response,
                                                              @Validated(value = {DeleteUser.class}) @NotNull @RequestBody UserApiRequestVO userApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.deleteUserInfo(userApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1005, "회원 정보 삭제 실패")));
    }

    public interface DeleteUser {}

    /**
     * 비밀번호 확인
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 확인 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "비밀번호 확인", description = "비밀번호를 확인한다.")
    @PostMapping(value = "/check-password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> checkPassword(HttpServletRequest request, HttpServletResponse response,
                                                             @Validated(value = {ChangePassword.class}) @NotNull @RequestBody UserPWApiRequestVO userPWApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.checkPassword(userPWApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1004, "회원 정보 조회 실패")));
    }

    /**
     * 비밀번호 변경 (비밀번호 분실 시...)
     *
     * @param request
     * @param response
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "비밀번호 변경", description = "비밀번호를 변경한다.")
    @PutMapping(value = "/change-password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> changePassword(HttpServletRequest request, HttpServletResponse response, Authentication authentication, Model model,
                                                              @Validated(value = {ChangePassword.class}) @NotNull @RequestBody UserApiRequestVO userApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.changePassword(request, userApiRequestVO, authentication))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1003, "회원 정보 수정 실패")));
    }

    /**
     * 재직증명서 업로드 API
     *
     * @param request
     * @param response
     * @param userId
     * @param attachFile
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재직증명서 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "재직증명서 업로드", description = "재직증명서 파일을 업로드한다.")
    @PutMapping(value = "/upload/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "회원 ID") @PathVariable(value = "userId", required = true) String userId,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.fileUpload(userId, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "재직증명서 업로드 실패")));
    }

    /**
     * 기관 정보 조회 API
     *
     * @param request
     * @param response
     * @param pageable
     * @param organizationCode
     * @param organizationName
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @Operation(operationId = "User", summary = "기관 정보 조회", description = "기관 정보 조회한다.")
    @GetMapping(value = "/organization", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOrganizationInfo(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable,
                                                      @RequestParam(value="organizationCode", required = false) String organizationCode,
                                                      @RequestParam(value="organizationName", required = false) String organizationName,
                                                      @RequestParam(value="organizationType", required = false) String organizationType) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> resultPaging(userService.getOrganizationInfo((OrganizationViewRequestVO) params(OrganizationViewRequestVO.class, searchMap, pageable)), Arrays.asList()))
                        .orElse(new HashMap<>()));
    }

    /**
     * 소속 기관 정보 생성
     *
     * @param request
     * @param response
     * @param organizationApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소속 기관 정보 생성 성공", content = @Content(schema = @Schema(implementation = OrganizationApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "소속 기관 정보 생성", description = "소속 기관 정보를 생성한다.")
    @PostMapping(value = "/organization/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationApiResponseVO> createOrganizationInfo(HttpServletRequest request, HttpServletResponse response,
                                                                            @Validated(value = {CreateOrganizationInfo.class}) @NotNull @RequestBody OrganizationApiRequestVO organizationApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.createOrganizationInfo(organizationApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1031, "소속 기관 정보 생성 실패")));
    }

    /**
     * 소속 기관(학교) 정보 생성
     *
     * @param request
     * @param response
     * @param organizationApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소속 기관(학교) 정보 생성 성공", content = @Content(schema = @Schema(implementation = OrganizationApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "소속 기관(학교) 정보 생성", description = "소속 기관(학교) 정보를 생성한다.")
    @PostMapping(value = "/school/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationApiResponseVO> createSchoolInfo(HttpServletRequest request, HttpServletResponse response,
                                                                            @Validated(value = {CreateOrganizationInfo.class}) @NotNull @RequestBody OrganizationApiRequestVO organizationApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.createSchoolInfo(organizationApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1031, "소속 학교 정보 생성 실패")));
    }

    /**
     * 소속 기관 정보 업데이트
     *
     * @param request
     * @param response
     * @param organizationApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소속 기관 정보 수정 성공", content = @Content(schema = @Schema(implementation = OrganizationApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "소속 기관 정보 업데이트", description = "소속 기관 정보를 업데이트한다.")
    @PutMapping(value = "/organization/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationApiResponseVO> updateOrganizationInfo(HttpServletRequest request, HttpServletResponse response,
                                                                            @Validated(value = {UpdateOrganizationInfo.class}) @NotNull @RequestBody OrganizationApiRequestVO organizationApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.updateOrganizationInfo(organizationApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1033, "소속 기관 정보 수정 실패")));
    }

    /**
     * 기관 정보 관련 첨부파일 업로드 API
     *
     * @param request
     * @param response
     * @param organizationCode
     * @param attachFile
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기관 정보 관련 첨부파일 업로드 성공", content = @Content(schema = @Schema(implementation = CSResponseVOSupport.class)))})
    @Operation(operationId = "User", summary = "기관 정보 관련 첨부파일 업로드", description = "기관 정보 관련 첨부파일을 업로드한다.")
    @PutMapping(value = "/organization/upload/{organizationCode}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> organizationFileUpload(HttpServletRequest request, HttpServletResponse response,
                                                          @Parameter(description = "기관코드") @PathVariable(value = "organizationCode", required = true) String organizationCode,
                                                          @Parameter(description = "첨부파일") @RequestPart(value = "attachFile", required = true) MultipartFile attachFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(userService.organizationFileUpload(organizationCode, attachFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "기관 정보 관련 첨부파일 업로드 실패")));
    }


    @Tag(name = "Media Management", description = "매체 관리 API")
    @Operation(operationId="Media", summary = "매체 정보 조회", description = "매체 정보 조회한다.")
    @GetMapping(value = "/media", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOrganizationInfo(HttpServletRequest request, HttpServletResponse response, @PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(CSSearchMap.of(request))
                        .map(searchMap -> resultPaging(userService.getMediaInfo((OrganizationMediaViewRequestVO) params(OrganizationMediaViewRequestVO.class, searchMap, pageable)), Arrays.asList()))
                        .orElse(new HashMap<>()));
    }
}