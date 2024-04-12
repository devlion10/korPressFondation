package kr.or.kpf.lms.biz.common.upload.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * 파일 업로드 관련 응답 객체
 */
@Schema(name="FileMasterViewResponseVO", description="파일 업로드 VIEW 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMasterViewResponseVO extends CSResponseVOSupport {

    @Schema(description="파일 일련번호", example="1")
    private Long fileSn;
}