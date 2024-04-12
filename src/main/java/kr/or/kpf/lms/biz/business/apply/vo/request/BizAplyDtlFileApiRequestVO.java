package kr.or.kpf.lms.biz.business.apply.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.business.apply.vo.CreateBizAplyDtlFile;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="BizAplyDtlFileApiRequestVO", description="자유형 사업신청서 파일 관리 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BizAplyDtlFileApiRequestVO {
    @Schema(description="파일 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizAplyDtlFile.class}, message="파일 일련번호는 필수 입니다.")
    private Long fileSn;

    @Schema(description="첨부 파일 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateBizAplyDtlFile.class}, message="첨부 파일 일련번호은 필수 입니다.")
    private String atchFileSn;

    @Schema(description="파일 저장 경로", required = true, example="aaa/bbb/")
    @NotEmpty(groups={CreateBizAplyDtlFile.class}, message="파일 저장 경로은 필수 입니다.")
    private String filePath;

    @Schema(description="저장 파일 명", required = true, example="test12345")
    @NotEmpty(groups={CreateBizAplyDtlFile.class}, message="저장 파일명은 필수 입니다.")
    private String fileName;

    @Schema(description="원본 파일 명", required = true, example="test")
    @NotNull(groups={CreateBizAplyDtlFile.class}, message="원본 파일명은 필수 입니다.")
    private String originalFileName;

    @Schema(description="파일 확장자 명", required = true, example="pdf")
    @NotNull(groups={CreateBizAplyDtlFile.class}, message="파일 확장자명은 필수 입니다.")
    private String fileExtension;

    @Schema(description="파일 크기", required = true, example="12.12")
    private BigInteger fileSize;
}
