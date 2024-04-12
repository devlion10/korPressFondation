package kr.or.kpf.lms.biz.communication.archive.data.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.biz.communication.archive.data.vo.CreateArchive;
import kr.or.kpf.lms.biz.communication.archive.data.vo.UpdateArchive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Schema(name="ArchiveApiRequestVO", description="자료실 API 관련 요청 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArchiveApiRequestVO {

    /** 시퀀스 번호 */
    @Schema(description="시퀀스 번호", required=true, example="")
    @NotNull(groups={CreateArchive.class, UpdateArchive.class}, message="시퀀스 번호는 필수 입니다.")
    private BigInteger sequenceNo;

    /** 팀 구분 */
    @Schema(description="팀 구분", required=true, example="")
    @NotEmpty(groups={CreateArchive.class, UpdateArchive.class}, message="팀 구분은 필수 입니다.")
    private String teamCategory;

    /** 자료 구분 */
    @Schema(description="자료 구분", required=true, example="")
    @NotEmpty(groups={CreateArchive.class, UpdateArchive.class}, message="자료 구분은 필수 입니다.")
    private String materialCategory;

    /** 자료 유형 */
    @Schema(description="자료 유형", required=true, example="")
    @NotEmpty(groups={CreateArchive.class, UpdateArchive.class}, message="자료 유형은 필수 입니다.")
    private String materialType;

    /** 제목 */
    @Schema(description="제목", required=true, example="")
    @NotEmpty(groups={CreateArchive.class, UpdateArchive.class}, message="제목은 필수 입니다.")
    private String title;

    /** 내용 */
    @Schema(description="내용", required=true, example="")
    @NotEmpty(groups={CreateArchive.class, UpdateArchive.class}, message="내용은 필수 입니다.")
    private String contents;

    /** 저자 */
    @Schema(description="저자", required=false, example="")
    private String author;

    /** 첨부 파일 크기 */
    @Schema(description="첨부 파일 크기", required=false, example="")
    private Long atchFileSize;

    /** 첨부 파일 경로 */
    @Schema(description="첨부 파일 경로", required=false, example="")
    private String atchFilePath;

    /** 썸네일 파일 경로 */
    @Schema(description="썸네일 파일 경로", required=false, example="")
    private String thumbFilePath;

    /** 영상 링크 */
    @Schema(description="영상 링크", required=false, example="")
    private String mediaFilePath;

    /** 조회수 */
    @Schema(description="조회수", required=true, example="")
    @NotEmpty(groups={CreateArchive.class, UpdateArchive.class}, message="조회수는 필수 입니다.")
    private BigInteger viewCount;
}
