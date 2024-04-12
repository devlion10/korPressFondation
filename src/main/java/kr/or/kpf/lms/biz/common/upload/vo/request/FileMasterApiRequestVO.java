package kr.or.kpf.lms.biz.common.upload.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.common.upload.vo.CreateFileMaster;
import kr.or.kpf.lms.biz.common.upload.vo.UpdateFileMaster;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="FileMasterApiRequestVO", description="업로드 파일 관리 API 관련 요청 객체")
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class FileMasterApiRequestVO {
    @Schema(description="파일 일련번호", required = true, example="1")
    @NotEmpty(groups={UpdateFileMaster.class}, message="파일 일련번호는 필수 입니다.")
    private Long fileSn;

    @Schema(description="첨부 파일 일련번호", required = true, example="1")
    @NotEmpty(groups={CreateFileMaster.class, UpdateFileMaster.class}, message="첨부 파일 일련번호은 필수 입니다.")
    private String atchFileSn;

    @Schema(description="파일 저장 경로", required = true, example="aaa/bbb/")
    @NotEmpty(groups={CreateFileMaster.class, UpdateFileMaster.class}, message="파일 저장 경로은 필수 입니다.")
    private String filePath;

    @Schema(description="저장 파일 명", required = true, example="test12345")
    @NotEmpty(groups={CreateFileMaster.class, UpdateFileMaster.class}, message="저장 파일명은 필수 입니다.")
    private String fileName;

    @Schema(description="원본 파일 명", required = true, example="test")
    @NotNull(groups={CreateFileMaster.class, UpdateFileMaster.class}, message="원본 파일명은 필수 입니다.")
    private String originalFileName;

    @Schema(description="파일 확장자 명", required = true, example="pdf")
    @NotNull(groups={CreateFileMaster.class, UpdateFileMaster.class}, message="파일 확장자명은 필수 입니다.")
    private String fileExtension;

    @Schema(description="파일 내용", required = true, example="파일 내용")
    private String fileComments;

    @Schema(description="파일 크기", required = true, example="12.12")
    private BigInteger fileSize;

    @Schema(description="파일 전송 키 값", required = false, example="")
    private String fileKeyValue;

}
