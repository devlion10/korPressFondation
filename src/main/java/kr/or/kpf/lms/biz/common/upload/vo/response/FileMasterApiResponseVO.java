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
@Schema(name="FileMasterApiResponseVO", description="파일 업로드 API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileMasterApiResponseVO extends CSResponseVOSupport {

    @Schema(description="일련번호", example="1")
    private Long fileSn;

    @Schema(description="파일 위치", example="1")
    private String atchFileSn;

    @Schema(description="파일 저장 경로", example="1")
    private String filePath;

    @Schema(description="파일 크기", example="1")
    private BigInteger fileSize;

}