package kr.or.kpf.lms.biz.educenter.press.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.or.kpf.lms.common.converter.DateToStringConverter;
import kr.or.kpf.lms.common.support.CSResponseVOSupport;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.math.BigInteger;

@Schema(name="PressCustomApiResponseVO", description="행사소개(보도자료) API 관련 응답 객체")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PressCustomApiResponseVO extends CSResponseVOSupport {

    @Schema(description="시퀀스 번호", example="")
    private BigInteger sequenceNo;

    @Schema(description="제목", example="")
    private String title;

    @Schema(description="내용", example="")
    private String contents;

    @Schema(description="첨부 파일 경로", example="")
    private String atchFilePath;

    @Schema(description="첨부 파일 크기", example="")
    private Long atchFileSize;

    @Schema(description="첨부 파일 원본명", example="")
    private String atchFileOrigin;

    @Schema(description="조회수", example="")
    private BigInteger viewCount;

    @Schema(description="등록일", example="")
    @Convert(converter= DateToStringConverter.class)
    private String createDateTime;

    @Schema(description="등록자 id", example="")
    private String registUserId;

    @Schema(description="등록자명", example="")
    private String userName;

    @Schema(description="신규 여부", example="")
    private Boolean isNew = false;

}