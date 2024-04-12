package kr.or.kpf.lms.biz.business.pbanc.master.template.controller;

import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.or.kpf.lms.biz.business.pbanc.master.template.service.BizPbancTmpl0ItemService;
import kr.or.kpf.lms.biz.business.pbanc.master.template.service.BizPbancTmpl0TrgtService;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.BizPbancTmpl0ItemApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.request.BizPbancTmpl0TrgtApiRequestVO;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response.BizPbancTmpl0ItemApiResponseVO;
import kr.or.kpf.lms.biz.business.pbanc.master.template.vo.response.BizPbancTmpl0TrgtApiResponseVO;
import kr.or.kpf.lms.common.result.KPF_RESULT;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.common.support.CSViewControllerSupport;
import kr.or.kpf.lms.framework.exception.KPFException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Tag(name = "Business Pbanc Tmpl 0 Item", description = "공고사업 템플릿 Item API")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/business/pbanc/tmpl/0/item")
public class BizPbancTmpl0ItemApiController extends CSViewControllerSupport {
    private final BizPbancTmpl0ItemService bizPbancTmpl0ItemService;

    /**
     * 공고사업 템플릿 생성
     *
     * @param request
     * @param response
     * @param bizPbancTmpl0ItemApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl 0 Item", description = "공고사업 템플릿 Item API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 Item 생성 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl0ItemApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl", summary = "사업 공고 템플릿 Item 생성", description = "사업 공고 템플릿 Item 생성한다.")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl0ItemApiResponseVO> createInfoTmpl0Item(HttpServletRequest request, HttpServletResponse response,
                                                                              @NotNull @RequestBody BizPbancTmpl0ItemApiRequestVO bizPbancTmpl0ItemApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmpl0ItemService.createBizPbancTmpl0Item(bizPbancTmpl0ItemApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3501, "사업 공고 템플릿 Item 생성 실패")));
    }

    /**
     * 공고사업 템플릿 업데이트
     *
     * @param request
     * @param response
     * @param bizPbancTmpl0ItemApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl 0 Item", description = "공고사업 템플릿 Item API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 Item 수정 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl0ItemApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl Item", summary = "사업 공고 템플릿 Item 업데이트", description = "사업 공고 템플릿 Item 업데이트한다.")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BizPbancTmpl0ItemApiResponseVO> updateInfoTmpl0Item(HttpServletRequest request, HttpServletResponse response,
                                                            @NotNull @RequestBody BizPbancTmpl0ItemApiRequestVO bizPbancTmpl0ItemApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmpl0ItemService.updateBizPbancTmpl0Item(bizPbancTmpl0ItemApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3503, "사업 공고 템플릿 Item 수정 실패")));
    }

    /**
     * 공고사업 템플릿 삭제
     *
     * @param request
     * @param response
     * @param bizPbancTmpl0ItemApiRequestVO
     * @return
     */
    @Tag(name = "Business Pbanc Tmpl 0 Item", description = "공고사업 템플릿 Item API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사업 공고 템플릿 Item 삭제 성공", content = @Content(schema = @Schema(implementation = BizPbancTmpl0ItemApiResponseVO.class)))})
    @Operation(operationId="Business Pbanc Tmpl Item", summary = "사업 공고 템플릿 Item 삭제", description = "사업 공고 템플릿 Item 삭제 한다.")
    @DeleteMapping(value = "/delete/{bizPbancTemp0ItemNo}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CSResponseVOSupport> deleteInfoTmpl0Item(HttpServletRequest request, HttpServletResponse response,
                                                              @NotNull @RequestBody BizPbancTmpl0ItemApiRequestVO bizPbancTmpl0ItemApiRequestVO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Optional.ofNullable(bizPbancTmpl0ItemService.deleteBizPbancTmpl0Item(bizPbancTmpl0ItemApiRequestVO))
                        .orElseThrow(() -> new KPFException(KPF_RESULT.ERROR3504, "사업 공고 템플릿 Trgt 삭제 실패")));
    }

}
