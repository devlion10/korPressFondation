package kr.or.kpf.lms.biz.user.authority.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.user.authority.service.AuthorityService;
import kr.or.kpf.lms.biz.user.authority.vo.request.AuthorityViewRequestVO;
import kr.or.kpf.lms.biz.user.authority.vo.request.IndividualAuthorityApiRequestVO;
import kr.or.kpf.lms.biz.user.authority.vo.request.OrganizationAuthorityApiRequestVO;
import kr.or.kpf.lms.biz.user.authority.vo.response.AuthorityViewResponseVO;
import kr.or.kpf.lms.biz.user.authority.vo.response.IndividualAuthorityApiResponseVO;
import kr.or.kpf.lms.biz.user.authority.vo.response.OrganizationAuthorityApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 회원 가입 > 회원정보 입력(권한 신청) API 관련 Controller
 */
@Tag(name = "User Management", description = "유저 정보 API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/user/authority")
public class AuthorityApiController {

    private final AuthorityService authorityService;

    /**
     * Sample (객체 Swagger 표출을 위해서 사용)
     *
     * @param request
     * @param response
     * @param authorityViewRequestVO
     * @return
     */
    @Tag(name = "Swagger USE(NOT API)", description = "사용하지 않는 API(객체 표출용)")
    @PostMapping(value = "/sample", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorityViewResponseVO> swaggerUse1(HttpServletRequest request, HttpServletResponse response, @RequestBody AuthorityViewRequestVO authorityViewRequestVO) {
        return null;
    }

    /**
     * 사업 참여 권한 신청(기관/학교)
     *
     * @param request
     * @param response
     * @param organizationAuthorityApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 참여 권한 신청(기관/학교) 생성 성공", content = @Content(schema = @Schema(implementation = OrganizationAuthorityApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "사업 참여 권한 신청(기관/학교) 생성", description = "사업 참여 권한 신청(기관/학교)를 생성한다.")
    @PostMapping(value = "/organization/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationAuthorityApiResponseVO> createOrganizationBusinessAuthority(HttpServletRequest request, HttpServletResponse response,
                                                                                                  @Validated(value = {CreateOrganizationAuthority.class}) @NotNull @RequestBody OrganizationAuthorityApiRequestVO organizationAuthorityApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(authorityService.createOrganizationBusinessAuthority(organizationAuthorityApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1061, "사업 참여 권한 신청(기관/학교) 생성 실패")));
    }

    public interface CreateOrganizationAuthority {}

    /**
     * 사업 참여 권한 신청(개인)
     *
     * @param request
     * @param response
     * @param individualAuthorityApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 참여 권한 신청(개인) 생성 성공", content = @Content(schema = @Schema(implementation = IndividualAuthorityApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "사업 참여 권한 신청(개인) 생성", description = "사업 참여 권한 신청(개인)를 생성한다.")
    @PostMapping(value = "/individual/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndividualAuthorityApiResponseVO> createIndividualBusinessAuthority(HttpServletRequest request, HttpServletResponse response,
                                                                                          @Validated(value = {CreateIndividualAuthority.class}) @NotNull @RequestBody IndividualAuthorityApiRequestVO individualAuthorityApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(authorityService.createIndividualBusinessAuthority(individualAuthorityApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1061, "사업 참여 권한 신청(개인) 생성 실패")));
    }

    public interface CreateIndividualAuthority {}

    /**
     * 사업 참여 권한 신청(기관/학교) 업데이트
     *
     * @param request
     * @param response
     * @param organizationAuthorityApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 참여 권한 신청(기관/학교) 수정 성공", content = @Content(schema = @Schema(implementation = OrganizationAuthorityApiResponseVO.class)))})
    @Operation(operationId="User", summary = "사업 참여 권한 신청(기관/학교) 업데이트", description = "사업 참여 권한 신청(기관/학교)를 업데이트한다.")
    @PutMapping(value = "/organization/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationAuthorityApiResponseVO> updateOrganizationApplicationInfo(HttpServletRequest request, HttpServletResponse response,
                                                                                                @Validated(value = {UpdateOrganizationAuthority.class}) @NotNull @RequestBody OrganizationAuthorityApiRequestVO organizationAuthorityApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(authorityService.updateOrganizationBusinessAuthority(organizationAuthorityApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1063, "사업 참여 권한 신청(기관/학교) 수정 실패")));
    }

    public interface UpdateOrganizationAuthority {}

    /**
     * 사업 참여 권한 신청(개인) 업데이트
     *
     * @param request
     * @param response
     * @param individualAuthorityApiRequestVO
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 참여 권한 신청(개인) 업데이트 성공", content = @Content(schema = @Schema(implementation = IndividualAuthorityApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "사업 참여 권한 신청(개인) 업데이트", description = "사업 참여 권한 신청(개인)를 업데이트한다.")
    @PutMapping(value = "/individual/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IndividualAuthorityApiResponseVO> updateIndividualBusinessAuthority(HttpServletRequest request, HttpServletResponse response,
                                                                                          @Validated(value = {UpdateIndividualAuthority.class}) @NotNull @RequestBody IndividualAuthorityApiRequestVO individualAuthorityApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(authorityService.updateIndividualBusinessAuthority(individualAuthorityApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR1061, "사업 참여 권한 신청(개인) 업데이트 실패")));
    }

    public interface UpdateIndividualAuthority {}

    /**
     * 사업 참여 권한 관련 파일 업로드 API
     *
     * @param request
     * @param response
     * @param userId
     * @param pictureFile
     * @param signFile
     * @return
     */
    @Tag(name = "User Management", description = "유저 정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 참여 권한 관련 파일 업로드 성공", content = @Content(schema = @Schema(implementation = IndividualAuthorityApiResponseVO.class)))})
    @Operation(operationId = "User", summary = "사업 참여 권한 관련 파일 업로드", description = "사업 참여 권한 관련 파일 업로드을 업로드한다.")
    @PutMapping(value = "/individual/upload/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CSResponseVOSupport> fileUpload(HttpServletRequest request, HttpServletResponse response,
        @Parameter(description = "사용자 아이디") @PathVariable(value="userId", required = true) String userId,
        @Parameter(description = "사진파일") @RequestPart(value="pictureFile", required = true) MultipartFile pictureFile,
        @Parameter(description = "서명/도장 파일") @RequestPart(value="signFile", required = true) MultipartFile signFile) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(authorityService.fileUpload(userId, pictureFile, signFile))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR9005, "사업 참여 권한 관련 파일 업로드 실패")));
    }
}