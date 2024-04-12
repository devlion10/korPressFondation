package kr.or.kpf.lms.biz.communication.archive.data.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import kr.or.kpf.lms.repository.entity.LmsDataFile;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Schema(name="ArchiveCustomApiResponseVO", description="자료실 API 관련 응답 객체")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveCustomApiResponseVO extends CSResponseVOSupport {

    @Schema(description="시퀀스 번호")
    private BigInteger sequenceNo;

    @Schema(description="팀 구분")
    private String teamCategory;

    @Schema(description="자료 구분(0: 기타 자료, 1: 교재 자료, 2: 연구/통계 자료, 3: 영상 자료, 4: 사업결과물, 5: 미디어리터러시)")
    private String materialCategory;

    @Schema(description="자료 유형")
    private String materialType;

    @Schema(description="제목")
    private String title;

    @Schema(description="내용")
    private String contents;

    @Schema(description="저자")
    private String author;

    @Schema(description="첨부 파일 크기")
    private Long atchFileSize;

    @Schema(description="첨부 파일 경로")
    private String atchFilePath;

    @Schema(description="썸네일 파일 경로")
    private String thumbFilePath;

    @Schema(description="영상 링크")
    private String mediaFilePath;

    @Schema(description="조회수")
    private BigInteger viewCount;

    @Schema(description="등록일", example="")
    @Convert(converter= DateToStringConverter.class)
    private String createDateTime;

    @Schema(description="등록자 id", example="")
    private String registUserId;

    @Schema(description="등록자명", example="")
    private String userName;

    @Schema(description="신규 여부")
    private Boolean isNew = false;

    @Schema(description="파일 리스트")
    private List<LmsDataFile> lmsDataFiles;

    @Schema(description="미디어리터러시 파일명")
    private String publicationFile;
}
